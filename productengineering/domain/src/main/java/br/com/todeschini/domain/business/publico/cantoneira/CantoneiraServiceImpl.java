package br.com.todeschini.domain.business.publico.cantoneira;

import br.com.todeschini.domain.ConversaoValores;
import br.com.todeschini.domain.Convertable;
import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.publico.cantoneira.api.CantoneiraService;
import br.com.todeschini.domain.business.publico.cantoneira.spi.CrudCantoneira;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.exceptions.BadRequestException;
import br.com.todeschini.domain.exceptions.RegistroDuplicadoException;
import br.com.todeschini.domain.metadata.BatchEditable;
import br.com.todeschini.domain.metadata.DomainService;
import br.com.todeschini.domain.metadata.Entidade;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@DomainService
public class CantoneiraServiceImpl implements CantoneiraService {

    private final CrudCantoneira crudCantoneira;
    private final ConversaoValores conversaoValores;

    public CantoneiraServiceImpl(CrudCantoneira crudCantoneira, ConversaoValores conversaoValores) {
        this.crudCantoneira = crudCantoneira;
        this.conversaoValores = conversaoValores;
    }

    @Override
    public Paged<DCantoneira> buscar(PageableRequest request) {
        return crudCantoneira.buscarTodos(request);
    }

    @Override
    public DCantoneira buscar(Integer id) {
        return crudCantoneira.buscar(id);
    }

    @Override
    public List<DHistory<DCantoneira>> buscarHistorico(Integer id) {
        return crudCantoneira.buscarHistorico(id);
    }

    @Override
    public List<String> buscarAtributosEditaveisEmLote() {
        return crudCantoneira.buscarAtributosEditaveisEmLote();
    }

    @Override
    public DCantoneira incluir(DCantoneira domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudCantoneira.inserir(domain);
    }

    @Override
    public DCantoneira atualizar(DCantoneira domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudCantoneira.atualizar(domain);
    }

    @Override
    public List<DCantoneira> atualizarEmLote(List<Integer> codigos, List<String> atributos, List<Object> valores) {
        if (atributos.size() != valores.size()) {
            throw new BadRequestException("O número de atributos e valores deve ser igual.");
        }

        List<DCantoneira> lista = codigos.stream()
                .map(this::buscar)
                .toList();

        for (DCantoneira obj : lista) {
            for (int i = 0; i < atributos.size(); i++) {
                String nomeAtributo = atributos.get(i);
                Object valorAtributo = valores.get(i);

                Field field;
                try {
                    field = conversaoValores.buscarCampoNaHierarquia(DCantoneira.class, nomeAtributo);
                    field.setAccessible(true);

                    if (field.isAnnotationPresent(BatchEditable.class)) {
                        Object valorConvertido = conversaoValores.convertValor(field.getType(), valorAtributo); // converte o Object para seu tipo de dado específico

                        // verificar se o tipo do valor convertido é uma entidade mapeada
                        if (valorConvertido != null && valorConvertido.getClass().isAnnotationPresent(Entidade.class)) {
                            // invocar o adaptador com base na entidade
                            Convertable<Object, Object> adapter = (Convertable<Object, Object>) conversaoValores.findAdapterForEntity(valorConvertido.getClass());
                            if (adapter != null) {
                                valorConvertido = adapter.toDomain(valorConvertido); // converte a entidade para o domínio
                            } else {
                                throw new BadRequestException("Nenhum adaptador encontrado para a entidade: " + valorConvertido.getClass().getName());
                            }
                        }

                        field.set(obj, valorConvertido);
                    } else {
                        throw new BadRequestException("O atributo " + nomeAtributo + " não pode ser editado em lote.");
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new BadRequestException("Erro ao tentar acessar o atributo " + nomeAtributo);
                }
            }
            atualizar(obj);
        }

        return crudCantoneira.atualizarEmLote(lista);
    }

    @Override
    public DCantoneira substituirPorVersaoAntiga(Integer id, Integer versionId) {
        return crudCantoneira.substituirPorVersaoAntiga(id, versionId);
    }

    @Override
    public void inativar(Integer id) {
        crudCantoneira.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudCantoneira.remover(id);
    }

    private void validarRegistroDuplicado(DCantoneira domain){
        if(crudCantoneira.pesquisarPorDescricao(domain.getDescricao())
                .stream()
                .anyMatch(t -> !t.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1)))){
            throw new RegistroDuplicadoException("Verifique o campo descrição.");
        }
    }
}
