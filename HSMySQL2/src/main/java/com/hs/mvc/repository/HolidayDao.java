package com.hs.mvc.repository;

import com.hs.mvc.entity.Holiday;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HolidayDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private ArgsDao argsDao;
    
    public List queryConfirm(String sql) {
        return jdbcTemplate.queryForList(sql);
    }
    
    public int updateBatch(String sql) {
        return jdbcTemplate.update(sql);
    }
    
    // key: 對應的資料表欄位
    // value: 欄位的值 (欲排班的人數)
    // 修改班表人數
    public boolean updateNop(java.util.Date date, String key, int value) {
        // 取得當日已排班人數
        String sql = "SELECT " + key + " FROM view_holiday_use WHERE date = ?";
        // 已排班人數
        int v_nop = jdbcTemplate.queryForObject(sql, Integer.class, date);
        
        // 取得表定排班人數
        sql = "SELECT " + key + " FROM Holiday WHERE date = ?";
        // 表定排班人數
        int h_nop = jdbcTemplate.queryForObject(sql, Integer.class, date);
        
        System.out.println("v_nop: " + v_nop);
        System.out.println("h_nop: " + h_nop);
        System.out.println("value: " + value);
      
        if(value < h_nop) {
            int nop = h_nop - v_nop; // 已排班人數
            if(value < nop) { // 欲修改的排班人數 < 已排班人數
                return false;
            }
        }
        
        // 取得排班表月份限制解除資料(1:解除, 0:限制)
        int scheduleMonthRestrictionInt = argsDao.getScheduleMonthRestrictionInt();
        if (scheduleMonthRestrictionInt == 1) {
            sql = "UPDATE Holiday SET " + key + " = ? WHERE date = ? ";
        } else {
            sql = "UPDATE Holiday SET " + key + " = ? WHERE date = ? AND date > now()";
        }
        int count = jdbcTemplate.update(sql, value, date);
        System.out.println(sql);
        return count == 1;
    }

    
    public boolean updateName(java.util.Date date, String name) {
        String sql = "SELECT count(*) FROM Holiday WHERE date = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{date}, Integer.class);
        if(count == 0) {
            sql = "INSERT INTO Holiday(date, name, is_holiday, holiday_category, description) VALUES(?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, date, name, "", "", "");
        } else {
            sql = "UPDATE Holiday SET name=? WHERE date=?";
            jdbcTemplate.update(sql, name, date);
        }
        return true;
    }
    
    public boolean updateIsHoliday(java.util.Date date, String isHoliday) {
        String sql = "SELECT count(*) FROM Holiday WHERE date = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{date}, Integer.class);
        if(count == 0) {
            sql = "INSERT INTO Holiday(date, name, is_holiday, holiday_category, description) VALUES(?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, date, "", isHoliday, "", "");
        } else {
            sql = "UPDATE Holiday SET is_holiday=? WHERE date=?";
            jdbcTemplate.update(sql, isHoliday, date);
        }
        return true;
    }
    
    //完整的員工排班數目資訊
    public List<Holiday> query(int year, int month) {
        String sql = "SELECT * FROM Holiday WHERE year(date) = ? AND month(date) = ?";
        return jdbcTemplate.query(sql, new Object[]{year, month}, new BeanPropertyRowMapper<>(Holiday.class));
    }
    
    //有減去員工排班數目的資訊
    public List<Holiday> queryView(int year, int month) {
        String sql = "SELECT * FROM view_holiday_use WHERE year(date) = ? AND month(date) = ?";
        return jdbcTemplate.query(sql, new Object[]{year, month}, new BeanPropertyRowMapper<>(Holiday.class));
    }
    
    public void save(Holiday holiday) {
        String sql = "Insert Into Holiday(date, name, is_holiday, holiday_category, description) values(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, holiday.getDate(), holiday.getName(), holiday.getIsHoliday(), holiday.getHolidayCategory(), holiday.getDescription());
    }

    public void multiSave(List<Holiday> holidays) {
        String sql = "Insert Into Holiday(date, name, is_holiday, holiday_category, description) values(?, ?, ?, ?, ?)";
        
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Holiday holiday = (Holiday) holidays.get(i);
                ps.setDate(1, new Date(holiday.getDate().getTime()));
                ps.setString(2, holiday.getName());
                ps.setString(3, holiday.getIsHoliday());
                ps.setString(4, holiday.getHolidayCategory());
                ps.setString(5, holiday.getDescription());
            }

            public int getBatchSize() {
                return holidays.size();
            }
        };

        jdbcTemplate.batchUpdate(sql, setter);
    }
    
    // 批次更改 nop_xxx = 0
    public void batchUpdateNOP_Zero() {
        
        //設定行事曆上船之後，將隔年假日的人數先歸0
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        int year = calendar.get(Calendar.YEAR) + 1;
        
        // 將 is_holiday = '是' 批次更改 nop_xxx = 0
        String sql = "UPDATE Holiday SET \n" +
                    "    nop_co1=0,nop_co2=0,nop_co3=0,nop_co4=0,nop_co5=0,nop_co6=0,nop_co7=0,nop_co8=0,nop_co9=0,nop_co10=0,nop_co11=0,nop_co12=0,nop_co13=0,nop_co14=0,nop_co15=0,\n" +
                    "    nop_ho1=0,nop_ho2=0,nop_ho3=0,nop_ho4=0,nop_ho5=0,nop_ho6=0,nop_ho7=0,nop_ho8=0,nop_ho9=0,nop_ho10=0,nop_ho11=0,nop_ho12=0,nop_ho13=0,nop_ho14=0,nop_ho15=0,\n" +
                    "    nop_ph1=0,nop_ph2=0,nop_ph3=0,nop_ph4=0,nop_ph5=0,nop_ph6=0,nop_ph7=0,nop_ph8=0,nop_ph9=0,nop_ph10=0,nop_ph11=0,nop_ph12=0,nop_ph13=0,nop_ph14=0,nop_ph15=0,\n" +
                    "    nop_fa1=0,nop_fa2=0,nop_fa3=0,nop_fa4=0,nop_fa5=0,nop_fa6=0,nop_fa7=0,nop_fa8=0,nop_fa9=0,nop_fa10=0,nop_fa11=0,nop_fa12=0,nop_fa13=0,nop_fa14=0,nop_fa15=0\n" +
                    " WHERE is_holiday = '是' and year(date)=" + year + ";";
        jdbcTemplate.update(sql);
        // 將沒有定義的 xxx_nop 欄位 批次更改 = 0
        String sql2 = "UPDATE Holiday SET \n" +
                    "    nop_co10=0,nop_co11=0,nop_co12=0,nop_co13=0,nop_co14=0,nop_co15=0,\n" +
                    "    nop_ho13=0,nop_ho14=0,nop_ho15=0,\n" +
                    "    nop_ph13=0,nop_ph14=0,nop_ph15=0,\n" +
                    "    nop_fa3=0,nop_fa4=0,nop_fa5=0,nop_fa6=0,nop_fa7=0,nop_fa8=0,nop_fa9=0,nop_fa10=0,nop_fa11=0,nop_fa12=0,nop_fa13=0,nop_fa14=0,nop_fa15=0\n" + 
                    "  WHERE year(date)=" + year + ";";
        jdbcTemplate.update(sql2);
        
        System.out.println(sql);
        System.out.println(sql2);
    }

}