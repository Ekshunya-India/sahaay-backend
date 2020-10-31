package com.ekshunya.sahaaybackend.mappers;

import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import io.undertow.server.handlers.form.FormData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Mapper
public interface FeedMapper {
	FeedMapper INSTANCE = Mappers.getMapper(FeedMapper.class);

	default Feed ticketFeedDtoToFeed(final TicketFeedDto ticketFeedDto){
		FormData formData= ticketFeedDto.getFormData(); //TODO need to correctlu find out how undertow server handles Attachment Data. Needs more investigation.
		return new Feed(UUID.randomUUID(), ZonedDateTime.now(),ZonedDateTime.now(),new ArrayList<>(),null);
	}

	TicketFeedDto feedToTicketFeedDt(final Feed feed);
}