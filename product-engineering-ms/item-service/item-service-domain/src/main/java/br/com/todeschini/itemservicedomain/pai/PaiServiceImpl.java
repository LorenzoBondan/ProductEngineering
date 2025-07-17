package br.com.todeschini.itemservicedomain.pai;

import br.com.todeschini.itemservicedomain.acessorio.api.AcessorioService;
import br.com.todeschini.itemservicedomain.acessoriousado.DAcessorioUsado;
import br.com.todeschini.itemservicedomain.acessoriousado.api.AcessorioUsadoService;
import br.com.todeschini.itemservicedomain.categoriacomponente.api.CategoriaComponenteService;
import br.com.todeschini.itemservicedomain.cor.DCor;
import br.com.todeschini.itemservicedomain.cor.api.CorService;
import br.com.todeschini.itemservicedomain.enums.DTipoFilhoEnum;
import br.com.todeschini.itemservicedomain.enums.DTipoMaterialEnum;
import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.filho.api.FilhoService;
import br.com.todeschini.itemservicedomain.material.DMaterial;
import br.com.todeschini.itemservicedomain.material.api.MaterialService;
import br.com.todeschini.itemservicedomain.medidas.DMedidas;
import br.com.todeschini.itemservicedomain.medidas.api.MedidasService;
import br.com.todeschini.itemservicedomain.modelo.api.ModeloService;
import br.com.todeschini.itemservicedomain.pai.api.PaiService;
import br.com.todeschini.itemservicedomain.pai.montadores.DAcessorioQuantidade;
import br.com.todeschini.itemservicedomain.pai.montadores.DItemModulacao;
import br.com.todeschini.itemservicedomain.pai.montadores.DMontadorEstruturaPai;
import br.com.todeschini.itemservicedomain.pai.montadores.DMontadorEstruturaPaiModulacao;
import br.com.todeschini.itemservicedomain.pai.spi.CrudPai;
import br.com.todeschini.itemservicedomain.processadores.MaterialProcessador;
import br.com.todeschini.itemservicedomain.processadores.MaterialProcessadorFactory;
import br.com.todeschini.itemservicedomain.roteiroservice.DMaquina;
import br.com.todeschini.itemservicedomain.roteiroservice.DRoteiro;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.DuplicatedResourceException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@DomainService
@RequiredArgsConstructor
public class PaiServiceImpl implements PaiService {

    private final CrudPai crudPai;
    private final ModeloService modeloService;
    private final CategoriaComponenteService categoriaComponenteService;
    private final FilhoService filhoService;
    private final MedidasService medidasService;
    private final MaterialService materialService;
    /*private final RoteiroService roteiroService;
    private final MaquinaService maquinaService;
    private final RoteiroMaquinaService roteiroMaquinaService;*/
    private final CorService corService;
    private final AcessorioService acessorioService;
    private final AcessorioUsadoService acessorioUsadoService;
    private final MaterialProcessadorFactory materialProcessadorFactory;

    @Override
    public Paged<DPai> buscar(PageableRequest request) {
        return crudPai.buscar(request);
    }

    @Override
    public List<DPai> buscarPorModelo(Integer cdmodelo) {
        return crudPai.buscarPorModelo(cdmodelo);
    }

    @Override
    public List<DPai> buscarPorCategoriaComponente(Integer cdcategoriaComponente) {
        return crudPai.buscarPorCategoriaComponente(cdcategoriaComponente);
    }

    @Override
    public DPai buscar(Integer id) {
        return crudPai.buscar(id);
    }

    @Override
    public DPai incluir(DPai domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudPai.incluir(domain);
    }

    @Override
    public DPai atualizar(DPai domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudPai.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudPai.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudPai.excluir(id);
    }

    @Override
    public DPai montarEstrutura(DMontadorEstruturaPai montadorEstruturaPai) {
        DPai pai = montarPai(montadorEstruturaPai);
        validarRegistroDuplicado(pai);
        pai = incluir(pai);
        processarMedidasEFilhos(montadorEstruturaPai, pai);
        return atualizar(pai);
    }

    @Override
    public DPai montarEstruturaModulacao(DMontadorEstruturaPaiModulacao montadorEstruturaPaiModulacao) {
        DMontadorEstruturaPai montadorEstruturaPai = new DMontadorEstruturaPai(
                montadorEstruturaPaiModulacao.getPaiPrincipal().getModelo(),
                montadorEstruturaPaiModulacao.getPaiPrincipal().getCategoriaComponente(),
                montadorEstruturaPaiModulacao.getCores(),
                new ArrayList<>(Collections.singletonList(montadorEstruturaPaiModulacao.getMedidasPaiPrincipal())),
                montadorEstruturaPaiModulacao.getImplantacao(),
                DTipoFilhoEnum.MDP
        );
        DPai paiPrincipal = montarPai(montadorEstruturaPai);
        paiPrincipal = incluir(paiPrincipal);
        processarMedidasEFilhosPaiPrincipalModulacao(montadorEstruturaPai, paiPrincipal);

        Map<String, DPai> paisJaCriados = new HashMap<>();

        for (int i = 0; i < montadorEstruturaPaiModulacao.getCores().size(); i++) {
            // Processa os pais secundários
            for (DItemModulacao paiSecundario : montadorEstruturaPaiModulacao.getPaisSecundarios()) {
                // Cria a lista mutável de cor e medidas para cada pai secundário
                montadorEstruturaPai = new DMontadorEstruturaPai(
                        paiSecundario.getPai().getModelo(),
                        paiSecundario.getPai().getCategoriaComponente(),
                        new ArrayList<>(Collections.singletonList(montadorEstruturaPaiModulacao.getCores().get(i))),
                        new ArrayList<>(Collections.singletonList(paiSecundario.getMedidas())),
                        montadorEstruturaPaiModulacao.getMateriais(),
                        paiSecundario.getMaquinas(),
                        montadorEstruturaPaiModulacao.getImplantacao(),
                        DTipoFilhoEnum.MDP,
                        paiSecundario.getPai().getBordasComprimento(),
                        paiSecundario.getPai().getBordasLargura(),
                        paiSecundario.getPai().getPlasticoAcima(),
                        paiSecundario.getPai().getPlasticoAdicional(),
                        paiSecundario.getPai().getLarguraPlastico(),
                        paiSecundario.getPai().getNumeroCantoneiras(),
                        paiSecundario.getPai().getTntUmaFace(),
                        null,
                        paiSecundario.getPai().getFaces(),
                        null
                );

                // Monta a estrutura para o pai secundário e adiciona à lista de filhos do pai principal
                DPai paiFilho;
                String descricao = categoriaComponenteService.buscar(montadorEstruturaPai.getCategoriaComponente().getCodigo()).getDescricao()
                        + " " + modeloService.buscar(montadorEstruturaPai.getModelo().getCodigo()).getDescricao();
                if(paisJaCriados.containsKey(descricao)) {
                    paiFilho = paisJaCriados.get(descricao);
                    processarMedidasEFilhos(montadorEstruturaPai, paiFilho);
                } else {
                    paiFilho = montarEstrutura(montadorEstruturaPai);
                    paisJaCriados.put(paiFilho.getDescricao(), paiFilho);
                }

                paiFilho.getFilhos().get(i).calcularValor();
                paiPrincipal.getFilhos().get(i).getFilhos().add(paiFilho.getFilhos().get(i));
                paiPrincipal.getFilhos().get(i).calcularValor();

                for(DAcessorioQuantidade acessorioQuantidade : montadorEstruturaPaiModulacao.getAcessoriosQuantidades()){
                    DAcessorioUsado acessorioUsado = new DAcessorioUsado();
                    acessorioUsado.setAcessorio(acessorioService.buscar(acessorioQuantidade.getAcessorio().getCodigo()));
                    acessorioUsado.setFilho(paiFilho.getFilhos().get(i));
                    acessorioUsado.setQuantidade(acessorioQuantidade.getQuantidade());
                    acessorioUsado.calcularValor();
                    acessorioUsado = acessorioUsadoService.incluir(acessorioUsado);
                    paiFilho.getFilhos().get(i).getAcessoriosUsados().add(acessorioUsado);
                }

                filhoService.atualizar(paiFilho.getFilhos().get(i));
                filhoService.atualizar(paiPrincipal.getFilhos().get(i));
            }
        }

        return atualizar(paiPrincipal);
    }

    private void validarRegistroDuplicado(DPai domain){
        Collection<DPai> registrosExistentes = crudPai.pesquisarPorDescricao(domain.getDescricao());

        for (DPai existente : registrosExistentes) {
            if (!existente.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1))) {
                if (DSituacaoEnum.ATIVO.equals(existente.getSituacao())) {
                    throw new DuplicatedResourceException("Verifique o campo descrição.");
                } else if (DSituacaoEnum.INATIVO.equals(existente.getSituacao())){
                    throw new DuplicatedResourceException("Já existe um registro inativo com essa descrição. Reative-o antes de criar um novo.");
                } else if (DSituacaoEnum.LIXEIRA.equals(existente.getSituacao())){
                    throw new DuplicatedResourceException("Já existe um registro com essa descrição na lixeira. Reative-o antes de criar um novo.");
                }
            }
        }
    }

    private DPai montarPai(DMontadorEstruturaPai montadorEstruturaPai) {
        DPai pai = DPai.builder()
                .modelo(modeloService.buscar(montadorEstruturaPai.getModelo().getCodigo()))
                .categoriaComponente(categoriaComponenteService.buscar(montadorEstruturaPai.getCategoriaComponente().getCodigo()))
                .bordasComprimento(montadorEstruturaPai.getBordasComprimento())
                .bordasLargura(montadorEstruturaPai.getBordasLargura())
                .numeroCantoneiras(montadorEstruturaPai.getNumeroCantoneiras())
                .tntUmaFace(montadorEstruturaPai.getTntUmaFace())
                .plasticoAcima(montadorEstruturaPai.getPlasticoAcima())
                .plasticoAdicional(montadorEstruturaPai.getPlasticoAdicional())
                .larguraPlastico(montadorEstruturaPai.getLarguraPlastico())
                .tipoPintura(montadorEstruturaPai.getTipoPintura())
                .faces(montadorEstruturaPai.getFaces())
                .especial(montadorEstruturaPai.getEspecial())
                .filhos(new ArrayList<>())
                .build();
        pai.gerarDescricao();
        pai.validar();
        return pai;
    }

    private void processarMedidasEFilhos(DMontadorEstruturaPai montadorEstruturaPai, DPai pai) {
        for (DMedidas medida : montadorEstruturaPai.getMedidas()) {
            medida = verificarOuIncluirMedida(medida, montadorEstruturaPai);

            for (DCor cor : montadorEstruturaPai.getCores()) {
                DFilho filho = filhoService.incluir(criarFilho(pai, pai.getDescricao(),
                        cor, medida, montadorEstruturaPai.getImplantacao(), montadorEstruturaPai.getTipoFilho()));

                processarFilhoComMateriais(montadorEstruturaPai, filho);
                processarRoteiro(filho, pai.getDescricao(), medida, montadorEstruturaPai.getMaquinas(), montadorEstruturaPai.getImplantacao());
                filhoService.atualizar(filho);
                pai.getFilhos().add(filho);
            }
        }
    }

    private DMedidas verificarOuIncluirMedida(DMedidas medida, DMontadorEstruturaPai montadorEstruturaPai) {
        List<DMedidas> medidaExistente = medidasService.buscarPorAlturaELarguraEEspessura(
                medida.getAltura(), medida.getLargura(), medida.getEspessura());

        if (!medidaExistente.isEmpty()) {
            medida.setCodigo(medidaExistente.iterator().next().getCodigo());
            medida.setSituacao(DSituacaoEnum.valueOf(medidaExistente.iterator().next().getSituacao().name()));
        } else {
            montadorEstruturaPai.getMedidas().remove(medida);
            medida = medidasService.incluir(medida);
            montadorEstruturaPai.getMedidas().add(medida);
        }

        return medida;
    }

    private void processarFilhoComMateriais(DMontadorEstruturaPai montadorEstruturaPai, DFilho filho) {
        if (filho.getTipo().equals(DTipoFilhoEnum.MDP)) {
            processarMateriaisMDP(filho, montadorEstruturaPai.getMateriais());
        } else if (filho.getTipo().equals(DTipoFilhoEnum.MDF)) {
            processarMateriaisMDF(filho, montadorEstruturaPai.getMateriais());
        }
    }

    private DFilho criarFilho(DPai pai, String descricao, DCor cor, DMedidas medida, LocalDate implantacao,
                              DTipoFilhoEnum tipoFilho) {
        return DFilho.builder()
                .codigo(null)
                .descricao(descricao)
                .pai(pai)
                .cor(cor)
                .medidas(medida)
                .roteiro(null)
                .unidadeMedida("UN")
                .implantacao(implantacao)
                .valor(null)
                .tipo(tipoFilho)
                .materiaisUsados(new ArrayList<>())
                .filhos(new ArrayList<>())
                .acessoriosUsados(new ArrayList<>())
                .build();
    }

    // vai para mdp-service
    private void processarMateriaisMDP(DFilho filho, List<DMaterial> materiais) {
        // Materiais acrescentados automaticamente com base em cores e medidas
        MaterialProcessador chapaMDPProcessador = materialProcessadorFactory.getProcessador(DTipoMaterialEnum.CHAPA_MDP.toString());
        chapaMDPProcessador.processarMaterial(filho, null);
        MaterialProcessador fitaBordaProcessador = materialProcessadorFactory.getProcessador(DTipoMaterialEnum.FITA_BORDA.toString());
        fitaBordaProcessador.processarMaterial(filho, null);

        // Materiais acrescentados via configurador por código
        for (DMaterial material : materiais) {
            material = materialService.buscar(material.getCodigo());
            MaterialProcessador processador = materialProcessadorFactory.getProcessador(material.getTipoMaterial().name());
            processador.processarMaterial(filho, material);
        }
    }

    // vai para mdf-service
    private void processarMateriaisMDF(DFilho filho, List<DMaterial> materiais) {
        // Processa o fundo
        DFilho fundo = processarFundo(filho);
        filho.getFilhos().add(fundo);

        // Materiais acrescentados automaticamente com base em cores e medidas
        MaterialProcessador pinturaProcessador = materialProcessadorFactory.getProcessador(DTipoMaterialEnum.PINTURA.toString());
        pinturaProcessador.processarMaterial(filho, null);

        // Materiais acrescentados via configurador por código
        for (DMaterial material : materiais) {
            material = materialService.buscar(material.getCodigo());
            MaterialProcessador processador = materialProcessadorFactory.getProcessador(material.getTipoMaterial().name());
            processador.processarMaterial(filho, material);
        }
    }

    // vai para mdf-service
    private DFilho processarFundo(DFilho filho) {
        String descricao = "Fundo " + filho.getDescricao() + " " + filho.getMedidas().getAltura() + "X" + filho.getMedidas().getLargura() + "X" + filho.getMedidas().getEspessura();
        List<DFilho> fundos = filhoService.pesquisarPorDescricaoEMedidas(descricao, filho.getMedidas().getCodigo());
        DFilho fundo;

        if (fundos.isEmpty()) {
            fundo = filhoService.incluir(criarFilho(filho.getPai(), descricao,
                    corService.buscar(4), filho.getMedidas(), filho.getImplantacao(), DTipoFilhoEnum.FUNDO));
            // colocar a chapa dentro do fundo
            MaterialProcessador chapaMDFProcessador = materialProcessadorFactory.getProcessador(DTipoMaterialEnum.CHAPA_MDF.toString());
            chapaMDFProcessador.processarMaterial(fundo, null);
            fundo.calcularValor();
            filhoService.atualizar(fundo);
        } else {
            fundo = fundos.get(0);
        }

        return fundo;
    }

    // vai para roteiro-service
    private void processarRoteiro(DFilho filho, String descricao, DMedidas medida, List<DMaquina> maquinas, LocalDate implantacao) {
        /*String roteiroDescricao = descricao + " - " + medida.getAltura() + "X" + medida.getLargura() + "X" + medida.getEspessura();
        DRoteiro roteiro = roteiroService.existePorDescricao(roteiroDescricao)
                ? roteiroService.buscarPorDescricao(roteiroDescricao).iterator().next()
                : criarNovoRoteiro(roteiroDescricao, medida, maquinas, implantacao);

        filho.setRoteiro(roteiro);*/
    }

    // vai para roteiro-service
    private DRoteiro criarNovoRoteiro(String descricao, DMedidas medida, List<DMaquina> maquinas, LocalDate implantacao) {
        /*DRoteiro roteiro = new DRoteiro();
        roteiro.setDescricao(descricao);
        roteiro.setImplantacao(implantacao);
        roteiro = roteiroService.incluir(roteiro);

        for (DMaquina maquina : maquinas) {
            maquina = maquinaService.buscar(maquina.getCodigo());
            DRoteiroMaquina roteiroMaquina = new DRoteiroMaquina();
            roteiroMaquina.setRoteiro(roteiro);
            roteiroMaquina.setMaquina(maquina);
            roteiroMaquina.calcularTempo(medida.getAltura(), medida.getLargura(), medida.getEspessura());
            roteiroMaquina.setUnidadeMedida("MIN");
            roteiroMaquina = roteiroMaquinaService.incluir(roteiroMaquina);
            roteiro.getRoteiroMaquinas().add(roteiroMaquina);
        }

        return roteiroService.buscar(roteiro.getCodigo());*/
        return null;
    }

    private void processarMedidasEFilhosPaiPrincipalModulacao(DMontadorEstruturaPai montadorEstruturaPai, DPai pai) {
        for (DMedidas medida : montadorEstruturaPai.getMedidas()) {
            medida = verificarOuIncluirMedida(medida, montadorEstruturaPai);

            for (DCor cor : montadorEstruturaPai.getCores()) {
                DFilho filho = filhoService.incluir(criarFilho(pai, pai.getDescricao(),
                        cor, medida, montadorEstruturaPai.getImplantacao(), montadorEstruturaPai.getTipoFilho()));

                filhoService.atualizar(filho);
                pai.getFilhos().add(filho);
            }
        }
    }
}