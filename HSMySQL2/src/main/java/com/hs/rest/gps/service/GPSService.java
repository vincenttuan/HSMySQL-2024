package com.hs.rest.gps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.hs.rest.gps.model.po.GPS;
import com.hs.rest.gps.repository.GPSRepository;
import com.hs.rest.gps.util.DistanceUtil;

@Service
public class GPSService {
	
	@Autowired
	//@Qualifier("GPSRepositoryImpl")
	private GPSRepository gpsRepository;
	
	// 多筆查詢
	public List<GPS> queryAllGps() {
		List<GPS> gpsList = gpsRepository.queryAllGps();
		return gpsList;
	}
	
	// 單筆查詢
	public GPS getGpsById(Integer id) {
		if(id == null) {
			return null;
		}
		GPS gps = gpsRepository.getGpsById(id);
		return gps;
	}
	
	public Boolean addGPS(GPS gps) {
		Boolean status = gpsRepository.addGPS(gps);
		return status;
	}
	
	public Boolean updateGPS(GPS gps) {
		return gpsRepository.updateGPS(gps);
	}
	
	public Boolean deleteGPS(Integer id) {
		Boolean status = gpsRepository.deleteGPSById(id);
		return status;
	}
	
	public List<GPS> findGPSWithinDistance(Double lng, Double lat) {
		List<GPS> matchingGPSList = new ArrayList<>(); // 存放距離符合的容器
		
		for(GPS gps : queryAllGps()) {
			double distance = DistanceUtil.getDistance(gps.getLongitude(), gps.getLatitude(), lng, lat);
			if(distance <= gps.getMeter()) { // 算出的距離 distance 是否小於設定的距離 meter
				matchingGPSList.add(gps); // 存放到距離符合的容器
			}
		}
		
		return matchingGPSList;
	}
	
	
}
