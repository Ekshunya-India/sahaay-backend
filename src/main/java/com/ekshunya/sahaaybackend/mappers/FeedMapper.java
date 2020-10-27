package com.ekshunya.sahaaybackend.mappers;

import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import org.mapstruct.factory.Mappers;

public interface FeedMapper {
	FeedMapper INSTANCE = Mappers.getMapper(FeedMapper.class);

	Feed ticketFeedToFeed(final TicketFeedDto ticketFeedDto);
}
