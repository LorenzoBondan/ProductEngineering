package br.com.todeschini.libauditwebapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditEntry {

    private Instant timestamp;
    private String service;
    private String entity;
    private String entityId;
    private String operation;
    private String user;
    private Object before;
    private Object after;
}

