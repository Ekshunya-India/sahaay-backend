package com.ekshunya.sahaaybackend.mappers;

import com.ekshunya.sahaaybackend.model.daos.Location;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.dtos.LocationDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {
	TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
	//TODO add the TicketDto to Ticket Mappings
	Ticket ticketToTicketDto(final TicketDto ticketDto);

	LocationDto locationToLocationDto(final Location location);


}
