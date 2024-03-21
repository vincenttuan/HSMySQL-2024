package com.hs.rest.gps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.rest.gps.model.GPS;
import com.hs.rest.gps.service.GPSService;

@RestController
@RequestMapping("/gps")
public class GPSController {
	
	@Autowired
	private GPSService gpsService;
	
	// GPS 的 CRUD
	@PostMapping
	public Boolean addGPS(
			@RequestParam("latitude") Double latitude,
			@RequestParam("longitude") Double longitude, 
			@RequestParam("meter") Integer meter, 
			@RequestParam("location") String location,
			@RequestParam("locationName") String locationName) {
		// 檢查參數
		// 略...
		// 傳送參數給 gpsService, 讓 gpsService 幫我新增
		Boolean status = gpsService.addGPS(latitude, longitude, meter, location, locationName);
		return status;
	}
	
	@GetMapping
	public List<GPS> findAllGps() {
		return gpsService.findAllGps();
	}
	
	
}
