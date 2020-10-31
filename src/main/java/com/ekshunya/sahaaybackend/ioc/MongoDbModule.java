package com.ekshunya.sahaaybackend.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
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
}
