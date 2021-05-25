package com.bachelor.externalauth;

import com.mongodb.client.MongoClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tenant {
    MongoClient client;
    String database;
}
