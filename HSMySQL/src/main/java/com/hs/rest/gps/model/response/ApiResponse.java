package com.hs.rest.gps.model.response;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse {
	private Boolean status;
	private String message;
	private String datetime = getNow();
	private Object data;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	
	public ApiResponse(Boolean status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	// 取得現在時間
	private String getNow() {
		return sdf.format(new Date());
	}

	
}
