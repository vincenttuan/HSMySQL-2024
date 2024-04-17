package com.hs.mvc.repository;

import com.hs.mvc.entity.views.EmpRankLevel;
import com.hs.mvc.entity.views.ViewClockOnReportMark;
import com.hs.mvc.entity.views.ViewSchedulerStatus;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ViewDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    //查詢員工職等職級表
    public List<EmpRankLevel> queryViewEmpRankLevels() {
        String sql = "SELECT * FROM view_emp_ranklevel_list";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EmpRankLevel.class));
    }

    // 查詢打卡記錄報表
    public List<ViewClockOnReportMark> queryViewClockOnReportMark(int year, int month, String empNo) {
        String sql = "SELECT * FROM view_clockon_report_mark\n"
                + "WHERE \n"
                + "    YEAR(report_date) = ? AND\n"
                + "    MONTH(report_date) = ? AND\n"
                + "    emp_no = ?\n"
                + "ORDER BY report_date";
        return jdbcTemplate.query(sql, new Object[]{year, month, empNo}, new BeanPropertyRowMapper<>(ViewClockOnReportMark.class));
    }

    // 查詢工作日報表
    public List<ViewClockOnReportMark> queryViewClockOnMemoReport(String empNo, String d1, String d2) {
        String sql = null;
        if (empNo.equalsIgnoreCase("all")) {
            sql = "SELECT * FROM view_clockon_report_mark v\n"
                    + "WHERE \n"
                    + "v.emp_no != ? AND \n"
                    + "v.report_date BETWEEN ? AND ?\n"
                    + "ORDER BY v.report_date, v.emp_no";
        } else {
            sql = "SELECT * FROM view_clockon_report_mark v\n"
                    + "WHERE \n"
                    + "v.emp_no = ? AND \n"
                    + "v.report_date BETWEEN ? AND ?\n"
                    + "ORDER BY v.report_date, v.emp_no";
        }
        return jdbcTemplate.query(sql, new Object[]{empNo, d1, d2}, new BeanPropertyRowMapper<>(ViewClockOnReportMark.class));
    }

    // 查詢 view_scheduler_status (首頁打卡專用)
    public List<ViewSchedulerStatus> queryViewSchedulerStatus(String empNo) {
        String sql = "SELECT v.emp_no, v.sdate, v.status_id, v.status_name \n"
                + "FROM view_scheduler_status v \n"
                + "WHERE v.emp_no = ? AND v.sdate = CURDATE() \n"
                + "ORDER BY v.status_id \n";
        return jdbcTemplate.query(sql, new Object[]{empNo}, new BeanPropertyRowMapper<>(ViewSchedulerStatus.class));
    }

}
