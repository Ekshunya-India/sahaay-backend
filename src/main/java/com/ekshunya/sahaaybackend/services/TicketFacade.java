package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.mappers.FeedMapper;
import com.ekshunya.sahaaybackend.mappers.TicketMapper;
import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.daos.TicketType;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketFacade {
	private final TicketService ticketService;

	@Inject
	public TicketFacade(final TicketService ticketService){
		this.ticketService = ticketService;
	}

	public TicketDto createTicket(final TicketCreateDto ticketCreateDto) {
		Ticket ticketToSave = TicketMapper.INSTANCE.ticketCreateDtoToTicket(ticketCreateDto);
		Ticket createdTicket = this.ticketService.createANewTicket(ticketToSave);
		return TicketMapper.INSTANCE.ticketToTicketDto(createdTicket);
	}

	public TicketDto updateTicket(final TicketDetailsUpdateDto ticketDetailsUpdateDto) {
		Ticket ticketToUpdate = TicketMapper.INSTANCE.ticketDetailsUpdateDtoToTicket(ticketDetailsUpdateDto);
		Ticket updatedTicket = this.ticketService.updateTicket(ticketToUpdate);
		return TicketMapper.INSTANCE.ticketToTicketDto(updatedTicket);
	}

	public TicketDto fetchTicketFromId(final UUID ticketId) {
		Ticket existingTicket = this.ticketService.fetchTicket(ticketId);
		return TicketMapper.INSTANCE.ticketToTicketDto(existingTicket);
	}

	public List<TicketDto> fetchAllTicketOfType(final String ticketType, final String latitude, final String longitude) {
		TicketType actualTicketType = TicketType.valueOf(ticketType);
		List<Ticket> openTickets = this.ticketService.fetchAllOpenedTicket(actualTicketType, latitude,longitude);
		//TODO do a pagination using bucket pattern in mongo DB.
		// https://www.mongodb.com/blog/post/paging-with-the-bucket-pattern--part-1
		// https://www.mongodb.com/blog/post/paging-with-the-bucket-pattern--part-2
		return TicketMapper.INSTANCE.ticketsToTicketDtos(openTickets);
	}

	public TicketDto updateTicketWithFeed(final TicketFeedDto ticketFeedDto) {
		Feed newFeed = FeedMapper.INSTANCE.ticketFeedToFeed(ticketFeedDto);
		Ticket updatedTicket = this.ticketService.updateWithFeed(newFeed);
		return TicketMapper.INSTANCE.ticketToTicketDto(updatedTicket);
	}

	public boolean deleteTicketWithId(final String ticketId) {
		//TODO delete a Ticket in MongoDB
		return false;
	}

	public List<TicketDto> fetchAllTicketsForUser(final String userId) {
		//TODO implement fetchTicketsOfUsers.
		return new ArrayList<>();
	}
}