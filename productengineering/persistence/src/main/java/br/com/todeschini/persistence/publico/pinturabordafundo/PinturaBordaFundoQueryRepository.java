package br.com.todeschini.persistence.publico.pinturabordafundo;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.PinturaBordaFundo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface PinturaBordaFundoQueryRepository extends PagingAndSortingRepository<PinturaBordaFundo, Integer>, JpaSpecificationExecutor<PinturaBordaFundo> {

    Collection<PinturaBordaFundo> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_material p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.cdmaterial = :id)")
    List<PinturaBordaFundo> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
