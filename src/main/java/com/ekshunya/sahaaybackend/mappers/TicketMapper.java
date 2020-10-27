package com.ekshunya.sahaaybackend.mappers;

import com.ekshunya.sahaaybackend.model.daos.Location;
import com.ekshunya.sahaaybackend.model.daos.Priority;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.daos.TicketType;
import com.ekshunya.sahaaybackend.model.dtos.LocationDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper
public interface TicketMapper {
	TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
	//TODO add the TicketDto to Ticket Mappings
	//TODO there are 4 warnings about Mappers which are not configured. Need to take a look at them.
	@Mapping(source = "expectedEnd", target = "expectedEnd", dateFormat = "yyyy-MM-dd HH:mm:ss ZZ")
	Ticket ticketDtoToTicket(final TicketDto ticketDto);
	Ticket ticketCreateDtoToTicket(final TicketCreateDto ticketCreateDto);
	TicketDto ticketToTicketDto(final Ticket ticket);
	Ticket ticketDetailsUpdateDtoToTicket(final TicketDetailsUpdateDto ticketDetailsUpdateDto);
	List<TicketDto> ticketsToTicketDtos(final List<Ticket> tickets);
	LocationDto locationToLocationDto(final Location location);

	default Priority mapPriority(final String priority){
		return Priority.valueOf(priority);
	}

	default TicketType mapTicketType(final String ticketType){
		return TicketType.valueOf(ticketType);
	}
	default UUID stringToUUID(final String userId){
		return UUID.fromString(userId);
	}
}