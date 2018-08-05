package com.example.service;

import com.example.dto.GeocoderAddressComponent;
import com.example.dto.PlaceDto;

public interface PoiService {
	
	PlaceDto listPlace(String poi);
	
	String getCompleteAddress(Double lat,Double lng,String name);
}
