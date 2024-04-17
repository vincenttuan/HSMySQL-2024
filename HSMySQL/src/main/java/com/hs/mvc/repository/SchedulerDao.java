package com.hs.mvc.repository;

import static java.util.stream.Collectors.toList;
import com.hs.mvc.entity.SchedulerEmployee;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;


@Repository
public class SchedulerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    //查詢所有班表
    public List<Map<String, Object>> querySchedulerItem() {
        String sql = "SELECT * FROM SchedulerItem WHERE remove = 0";
        return jdbcTemplate.queryForList(sql);
    }

    //查詢每日排班人員狀況
    public List<Map<String, Object>> querySchedulerSearch(Date date) {
        String sql = "SELECT se.id, se.sdate, se.emp_no, e.emp_name,\n"
                + "       se.gid, sg.`name`, se.iid, si.class_name,\n"
                + "       s.status_name, s.status_begin, s.status_end\n"
                + "FROM SchedulerEmployee se, SchedulerGroup sg, SchedulerItem si, employee e, status s\n"
                + "WHERE sdate = ? AND se.gid = sg.id AND se.iid = si.id AND se.emp_no = e.emp_no AND si.status_no1 = s.status_no\n"
                + "order by se.gid, se.iid, se.emp_no";
        return jdbcTemplate.queryForList(sql, date);
    }

//    public List<SchedulerEmployee> querySchedulerEmployees(int year, int month) {
//        String sql = "SELECT * FROM scheduleremployee WHERE year(sdate) = ? AND month(sdate) = ?";
//        return jdbcTemplate.query(sql, new Object[]{year, month}, new BeanPropertyRowMapper<>(SchedulerEmployee.class));
//    }
//    
//    public List<SchedulerEmployee> querySchedulerEmployees(int year, int month, String empNo) {
//        String sql = "SELECT * FROM scheduleremployee WHERE year(sdate) = ? AND month(sdate) = ? AND emp_no = ?";
//        return jdbcTemplate.query(sql, new Object[]{year, month, empNo}, new BeanPropertyRowMapper<>(SchedulerEmployee.class));
//    }
    
    //多對一的基本寫法，但會產生多筆SQL查詢，耗資源
//    RowMapper rm = new RowMapper() {
//        @Override
//        public Object mapRow(ResultSet rs, int i) throws SQLException {
//            SchedulerEmployee se = new SchedulerEmployee();
//            se.setId(rs.getInt("id"));
//            se.setGid(rs.getInt("gid"));
//            se.setIid(rs.getInt("iid"));
//            se.setSdate(rs.getDate("sdate"));
//            String empNo = rs.getString("emp_no");
//            se.setEmpNo(empNo);
//            Employee emp = jdbcTemplate.queryForObject("SELECT * FROM employee WHERE emp_no = ?", new BeanPropertyRowMapper<>(Employee.class), empNo);
//            se.setEmployee(emp);
//            return se;
//        }
//    };

    //多對一的正確寫法，只要一筆SQL查詢
    public List<SchedulerEmployee> querySchedulerEmployees(int year, int month) {
//        String sql = "SELECT * FROM scheduleremployee WHERE year(sdate) = ? AND month(sdate) = ?";
//        return jdbcTemplate.query(sql, new Object[]{year, month}, rm);
        String sql = "SELECT se.id, se.gid, se.iid, se.sdate, se.emp_no,\n"
                + "       e.emp_id as employee_empId, e.emp_no as employee_empNo, e.emp_name as employee_empName, \n"
                + "       e.emp_active as employee_empActive, e.emp_ct as employee_empCt,\n"
                + "       e.emp_rfid as employee_empRfid, e.emp_pwd as employee_empPwd, e.agent1_id as employee_agent1Id, \n"
                + "       e.emp_priority as employee_empPriority, e.dept_id1 as employee_deptId1, e.dept_id2 as employee_deptId2\n"
                + "FROM scheduleremployee se, employee e\n"
                + "WHERE year(sdate) = ? AND month(sdate) = ? AND se.emp_no = e.emp_no";
        // SimpleFlatMapper
        ResultSetExtractor<List<SchedulerEmployee>> mapper = JdbcTemplateMapperFactory
                .newInstance()
                .addKeys("id")
                .newResultSetExtractor(SchedulerEmployee.class);
        List<SchedulerEmployee> schedulerEmployees= jdbcTemplate.query(sql, mapper, year, month);
        return schedulerEmployees;
    }

    public List<SchedulerEmployee> querySchedulerEmployees(int year, int month, String empNo) {
        List<SchedulerEmployee> ses = querySchedulerEmployees(year, month);
        return ses.stream()
                .filter(se -> se.getEmpNo().equals(empNo))
                .collect(toList());
    }

    // 員工新增單日排班日期或取消排班日期
    public synchronized Integer updateSchedulerEmployee(String empNo, Date sdate, Integer gid, Integer iid, String colName) {

        // 查詢員工(emp_no)在指定日期(sdate)指定部門(gid)指定時段(iid)是否已排過班表 ?
        String sql = "SELECT count(*) FROM scheduleremployee WHERE emp_no=? AND sdate=? AND gid=? AND iid=?";
        // 得到查詢筆數
        int count = jdbcTemplate.queryForObject(sql, Integer.class, empNo, sdate, gid, iid);
        if (count > 0) { // 該員工已排過班
            sql = "DELETE FROM scheduleremployee WHERE emp_no=? AND sdate=? AND gid=? AND iid=?";
            jdbcTemplate.update(sql, empNo, sdate, gid, iid);
            return 2; // 該員工當日該班已刪除
        }

        // 查詢該時段是否有排班人數餘額
        Integer memberCount = getNop(colName, sdate);
        if (memberCount < 1) {
            return 0;
        }

        //新增排班
        sql = "INSERT INTO scheduleremployee (emp_no, sdate, gid, iid) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, empNo, sdate, gid, iid);
        return 1; // 該員工當日該班已新增
    }

    public Map<String[], String[][]> getNewNopMap() {
        // 資料庫抓取 scheduleritem 與 schedulergroup
        String sql2 = "select si.gid, si.id, si.class_name, \n"
                + "       concat('nop_', si.nop_name) as nop_name,\n"
                + "       concat('nop', upper(substring(si.nop_name, 1, 1)), substring(si.nop_name, 2)) as nop_property\n"
                + "from scheduleritem si";
        List<Map<String, Object>> items = jdbcTemplate.queryForList(sql2);

        String sql = "select sg.id, sg.name, sg.color, sg.dept_id from schedulergroup sg";
        List<Map<String, Object>> groups = jdbcTemplate.queryForList(sql);

        // 宣告 nopMap
        Map<String[], String[][]> nopMap = new LinkedHashMap<>();

        // 組合 nopMap
        for (Map<String, Object> gm : groups) {
            // nopMap's key
            String[] key = new String[]{
                gm.get("name") + "", gm.get("color") + "", gm.get("dept_id") + ""
            };
            // nopMap's values
            // 計算 items 的筆數
            int items_count = 0;
            for (Map<String, Object> item : items) {
                String gm_id = gm.get("id").toString();
                String item_gid = item.get("gid").toString();
                if (gm_id.equals(item_gid)) {
                    items_count++;
                }
            }
            String[][] values = new String[items_count][3];
            // 尋訪 items
            int index = 0;
            for (Map<String, Object> item : items) {
                String gm_id = gm.get("id").toString();
                String item_gid = item.get("gid").toString();
                if (gm_id.equals(item_gid)) {
                    String[] value = {
                        item.get("class_name") + "",
                        item.get("nop_name") + "",
                        item.get("nop_property") + "",
                        item.get("gid") + "",
                        item.get("id") + ""
                    };
                    values[index++] = value;
                }
            }
            nopMap.put(key, values);
        }

        return nopMap;
    }

    public Integer getNop(String colName, Date sdate) {
        // 查詢該時段是否有排班人數餘額
        String sql = "SELECT " + colName + " FROM view_holiday_use WHERE date = ?";
        int memberCount = jdbcTemplate.queryForObject(sql, Integer.class, sdate);
        return memberCount;
    }
}
