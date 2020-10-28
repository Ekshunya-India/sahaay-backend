package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.exceptions.InternalServerException;
import com.ekshunya.sahaaybackend.mappers.FeedMapper;
import com.ekshunya.sahaaybackend.mappers.TicketMapper;
import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.daos.TicketType;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import com.google.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class TicketFacade {
    private final TicketService ticketService;
    private final static String ERROR_MESSAGE = "ERROR : There was an Error while processing the request";

    @Inject
    public TicketFacade(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public TicketDto createTicket(@NonNull final TicketCreateDto ticketCreateDto) throws InterruptedException {
        //First time using the Java Fibers. Hopefully its correct.
        ThreadFactory factory = Thread.builder().virtual().factory();
        Future<TicketDto> ticketCreatedDto;
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            ticketCreatedDto = executor.submit(() -> {
                Ticket ticketToSave = TicketMapper.INSTANCE.ticketCreateDtoToTicket(ticketCreateDto);
                //TODO in the next iteration we will change the call with fibers so that only the DB call is inside one of these. Mapper code can be moved out of fiber.
                Ticket createdTicket = this.ticketService.createANewTicket(ticketToSave);
                return TicketMapper.INSTANCE.ticketToTicketDto(createdTicket);
            });
            return ticketCreatedDto.get();
        } catch (ExecutionException exception) {
            log.error(ERROR_MESSAGE, exception);
            throw new BadDataException(Arrays.toString(exception.getStackTrace()));
        }
    }

    public TicketDto updateTicket(@NonNull final TicketDetailsUpdateDto ticketDetailsUpdateDto) throws InterruptedException {
        ThreadFactory factory = Thread.builder().virtual().factory();
        Future<TicketDto> ticketUpdatedFuture;
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            ticketUpdatedFuture = executor.submit(() -> {
                Ticket ticketToUpdate = TicketMapper.INSTANCE.ticketDetailsUpdateDtoToTicket(ticketDetailsUpdateDto);
                Ticket updatedTicket = this.ticketService.updateTicket(ticketToUpdate);
                return TicketMapper.INSTANCE.ticketToTicketDto(updatedTicket);
            });
            return ticketUpdatedFuture.get();
        } catch (ExecutionException exception) {
            log.error(ERROR_MESSAGE, exception);
            throw new BadDataException(Arrays.toString(exception.getStackTrace()));
        }
    }

    public TicketDto fetchTicketFromId(@NonNull final UUID ticketId) throws InterruptedException {
        Future<TicketDto> updatedTicketFuture;
        ThreadFactory factory = Thread.builder().virtual().factory();
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            updatedTicketFuture = executor.submit(() -> {
                Ticket existingTicket = this.ticketService.fetchTicket(ticketId);
                return TicketMapper.INSTANCE.ticketToTicketDto(existingTicket);
            });
            return updatedTicketFuture.get();
        } catch (ExecutionException e) { //This is wrong. We need to catch the indivizual exception that is nested inside the ExecutionException.
            if(e.getMessage().contains("com.ekshunya.sahaaybackend.exceptions.DataNotFoundException")){
                log.error(ERROR_MESSAGE,e);
                throw new DataNotFoundException(e.getMessage());
            }
            throw new BadDataException("There was an error while fetching the data from Database"); //TODO this is wrong. This needs to change.
        }
    }

    public List<TicketDto> fetchAllTicketOfType(@NonNull final String ticketType, @NonNull final String latitude, @NonNull final String longitude) throws InterruptedException {
        Future<List<Ticket>> futureTickets;
        ThreadFactory factory = Thread.builder().virtual().factory();
        TicketType actualTicketType = TicketType.valueOf(ticketType);
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            futureTickets = executor.submit(() -> this.ticketService.fetchAllOpenedTicket(actualTicketType, latitude, longitude));
            List<Ticket> openTickets = futureTickets.get();
            return TicketMapper.INSTANCE.ticketsToTicketDtos(openTickets);
        } catch (ExecutionException e) {
            log.error(ERROR_MESSAGE, e);
            throw new InternalServerException(ERROR_MESSAGE);
        }
    }

    public TicketDto updateTicketWithFeed(@NonNull final TicketFeedDto ticketFeedDto) throws InterruptedException {
        Feed newFeed = FeedMapper.INSTANCE.ticketFeedToFeed(ticketFeedDto);
        ThreadFactory factory = Thread.builder().virtual().factory();
        Ticket updatedTicket;
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            Future<Ticket> ticketFuture = executor.submit(() ->
                    this.ticketService.updateWithFeed(newFeed));
            updatedTicket = ticketFuture.get();
        } catch (ExecutionException e) {
            log.error(ERROR_MESSAGE, e);
            throw new InternalServerException(ERROR_MESSAGE);
        }
        return TicketMapper.INSTANCE.ticketToTicketDto(updatedTicket);
    }

    public boolean deleteTicketWithId(@NonNull final String ticketId) throws InterruptedException {
        //TODO delete a Ticket in MongoDB
        ThreadFactory factory = Thread.builder().virtual().factory();
        UUID ticketIdToDelete = UUID.fromString(ticketId);
        Future<Boolean> deletedFlagFuture;
        boolean isDeleted = false;
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            deletedFlagFuture = executor.submit(() -> this.ticketService.deleteTicket(ticketIdToDelete));
            isDeleted = deletedFlagFuture.get();
        } catch (ExecutionException e) {
            log.error(ERROR_MESSAGE, e);
            throw new InternalServerException(ERROR_MESSAGE);
        }
        return isDeleted;
    }

    public List<TicketDto> fetchAllTicketsForUser(@NonNull final String userIdAsString) throws InterruptedException {
        ThreadFactory factory = Thread.builder().virtual().factory();
        UUID userId = UUID.fromString(userIdAsString);
        Future<List<Ticket>> ticketsFuture;
        List<Ticket> ticketDtosToReturn;
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            ticketsFuture = executor.submit(() -> this.ticketService.fetchAllOpenTicketsForUser(userId));
            ticketDtosToReturn = ticketsFuture.get();
        } catch (ExecutionException e) {
            log.error(ERROR_MESSAGE, e);
            throw new InternalServerException(ERROR_MESSAGE);
        }
        return TicketMapper.INSTANCE.ticketsToTicketDtos(ticketDtosToReturn);
    }
}