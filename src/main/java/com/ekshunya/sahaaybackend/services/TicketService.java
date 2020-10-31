package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.daos.TicketType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@Slf4j
public class TicketService {
    private final MongoClientSettings clientSettings;
    private final ObjectMapper objectMapper;

    @Inject
    public TicketService(final MongoClientSettings clientSettings, final ObjectMapper objectMapper) {
        this.clientSettings = clientSettings;
        this.objectMapper = objectMapper;
    }


    //TODO add UUID to the ticket here. Lets not wait for the Id of the Resource from the DB.
    public boolean createANewTicket(final Ticket ticketToSave) {
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("sahaay-db");
            MongoCollection<Ticket> tickets = db.getCollection("ticket", Ticket.class);
            return tickets.insertOne(ticketToSave).wasAcknowledged();
        }
    }

    public Ticket updateTicket(final Ticket ticketToUpdate) {
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("sahaay-db");
            MongoCollection<Ticket> tickets = db.getCollection("ticket", Ticket.class);
            Document mongoDocumentToUpdate = Document.parse(this.objectMapper.writeValueAsString(ticketToUpdate));
            return tickets.findOneAndUpdate(eq("id", ticketToUpdate.getId()), mongoDocumentToUpdate);
        } catch (JsonProcessingException e) {
            log.error("There was an error while parsing the data");
            throw new BadDataException("There was an error while converting the data to Json format");
        }
    }

    public Ticket fetchTicket(final UUID ticketId) throws DataNotFoundException {
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("sahaay-db");
            MongoCollection<Ticket> tickets = db.getCollection("ticket", Ticket.class);
            return tickets.find(eq("id",ticketId),Ticket.class).first();
        }
    }

    public List<Ticket> fetchAllOpenedTicket(final TicketType actualTicketType, final double latitude, final double longitude) {
        //TODO put inside a Java Fiber. If 16 SDK does not work out then we can put this inside a Kotlin Global Async
        //TODO do a pagination using bucket pattern in mongo DB.
        // https://www.mongodb.com/blog/post/paging-with-the-bucket-pattern--part-1
        // https://www.mongodb.com/blog/post/paging-with-the-bucket-pattern--part-2
        throw new NotImplementedException("Yet to Implement");
    }

    public Ticket updateWithFeed(final Feed newFeed) {
        //TODO put inside a Java Fiber the call to MongoDB.
        throw new NotImplementedException("Yet to Implement");
    }

    public boolean deleteTicket(final UUID ticketIdToDelete) {

        //TODO put inside a Java Fiber the call to MongoDB.
        throw new NotImplementedException("Yet to Implement");
    }

    public List<Ticket> fetchAllOpenTicketsForUser(final UUID userId) {
        //TODO put inside a Java Fiber the call to MongoDB.
        throw new NotImplementedException("Yet to Implement");
    }
}
