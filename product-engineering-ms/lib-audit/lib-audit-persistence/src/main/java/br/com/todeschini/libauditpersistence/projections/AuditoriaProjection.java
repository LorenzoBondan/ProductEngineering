package br.com.todeschini.libauditpersistence.projections;

import java.time.LocalDateTime;

public interface AuditoriaProjection {
    String criadoPor();
    LocalDateTime criadoEm();
    String situacao();
}