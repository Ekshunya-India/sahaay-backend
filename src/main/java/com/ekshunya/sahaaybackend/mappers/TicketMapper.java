package com.ekshunya.sahaaybackend.mappers;

import com.ekshunya.sahaaybackend.model.daos.Location;
import com.ekshunya.sahaaybackend.model.daos.Priority;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.daos.TicketType;
import com.ekshunya.sahaaybackend.model.dtos.LocationDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {
	TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
	//TODO add the TicketDto to Ticket Mappings
	@Mapping(source = "description", target = "desc")
	@Mapping(source = "expectedEnd", target = "expectedEnd", dateFormat = "yyyy-MM-dd HH:mm:ss ZZ")
	Ticket ticketDtoToTicket(final TicketDto ticketDto);
	LocationDto locationToLocationDto(final Location location);

	default Priority mapPriority(final String priority){
		return Priority.valueOf(priority);
	}

	default TicketType mapTicketType(final String ticketType){
		return TicketType.valueOf(ticketType);
	}
}