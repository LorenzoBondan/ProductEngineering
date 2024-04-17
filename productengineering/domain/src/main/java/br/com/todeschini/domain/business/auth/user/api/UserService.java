package br.com.todeschini.domain.business.auth.user.api;

import org.springframework.stereotype.Component;

@Component
public interface UserService extends FindUser, InsertUser, UpdateUser, DeleteUser, UpdateUserPassword, InactivateUser,
        FindAllActiveUserAndCurrentOne {
}
