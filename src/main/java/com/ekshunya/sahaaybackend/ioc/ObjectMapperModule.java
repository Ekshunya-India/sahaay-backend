package com.ekshunya.sahaaybackend.ioc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ObjectMapperModule extends AbstractModule {
	@Provides
	public ObjectMapper provideObjectMapper(){
		return new ObjectMapper();
	}
}
