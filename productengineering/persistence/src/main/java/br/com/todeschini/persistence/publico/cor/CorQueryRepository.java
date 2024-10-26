package br.com.todeschini.persistence.publico.cor;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Cor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface CorQueryRepository extends PagingAndSortingRepository<Cor, Integer>, JpaSpecificationExecutor<Cor> {

    Collection<Cor> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_cor p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.cdcor = :id)")
    List<Cor> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
