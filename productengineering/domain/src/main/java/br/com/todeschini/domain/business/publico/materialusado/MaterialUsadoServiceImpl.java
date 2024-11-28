package br.com.todeschini.domain.business.publico.materialusado;

import br.com.todeschini.domain.ConversaoValores;
import br.com.todeschini.domain.Convertable;
import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.materialusado.api.MaterialUsadoService;
import br.com.todeschini.domain.business.publico.materialusado.spi.CrudMaterialUsado;
import br.com.todeschini.domain.exceptions.BadRequestException;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.BatchEditable;
import br.com.todeschini.domain.metadata.DomainService;
import br.com.todeschini.domain.metadata.Entidade;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@DomainService
public class MaterialUsadoServiceImpl implements MaterialUsadoService {

    private final CrudMaterialUsado crudMaterialUsado;
    private final ConversaoValores conversaoValores;

    public MaterialUsadoServiceImpl(CrudMaterialUsado crudMaterialUsado, ConversaoValores conversaoValores) {
        this.crudMaterialUsado = crudMaterialUsado;
        this.conversaoValores = conversaoValores;
    }

    @Override
    public Paged<DMaterialUsado> buscar(PageableRequest request) {
        return crudMaterialUsado.buscarTodos(request);
    }

    @Override
    public DMaterialUsado buscar(Integer id) {
        return crudMaterialUsado.buscar(id);
    }

    @Override
    public List<DHistory<DMaterialUsado>> buscarHistorico(Integer id) {
        return crudMaterialUsado.buscarHistorico(id);
    }

    @Override
    public List<String> buscarAtributosEditaveisEmLote() {
        return crudMaterialUsado.buscarAtributosEditaveisEmLote();
    }

    @Override
    public DMaterialUsado incluir(DMaterialUsado domain) {
        domain.validar();
        validarRegistroDuplicado(domain);
        return crudMaterialUsado.inserir(domain);
    }

    @Override
    public DMaterialUsado atualizar(DMaterialUsado domain) {
        domain.validar();
        validarRegistroDuplicado(domain);
        return crudMaterialUsado.atualizar(domain);
    }

    @Override
    public List<DMaterialUsado> atualizarEmLote(List<Integer> codigos, List<String> atributos, List<Object> valores) {
        if (atributos.size() != valores.size()) {
            throw new BadRequestException("O número de atributos e valores deve ser igual.");
        }

        List<DMaterialUsado> lista = codigos.stream()
                .map(this::buscar)
                .toList();

        for (DMaterialUsado obj : lista) {
            for (int i = 0; i < atributos.size(); i++) {
                String nomeAtributo = atributos.get(i);
                Object valorAtributo = valores.get(i);

                Field field;
                try {
                    field = conversaoValores.buscarCampoNaHierarquia(DMaterialUsado.class, nomeAtributo);
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

        return crudMaterialUsado.atualizarEmLote(lista);
    }

    @Override
    public DMaterialUsado substituirPorVersaoAntiga(Integer id, Integer versionId) {
        return crudMaterialUsado.substituirPorVersaoAntiga(id, versionId);
    }

    @Override
    public void inativar(Integer id) {
        crudMaterialUsado.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudMaterialUsado.remover(id);
    }

    private void validarRegistroDuplicado(DMaterialUsado domain){
        if(crudMaterialUsado.pesquisarPorFilhoEMaterial(domain.getFilho().getCodigo(), domain.getMaterial().getCodigo())
                .stream()
                .anyMatch(t -> !t.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Filho e Material.");
        }
    }
}
