package com.ekshunya.sahaaybackend.mappers;

import com.ekshunya.sahaaybackend.model.daos.Comment;
import com.ekshunya.sahaaybackend.model.dtos.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface CommentMapper {
	CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
	CommentDto commentToCommentDto(final Comment comment);
	default UUID stringToUUID(final String userId){
		return UUID.fromString(userId);
	}
	default String uuidToString(final UUID userId){
		return userId.toString();
	}
}