package com.bachelor.externalauth;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@Slf4j
public class MultiTenantMongoDBFactory extends SimpleMongoClientDatabaseFactory {

    @Autowired
    CachedMongoClients cachedMongoClients;

    public MultiTenantMongoDBFactory(MongoClient mongoClient, String databaseName) {
        super(mongoClient, databaseName);
    }

    protected MongoDatabase doGetMongoDatabase(String dbName) {
        return cachedMongoClients.getMongoDatabaseForCurrentContext();
    }

}
