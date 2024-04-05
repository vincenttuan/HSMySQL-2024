package com.hs.rest.key.repository;

import com.hs.rest.key.model.po.ArgKey;

public interface ArgKeyRepository {
	// 比對 key 是否正確
	public Boolean checkKey(String key);
	// 取得資料 id 固定是 7
	public ArgKey getArgKey();
	// 更新資料
	public void update(ArgKey argKey);
}
