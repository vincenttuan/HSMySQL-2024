package com.hs.rest.gps.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 資料庫	: GPS
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GPS {
	// 欄位
	private Integer id;
	private Double latitude;
	private Double longitude;
	private Integer meter;
	private String location; // 公司地址
	private String locationName; // 公司地址單位名稱
	
}
