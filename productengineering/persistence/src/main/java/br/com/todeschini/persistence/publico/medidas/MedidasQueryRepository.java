package br.com.todeschini.persistence.publico.medidas;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Medidas;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface MedidasQueryRepository extends PagingAndSortingRepository<Medidas, Integer>, JpaSpecificationExecutor<Medidas> {

    Collection<Medidas> findByAlturaAndLarguraAndEspessura(Integer altura, Integer largura, Integer espesura); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_medidas p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.cdmedidas = :id)")
    List<Medidas> findAllActiveAndCurrentOne(@Param("id") Integer id);

    Boolean existsByAlturaAndLarguraAndEspessura(Integer altura, Integer largura, Integer espesura);
}
