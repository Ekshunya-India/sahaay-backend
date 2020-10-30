package com.ekshunya.sahaaybackend.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactor.client.MongoReactorKt;
import com.mongodb.reactor.client.ReactorMongoClient;
import com.mongodb.reactor.client.ReactorMongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDbModule extends AbstractModule {
	//TODO add SSL/TLS support to MongoDB in the configuration below.
	@Provides
	public MongoClientSettings providesClientSettings(){
		ConnectionString connectionString = new ConnectionString(System.getProperty("mongodb.uri"));
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				pojoCodecRegistry);
		return MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.codecRegistry(codecRegistry)
				.build();
	}

	//TODO add in the Readme to read about this project https://github.com/jntakpe/mongo-reactor-adapter
	@Provides
	public ReactorMongoDatabase providesMongoDatabase() {
		try (MongoClient mongoClient = MongoClients.create(providesClientSettings())) {

			ReactorMongoClient reactorClient = MongoReactorKt.toReactor(mongoClient);
			return reactorClient.getDatabase("sahaay-db");
		}
	}
}
