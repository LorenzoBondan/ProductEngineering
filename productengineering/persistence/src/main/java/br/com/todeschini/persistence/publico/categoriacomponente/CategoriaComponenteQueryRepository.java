package br.com.todeschini.persistence.publico.categoriacomponente;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.CategoriaComponente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface CategoriaComponenteQueryRepository extends PagingAndSortingRepository<CategoriaComponente, Integer>, JpaSpecificationExecutor<CategoriaComponente> {

    Collection<CategoriaComponente> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_categoria_componente p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.cdcategoria_componente = :id)")
    List<CategoriaComponente> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
