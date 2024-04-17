package br.com.todeschini.persistence.auth.user;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.domain.projections.UserDetailsProjection;
import br.com.todeschini.persistence.entities.aluminium.Screw;
import br.com.todeschini.persistence.entities.auth.User;
import br.com.todeschini.persistence.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@QueryService
public interface UserRepository extends JpaRepository<User, Long> {

	Collection<User> findByEmail(String email);

    @Query("SELECT DISTINCT obj FROM User obj " +
            "WHERE (UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%')) ) ORDER BY obj.name")
    Page<User> find(String name, Pageable pageable);

	@Query("SELECT c FROM User c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
	List<User> findAllActiveAndCurrentOne(@Param("id") Long id);

	<T> Page<T> findByStatusInAndNameContainingIgnoreCase(List<Status> statusList, String description, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
				SELECT tb_user.email AS username, tb_user.password, tb_role.id AS roleId, tb_role.authority
				FROM tb_user
				INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
				INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id
				WHERE tb_user.email = :email
			""")
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = """
            UPDATE tb_user SET password = :password
            WHERE tb_user.id = :userId
            """)
	void updatePassword(@Param("password") String password, @Param("userId") Long userId);
}
