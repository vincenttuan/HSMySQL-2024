package com.hs.mvc.repository;

import com.hs.mvc.entity.NOP;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NOPDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean updateNOP(String columnName, int nop, int spec) {
        String sql = "UPDATE Holiday SET " + columnName + "=? WHERE spec=?";
        jdbcTemplate.update(sql, nop, spec);
        return true;
    }

    public int getNOP(Date date, int spec) {
        String[] weekDays = {"day0", "day1", "day2", "day3", "day4", "day5", "day6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        
        String sql = "SELECT " + weekDays[w] + " FROM NOP WHERE spec=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{spec}, Integer.class);
    }

    public NOP getNOP() {
        String sql = "SELECT id, day0, day1, day2, day3, day4, day5, day6, spec FROM NOP";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(NOP.class)).get(0);
    }

}
