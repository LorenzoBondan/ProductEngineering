package br.com.todeschini.domain.business.publico.user.api;

public interface UserService extends BuscarUser, CriarUser, AlterarUser, AtualizarSenhaUsuario, RemoverUser, InativarUser, BuscarTodosUserAtivosMaisAtual, BuscarHistoricoUser, SubstituirUserPorVersaoAntiga {
}
