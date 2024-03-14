package com.hs.mvc.repository;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChartDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 員工出勤狀況
    // 資料：每月出勤異常數量，每月出勤正常數量
    public List<Map<String,Object>> queryStaffAttendant(String emp_no, Integer year, Integer month) {
        String sql = "SELECT count(report_date) as count FROM view_clockon_report_mark \n" +
                     "WHERE year(report_date) = ? AND month(report_date) = ?\n" +
                     "      AND emp_no = ? AND (mark1=\"*\" OR mark2=\"*\" OR mark3=\"*\" OR mark4=\"*\")\n" +
                     "UNION ALL\n" +
                     "SELECT count(report_date) as count FROM view_clockon_report_mark \n" +
                     "WHERE year(report_date) = ? AND month(report_date) = ?\n" +
                     "      AND emp_no = ? AND (mark1!=\"*\" AND mark2!=\"*\" AND mark3!=\"*\" AND mark4!=\"*\")";
        Object[] args = {year, month, emp_no, year, month, emp_no};
        return jdbcTemplate.queryForList(sql, args);
    }
}
