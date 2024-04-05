package com.hs.rest.key.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.rest.key.model.po.ArgKey;
import com.hs.rest.key.model.response.ApiResponse;
import com.hs.rest.key.service.ArgKeyService;

@RestController
@RequestMapping("/argkey")
public class ArgKeyController {
	
	@Autowired
	private ArgKeyService argKeyService;
	
	@GetMapping("/check")
	// 使用範例: /argkey/check?key=abc123
	public ApiResponse checkKey(@RequestParam("key") String key) {
		Boolean result = argKeyService.checkKey(key);
		if (result) {
        	return new ApiResponse(true, "驗證成功", result);
		} else {
			return new ApiResponse(false, "驗證失敗", result);
		}
	}
	
	@GetMapping
	// 使用範例: /argkey
	public ApiResponse getKey() {
		ArgKey argKey = argKeyService.getArgKey();
        if (argKey != null) {
            return new ApiResponse(true, "取得成功", argKey);
        } else {
            return new ApiResponse(false, "取得失敗", null);
        }
    }
	
}
