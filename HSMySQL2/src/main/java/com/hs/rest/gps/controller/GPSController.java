package com.hs.rest.gps.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.rest.gps.model.po.GPS;
import com.hs.rest.gps.model.response.ApiResponse;
import com.hs.rest.gps.service.GPSService;

@RestController
@RequestMapping("/gps")
public class GPSController {
	
	@Autowired
	private GPSService gpsService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	
	// 取得現在時間
	private String getNow() {
		return sdf.format(new Date());
	}
	
	@GetMapping
	// 多筆查詢 http://localhost:8080/HSMySQL2/mvc/gps
	public ApiResponse queryAllGps() {
		List<GPS> gpsList = gpsService.queryAllGps();
		// 建立回應物件
		if(gpsList.size() == 0) {
			ApiResponse apiResponse = new ApiResponse(false, "無資料", getNow(), null);
			return apiResponse;
		}
		ApiResponse apiResponse = new ApiResponse(true, "多筆查詢成功", getNow(), gpsList);
		return apiResponse;
	}
	
	@GetMapping("/{id}")
	// 單筆查詢 http://localhost:8080/HSMySQL2/mvc/gps/1
	// 單筆查詢 http://localhost:8080/HSMySQL2/mvc/gps/3
	public ApiResponse getGpsById(@PathVariable("id") Integer id) {
		GPS gps = gpsService.getGpsById(id);
		if(gps == null) {
			ApiResponse apiResponse = new ApiResponse(false, "無資料", getNow(), null);
			return apiResponse;
		}
		ApiResponse apiResponse = new ApiResponse(true, "單筆查詢成功", getNow(), gps);
		return apiResponse;
	}
	
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
