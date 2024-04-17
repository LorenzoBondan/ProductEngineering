package br.com.todeschini.domain.business.auth.user.api;

public interface UpdateUserPassword {

    void updatePassword (String newPassword, String oldPassword);
}
