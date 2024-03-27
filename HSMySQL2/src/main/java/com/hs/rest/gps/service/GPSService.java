package com.hs.rest.gps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hs.rest.gps.model.GPS;
import com.hs.rest.gps.repository.GPSRepository;

@Service
public class GPSService {
	
	@Autowired
	//@Qualifier("GPSRepositoryImpl")
	private GPSRepository gpsRepository;
	
	// 多筆查詢
	public List<GPS> findAllGps() {
		List<GPS> gpsList = gpsRepository.findAllGps();
		return gpsList;
	}
	
	// 單筆查詢
	public GPS findById(Integer id) {
		if(id == null) {
			return null;
		}
		GPS gps = gpsRepository.getGpsById(id);
		return gps;
	}
	
	public Boolean addGPS(Double latitude,
			Double longitude, Integer meter,
			String location, String locationName) {
		// 組裝 GPS 物件
		GPS gps = new GPS();
		gps.setLatitude(latitude);
		gps.setLongitude(longitude);
		gps.setMeter(meter);
		gps.setLocation(location);
		gps.setLocationName(locationName);
		// 將 GPS 物件傳送給 gpsRepository.addGPS 去新增
		Boolean status = gpsRepository.addGPS(gps);
		return status;
	}
	
	
	
	public Boolean deleteGps(Integer id) {
		Boolean status = gpsRepository.deleteGPSById(id);
		return status;
	}
	
}
