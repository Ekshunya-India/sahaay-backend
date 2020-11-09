package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.exceptions.InternalServerException;
import com.ekshunya.sahaaybackend.mapper.MainMapper;
import com.ekshunya.sahaaybackend.model.daos.*;
import com.ekshunya.sahaaybackend.model.dtos.LocationDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import io.undertow.server.handlers.form.FormData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketFacadeTest {
	@Mock
	private TicketService ticketService;
	@InjectMocks
	private TicketFacade sut;
	@Captor
	private ArgumentCaptor<Ticket> ticketArgumentCaptor;
	@Captor
	private ArgumentCaptor<Feed> feedCaptor;
	@Mock
	private MainMapper mainMapper;
	private TicketCreateDto ticketCreateDto;
	private TicketCreateDto invalidCreateDto;
	private LocationDto locationDto;
	@Mock
	private TicketDetailsUpdateDto ticketDetailsUpdateDto;
	private TicketDetailsUpdateDto invalidTicketDetails;
	private Ticket validTicket;
	private TicketFeedDto validFeedDto;
	private FormData formData;
	private static final double LAT = 20.00;
	private static final double LNG = 20.00;
	private static final UUID uuid = UUID.randomUUID();
	private static final String DESC = "A Bridge is about to crumble";
	private static final String TITLE = "A new problem in the Aread";
	private static final String USER_ID = UUID.randomUUID().toString();

	public void setUp() throws Exception {
		locationDto = new LocationDto(22.00, 23.00);
		ticketCreateDto = new TicketCreateDto(uuid,locationDto, ZonedDateTime.now().plusDays(10).toString(), 1,
				DESC, "PROBLEM", TITLE, "P1", USER_ID);
		invalidCreateDto = new TicketCreateDto(uuid, locationDto, ZonedDateTime.now().plusDays(10).toString(), 1,
				DESC, "PROBLEM", TITLE, "SOME_OTHER_PRIORITY", USER_ID);
		ticketDetailsUpdateDto = new TicketDetailsUpdateDto(locationDto, ZonedDateTime.now().plusDays(20).toString(),
				1, UUID.randomUUID().toString(), ZonedDateTime.now().toString(), DESC, "PROBLEM", TITLE, "P1", "CANCELLED");
		invalidTicketDetails = new TicketDetailsUpdateDto(locationDto, ZonedDateTime.now().plusDays(20).toString(),
				1, UUID.randomUUID().toString(), ZonedDateTime.now().toString(), DESC, "SOME_OTHER_INVALID", "SOME_TITLE", "P1", "CANCELLED");
		validTicket = new Ticket(uuid, TITLE, DESC, ZonedDateTime.now(), ZonedDateTime.now().plusDays(30)
				, null, uuid, uuid, new Location(22.00, 23.00), Priority.P1, TicketType.PROBLEM, State.OPENED, 1, 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		formData = new FormData(1);
		validFeedDto = new TicketFeedDto(uuid, formData, uuid);
	}

	@Test
	public void ticketFacadeCreateTicketCallsTheServiceWithTheSame() throws InterruptedException {
		when(mainMapper.ticketCreateDtoToTicket(ticketCreateDto)).thenReturn(validTicket);
		sut.createTicket(ticketCreateDto);

		verify(this.ticketService,times(1)).createANewTicket(ticketArgumentCaptor.capture());
		Ticket capturedTicket = ticketArgumentCaptor.getValue();
		assertEquals(capturedTicket,validTicket);
	}

	@Test //TODO since we are emitting Nullpointer exception to the handler we need to atleast handle it in the Upper handler and give a nice 500 Error Page in HTML.
	public void createTicketIsCalledWithANullValueThrowsException() throws InterruptedException {
		assertThrows(NullPointerException.class,()->sut.createTicket(null));
	}

	@Test
	public void createTicketThrowsBadDataExceotion() throws InterruptedException {
		assertThrows(BadDataException.class,()->sut.createTicket(invalidCreateDto));

	}

	@Test
	public void createTicketThrowsBadDataExceptionWhenThereIsABadDateFormatException() throws InterruptedException {
		invalidCreateDto = new TicketCreateDto(uuid, locationDto, "SOME_BAD_DATA",1,
				DESC,"PROBLEM",TITLE,"P1", USER_ID);
		assertThrows(BadDataException.class,()->sut.createTicket(invalidCreateDto));
	}

	@Test //TODO I do not like the fact that we are emitting Null Pointer Exception here. But the thinking was that handler would have already done this check.
	public void updateTicketThrowsNullPointerExceptionWhenNullIsPassed() throws InterruptedException {
		assertThrows(NullPointerException.class, ()->sut.updateTicket(null));
	}

	@Test
	public void updateTicketThrowsBadDataExceptionWhenThereIsBadData() throws InterruptedException {
		assertThrows(BadDataException.class, ()->sut.updateTicket(invalidTicketDetails));
	}

	@Test
	public void updateTicketGivesTheDetailsToServiceToUpdate() throws InterruptedException{
		when(mainMapper.ticketDetailsUpdateDtoToTicket(any())).thenReturn(validTicket);
		sut.updateTicket(ticketDetailsUpdateDto);

		verify(this.ticketService,times(1)).updateTicket(ticketArgumentCaptor.capture());
		assertEquals(ticketArgumentCaptor.getValue(),validTicket);
	}

	@Test
	public void fetchTicketFromIdThowsNullPointerExceptionWhenidIsNull() throws InterruptedException {
		assertThrows(NullPointerException.class,()->sut.fetchTicketFromId(null));
	}

	@Test
	public void whenNoTicketWithIdPresentThrowsDataNotFoundException() throws InterruptedException {
		when(ticketService.fetchTicket(eq(uuid))).thenThrow(new DataNotFoundException("THIS IS ME BEING STUPID"));
		assertThrows(DataNotFoundException.class,()->sut.fetchTicketFromId(uuid));
	}

	@Test
	public void whenValidTicketIdIsGivenTheDataReturnedByServiceIsReturned() throws InterruptedException {
		when(ticketService.fetchTicket(eq(uuid))).thenReturn(validTicket);
		sut.fetchTicketFromId(uuid);
		verify(ticketService,times(1)).fetchTicket(eq(uuid));
		}


	@Test
	public void whenInValidDataGivenToFetchThrowsBadDataException() throws InterruptedException {
		assertThrows(BadDataException.class,()->sut.fetchAllTicketOfType("SOME_TICKETTYPE","20.00","30.00"));
	}

	@Test
	public void whenInValidDataGivenToFetchThrowsBadDataExceptionWhenParsingWrongDouble() throws InterruptedException {
		assertThrows(BadDataException.class,()->sut.fetchAllTicketOfType("PROBLEM","SOME_BAD_DATA","30.00"));
	}

	@Test
	public void whenDataNotFoundExceptionIsThrownInExcecutorThenItIsPropagated() throws InterruptedException {
		when(ticketService.fetchAllOpenedTicket(eq(TicketType.PROBLEM),eq(LAT),eq(LNG))).thenThrow(new DataNotFoundException("SOME WEIRED EXCEPTION THROWN HERE"));
		assertThrows(DataNotFoundException.class,()->sut.fetchAllTicketOfType(TicketType.PROBLEM.name(),String.valueOf(LAT), String.valueOf(LNG)));
	}

	@Test
	public void validDataGivenToFetchAllTicketsValidDataIsPassedOnToService() throws InterruptedException {
		when(ticketService.fetchAllOpenedTicket(eq(TicketType.PROBLEM),eq(LAT),eq(LNG))).thenReturn(new ArrayList<>());

		sut.fetchAllTicketOfType(TicketType.PROBLEM.name(),String.valueOf(LAT), String.valueOf(LNG));

		verify(ticketService,times(1)).fetchAllOpenedTicket(eq(TicketType.PROBLEM),eq(LAT),eq(LNG));
	}
	//TODO there is a need to add in validations to check if the Mapper is working for Ticket related Mappers atleast.

	@Test
	public void deleteTicketGivenRightTicketNumberRecivesTrueAfterDelete() throws InterruptedException {
		when(ticketService.deleteTicket(eq(uuid),eq(uuid))).thenReturn(1L);

		assertTrue(sut.deleteTicketWithIdForUserId(uuid,uuid));
	}

	@Test
	public void deleteTicketGivenWrongTicketNumberRecivesFalseAfterDelete() throws InterruptedException {
		when(ticketService.deleteTicket(eq(uuid),eq(uuid))).thenReturn(2L);

		assertFalse(sut.deleteTicketWithIdForUserId(uuid,uuid));
	}

	@Test
	public void deleteTicketThrowsInternalServerErrorWhenServiceThrowsUnExpectedError() throws InterruptedException {
		when(ticketService.deleteTicket(eq(uuid),eq(uuid))).thenThrow(new IllegalStateException("SOME OTHER EXCEPTION"));

		assertThrows(InternalServerException.class,()->sut.deleteTicketWithIdForUserId(uuid,uuid));
	}
}