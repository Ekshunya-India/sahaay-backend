package com.ekshunya.sahaaybackend.ioc;

import com.ekshunya.sahaaybackend.services.UserService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class UserServiceModule extends AbstractModule {
	@Provides
	public UserService providesUserService(final MongoDatabase mongoDatabase ){
		return new UserService(mongoDatabase);
	}
}
