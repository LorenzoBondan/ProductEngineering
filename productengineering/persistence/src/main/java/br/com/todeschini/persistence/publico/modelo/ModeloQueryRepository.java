package br.com.todeschini.persistence.publico.modelo;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Modelo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface ModeloQueryRepository extends PagingAndSortingRepository<Modelo, Integer>, JpaSpecificationExecutor<Modelo> {

    Collection<Modelo> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_modelo p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.cdmodelo = :id)")
    List<Modelo> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
