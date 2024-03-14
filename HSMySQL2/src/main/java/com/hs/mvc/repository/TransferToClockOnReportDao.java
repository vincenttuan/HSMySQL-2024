package com.hs.mvc.repository;

import com.hs.mvc.entity.ClockOn;
import com.hs.mvc.entity.Status;
import com.hs.mvc.entity.views.ViewSchedulerStatus;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransferToClockOnReportDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ViewDao viewDao;

    private List<Status> statusList;

    // 今日 + 員工編號
    public void transferTodayByEmpNo(String empNo) {
        // 透過empNo找出今日的打卡紀錄
        // 範例:
        // clock_id | emp_no  | status_id | clock_on
        // 4776	| HS00010 | 22	      | 2021-02-23 15:45:20.000
        String sql = "SELECT clock_id, emp_no, status_id, clock_on "
                + "FROM ClockOn "
                + "WHERE DATE(clock_on) = CURDATE() AND emp_no = '" + empNo + "' "
                + "ORDER BY clock_id";
        // 轉檔
        transfer(sql);
    }

    // 全部
    public void transferAll() {
        String sql = "SELECT clock_id, emp_no, status_id, clock_on "
                + "FROM ClockOn "
                + "ORDER BY clock_id";
        transfer(sql);
    }

    // 轉檔
    // 在clockonreport中產生一筆紀錄
    // 若待筆紀錄已經存在，則進行相關欄位的更新
    private void transfer(String sql) {
        //System.out.println(sql);
        statusList = jdbcTemplate.query("SELECT * FROM Status", new BeanPropertyRowMapper<>(Status.class));
        // transfer得到的 sql 資料範例:
        // clock_id | emp_no  | status_id | clock_on
        // 4776	| HS00010 | 22	      | 2021-02-23 15:45:20.000

        //取得Status資料表的列表資訊
        List<ClockOn> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ClockOn.class));
        //取得ClockOn資料表的列表資訊
        for (ClockOn clockOn : list) {
            // 在今日的ClockOnReport有沒有該員工的紀錄
            sql = "SELECT IF(EXISTS(SELECT * FROM ClockOnReport Where report_date = ? and emp_no = ?), 'true', 'false') as ex";
            // ex：true有紀錄 false無紀錄
            boolean ex = Boolean.valueOf(jdbcTemplate.queryForList(sql, new Object[]{getDate(clockOn.getClockOn()), clockOn.getEmpNo()}).get(0).get("ex").toString());
            //System.out.println("ex=" + ex);

            // 取得目前的打卡時段
            // 第一版：根據status的id，取得目前的打卡時段(可能是clock_on_1或clock_on_2或clock_on_3或clock_on_4)
            String columnName = getColumnName(clockOn.getStatusId());
            if (columnName == null) {
                // 第二版：根據"status_id+員工排班資訊"來得到status_id的順位
                columnName = getColumnName(clockOn);
                if (columnName == null) {
                    continue;
                }
            }

            // 要新增或修改資料到 status_id_1 或 status_id_2 或 status_id_3 或 status_id_4
            // 由columnName來進行判斷
            String statusIdColumnName = null;
            switch (columnName) {
                case "clock_on_1":
                    statusIdColumnName = "status_id_1";
                    break;
                case "clock_on_2":
                    statusIdColumnName = "status_id_2";
                    break;
                case "clock_on_3":
                    statusIdColumnName = "status_id_3";
                    break;
                case "clock_on_4":
                    statusIdColumnName = "status_id_4";
                    break;
            }
            if(statusIdColumnName == null){
                continue;
            }

            if (ex) {
                sql = "Update ClockOnReport set "+ columnName +" = ?, "+ statusIdColumnName +" = ? "
                        + "Where report_date = ? and emp_no = ?";
                int rowcount = jdbcTemplate.update(sql, clockOn.getClockOn(), clockOn.getStatusId(), getDate(clockOn.getClockOn()), clockOn.getEmpNo());
                System.out.println("update:" + rowcount);
            } else {
                sql = "Insert Into ClockOnReport(report_date, group_id, emp_no, " + columnName + ", " + statusIdColumnName + ") "
                        + "values(?, ?, ?, ?, ?)";
                int rowcount = jdbcTemplate.update(sql, clockOn.getClockOn(), getGroupId(clockOn.getStatusId()), clockOn.getEmpNo(), clockOn.getClockOn(), clockOn.getStatusId());
                System.out.println("insert:" + rowcount);
            }
        }
    }

    // 第一版打卡選單產生方法
    private String getColumnName(int status_id) {
        switch (status_id) {
            case 8:
            case 12:
            case 16:
            case 20:
            case 24:
            case 28:
            case 32:
            case 36:
                return "clock_on_1";
            case 9:
            case 13:
            case 17:
            case 21:
            case 25:
            case 29:
            case 33:
            case 37:
                return "clock_on_2";
            case 10:
            case 14:
            case 18:
            case 22:
            case 26:
            case 30:
            case 34:
            case 38:
                return "clock_on_3";
            case 11:
            case 15:
            case 19:
            case 23:
            case 27:
            case 31:
            case 35:
            case 39:
                return "clock_on_4";

        }
        return null;
    }

    // 第二版打卡選單產生方法
    private String getColumnName(ClockOn clockOn) {
        List<ViewSchedulerStatus> list = viewDao.queryViewSchedulerStatus(clockOn.getEmpNo());
        for (int i = 0; i < list.size(); i++) {
            // 比對所點選radio的打卡班表在今日排班的哪一個順位
            if (list.get(i).getStatusId().equals(clockOn.getStatusId())) {
                return "clock_on_" + (i + 1);
            }
        }
        return null;
    }

    private String getDate(Date current) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sdFormat.format(current);
    }

    private Integer getGroupId(Integer status_id) {
        Integer group_id = 0;
        for (Status status : statusList) {
            if (status.getStatusId() == status_id) {
                return status.getGroupId();
            }
        }
//        if (group_id == 0){
//            根據 status_id 找出 group_id 並回傳
//            若仍沒找到回傳 -1
//        }
        return group_id;
    }
}
