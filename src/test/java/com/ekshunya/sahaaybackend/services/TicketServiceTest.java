package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import com.ekshunya.sahaaybackend.model.daos.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.json.JsonParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MongoClients.class)
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
    private ArgumentCaptor<Document> documentArgumentCaptor;
    private Ticket validTicket;
    private UUID uuid;
    private Location location;
    @Before
    public void setUp() throws Exception {
        this.uuid = UUID.randomUUID();
        location = new Location(2L,3L);
        validTicket = new Ticket(uuid,"SOME_TITLE","SOME_DESC", ZonedDateTime.now(),ZonedDateTime.now(),null,
                uuid,uuid,location, Priority.P1, TicketType.PROBLEM, State.OPENED,1,0,0,new ArrayList<>(),new ArrayList<>(), Arrays.asList("ROAD_WORK","ROAD REPAIR","ROADS"));
        when(MongoClients.create(eq(clientSettings))).thenReturn(eq(mongoClient));
        when(mongoClient.getDatabase(eq("sahaay-db"))).thenReturn(db);
        when(db.getCollection(eq("ticket"), eq(Ticket.class))).thenReturn(tickets);
    }

    @Test
    public void aValidCreateTicketGivesBackTrueWhenInserted(){
        when(tickets.insertOne(eq(validTicket))).thenReturn(InsertOneResult.acknowledged(null));

        sut.createANewTicket(validTicket);

        verify(tickets.insertOne(ticketArgumentCaptor.capture()));
        assertEquals(validTicket, ticketArgumentCaptor.getValue());
    }

    @Test(expected = IllegalStateException.class)
    public void anyExceptionThrownInCreateIsSentBack(){
        when(tickets.insertOne(eq(validTicket))).thenThrow(new IllegalStateException("SOME PROBLEM HAPPENED GOLEM"));
        sut.createANewTicket(validTicket);
    }

    @Test(expected = BadDataException.class)
    public void updateTicketThrowsBadDataExceptionWhenThereIsAJsonProcessingException() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(eq(validTicket))).thenThrow(new JsonParseException("Bad Data"));
        sut.updateTicket(validTicket);
    }

    @Test
    public void upddateTicketSendsDataToMongoDbAndSendsAValidTicketBack() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(eq(validTicket))).thenReturn(new ObjectMapper().writeValueAsString(validTicket));
        Document mongoDocumentToUpdate = Document.parse(this.objectMapper.writeValueAsString(validTicket));
        sut.updateTicket(validTicket);
        verify(tickets, times(1)).findOneAndUpdate(any(), documentArgumentCaptor.capture());
        assertEquals(documentArgumentCaptor.getValue(), mongoDocumentToUpdate);
    }

    @Test(expected = IllegalStateException.class)
    public void updateTicketPropagatesExceptionInFindOneAndUpdate() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(eq(validTicket))).thenReturn(new ObjectMapper().writeValueAsString(validTicket));
        Document mongoDocumentToUpdate = Document.parse(this.objectMapper.writeValueAsString(validTicket));
        when(tickets.findOneAndUpdate(any(),eq(mongoDocumentToUpdate))).thenThrow(new IllegalStateException("SOME UNKNOWN EXCEPTION"));
        sut.updateTicket(validTicket);
    }
}