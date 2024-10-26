package br.com.todeschini.domain.business.publico.user.api;

public interface AtualizarSenhaUsuario {

    void updatePassword (String newPassword, String oldPassword);
}
