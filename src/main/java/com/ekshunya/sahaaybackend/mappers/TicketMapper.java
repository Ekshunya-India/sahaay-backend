package com.ekshunya.sahaaybackend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {
	TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
	//TODO add the TicketDto to Ticket Mappings
}
