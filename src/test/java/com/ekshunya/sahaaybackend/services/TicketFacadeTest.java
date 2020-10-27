package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.dtos.LocationDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TicketFacadeTest {
	@Mock
	private TicketService ticketService;
	@InjectMocks
	private TicketFacade sut;
	@Captor
	private ArgumentCaptor<Ticket> ticketArgumentCaptor;
	private TicketCreateDto ticketCreateDto;
	private TicketCreateDto invalidCreateDto;
	private LocationDto locationDto;
	private TicketDetailsUpdateDto ticketDetailsUpdateDto;
	private static final String DESC = "A Bridge is about to crumble";
	private static final String TITLE = "A new problem in the Aread";
	private static final String USER_ID = UUID.randomUUID().toString();

	@Before
	public void setUp() throws Exception {
		locationDto = new LocationDto(22.00, 23.00);
		ticketCreateDto = new TicketCreateDto(locationDto, ZonedDateTime.now().plusDays(10).toString(),1,
				DESC,"PROBLEM",TITLE,"P1", USER_ID);
		invalidCreateDto = new TicketCreateDto(locationDto, ZonedDateTime.now().plusDays(10).toString(),1,
				DESC,"PROBLEM",TITLE,"SOME_OTHER_PRIORITY", USER_ID);
		ticketDetailsUpdateDto = new TicketDetailsUpdateDto(locationDto,new ArrayList<>(),ZonedDateTime.now().plusDays(20),
				1,UUID.randomUUID().toString(),ZonedDateTime.now(),"SOME_DESC","PROBLEM","SOME_TITLE","P!","CANCELLED");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void ticketFacadeCreateTicketCallsTheServiceWithTheSame() throws InterruptedException {
		TicketDto actualTicketDto = sut.createTicket(ticketCreateDto);

		verify(this.ticketService,times(1)).createANewTicket(ticketArgumentCaptor.capture());
		Ticket capturedTicket = ticketArgumentCaptor.getValue();
		assertNotNull(capturedTicket);
		assertEquals(DESC, capturedTicket.getDesc());
		assertEquals(TITLE, capturedTicket.getTitle());
		assertEquals("P1", capturedTicket.getPriority().name());
	}

	@Test(expected = NullPointerException.class) //TODO since we are emitting Nullpointer exception to the handler we need to atleast handle it in the Upper handler and give a nice 500 Error Page in HTML.
	public void createTicketIsCalledWithANullValueThrowsException() throws InterruptedException {
		sut.createTicket(null);
	}

	@Test(expected = BadDataException.class)
	public void createTicketThrowsBadDataExceotion() throws InterruptedException {
		sut.createTicket(invalidCreateDto);
	}

	@Test(expected = BadDataException.class)
	public void createTicketThrowsBadDataExceptionWhenThereIsABadDateFormatException() throws InterruptedException {
		invalidCreateDto = new TicketCreateDto(locationDto, "SOME_BAD_DATA",1,
				DESC,"PROBLEM",TITLE,"P1", USER_ID);

		sut.createTicket(invalidCreateDto);
	}

	@Test(expected = NullPointerException.class)
	public void updateTicketThrowsNullPointerExceptionWhenNullIsPassed() throws InterruptedException {
		sut.updateTicket(null);
	}
}