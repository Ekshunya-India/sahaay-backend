package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class Attachment {
	@NotNull
	AttachmentType attachmentType;
	String url;
	UUID id; //Generated.
}
