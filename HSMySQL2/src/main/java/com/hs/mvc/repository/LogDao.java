package com.hs.mvc.repository;

import com.hs.mvc.entity.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Log log) {
        String sql = "Insert Into Log(emp_no, log_path, from_address, type) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql, log.getEmpNo(), log.getLogPath(), log.getFromAddress(), log.getType());

    }

    // 查詢員工撰寫 memo 工作回報所花費的時間細目
    public List<Map<String, String>> queryUpdatememoSpendtimeDetail(String empNo, int year, int month) {
        String sql = "SELECT * FROM Log WHERE log.type=0 AND log.emp_no = '" + empNo + "' AND YEAR(log.log_ct) = " + year + " AND MONTH(log.log_ct) = " + month + " order by log.id";
        List<Log> logs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Log.class));
        List<Map<String, String>> detailList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i).getLogPath().contains("queryViewClockOnReportMark")) {
                if ((i + 1) < logs.size()) {
                    if (logs.get(i + 1).getLogPath().contains("updatememo")) {
                        long diff = logs.get(i + 1).getLogCt().getTime() - logs.get(i).getLogCt().getTime();
                        Map<String, String> map = new LinkedHashMap<>();
                        String key = sdf.format(logs.get(i).getLogCt());
                        String value = logs.get(i).getLogCt() + " - " + logs.get(i + 1).getLogCt() + " = " + String.format("%.2f", diff / 1000 / 60.0);
                        map.put(key, value);
                        detailList.add(map);
                    }
                }
            }
        }
        return detailList;
    }

    // 查詢員工撰寫 memo 工作回報所花費的時間
    public List<Map<String, Long>> queryUpdatememoSpendtime(String empNo, int year, int month) {
        String sql = "SELECT * FROM Log WHERE log.type=0 AND log.emp_no = '" + empNo + "' AND YEAR(log.log_ct) = " + year + " AND MONTH(log.log_ct) = " + month + " order by log.id";
        List<Log> logs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Log.class));
        long sum = 0;
        List<Map<String, Long>> list = new ArrayList<>();
        String preKeyDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i).getLogPath().contains("queryViewClockOnReportMark")) {
                if ((i + 1) < logs.size()) {
                    if (logs.get(i + 1).getLogPath().contains("updatememo")) {
                        long diff = logs.get(i + 1).getLogCt().getTime() - logs.get(i).getLogCt().getTime();
                        String keyDate = sdf.format(logs.get(i).getLogCt());
                        if (keyDate.equals(preKeyDate)) {
                            for (Map<String, Long> map : list) {
                                if (map.get(keyDate) != null) {
                                    map.put(keyDate, map.get(keyDate) + diff);
                                }
                            }
                        } else {
                            preKeyDate = keyDate;
                            Map<String, Long> map = new LinkedHashMap<>();
                            map.put(keyDate, diff);
                            list.add(map);
                        }
                        sum += diff;
                    }
                }
            }
        }
        return list;
    }

}
