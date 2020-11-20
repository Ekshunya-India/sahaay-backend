package com.ekshunya.sahaaybackend.services.multipart;


import com.ekshunya.sahaaybackend.exceptions.InternalServerException;
import com.ekshunya.sahaaybackend.model.daos.Attachment;
import com.ekshunya.sahaaybackend.model.daos.AttachmentType;
import io.undertow.server.handlers.form.FormData;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class AttachmentProcessor {
    private final VideoProcessor videoProcessor;
    private final AudioProcessor audioProcessor;
    private final ImageProcessor imageProcessor;
    private final DocumentProcessor documentProcessor;
    private static final String FILE_PATH=""; //TODO this needs to be picked from the properties files.

    public AttachmentProcessor(VideoProcessor videoProcessor, AudioProcessor audioProcessor, ImageProcessor imageProcessor, DocumentProcessor documentProcessor) {
        this.videoProcessor = videoProcessor;
        this.audioProcessor = audioProcessor;
        this.imageProcessor = imageProcessor;
        this.documentProcessor = documentProcessor;
    }

    public List<Attachment> processAttachment(@NonNull final FormData attachment, @NonNull final String ticketId) {
        ThreadFactory factory = Thread.builder().virtual().factory();
        Future<List<Attachment>> listOfAttachment;
        try (var executor = Executors.newThreadExecutor(factory).withDeadline(Instant.now().plusSeconds(2))) {
            listOfAttachment = executor.submit(()->{
                    return readAttachment(attachment,ticketId);
                });
            return listOfAttachment.get();
        } catch (InterruptedException e) {
            log.error("There was an Interupption to the File Upload",e);
        } catch (ExecutionException e) {
            log.error("There was an execution exception while uploading the files",e);
            throw new InternalServerException("There was an error while uploading the files in the Feed");
        }
        //If the control comes here that means there was an exception so we return empty list.
        return new ArrayList<>();
    }

    private List<Attachment> readAttachment(@NonNull final FormData formData,
                                            @NonNull final String ticketId){
        List<Attachment> attachmentsToReturn = new ArrayList<>();
        for (String data : formData) {
            for (FormData.FormValue formValue : formData.get(data)) {
                if (formValue.isFileItem()) {
                    // Process file here
                    try {
                        File fileToUpload = formValue.getFileItem().getFile().toFile();
                        String fileType = getFileExtension(fileToUpload);
                        AttachmentType attachmentType = AttachmentType.valueOf(fileType.toUpperCase(Locale.ROOT));
                        UUID attachmentId = UUID.randomUUID();
                        String pathOfAttachment = saveToLocalFolder(fileToUpload, ticketId ,attachmentId);
                        attachmentsToReturn.add(new Attachment(attachmentType,pathOfAttachment,attachmentId));
                    } catch (IOException e) {
                        log.error("There was an IO exception when saving a new Feed to ticket", e);
                    }
                }
            }
        }
        return attachmentsToReturn;
    }

    private String getFileExtension(@NonNull final File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf+1);
    }

    //TODO this will change in the future. We need to put all the file data to the cloud. For the initial prototype we will just put this here.
    private String saveToLocalFolder(@NonNull final File fileToSave, @NonNull final String ticketId, @NonNull final UUID attachmentId) throws IOException {
            //The location of the file
        String filePathToSave = FILE_PATH+"/"+ticketId+"/";
        Path newPath = Files.move(Paths.get(fileToSave.getPath()), Paths.get(filePathToSave+attachmentId.toString()));
        return newPath.toUri().getPath();
    }
}
