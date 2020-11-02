package com.ekshunya.sahaaybackend.mapper;

import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import com.google.inject.Inject;
import com.googlecode.jmapper.JMapper;

public class MainMapper {
    private final JMapper<Ticket, TicketCreateDto> ticketTicketCreateDtoJMapper;
    private final JMapper<Ticket, TicketDetailsUpdateDto> ticketDetailsMapper;
    private final JMapper<TicketDto, Ticket> ticketToTicketDtoMapper;
    private final JMapper<Feed, TicketFeedDto> ticketFeedDtoToFeed;

    @Inject
    public MainMapper(final JMapper<Ticket, TicketCreateDto> ticketTicketCreateDtoJMapper,
                      final JMapper<Ticket, TicketDetailsUpdateDto> ticketDetailsMapper,
                      final JMapper<TicketDto, Ticket> ticketToTicketDtoMapper,
                      final JMapper<Feed, TicketFeedDto> ticketFeedDtoToFeed) {
        this.ticketTicketCreateDtoJMapper = ticketTicketCreateDtoJMapper;
        this.ticketDetailsMapper = ticketDetailsMapper;
        this.ticketToTicketDtoMapper = ticketToTicketDtoMapper;
        this.ticketFeedDtoToFeed = ticketFeedDtoToFeed;
    }

    public TicketDto ticketToTicketDto(final Ticket ticket) {
        return this.ticketToTicketDtoMapper.getDestination(ticket);
    }

    public Feed ticketFeedToTicket(final TicketFeedDto feedDto) {
        return this.ticketFeedDtoToFeed.getDestination(feedDto);
    }

    public Ticket ticketDetailsUpdateDtoToTicket(final TicketDetailsUpdateDto ticketDetailsUpdateDto) {
        return this.ticketDetailsMapper.getDestination(ticketDetailsUpdateDto);
    }

    public Ticket ticketCreateDtoToTicket(final TicketCreateDto ticketCreateDto) {
        return this.ticketTicketCreateDtoJMapper.getDestination(ticketCreateDto);
    }
}
