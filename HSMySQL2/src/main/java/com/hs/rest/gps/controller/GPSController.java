package com.hs.rest.gps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		List<GPS> gpsList = gpsService.findAllGps();
		return gpsList;
	}
	
	// 刪除
	// 例如: 網址.../gps/1 <- 刪除 id = 1 的紀錄
	// 例如: 網址.../gps/3 <- 刪除 id = 3 的紀錄
	// 例如: 網址.../gps/5 <- 刪除 id = 5 的紀錄
	@DeleteMapping("/{id}")
	public Boolean deleteGps(@PathVariable("id") Integer id) {
		Boolean status = gpsService.deleteGps(id);
		return status;
	}
	
	
}
