package com.ekshunya.sahaaybackend.ioc;

import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.googlecode.jmapper.JMapper;

public class JMapperModule extends AbstractModule {
    @Provides
    public JMapper<Ticket, TicketCreateDto> providesTicketTicketCreateDtoJMapper(){
       return new JMapper <>(Ticket.class, TicketCreateDto.class);
    }

    @Provides
    public JMapper<Ticket, TicketDetailsUpdateDto> providesTicketDetailsMapper(){
        return new JMapper <>(Ticket.class, TicketDetailsUpdateDto.class);
    }

    @Provides
    public JMapper<TicketDto, Ticket> providesTicketToTicketDtoMapper(){
        return new JMapper <>(TicketDto.class, Ticket.class);
    }

    @Provides
    public JMapper<Feed, TicketFeedDto> providesTicketFeedDtoToFeed(){
        return new JMapper <>(Feed.class, TicketFeedDto.class);
    }
}
