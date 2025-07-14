package br.com.todeschini.libauditwebapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "audit.mongo")
@Getter
@Setter
public class AuditMongoConfig {

    private String uri;
    private String database;
    private String collection;
}
