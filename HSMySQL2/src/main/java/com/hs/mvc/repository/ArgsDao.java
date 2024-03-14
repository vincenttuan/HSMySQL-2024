package com.hs.mvc.repository;

import com.hs.mvc.entity.Args;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArgsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //更改參數內容
    public int updateArgs(Integer id, String columnName, Object value){
        String sql = null;
        if (value == null || value.toString().trim().equals("")){
            sql = "update args set %s=null where id=%d";
            sql = String.format(sql, columnName, id);
        } else {
            sql = "update args set %s='%s' where id=%d";
            sql = String.format(sql, columnName, value+"", id);
        }
        return jdbcTemplate.update(sql);
    }
    
    
    // 查詢後台所有參數資料
    public List<Args> queryAllArgs() {
        String sql = "SELECT * FROM args";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Args.class));
    }
    
    //取得員工網站使用授權碼auth
    public String getAuthStringt() {
        String sql = "SELECT str_arg1 FROM args WHERE id=4";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
    
    // 取得排班表月份限制解除資料(1:解除, 0:限制)
    public Integer getScheduleMonthRestrictionInt() {
        String sql = "SELECT int_arg1 FROM args WHERE id = 5";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    
    //員工排班表是否可修改
    public boolean isEditEnableByCalender() {
        String sql = "SELECT (DAYOFMONTH(now()) >= int_arg1 and DAYOFMONTH(now()) <= int_arg2) as 'check' "
                + "FROM args "
                + "WHERE id = 1 ";
        boolean check = jdbcTemplate.queryForObject(sql, Boolean.class);
        return check;
    }
    

}
