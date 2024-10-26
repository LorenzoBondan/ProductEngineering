package br.com.todeschini.domain.business.publico.modelo;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.modelo.api.ModeloService;
import br.com.todeschini.domain.business.publico.modelo.spi.CrudModelo;
import br.com.todeschini.domain.exceptions.RegistroDuplicadoException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class ModeloServiceImpl implements ModeloService {

    private final CrudModelo crudModelo;

    public ModeloServiceImpl(CrudModelo crudModelo) {
        this.crudModelo = crudModelo;
    }

    @Override
    public Paged<DModelo> buscar(PageableRequest request) {
        return crudModelo.buscarTodos(request);
    }

    @Override
    public List<DModelo> buscarTodosMaisAtual(Integer id) {
        return crudModelo.buscarTodosAtivosMaisAtual(id);
    }

    @Override
    public DModelo buscar(Integer id) {
        return crudModelo.buscar(id);
    }

    @Override
    public List<DHistory<DModelo>> buscarHistorico(Integer id) {
        return crudModelo.buscarHistorico(id);
    }

    @Override
    public DModelo incluir(DModelo domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudModelo.inserir(domain);
    }

    @Override
    public DModelo atualizar(DModelo domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudModelo.atualizar(domain);
    }

    @Override
    public DModelo substituirPorVersaoAntiga(Integer id, Integer versionId) {
        return crudModelo.substituirPorVersaoAntiga(id, versionId);
    }

    @Override
    public void inativar(Integer id) {
        crudModelo.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudModelo.remover(id);
    }

    private void validarRegistroDuplicado(DModelo domain){
        if(crudModelo.pesquisarPorDescricao(domain.getDescricao())
                .stream()
                .anyMatch(t -> !t.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1)))){
            throw new RegistroDuplicadoException("Verifique o campo descrição.");
        }
    }
}
