package com.hs.rest.key.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.rest.key.model.response.ApiResponse;
import com.hs.rest.key.service.ArgKeyService;

@RestController
@RequestMapping("/argkey")
public class ArgKeyController {
	
	@Autowired
	private ArgKeyService argKeyService;
	
	@GetMapping("/check")
	public ApiResponse checkKey(String key) {
		Boolean result = argKeyService.checkKey(key);
		if (result) {
        	return new ApiResponse(true, "驗證成功", result);
		} else {
			return new ApiResponse(false, "驗證失敗", result);
		}
	}
	
	
	
}
