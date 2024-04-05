package com.hs.rest.key.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hs.rest.key.model.po.ArgKey;

@Repository
// 對應的資料表: args
public class ArgKeyRepositoryImpl implements ArgKeyRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Boolean checkKey(String key) {
		Integer id = ArgKey.id;
		String sql = "select count(*) from args where str_arg1 = ? and id = ?";
		// 比對 key 是否正確
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, key, id);
		return count > 0;
	}

	@Override
	public ArgKey getArgKey() {
		Integer id = ArgKey.id;
		String sql = "select id, name, str_arg1, memo from args where id = ?";
		//ArgKey argKey = jdbcTemplate.queryForObject(sql, ArgKey.class, id);
		ArgKey argKey = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ArgKey.class), id);
		return argKey;
	}

	@Override
	public void update(ArgKey argKey) {
		// TODO Auto-generated method stub
		
	}

	

}
