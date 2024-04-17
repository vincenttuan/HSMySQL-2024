package com.hs.rest.gps.repository;

import java.util.ArrayList;
import java.util.List;

import com.hs.rest.gps.model.po.GPS;

public class Test {
	public static void main(String[] args) {
		GPS gps = new GPS();
		gps.setId(1);
		gps.setMeter(20);
		gps.setLocation("AAA");
		
		StringBuilder sqlBuilder = new StringBuilder("UPDATE gps SET ");
		List<Object> params = new ArrayList<>();

		// 依次檢查每個字段是否為 null，並相應地構建 SQL 語句
		if (gps.getLatitude() != null) {
			sqlBuilder.append("latitude = ?, ");
			params.add(gps.getLatitude());
		}
		if (gps.getLongitude() != null) {
			sqlBuilder.append("longitude = ?, ");
			params.add(gps.getLongitude());
		}
		if (gps.getMeter() != null) {
			sqlBuilder.append("meter = ?, ");
			params.add(gps.getMeter());
		}
		if (gps.getLocation() != null) {
			sqlBuilder.append("location = ?, ");
			params.add(gps.getLocation());
		}
		if (gps.getLocationName() != null) {
			sqlBuilder.append("location_name = ?, ");
			params.add(gps.getLocationName());
		}

		// 檢查是否有至少一個字段需要更新
		if (params.isEmpty()) {
			// 所有字段都是 null，不執行更新操作
			System.out.println("所有字段都是 null，不執行更新操作");
			return;
		}

		// 移除最後的逗號和空格
		sqlBuilder.setLength(sqlBuilder.length() - 2);

		// 添加 WHERE 條件
		sqlBuilder.append(" WHERE id = ?");
		params.add(gps.getId());
		
		System.out.println(sqlBuilder.toString());
		System.out.println(params);
	}
}
