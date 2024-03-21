package com.hs.rest.gps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hs.rest.gps.model.GPS;
import com.hs.rest.gps.repository.GPSRepository;

@Service
public class GPSService {
	
	@Autowired
	//@Qualifier("GPSRepositoryImpl")
	private GPSRepository gpsRepository;
	
	public Boolean addGPS(Double latitude,
			Double longitude, Integer meter,
			String location, String locationName) {
		// 組裝 GPS 物件
		GPS gps = new GPS();
		gps.setLatitude(latitude);
		gps.setLongitude(longitude);
		gps.setMeter(meter);
		gps.setLongitude(longitude);
		gps.setLocationName(locationName);
		Boolean status = gpsRepository.addGPS(gps);
		return status;
	}
}
