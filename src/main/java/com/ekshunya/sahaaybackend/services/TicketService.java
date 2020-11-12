package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.daos.State;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.daos.TicketType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;

//TODO add a Kafka Event which will be helpful in recognizing every transcation to the Database so that Sahaay Coins can be linked here.
@Slf4j
public class TicketService {
    private final MongoClientSettings clientSettings;
    private final ObjectMapper objectMapper;
    private final static String ERROR = "ERROR : There was an error while parsing the data for ";

    @Inject
    public TicketService(final MongoClientSettings clientSettings, final ObjectMapper objectMapper) {
        this.clientSettings = clientSettings;
        this.objectMapper = objectMapper;
    }


    //TODO add UUID to the ticket here. Lets not wait for the Id of the Resource from the DB.
        public boolean createANewTicket(@NonNull final Ticket ticketToSave) {
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("sahaay-db");
            MongoCollection<Ticket> tickets = db.getCollection("ticket", Ticket.class);
            ticketToSave.setId(UUID.randomUUID());
            return tickets.insertOne(ticketToSave).wasAcknowledged();
        }
    }

    public Ticket updateTicket(@NonNull final Ticket ticketToUpdate) {
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("sahaay-db");
            MongoCollection<Ticket> tickets = db.getCollection("ticket", Ticket.class);
            Document mongoDocumentToUpdate = Document.parse(this.objectMapper.writeValueAsString(ticketToUpdate));
            FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true);
            return tickets.findOneAndUpdate(eq("id", ticketToUpdate.getId()), mongoDocumentToUpdate, options);
        } catch (JsonProcessingException e) {
            log.error(ERROR);
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

    /**
     * THis method is to update a ticket with a new feed.
     * @param newFeed the new Feed to be added to Mongo.
     * @param ticketId ticketId to which the feed needs to be added.
     * @return long value which indicates the number of document updated. This ideally should be one for a Feed Add.
     */
    public long updateWithFeed(final Feed newFeed, final UUID ticketId) {
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("sahaay-db");
            MongoCollection<Ticket> tickets = db.getCollection("ticket", Ticket.class);
            Document feedToAdd = Document.parse(this.objectMapper.writeValueAsString(newFeed));
            return tickets.updateOne(Filters.eq("id",ticketId), Updates.push("id.feeds.$.",feedToAdd)).getMatchedCount();  //TODO the fieldName in Updates.push needs to be looked at properly.
        } catch (JsonProcessingException e) {
            log.error(ERROR);
            throw new BadDataException("There was an error while converting the data to Json format");
        }
    }

    /**
     * A method to delete a ticket from the DB.
     * @param ticketIdToDelete the Id of the ticket to delete.
     * @param userId the Id of the User who the ticket was opened by. //TODO we need to add the funcanality that userId with Sahaay Premium can also delete the ticket.
     * @return the value of how many documents were deleted. Deleting a ticket ideally should give back a result of one.
     */   //TODO currently the functionality is that only the user who opened the ticket can delete the ticket. This will change in the future releases.
          //TODO i already can imagine a new deleteTicket fuction because Problem type does not have user Id associated with it, So we just have to delete the data.
    public long deleteTicket(final UUID ticketIdToDelete, final UUID userId) {
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("sahaay-db");
            MongoCollection<Ticket> tickets = db.getCollection("ticket", Ticket.class); //
            return tickets.deleteOne(and(eq("id",ticketIdToDelete),eq("openedBy",userId), all("state", State.values()))).getDeletedCount();
        }
    }

    public List<Ticket> fetchAllOpenTicketsForUser(@NonNull final UUID userId,
                                                           @NonNull final String sortBy,
                                                           @NonNull final String greaterThanValue,
                                                           @NonNull final String limitValuesTo) {
        int limit;
        try{
            limit = Integer.parseInt(limitValuesTo);
        } catch (NumberFormatException numberFormatException){
            limit=20;
        }
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("sahaay-db");
            MongoCollection<Ticket> tickets = db.getCollection("ticket", Ticket.class); //
            List<Ticket> ticketsToReturn = new ArrayList<>();
            FindIterable<Ticket> ticketFindIterable;
            //TODO currently the we assume we need only in Awscending order. This needs to change when the user wants in anotother order.
            ticketFindIterable = tickets.find(and(eq("openedBy", userId),gt(sortBy,greaterThanValue),ascending(sortBy))).limit(limit);
            while (ticketFindIterable.cursor().hasNext()){
                ticketsToReturn.add(ticketFindIterable.cursor().next());
            }
            return ticketsToReturn;
        }
    }
}