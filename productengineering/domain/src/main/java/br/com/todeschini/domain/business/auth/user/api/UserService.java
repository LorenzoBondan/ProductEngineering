package br.com.todeschini.domain.business.auth.user.api;

public interface UserService extends FindUser, InsertUser, UpdateUser, DeleteUser, UpdateUserPassword, InactivateUser,
        FindAllActiveUserAndCurrentOne {
}
