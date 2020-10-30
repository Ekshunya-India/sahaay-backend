package com.ekshunya.sahaaybackend.model.daos;

import com.mongodb.client.model.geojson.GeoJsonObjectType;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class Location  {
	@NotNull(message = "A location must have a type for it. Perhapes a Point?")
	GeoJsonObjectType type;
	Point point;

	public Location(final double lng, final double lat){
		this.type= GeoJsonObjectType.POINT;
		this.point = new Point(new Position(lng,lat));
	}
}
