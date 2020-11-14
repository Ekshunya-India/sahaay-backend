package com.ekshunya.sahaaybackend.services.multipart;


import io.undertow.server.handlers.form.FormData;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AttachmentProcessor {
    private final VideoProcessor videoProcessor;
    private final AudioProcessor audioProcessor;
    private final ImageProcessor imageProcessor;
    private final DocumentProcessor documentProcessor;

    public void processAttachment(@NonNull final FormData attachment) {
        //TODO Use Virtual Threads and call different Processor based on different types of files.
    }
}
