package com.hs.rest.gps.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
	private Boolean status;
	private String message;
	private String datetime;
	private Object data;
}
