package com.hs.rest.key.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hs.rest.key.model.po.ArgKey;
import com.hs.rest.key.repository.ArgKeyRepository;

@Service
public class ArgKeyService {
	
	@Autowired
	private ArgKeyRepository argKeyRepository;
	
	public Boolean checkKey(String key) {
		if (key == null || key.isEmpty()) {
			return false;
		}
		return argKeyRepository.checkKey(key);
	}
	
	public ArgKey getArgKey() {
		return argKeyRepository.getArgKey();
	}
	
	public Boolean update(ArgKey argKey) {
		if (argKey == null || argKey.getStrArg1() == null || argKey.getStrArg1().isEmpty()) {
			return false;
		}
		return argKeyRepository.update(argKey);
	}
	
	// 只更新 key
	public Boolean updateKey(String newKey) {
		if (newKey == null || newKey.isEmpty()) {
			return false;
		}
		return argKeyRepository.updateKey(newKey);
	}
	
	
}
