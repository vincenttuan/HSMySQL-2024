package com.hs.rest.gps.repository;

import java.util.List;

import com.hs.rest.gps.model.po.GPS;
// getXXX 單筆
// queryXXX 多筆
// 資料庫要提供哪些服務
public interface GPSRepository {
	// 查詢所有 GPS
	List<GPS> queryAllGps();
	// 根據 id 查詢 GPS
	GPS getGpsById(Integer id);
	// 根據 location 查詢 GPS
	GPS getGpsByLocationName(String locationName);
	// 新增 GPS
	Boolean addGPS(GPS gps);
	// 修改 GPS
	Boolean updateGPS(GPS gps);
	// 刪除 GPS
	Boolean deleteGPSById(Integer id);
	
	
}
