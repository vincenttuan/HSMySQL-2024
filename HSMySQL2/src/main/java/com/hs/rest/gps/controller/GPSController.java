package com.hs.rest.gps.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping
	// 多筆查詢 http://localhost:8080/HSMySQL2/mvc/gps
	public ApiResponse queryAllGps() {
		List<GPS> gpsList = gpsService.queryAllGps();
		// 建立回應物件
		if(gpsList.size() == 0) {
			return new ApiResponse(false, "無資料", null);
		}
		return new ApiResponse(true, "多筆查詢成功", gpsList);
	}
	
	@GetMapping("/{id}")
	// 單筆查詢 http://localhost:8080/HSMySQL2/mvc/gps/1
	// 單筆查詢 http://localhost:8080/HSMySQL2/mvc/gps/3
	public ApiResponse getGpsById(@PathVariable("id") Integer id) {
		GPS gps = gpsService.getGpsById(id);
		if(gps == null) {
			return new ApiResponse(false, "無資料", null);
		}
		return new ApiResponse(true, "單筆查詢成功", gps);
	}
	
	@GetMapping("/findGpsWithinDistance")
	// 接收前端傳來的經緯度(lng, lat)
	// 單筆查詢 http://localhost:8080/HSMySQL2/mvc/gps/findGpsWithinDistance?lng=121.567&lat=24.123
	public ApiResponse findGpsWithinDistance(@RequestParam Double lng, @RequestParam Double lat) {
		// 透過 gpsService 查詢符合距離的 GPS 資料有哪些
		List<GPS> matchingGpsList = gpsService.findGpsWithinDistance(lng, lat);
		if(matchingGpsList.size() == 0) { // 若容器無 GPS 資料
			return new ApiResponse(false, "無符合距離的 GPS 資料", null);
		}
		return new ApiResponse(true, "有符合距離的 GPS 資料", matchingGpsList);
	}
	
	
	// GPS 的 CRUD
	@PostMapping
	public ApiResponse addGPS(@RequestBody GPS gps) {
		// 檢查參數
		// 略...
		// 傳送參數給 gpsService, 讓 gpsService 幫我新增
		ApiResponse apiResponse = null;
		try {
			Boolean status = gpsService.addGPS(gps);
			if(status) {
				apiResponse = new ApiResponse(true, "新增成功", status);
			} else {
				apiResponse = new ApiResponse(false, "新增失敗", null);
			}
		} catch (Exception e) {
			apiResponse = new ApiResponse(false, "新增錯誤", e.getMessage());
			e.printStackTrace();
		}
		return apiResponse;
	}
	
	// 動態修改
	// 例如: 網址.../gps/1 <- 修改 id = 1 的紀錄
	// 接收 json 參數資料
	@PatchMapping("/{id}")
	public ApiResponse updateGPS(@PathVariable("id") Integer id, @RequestBody GPS gps) {
		// 注入 id (重要 !!!)
		gps.setId(id);
		Boolean status = gpsService.updateGPS(gps);
		ApiResponse apiResponse = null;
		if(status) {
			apiResponse = new ApiResponse(true, "修改成功", status);
		} else {
			apiResponse = new ApiResponse(false, "修改失敗", null);
		}
		return apiResponse;
	}
	
	
	// 刪除
	// 例如: 網址.../gps/1 <- 刪除 id = 1 的紀錄
	// 例如: 網址.../gps/3 <- 刪除 id = 3 的紀錄
	// 例如: 網址.../gps/5 <- 刪除 id = 5 的紀錄
	@DeleteMapping("/{id}")
	public ApiResponse deleteGPS(@PathVariable("id") Integer id) {
		Boolean status = gpsService.deleteGPS(id);
		ApiResponse apiResponse = null;
		if(status) {
			apiResponse = new ApiResponse(true, "刪除成功", status);
		} else {
			apiResponse = new ApiResponse(false, "刪除失敗", null);
		}
		return apiResponse;
	}
	
}
