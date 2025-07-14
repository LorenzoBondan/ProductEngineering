package br.com.todeschini.libauditwebapi.service;

import br.com.todeschini.libauditwebapi.model.AuditEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class AuditService {

    private final MongoCollection<Document> collection;
    private final ObjectMapper objectMapper;

    public AuditService(MongoCollection<Document> collection) {
        this.collection = collection;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    public void log(AuditEntry entry) {
        try {
            String json = objectMapper.writeValueAsString(entry);
            Document doc = Document.parse(json);
            collection.insertOne(doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write audit log", e);
        }
    }
}

