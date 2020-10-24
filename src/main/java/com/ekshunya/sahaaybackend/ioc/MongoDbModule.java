package com.ekshunya.sahaaybackend.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class MongoDbModule extends AbstractModule {
	@Provides
	public MongoDatabase providesMongoDb(){
		//TODO the mongoDB connection needs to come from config properties file.
		MongoClient mongoClient = MongoClients.create("mongodb://hostOne:27017");
		return mongoClient.getDatabase("mydb");
	}
}
