package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.exceptions.InternalServerException;
import com.ekshunya.sahaaybackend.model.daos.*;
import com.ekshunya.sahaaybackend.model.dtos.LocationDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
import static org.mockito.Mockito.*;

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
	private TicketDetailsUpdateDto invalidTicketDetails;
	private Ticket validTicket;
	private static final double LAT = 20.00;
	private static final double LNG = 20.00;
	private static final UUID uuid = UUID.randomUUID();
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
		ticketDetailsUpdateDto = new TicketDetailsUpdateDto(locationDto,new ArrayList<>(),ZonedDateTime.now().plusDays(20).toString(),
				1,UUID.randomUUID().toString(),ZonedDateTime.now().toString(),DESC,"PROBLEM",TITLE,"P1","CANCELLED");
		invalidTicketDetails = new TicketDetailsUpdateDto(locationDto,new ArrayList<>(),ZonedDateTime.now().plusDays(20).toString(),
				1,UUID.randomUUID().toString(),ZonedDateTime.now().toString(),DESC,"SOME_OTHER_INVALID","SOME_TITLE","P1","CANCELLED");
		validTicket = Ticket.builder().id(uuid).ticketType(TicketType.PROBLEM).title(TITLE).desc(DESC).created(ZonedDateTime.now()).expectedEnd(ZonedDateTime.now().plusDays(20))
		.priority(Priority.P1).location(new Location(22.00,23.00)).state(State.OPENED).build();
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

	@Test(expected = NullPointerException.class) //TODO I do not like the fact that we are emitting Null Pointer Exception here. But the thinking was that handler would have already done this check.
	public void updateTicketThrowsNullPointerExceptionWhenNullIsPassed() throws InterruptedException {
		sut.updateTicket(null);
	}

	@Test(expected = BadDataException.class)
	public void updateTicketThrowsBadDataExceptionWhenThereIsBadData() throws InterruptedException {
		sut.updateTicket(invalidTicketDetails);
	}

	@Test
	public void updateTicketGivesTheDetailsToServiceToUpdate() throws InterruptedException{
		sut.updateTicket(ticketDetailsUpdateDto);

		verify(this.ticketService,times(1)).updateTicket(ticketArgumentCaptor.capture());
		Ticket capturedTicket = ticketArgumentCaptor.getValue();
		assertNotNull(capturedTicket);
		assertEquals(DESC, capturedTicket.getDesc());
		assertEquals(TITLE, capturedTicket.getTitle());
		assertEquals("P1", capturedTicket.getPriority().name());
	}

	@Test(expected = NullPointerException.class)
	public void fetchTicketFromIdThowsNullPointerExceptionWhenidIsNull() throws InterruptedException {
		sut.fetchTicketFromId(null);
	}

	@Test(expected = DataNotFoundException.class)
	public void whenNoTicketWithIdPresentThrowsDataNotFoundException() throws InterruptedException {
		when(ticketService.fetchTicket(eq(uuid))).thenThrow(new DataNotFoundException("THIS IS ME BEING STUPID"));
		sut.fetchTicketFromId(uuid);
	}

	@Test
	public void whenValidTicketIdIsGivenTheDataReturnedByServiceIsReturned() throws InterruptedException {
		when(ticketService.fetchTicket(eq(uuid))).thenReturn(validTicket);
		TicketDto ticketDto = sut.fetchTicketFromId(uuid);
		assertEquals(TicketType.PROBLEM.name(), ticketDto.getTicketType());
		assertEquals(State.OPENED.name(),ticketDto.getState());
		assertEquals(DESC,ticketDto.getDesc());
		assertEquals(TITLE,ticketDto.getTitle());
	}

	@Test(expected = BadDataException.class)
	public void whenInValidDataGivenToFetchThrowsBadDataException() throws InterruptedException {
		sut.fetchAllTicketOfType("SOME_TICKETTYPE","20.00","30.00");
	}

	@Test(expected = BadDataException.class)
	public void whenInValidDataGivenToFetchThrowsBadDataExceptionWhenParsingWrongDouble() throws InterruptedException {
		sut.fetchAllTicketOfType("PROBLEM","SOME_BAD_DATA","30.00");
	}

	@Test(expected = DataNotFoundException.class)
	public void whenDataNotFoundExceptionIsThrownInExcecutorThenItIsPropagated() throws InterruptedException {
		when(ticketService.fetchAllOpenedTicket(eq(TicketType.PROBLEM),eq(LAT),eq(LNG))).thenThrow(new DataNotFoundException("SOME WEIRED EXCEPTION THROWN HERE"));

		sut.fetchAllTicketOfType(TicketType.PROBLEM.name(),String.valueOf(LAT), String.valueOf(LNG));
	}

	@Test
	public void validDataGivenToFetchAllTicketsValidDataIsPassedOnToService() throws InterruptedException {
		when(ticketService.fetchAllOpenedTicket(eq(TicketType.PROBLEM),eq(LAT),eq(LNG))).thenReturn(new ArrayList<>());

		sut.fetchAllTicketOfType(TicketType.PROBLEM.name(),String.valueOf(LAT), String.valueOf(LNG));

		verify(ticketService,times(1)).fetchAllOpenedTicket(eq(TicketType.PROBLEM),eq(LAT),eq(LNG));
	}
}