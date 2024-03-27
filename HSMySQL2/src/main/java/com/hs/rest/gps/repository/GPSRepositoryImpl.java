package com.hs.rest.gps.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hs.rest.gps.model.po.GPS;

//@Repository("GPSRepositoryImpl")
@Repository
public class GPSRepositoryImpl implements GPSRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<GPS> queryAllGps() {
		String sql = "select id, latitude, longitude, meter, location, location_name from gps";
		List<GPS> gpsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GPS.class));
		return gpsList;
	}

	@Override
	public GPS getGpsById(Integer id) {
		String sql = "select id, latitude, longitude, meter, location, location_name from gps where id=?";
		try {
			GPS gps = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(GPS.class), id);
			return gps;
		} catch (Exception e) { // 若沒有找到會發生例外
			return null;
		}
	}

	@Override
	public GPS getGpsByLocationName(String locationName) {
		String sql = "select id, latitude, longitude, meter, location, location_name from gps where locationName=?";
		try {
			GPS gps = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(GPS.class), locationName);
			return gps;
		} catch (Exception e) { // 若沒有找到會發生例外
			return null;
		}
	}

	@Override
	public Boolean addGPS(GPS gps) {
		String sql = "insert into gps(latitude, longitude, meter, location, location_name) values (?, ?, ?, ?, ?)";
		int rowcount = jdbcTemplate.update(sql, gps.getLatitude(), gps.getLongitude(), gps.getMeter(), gps.getLocation(), gps.getLocationName());
		return rowcount > 0;
	}

	@Override
	public Boolean updateGPS(GPS gps) {
		String sql = "update gps set latitude=?, longitude=?, meter=?, location=?, location_name=? where id=?";
		int rowcount = jdbcTemplate.update(sql, gps.getLatitude(), gps.getLongitude(), gps.getMeter(), gps.getLocation(), gps.getLocationName(), gps.getId());
		return rowcount > 0;
	}

	@Override
	public Boolean deleteGPSById(Integer id) {
		String sql = "delete from gps where id = ?";
		int rowcount = jdbcTemplate.update(sql, id);
		return rowcount > 0;
	}

}
