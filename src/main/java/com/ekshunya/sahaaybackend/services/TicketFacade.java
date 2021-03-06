package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.exceptions.InternalServerException;
import com.ekshunya.sahaaybackend.mapper.MainMapper;
import com.ekshunya.sahaaybackend.model.daos.Attachment;
import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.daos.TicketType;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import com.ekshunya.sahaaybackend.services.multipart.AttachmentProcessor;
import com.google.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

@Slf4j
public class TicketFacade {
    private final TicketService ticketService;
    private final MainMapper mainMapper;
    private final static String ERROR_MESSAGE = "ERROR : There was an Error while processing the request";
    private final AttachmentProcessor attachmentProcessor;

    @Inject
    public TicketFacade(final TicketService ticketService, final MainMapper mainMapper, final AttachmentProcessor attachmentProcessor) {
        this.ticketService = ticketService;
        this.mainMapper=mainMapper;
        this.attachmentProcessor = attachmentProcessor;
    }

    public UUID createTicket(@NonNull final TicketCreateDto ticketCreateDto) throws InterruptedException {
        //First time using the Java Fibers. Hopefully its correct.
        ThreadFactory factory = Thread.builder().virtual().factory();
        Future<UUID> ticketCreatedAck;
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            ticketCreatedAck = executor.submit(() -> {
                Ticket ticketToSave = this.mainMapper.ticketCreateDtoToTicket(ticketCreateDto);
                ticketToSave.setId(UUID.randomUUID());
                //TODO in the next iteration we will change the call with fibers so that only the DB call is inside one of these. Mapper code can be moved out of fiber.
                return this.ticketService.createANewTicket(ticketToSave);
            });
            return ticketCreatedAck.get();
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
                log.info("******************");
                log.info(this.mainMapper.toString());
                Ticket ticketToUpdate = this.mainMapper.ticketDetailsUpdateDtoToTicket(ticketDetailsUpdateDto);
                if (ticketToUpdate == null) {
                    throw new BadDataException("ERROR : The Incoming Ticket Details were of in-valid format");   //TODO this results in 2 exception to be thrown.
                }
                Ticket updatedTicket = this.ticketService.updateTicket(ticketToUpdate);
                return this.mainMapper.ticketToTicketDto(updatedTicket);
            });
            return ticketUpdatedFuture.get();
        } catch (ExecutionException exception) {
            log.error(ERROR_MESSAGE, exception);
            if (exception.getMessage().contains("com.ekshunya.sahaaybackend.exceptions.BadDataException")) {
                throw new BadDataException(Arrays.toString(exception.getStackTrace()));
            }
            throw new InternalServerException("ERROR : There was an exception while processing the request to Update a ticket");
        }
    }

    public TicketDto fetchTicketFromId(@NonNull final UUID ticketId) throws InterruptedException {
        Future<TicketDto> updatedTicketFuture;
        ThreadFactory factory = Thread.builder().virtual().factory();
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            updatedTicketFuture = executor.submit(() -> {
                Ticket existingTicket = this.ticketService.fetchTicket(ticketId);
                return this.mainMapper.ticketToTicketDto(existingTicket);
            });
            return updatedTicketFuture.get();
        } catch (ExecutionException e) { //TODO This is wrong. We need to catch the indivizual exception that is nested inside the ExecutionException.
            if(e.getMessage().contains("com.ekshunya.sahaaybackend.exceptions.DataNotFoundException")){
                log.error(ERROR_MESSAGE,e);
                throw new DataNotFoundException(e.getMessage());
            }
            throw new BadDataException("ERROR : There was an error while converting the data from Database"); //TODO this is wrong. This needs to change.
        }
    }
//TODO we need to also add the logic of valueOfLastElement being "NO_VALUE". in the service that is.
    public List<TicketDto> fetchAllTicket(@NonNull final String ticketType,
                                          @NonNull final String latitude,
                                          @NonNull final String longitude,
                                          @NonNull final String sortBy,
                                          @NonNull final String valueOfLastElement,
                                          @NonNull final String limitValuesTo) throws InterruptedException {
        Future<List<Ticket>> futureTickets;
        ThreadFactory factory = Thread.builder().virtual().factory();
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            TicketType actualTicketType = TicketType.valueOf(ticketType);
            double lat = Double.parseDouble(latitude);
            double lng = Double.parseDouble(longitude);
            futureTickets = executor.submit(() -> this.ticketService.fetchAllTickets(actualTicketType, lat, lng, sortBy, valueOfLastElement, limitValuesTo));
            List<Ticket> openTickets = futureTickets.get();
            return openTickets.stream().map(this.mainMapper::ticketToTicketDto).collect(Collectors.toList());
        } catch (ExecutionException e) {
            log.error(ERROR_MESSAGE, e);
            if(e.getMessage().contains("com.ekshunya.sahaaybackend.exceptions.DataNotFoundException")){
                throw new DataNotFoundException(e);
            }
            throw new InternalServerException(ERROR_MESSAGE);
        } catch (IllegalArgumentException e){
            log.error(ERROR_MESSAGE, e);
            throw new BadDataException(e);
        }
    }

    //TODO add unit tests to cover this method.
    public boolean updateTicketWithFeed(@NonNull final TicketFeedDto ticketFeedDto) throws InterruptedException {
        ThreadFactory factory = Thread.builder().virtual().factory();
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            Feed newFeed =  this.mainMapper.ticketFeedToTicket(ticketFeedDto);
            List<Attachment> attachments = attachmentProcessor.processAttachment(ticketFeedDto.getFormData(), ticketFeedDto.getId().toString());
            newFeed.setAttachments(attachments);
            Future<Long> ticketFuture = executor.submit(() ->
                    this.ticketService.updateWithFeed(newFeed,ticketFeedDto.getId()));
            return ticketFuture.get().equals(1L);
        } catch (ExecutionException e) {
            log.error(ERROR_MESSAGE, e);
            if(e.getMessage().contains("com.ekshunya.sahaaybackend.exceptions.BadDataException")){
                throw new BadDataException("There was a problem while converting the Data"); //TODO This rethrow might distort the logs as the logs will show 2 exceptions in place of one.
            }
            throw new InternalServerException(ERROR_MESSAGE);
        }
    }

    //TODO add unit tests to cover this method.
    public boolean deleteTicketWithIdForUserId(final @NonNull UUID ticketId, @NonNull final UUID userId) throws InterruptedException {
        //TODO delete a Ticket in MongoDB
        ThreadFactory factory = Thread.builder().virtual().factory();
        Future<Long> deletedFlagFuture;
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            deletedFlagFuture = executor.submit(() -> this.ticketService.deleteTicket(ticketId,userId));
            return deletedFlagFuture.get().equals(1L);
        } catch (ExecutionException e) {
            log.error(ERROR_MESSAGE, e);
            throw new InternalServerException(ERROR_MESSAGE);
        }
    }

    //TODO add unit tests to cover this method.
    public List<TicketDto> fetchAllTicketsPaginatedForUser(@NonNull final String userIdAsString,
                                                           @NonNull final String sortBy,
                                                           @NonNull final String greaterThanValue,
                                                           @NonNull final String limitValuesTo) throws InterruptedException {
        ThreadFactory factory = Thread.builder().virtual().factory();
        UUID userId = UUID.fromString(userIdAsString);
        Future<List<Ticket>> ticketsFuture;
        List<Ticket> ticketDtosToReturn;

        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            ticketsFuture = executor.submit(() -> this.ticketService.fetchAllOpenTicketsForUser(userId, sortBy, greaterThanValue,limitValuesTo));
            ticketDtosToReturn = ticketsFuture.get();
        } catch (ExecutionException e) {
            log.error(ERROR_MESSAGE, e);
            throw new InternalServerException(ERROR_MESSAGE);
        }
        return ticketDtosToReturn.stream().map(this.mainMapper::ticketToTicketDto).collect(Collectors.toList());
    }
}