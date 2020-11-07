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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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
	private String sortBy;
	private String valueOfLastElement;
	private String limitValuesTo;
	private TicketCreateDto ticketCreateDto;
	private TicketCreateDto invalidCreateDto;
	private LocationDto locationDto;
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

	@Before
	public void setUp() throws Exception {
		sortBy="created";
		limitValuesTo="20";
		valueOfLastElement = "SOME_VALUE";
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
		invalidCreateDto = new TicketCreateDto(uuid, locationDto, "SOME_BAD_DATA",1,
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
		when(mainMapper.ticketDetailsUpdateDtoToTicket(eq(ticketDetailsUpdateDto))).thenReturn(validTicket);
		sut.updateTicket(ticketDetailsUpdateDto);

		verify(this.ticketService,times(1)).updateTicket(ticketArgumentCaptor.capture());
		assertEquals(ticketArgumentCaptor.getValue(),validTicket);
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
		sut.fetchTicketFromId(uuid);
		verify(ticketService,times(1)).fetchTicket(eq(uuid));
		}


	@Test(expected = BadDataException.class)
	public void whenInValidDataGivenToFetchThrowsBadDataException() throws InterruptedException {
		sut.fetchAllTicket("SOME_TICKETTYPE",String.valueOf(LAT),String.valueOf(LNG),sortBy,valueOfLastElement,limitValuesTo);
	}

	@Test(expected = BadDataException.class)
	public void whenInValidDataGivenToFetchThrowsBadDataExceptionWhenParsingWrongDouble() throws InterruptedException {
		sut.fetchAllTicket("PROBLEM","SOME_BAD_DATA",String.valueOf(LNG),sortBy,valueOfLastElement,limitValuesTo);
	}

	@Test(expected = DataNotFoundException.class)
	public void whenDataNotFoundExceptionIsThrownInExcecutorThenItIsPropagated() throws InterruptedException {
		when(ticketService.fetchAllOpenedTicket(eq(TicketType.PROBLEM),eq(LAT),eq(LNG),eq(sortBy),eq(valueOfLastElement),eq(limitValuesTo)))
				.thenThrow(new DataNotFoundException("SOME WEIRED EXCEPTION THROWN HERE"));

		sut.fetchAllTicket(TicketType.PROBLEM.name(),String.valueOf(LAT), String.valueOf(LNG),sortBy,valueOfLastElement,limitValuesTo);
	}

	@Test
	public void validDataGivenToFetchAllTicketsValidDataIsPassedOnToService() throws InterruptedException {
		when(ticketService.fetchAllOpenedTicket(eq(TicketType.PROBLEM),eq(LAT),eq(LNG),eq(sortBy),eq(valueOfLastElement),eq(limitValuesTo))).thenReturn(new ArrayList<>());

		sut.fetchAllTicket(TicketType.PROBLEM.name(),String.valueOf(LAT), String.valueOf(LNG),sortBy,valueOfLastElement,limitValuesTo);

		verify(ticketService,times(1)).fetchAllOpenedTicket(eq(TicketType.PROBLEM),eq(LAT),eq(LNG),eq(sortBy),eq(valueOfLastElement),eq(limitValuesTo));
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

	@Test(expected = InternalServerException.class)
	public void deleteTicketThrowsInternalServerErrorWhenServiceThrowsUnExpectedError() throws InterruptedException {
		when(ticketService.deleteTicket(eq(uuid),eq(uuid))).thenThrow(new IllegalStateException("SOME OTHER EXCEPTION"));

		sut.deleteTicketWithIdForUserId(uuid,uuid);
	}
}