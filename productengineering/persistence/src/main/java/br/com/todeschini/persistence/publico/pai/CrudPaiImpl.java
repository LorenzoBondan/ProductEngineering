package br.com.todeschini.persistence.publico.pai;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.PagedBuilder;
import br.com.todeschini.domain.business.enums.DSituacaoEnum;
import br.com.todeschini.domain.business.enums.DTipoFilhoEnum;
import br.com.todeschini.domain.business.publico.acessorio.api.AcessorioService;
import br.com.todeschini.domain.business.publico.acessoriousado.DAcessorioUsado;
import br.com.todeschini.domain.business.publico.acessoriousado.api.AcessorioUsadoService;
import br.com.todeschini.domain.business.publico.categoriacomponente.api.CategoriaComponenteService;
import br.com.todeschini.domain.business.publico.cor.DCor;
import br.com.todeschini.domain.business.publico.cor.api.CorService;
import br.com.todeschini.domain.business.publico.filho.DFilho;
import br.com.todeschini.domain.business.publico.filho.api.FilhoService;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.history.api.HistoryService;
import br.com.todeschini.domain.business.publico.maquina.DMaquina;
import br.com.todeschini.domain.business.publico.maquina.api.MaquinaService;
import br.com.todeschini.domain.business.publico.material.DMaterial;
import br.com.todeschini.domain.business.publico.material.api.MaterialService;
import br.com.todeschini.domain.business.publico.materialusado.api.MaterialUsadoService;
import br.com.todeschini.domain.business.publico.medidas.DMedidas;
import br.com.todeschini.domain.business.publico.medidas.api.MedidasService;
import br.com.todeschini.domain.business.publico.modelo.api.ModeloService;
import br.com.todeschini.domain.business.publico.pai.DPai;
import br.com.todeschini.domain.business.publico.pai.montadores.DAcessorioQuantidade;
import br.com.todeschini.domain.business.publico.pai.montadores.DItemModulacao;
import br.com.todeschini.domain.business.publico.pai.montadores.DMontadorEstruturaPai;
import br.com.todeschini.domain.business.publico.pai.montadores.DMontadorEstruturaPaiModulacao;
import br.com.todeschini.domain.business.publico.pai.spi.CrudPai;
import br.com.todeschini.domain.business.publico.roteiro.DRoteiro;
import br.com.todeschini.domain.business.publico.roteiro.api.RoteiroService;
import br.com.todeschini.domain.business.publico.roteiromaquina.DRoteiroMaquina;
import br.com.todeschini.domain.business.publico.roteiromaquina.api.RoteiroMaquinaService;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.SituacaoEnum;
import br.com.todeschini.persistence.entities.enums.TipoMaterialEnum;
import br.com.todeschini.persistence.entities.publico.Pai;
import br.com.todeschini.persistence.processadores.MaterialProcessador;
import br.com.todeschini.persistence.processadores.MaterialProcessadorFactory;
import br.com.todeschini.persistence.util.AttributeMappings;
import br.com.todeschini.persistence.util.EntityService;
import br.com.todeschini.persistence.util.PageRequestUtils;
import br.com.todeschini.persistence.util.SpecificationHelper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CrudPaiImpl implements CrudPai {

    private final PaiRepository repository;
    private final PaiQueryRepository queryRepository;
    private final PaiDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final HistoryService historyService;
    private final FilhoService filhoService;
    private final RoteiroService roteiroService;
    private final RoteiroMaquinaService roteiroMaquinaService;
    private final MaquinaService maquinaService;
    private final MedidasService medidasService;
    private final MaterialService materialService;
    private final MaterialProcessadorFactory materialProcessadorFactory;
    private final CorService corService;
    private final AcessorioUsadoService acessorioUsadoService;
    private final AcessorioService acessorioService;
    private final ModeloService modeloService;
    private final CategoriaComponenteService categoriaComponenteService;

    public CrudPaiImpl(PaiRepository repository, PaiQueryRepository queryRepository, PaiDomainToEntityAdapter adapter,
                       EntityService entityService, PageRequestUtils pageRequestUtils, HistoryService historyService,
                       FilhoService filhoService, MaterialUsadoService materialUsadoService, RoteiroService roteiroService,
                       RoteiroMaquinaService roteiroMaquinaService, MaquinaService maquinaService, MedidasService medidasService,
                       MaterialService materialService, MaterialProcessadorFactory materialProcessadorFactory, CorService corService, AcessorioUsadoService acessorioUsadoService, AcessorioService acessorioService, ModeloService modeloService, CategoriaComponenteService categoriaComponenteService) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.entityService = entityService;
        this.pageRequestUtils = pageRequestUtils;
        this.historyService = historyService;
        this.filhoService = filhoService;
        this.roteiroService = roteiroService;
        this.roteiroMaquinaService = roteiroMaquinaService;
        this.maquinaService = maquinaService;
        this.medidasService = medidasService;
        this.materialService = materialService;
        this.materialProcessadorFactory = materialProcessadorFactory;
        this.corService = corService;
        this.acessorioUsadoService = acessorioUsadoService;
        this.acessorioService = acessorioService;
        this.modeloService = modeloService;
        this.categoriaComponenteService = categoriaComponenteService;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DPai> buscarTodos(PageableRequest request) {
        SpecificationHelper<Pai> helper = new SpecificationHelper<>();
        Specification<Pai> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());
        
        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DPai>()
                        .withContent(r.getContent().stream().map(adapter::toDomain).toList())
                        .withSortedBy(String.join(";", request.getSort()))
                        .withFirst(r.isFirst())
                        .withLast(r.isLast())
                        .withPage(r.getNumber())
                        .withSize(r.getSize())
                        .withTotalPages(r.getTotalPages())
                        .withNumberOfElements(Math.toIntExact(r.getTotalElements()))
                        .build())
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DPai> pesquisarPorDescricao(String descricao) {
        return queryRepository.findByDescricaoIgnoreCase(descricao).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DPai> buscarTodosAtivosMaisAtual(Integer obj) {
        return queryRepository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DPai buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DHistory<DPai>> buscarHistorico(Integer id) {
        return historyService.getHistoryEntityByRecord(Pai.class, "tb_pai", id.toString(), AttributeMappings.PAI.getMappings()).stream()
                .map(history -> new DHistory<>(history.getId(), history.getDate(), history.getAuthor(), adapter.toDomain(history.getEntity())))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> buscarAtributosEditaveisEmLote() {
        return entityService.obterAtributosEditaveis(DPai.class);
    }

    @Override
    @Transactional
    public DPai inserir(DPai obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DPai atualizar(DPai obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        Pai entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    @Transactional
    public List<DPai> atualizarEmLote(List<DPai> list) {
        return list;
    }

    @Override
    @Transactional
    public DPai substituirPorVersaoAntiga(Integer id, Integer versionId) {
        DHistory<Pai> antiga = historyService.getHistoryEntityByRecord(Pai.class, "tb_pai", id.toString(), AttributeMappings.PAI.getMappings())
                .stream()
                .filter(historyWithId -> historyWithId.getId().equals(versionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Versão " + versionId + " não encontrada para o código " + id));
        return adapter.toDomain(repository.save(antiga.getEntity()));
    }

    @Override
    @Transactional
    public void inativar(Integer id) {
        Pai entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        SituacaoEnum situacao = entity.getSituacao() == SituacaoEnum.ATIVO ? SituacaoEnum.INATIVO : SituacaoEnum.ATIVO;
        entity.setSituacao(situacao);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void remover(Integer id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), SituacaoEnum.LIXEIRA);
    }

    @Override
    @Transactional
    public DPai montarEstrutura(DMontadorEstruturaPai montadorEstruturaPai) {
        DPai pai = montarPai(montadorEstruturaPai);
        pai = validarEInserirPai(pai);
        processarMedidasEFilhos(montadorEstruturaPai, pai);
        return atualizar(pai);
    }

    @Override
    @Transactional
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
        paiPrincipal = validarEInserirPai(paiPrincipal);
        processarMedidasEFilhosPaiPrincipalModulacao(montadorEstruturaPai, paiPrincipal);

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
                DPai paiFilho = montarEstrutura(montadorEstruturaPai);
                paiFilho.getFilhos().get(i).calcularValor();
                paiPrincipal.getFilhos().get(i).getFilhos().add(paiFilho.getFilhos().get(i));
                paiPrincipal.getFilhos().get(i).calcularValor();

                for(DAcessorioQuantidade acessorioQuantidade : montadorEstruturaPaiModulacao.getAcessoriosQuantidades()){
                    DAcessorioUsado acessorioUsado = new DAcessorioUsado();
                    acessorioUsado.setAcessorio(acessorioService.buscar(acessorioQuantidade.getAcessorio().getCodigo()));
                    acessorioUsado.setFilho(paiFilho.getFilhos().get(i));
                    acessorioUsado.setQuantidade(acessorioQuantidade.getQuantidade());
                    acessorioUsado = acessorioUsadoService.incluir(acessorioUsado);
                    paiFilho.getFilhos().get(i).getAcessoriosUsados().add(acessorioUsado);
                }

                filhoService.atualizar(paiFilho.getFilhos().get(i));
                filhoService.atualizar(paiPrincipal.getFilhos().get(i));
            }
        }

        return atualizar(paiPrincipal);
    }

    private DPai montarPai(DMontadorEstruturaPai montadorEstruturaPai) {
        DPai pai = new DPai();
        pai.setModelo(modeloService.buscar(montadorEstruturaPai.getModelo().getCodigo()));
        pai.setCategoriaComponente(categoriaComponenteService.buscar(montadorEstruturaPai.getCategoriaComponente().getCodigo()));
        pai.gerarDescricao();
        pai.setBordasComprimento(montadorEstruturaPai.getBordasComprimento());
        pai.setBordasLargura(montadorEstruturaPai.getBordasLargura());
        pai.setNumeroCantoneiras(montadorEstruturaPai.getNumeroCantoneiras());
        pai.setTntUmaFace(montadorEstruturaPai.getTntUmaFace());
        pai.setPlasticoAcima(montadorEstruturaPai.getPlasticoAcima());
        pai.setPlasticoAdicional(montadorEstruturaPai.getPlasticoAdicional());
        pai.setLarguraPlastico(montadorEstruturaPai.getLarguraPlastico());
        pai.setTipoPintura(montadorEstruturaPai.getTipoPintura());
        pai.setFaces(montadorEstruturaPai.getFaces());
        pai.setEspecial(montadorEstruturaPai.getEspecial());
        pai.setFilhos(new ArrayList<>());
        pai.validar();
        return pai;
    }

    private DPai validarEInserirPai(DPai pai) {
        Collection<? extends DPai> lista = pesquisarPorDescricao(pai.getDescricao());
        if (lista.stream()
                .anyMatch(t -> !t.getCodigo().equals(Optional.ofNullable(pai.getCodigo()).orElse(-1)))) {
            return lista.stream().iterator().next();
        }
        return inserir(pai);
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

    private DMedidas verificarOuIncluirMedida(DMedidas medida, DMontadorEstruturaPai montadorEstruturaPai) {
        Collection<? extends DMedidas> medidaExistente = medidasService.buscarPorAlturaELarguraEEspessura(
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

    private void processarMateriaisMDP(DFilho filho, List<DMaterial> materiais) {
        // Materiais acrescentados automaticamente com base em cores e medidas
        MaterialProcessador chapaMDPProcessador = materialProcessadorFactory.getProcessador(TipoMaterialEnum.CHAPA_MDP.toString());
        chapaMDPProcessador.processarMaterial(filho, null);
        MaterialProcessador fitaBordaProcessador = materialProcessadorFactory.getProcessador(TipoMaterialEnum.FITA_BORDA.toString());
        fitaBordaProcessador.processarMaterial(filho, null);

        // Materiais acrescentados via configurador por código
        for (DMaterial material : materiais) {
            material = materialService.buscar(material.getCodigo());
            MaterialProcessador processador = materialProcessadorFactory.getProcessador(material.getTipoMaterial().name());
            processador.processarMaterial(filho, material);
        }
    }

    private void processarMateriaisMDF(DFilho filho, List<DMaterial> materiais) {
        // Processa o fundo
        DFilho fundo = processarFundo(filho);
        filho.getFilhos().add(fundo);

        // Materiais acrescentados automaticamente com base em cores e medidas
        MaterialProcessador pinturaProcessador = materialProcessadorFactory.getProcessador(TipoMaterialEnum.PINTURA.toString());
        pinturaProcessador.processarMaterial(filho, null);

        // Materiais acrescentados via configurador por código
        for (DMaterial material : materiais) {
            material = materialService.buscar(material.getCodigo());
            MaterialProcessador processador = materialProcessadorFactory.getProcessador(material.getTipoMaterial().name());
            processador.processarMaterial(filho, material);
        }
    }

    private DFilho processarFundo(DFilho filho) {
        String descricao = "Fundo " + filho.getDescricao() + " " + filho.getMedidas().getAltura() + "X" + filho.getMedidas().getLargura() + "X" + filho.getMedidas().getEspessura();
        List<DFilho> fundos = filhoService.pesquisarPorDescricaoEMedidas(descricao, filho.getMedidas().getCodigo());
        DFilho fundo;

        if (fundos.isEmpty()) {
            fundo = filhoService.incluir(criarFilho(filho.getPai(), descricao,
                    corService.buscar(4), filho.getMedidas(), filho.getImplantacao(), DTipoFilhoEnum.FUNDO));
            // colocar a chapa dentro do fundo
            MaterialProcessador chapaMDFProcessador = materialProcessadorFactory.getProcessador(TipoMaterialEnum.CHAPA_MDF.toString());
            chapaMDFProcessador.processarMaterial(fundo, null);
            fundo.calcularValor();
            filhoService.atualizar(fundo);
        } else {
            fundo = fundos.get(0);
        }

        return fundo;
    }

    private void processarRoteiro(DFilho filho, String descricao, DMedidas medida, List<DMaquina> maquinas, LocalDate implantacao) {
        String roteiroDescricao = descricao + " - " + medida.getAltura() + "X" + medida.getLargura() + "X" + medida.getEspessura();
        DRoteiro roteiro = roteiroService.existePorDescricao(roteiroDescricao)
                ? roteiroService.buscarPorDescricao(roteiroDescricao).iterator().next()
                : criarNovoRoteiro(roteiroDescricao, medida, maquinas, implantacao);

        filho.setRoteiro(roteiro);
    }

    private DRoteiro criarNovoRoteiro(String descricao, DMedidas medida, List<DMaquina> maquinas, LocalDate implantacao) {
        DRoteiro roteiro = new DRoteiro();
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

        return roteiroService.atualizar(roteiro);
    }

    private void setCreationProperties(Pai obj){
        obj.setCriadoem(repository.findCriadoemById(obj.getCdpai()));
        obj.setCriadopor(repository.findCriadoporById(obj.getCdpai()));
    }
}
