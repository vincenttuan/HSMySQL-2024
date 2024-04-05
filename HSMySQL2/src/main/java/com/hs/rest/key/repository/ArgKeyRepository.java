package com.hs.rest.key.repository;

import com.hs.rest.key.model.po.ArgKey;

public interface ArgKeyRepository {
	public Boolean checkKey(String key);
	public ArgKey findById(Integer id);
	public void update(ArgKey argKey);
}
