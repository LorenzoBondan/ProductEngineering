package br.com.todeschini.libauditwebapi.config;

import br.com.todeschini.libauditwebapi.service.AuditService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MongoClient.class)
@EnableConfigurationProperties(AuditMongoConfig.class)
public class AuditAutoConfiguration {

    @Bean
    public MongoClient mongoClient(AuditMongoConfig config) {
        return MongoClients.create(config.getUri());
    }

    @Bean
    public AuditService auditService(AuditMongoConfig config, MongoClient mongoClient) {
        MongoDatabase db = mongoClient.getDatabase(config.getDatabase());
        MongoCollection<Document> collection = db.getCollection(config.getCollection());
        return new AuditService(collection);
    }
}
