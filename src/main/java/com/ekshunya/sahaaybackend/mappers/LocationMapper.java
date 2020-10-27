package com.ekshunya.sahaaybackend.mappers;

import com.ekshunya.sahaaybackend.model.daos.Location;
import com.ekshunya.sahaaybackend.model.dtos.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
	LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);
	LocationDto locationToLocationDto(final Location location);
}