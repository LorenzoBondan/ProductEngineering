package br.com.todeschini.notificationservicepersistence.notification;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.notificationservicepersistence.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	Page<Notification> findAllByUserId(Integer userId, Pageable pageable);
}
