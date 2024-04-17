package com.hs.mvc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ErrorLogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // save error log
    public void saveErrorLog(String request_uri, String content, int error_code, String error_exception) {
        String sql = "INSERT INTO ErrorLog(request_uri, content, error_code, error_exception)" + 
                     "VALUES (?, ?, ?, ?)";
        System.out.println("ArgsDao:" + sql);
        jdbcTemplate.update(sql, request_uri, content, error_code, error_exception);
    }
}
