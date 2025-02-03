package br.com.todeschini.domain.business.publico.modelo;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.publico.filho.DFilho;
import br.com.todeschini.domain.business.publico.filho.api.FilhoService;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.modelo.api.ModeloService;
import br.com.todeschini.domain.business.publico.modelo.spi.CrudModelo;
import br.com.todeschini.domain.business.publico.pai.DPai;
import br.com.todeschini.domain.business.publico.pai.api.PaiService;
import br.com.todeschini.domain.exceptions.RegistroDuplicadoException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class ModeloServiceImpl implements ModeloService {

    private final CrudModelo crudModelo;
    private final PaiService paiService;
    private final FilhoService filhoService;

    public ModeloServiceImpl(CrudModelo crudModelo, PaiService paiService, FilhoService filhoService) {
        this.crudModelo = crudModelo;
        this.paiService = paiService;
        this.filhoService = filhoService;
    }

    @Override
    public Paged<DModelo> buscar(PageableRequest request) {
        return crudModelo.buscarTodos(request);
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

        String descricaoAntiga = buscar(domain.getCodigo()).getDescricao();
        String descricaoNova = domain.getDescricao();

        DModelo obj = crudModelo.atualizar(domain);

        if(!descricaoAntiga.equals(descricaoNova)){
            List<DPai> pais = paiService.buscarPorModelo(domain.getCodigo());

            for(DPai pai : pais){
                if(pai.getDescricao().contains(descricaoAntiga)){
                    pai.setDescricao(pai.getDescricao().replace(descricaoAntiga, descricaoNova));
                    paiService.atualizar(pai);
                }

                for(DFilho filho : pai.getFilhos()) {
                    if(filho.getDescricao().contains(descricaoAntiga)){
                        filho.setDescricao(filho.getDescricao().replace(descricaoAntiga, descricaoNova));
                        filhoService.atualizar(filho);
                    }

                    for(DFilho filhoFilho : filho.getFilhos()) {
                        if(filhoFilho.getDescricao().contains(descricaoAntiga)){
                            filhoFilho.setDescricao(filhoFilho.getDescricao().replace(descricaoAntiga, descricaoNova));
                            filhoService.atualizar(filhoFilho);
                        }
                    }
                }
            }
        }

        return obj;
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
