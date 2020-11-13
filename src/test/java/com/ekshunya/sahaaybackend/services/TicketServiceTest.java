package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import com.ekshunya.sahaaybackend.model.daos.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.and;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private MongoClientSettings clientSettings;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private TicketService sut;
    @Mock
    private MongoDatabase db;
    @Mock
    private MongoCollection<Ticket> tickets;
    @Mock
    private MongoClient mongoClient;
    @Captor
    private ArgumentCaptor<Ticket> ticketArgumentCaptor;
    @Captor
    private ArgumentCaptor<Bson> documentArgumentCaptor;
    @Captor
    private ArgumentCaptor<Bson> filtersArgumentCaptor;
    @Captor
    private ArgumentCaptor<FindOneAndUpdateOptions> findOneAndUpdateOptionsArgumentCaptor;
    @Mock
    private FindIterable<Ticket> findIterable;
    @Mock
    private UpdateResult updateResult;
    @Mock
    private DeleteResult deleteResult;
    @Mock
    private MongoCursor<Ticket> mongoCursor;
    private static final FindOneAndUpdateOptions UPSERT_OPTIONS = new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER);
    @Captor
    private ArgumentCaptor<FindOneAndUpdateOptions> optionsArgumentCaptor;
    private Bson FILTER;
    private Ticket validTicket;
    private UUID uuid;
    private Location location;
    private Feed newFeed;
    private FindOneAndUpdateOptions options;

    @BeforeEach
    public void setUp() {
        this.uuid = UUID.randomUUID();
        location = new Location(2L, 3L);
        validTicket = new Ticket(uuid, "SOME_TITLE", "SOME_DESC", ZonedDateTime.now(), ZonedDateTime.now(), null,
                uuid, uuid, location, Priority.P1, TicketType.PROBLEM, State.OPENED, 1, 0, 0, new ArrayList<>(), new ArrayList<>(), Arrays.asList("ROAD_WORK", "ROAD REPAIR", "ROADS"));
        when(mongoClient.getDatabase(eq("sahaay-db"))).thenReturn(db);
        when(db.getCollection(eq("ticket"), eq(Ticket.class))).thenReturn(tickets);
        newFeed = new Feed(uuid, ZonedDateTime.now(), ZonedDateTime.now(), new ArrayList<>(), uuid);
        FILTER = Filters.eq("id", this.uuid);
        options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true);
    }

    @Test
    public void aValidCreateTicketGivesBackTrueWhenInserted() {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(tickets.insertOne(eq(validTicket))).thenReturn(InsertOneResult.acknowledged(null));

            sut.createANewTicket(validTicket);

            verify(tickets, times(1)).insertOne(ticketArgumentCaptor.capture());
            Ticket actualTicket = ticketArgumentCaptor.getValue();
            assertEquals(validTicket, actualTicket);
            assertNotNull(validTicket.getId());
        }
    }

    @Test
    public void anyExceptionThrownInCreateIsSentBack() {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);

            when(tickets.insertOne(eq(validTicket))).thenThrow(new IllegalStateException("SOME PROBLEM HAPPENED GOLEM"));
            assertThrows(IllegalStateException.class, () -> sut.createANewTicket(validTicket));
        }
    }

    @Test
    public void updateTicketThrowsBadDataExceptionWhenThereIsAJsonProcessingException() throws JsonProcessingException {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(objectMapper.writeValueAsString(eq(validTicket))).thenThrow(new JsonGenerationException("SOME MESSAGE"));
            assertThrows(BadDataException.class, () -> sut.updateTicket(validTicket));
        }
    }

    @Test
    public void upddateTicketSendsDataToMongoDbAndSendsAValidTicketBack() throws JsonProcessingException {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(objectMapper.writeValueAsString(eq(validTicket))).thenReturn(new ObjectMapper().writeValueAsString(validTicket));
            Document mongoDocumentToUpdate = Document.parse(this.objectMapper.writeValueAsString(validTicket));
            sut.updateTicket(validTicket);
            verify(tickets, times(1)).findOneAndUpdate(eq(Filters.eq("id", uuid)), eq(mongoDocumentToUpdate), optionsArgumentCaptor.capture());
            FindOneAndUpdateOptions actualOptions = optionsArgumentCaptor.getValue();
            assertTrue(actualOptions.isUpsert());
            assertEquals(ReturnDocument.AFTER, actualOptions.getReturnDocument());
        }
    }

    @Test
    public void updateTicketPropagatesExceptionInFindOneAndUpdate() throws JsonProcessingException {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(objectMapper.writeValueAsString(eq(validTicket))).thenReturn(new ObjectMapper().writeValueAsString(validTicket));
            //Document mongoDocumentToUpdate = Document.parse(this.objectMapper.writeValueAsString(validTicket));
            when(tickets.findOneAndUpdate(eq(Filters.eq("id", uuid)), any(Document.class), any(FindOneAndUpdateOptions.class))) //TODO this needs to chamge. We need to capture the document and verify and also equate the FindOneAndUpdateOptions.
                    .thenThrow(new IllegalStateException("SOME UNKNOWN EXCEPTION"));
            assertThrows(IllegalStateException.class, () -> sut.updateTicket(validTicket));
        }
    }

    @Test
    public void validIdSentToTheIdIsGivenToMongoDb() {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(tickets.find(eq(FILTER), eq(Ticket.class))).thenReturn(findIterable);
            when(findIterable.first()).thenReturn(validTicket);
            Ticket actualTicket = sut.fetchTicket(uuid);
            assertEquals(validTicket, actualTicket);
        }
    }

    //TODO here we are propagating the all DB exceptions out of service layer to Facade. Atleast in Facade we need to capture the DB exceptions.
    @Test
    public void AnyExceptionThrownByTheMongoCollectionIsPropagatedBack() {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(tickets.find(any(Bson.class), eq(Ticket.class))).thenThrow(new IllegalStateException("SOMETHING BAD HAPPENED ALFRED"));
            assertThrows(IllegalStateException.class, () -> sut.fetchTicket(uuid));
        }
    }

    @Test
    public void updateWithFeedUpdatesItInMongoDb() throws JsonProcessingException {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(objectMapper.writeValueAsString(eq(newFeed))).thenReturn(new ObjectMapper().writeValueAsString(newFeed));
            Document feedToAdd = Document.parse(new ObjectMapper().writeValueAsString(newFeed));
            Bson updates = Updates.push("id.feeds.$.", feedToAdd);
            when(tickets.updateOne(eq(FILTER), eq(updates))).thenReturn(updateResult);
            when(updateResult.getMatchedCount()).thenReturn(1L);
            sut.updateWithFeed(newFeed, uuid);
            verify(tickets, times(1)).updateOne(eq(FILTER), eq(updates));
        }
    }

    @Test
    public void updateWithFeedThrowsBadDataExceptionWhenJsonProcessingIsThrown() throws JsonProcessingException {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(objectMapper.writeValueAsString(eq(newFeed))).thenThrow(new JsonMappingException("SOME BAD DATA"));

            assertThrows(BadDataException.class, () -> sut.updateWithFeed(newFeed, uuid));
        }
    }

    @Test
    public void deleteTicketsPassesOnTheIdsToMongoLikeAGoodBoy() {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            Bson andDeleteBson = and(Filters.eq("id", uuid), Filters.eq("openedBy", uuid), all("state", State.values()));
            when(tickets.deleteOne(eq(andDeleteBson))).thenReturn(deleteResult);
            when(deleteResult.getDeletedCount()).thenReturn(1L);
            sut.deleteTicket(uuid, uuid);
            verify(tickets, times(1)).deleteOne(eq(andDeleteBson));
        }
    }

    @Test
    public void deleteTicketPropagatesExceptionThrownByMongoDb() {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            Bson andDeleteBson = and(Filters.eq("id", uuid), Filters.eq("openedBy", uuid), all("state", State.values()));
            when(tickets.deleteOne(eq(andDeleteBson))).thenThrow(new IllegalStateException("SOME THING REALLY BAD"));
            assertThrows(IllegalStateException.class, () -> sut.deleteTicket(uuid, uuid));
        }
    }

    @Test
    public void validNumberNotGivenToLimitThenGivesBack20Results() {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(tickets.find(any(Bson.class))).thenReturn(findIterable);
            when(findIterable.limit(eq(20))).thenReturn(findIterable);
            when(findIterable.cursor()).thenReturn(mongoCursor);
            when(mongoCursor.hasNext()).thenReturn(false);
            sut.fetchAllTickets(TicketType.PROBLEM, 22.00, 23.00, "created", "last_element", "SOME_INVALID_VALUES");

            verify(tickets, times(1)).find(documentArgumentCaptor.capture());
            verify(findIterable, times(1)).limit(eq(20));
        }
    }

    @Test
    public void whenNoLastValueFoundThenItIsNotUsedInTheQuery() {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(tickets.find(any(Bson.class))).thenReturn(findIterable);
            when(findIterable.limit(eq(30))).thenReturn(findIterable);
            when(findIterable.cursor()).thenReturn(mongoCursor);
            when(mongoCursor.hasNext()).thenReturn(false);

            sut.fetchAllTickets(TicketType.PROBLEM, 22.00, 23.00, "created", "NO_VALUE", "30");
            verify(tickets, times(1)).find(documentArgumentCaptor.capture());
            verify(findIterable, times(1)).limit(eq(30));
            Bson filters = documentArgumentCaptor.getValue();
            assertTrue("And Filter{filters=[Geometry Operator Filter{fieldName='location', operator='$near', geometry=Point{coordinate=Position{values=[23.0, 22.0]}}, maxDistance=100.0, minDistance=0.0}, Filter{fieldName='ticketType', value=PROBLEM}, {\"created\": 1}]}\n"
                    .contains(filters.toString()));  //TODO i do not like this unit test as this is not correctly checking the required fileters.
            //TODO this is not testing the ascending order filter as well. This needs to change.
        }
    }

    @Test
    public void whenValidLastValueFoundThenItIsUsedInTheQuery() {
        try (MockedStatic mocked = mockStatic(MongoClients.class)) {
            mocked.when(() -> MongoClients.create(eq(clientSettings))).thenReturn(mongoClient);
            when(tickets.find(any(Bson.class))).thenReturn(findIterable);
            when(findIterable.limit(eq(30))).thenReturn(findIterable);
            when(findIterable.cursor()).thenReturn(mongoCursor);
            when(mongoCursor.hasNext()).thenReturn(false);

            sut.fetchAllTickets(TicketType.PROBLEM, 22.00, 23.00, "created", "some_last_value", "30");
            verify(tickets, times(1)).find(documentArgumentCaptor.capture());
            verify(findIterable, times(1)).limit(eq(30));
            Bson filters = documentArgumentCaptor.getValue();
            assertTrue("And Filter{filters=[Geometry Operator Filter{fieldName='location', operator='$near', geometry=Point{coordinate=Position{values=[23.0, 22.0]}}, maxDistance=100.0, minDistance=0.0}, Filter{fieldName='ticketType', value=PROBLEM}, Operator Filter{fieldName='created', operator='$gt', value=some_last_value}, {\"created\": 1}]}\n"
            .contains(filters.toString()));
        }
    }
}