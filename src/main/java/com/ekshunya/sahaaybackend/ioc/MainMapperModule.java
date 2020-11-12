package com.ekshunya.sahaaybackend.ioc;

import com.ekshunya.sahaaybackend.mapper.MainMapper;
import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.googlecode.jmapper.JMapper;

public class MainMapperModule extends AbstractModule {
    @Provides
    public MainMapper provideMainMapper(final JMapper<Ticket, TicketCreateDto> ticketTicketCreateDtoJMapper,
                                        final JMapper<Ticket, TicketDetailsUpdateDto> ticketDetailsMapper,
                                        final JMapper<TicketDto, Ticket> ticketToTicketDtoMapper,
                                        final JMapper<Feed, TicketFeedDto> ticketFeedDtoToFeed){
        return new MainMapper(ticketTicketCreateDtoJMapper, ticketDetailsMapper, ticketToTicketDtoMapper, ticketFeedDtoToFeed);
    }
}
