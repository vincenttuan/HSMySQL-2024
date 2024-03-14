package com.hs.mvc.controller.console;

import com.google.gson.Gson;
import com.hs.mvc.entity.Employee;
import com.hs.mvc.entity.SchedulerEmployee;
import com.hs.mvc.repository.ArgsDao;
import com.hs.mvc.repository.HolidayDao;
import com.hs.mvc.repository.SchedulerDao;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/console/calendar")
public class CalendarController {

    private Gson gson = new Gson();

    @Autowired
    private ArgsDao argsDao;

    @Autowired
    private HolidayDao holidayDao;

    @Autowired
    private SchedulerDao schedulerDao;
    
    // 調整排班視窗
    @RequestMapping("/switch/emp")
    public String switchEmp(Model model, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "month", required = false) Integer month) {
        return "calendar_switch_emp";
    }
    
    // 行事曆-班表檢視
    @RequestMapping("/view")
    public String view(Model model, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "month", required = false) Integer month) {
        if (year == null || month == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());//現在日期
            cal.add(Calendar.MONTH, 1);//點選班表設定預設現在月份加1
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
        }

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("holidays", holidayDao.query(year, month));
        model.addAttribute("nopMap", schedulerDao.getNewNopMap());
        model.addAttribute("schedulerItems", gson.toJson(schedulerDao.querySchedulerItem()));
        model.addAttribute("lastDate", lastDayOfMonth(year, month));
        return "calendar";
    }

    // 查詢每日排班人員狀況
    @RequestMapping(value = "/scheduler/search")
    //@ResponseBody
    public String querySchedulerSearch(Model model,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "day", required = false) Integer day) {

        List<Map<String, Object>> list = null;

        if (year == null || month == null || day == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
            day = cal.get(Calendar.DATE);
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse(year + "/" + month + "/" + day);
            list = schedulerDao.querySchedulerSearch(date);

        } catch (Exception e) {
        }
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);
        model.addAttribute("list", list);
        return "calendar_scheduler_search";
    }

    // 查詢最新 nop 人數
    @RequestMapping(value = "/get/scheduler/nop")
    @ResponseBody
    public Integer getNop(@RequestBody Map<String, String> map) {
        int nop = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date sdate = sdf.parse(map.get("sdate").toString());
            String colName = map.get("colName").toString();
            nop = schedulerDao.getNop(colName, sdate);
        } catch (Exception e) {
        }
        return nop;
    }

    // 排班表打勾 V 更新
    @RequestMapping(value = "/update/scheduler/employee")
    @ResponseBody
    public Integer updateSchedulerEmployee(@RequestBody Map<String, String> map) {
        // 判斷是否今天可以修改排班表 ?
        boolean editable = true;//argsDao.isEditEnableByCalender();
        Integer flag = 0;
        if (editable) {
            try {
                String empNo = map.get("empNo").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date sdate = sdf.parse(map.get("sdate").toString());
                Integer gid = Integer.parseInt(map.get("gid").toString());
                Integer iid = Integer.parseInt(map.get("iid").toString());
                String colName = map.get("colName").toString();
                // 存入資料表
                flag = schedulerDao.updateSchedulerEmployee(empNo, sdate, gid, iid, colName);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // flag = 0 建立失敗, 1: 新增成功, 2: 刪除成功
        return flag;
    }

    // 員工排班表輸入
    @RequestMapping(value = "/scheduler/input")
    public String schedulerInput(Model model,
            HttpSession session,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        if (year == null || month == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
        }

        // 判斷是否可以取得下月資料
        // 今天年月日
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        Integer todayYear = today.get(Calendar.YEAR);
        Integer todayMonth = today.get(Calendar.MONTH) + 1;
        Integer todayDay = today.get(Calendar.DATE);

        int todayYearMonth = todayYear * 100 + todayMonth;
        int yearMonth = year * 100 + month;
        System.out.println(yearMonth + " > " + todayYearMonth);
        // 202101 > 202012 
        // 是否可以回到上個月分?
         // 取得排班表月份限制解除資料(1:解除, 0:限制)
        int scheduleMonthRestrictionInt = argsDao.getScheduleMonthRestrictionInt();

        if (scheduleMonthRestrictionInt == 0) {
            // 202101 > 202012
            if (yearMonth > todayYearMonth) {
                if (todayDay >= 20) {
                    today.add(Calendar.MONTH, 1);
                    year = today.get(Calendar.YEAR);
                    month = today.get(Calendar.MONTH) + 1;
                } else {
                    year = todayYear;
                    month = todayMonth;
                }
            } else if (yearMonth < todayYearMonth) {
                year = todayYear;
                month = todayMonth;
            }
        }


        // 取得登入者的 empNo
        String empNo = ((Employee) session.getAttribute("employee")).getEmpNo();

        model.addAttribute("year", year);
        model.addAttribute("month", month);
//        model.addAttribute("holidays", holidayDao.queryView(year, month));
        model.addAttribute("holidays", holidayDao.query(year, month));
        model.addAttribute("nopMap", schedulerDao.getNewNopMap());
        model.addAttribute("schedulerEmployees", schedulerDao.querySchedulerEmployees(year, month, empNo));
        model.addAttribute("employees", schedulerDao.querySchedulerEmployees(year, month));
        return "calendar_scheduler";
    }

    // 行事曆批次修改
    @RequestMapping(value = "/update/batch", produces = "text/plain; charset=utf-8")
    //測試輸出內容，平常要註解掉
    //@ResponseBody
    public String updateBatch(Model model,
            HttpSession session,
            @RequestBody MultiValueMap<String, String> formData) {
        
        String u_nop = formData.get("u_nop").get(0);
        String u_peroid = formData.get("u_peroid").get(0);
        String u_people = formData.get("u_people").get(0);
        
        String update_sql = "UPDATE Holiday SET \n";
        List<String> u_peroids = formData.get("u_peroid"); // 時段
        for(int i=0;i<u_peroids.size();i++) {
            update_sql += "nop_" + u_nop + u_peroids.get(i) + " = " + u_people;
            if(i != u_peroids.size()-1) {
                update_sql += ", ";
            }
        }
        
        String confirm_sql = "SELECT date as '日期', MONTH(date) as '月份', DAYOFWEEK(date) as '星期', '%s' as '部門', %s as '時段', nop_%s%s as '原配置人數', %s as '新配置人數' from Holiday \n";

        String where_sql = "WHERE \n"
                + "    YEAR(date) = %s and\n"
                + "    MONTH(date) in (%s) and\n"
                + "    (WEEK(date, 0) - WEEK(DATE_SUB(date, INTERVAL DAYOFMONTH(date)-1 DAY),5)+1) in (%s) and\n";
        // 取得排班表月份限制解除資料(1:解除, 0:限制)
        int scheduleMonthRestrictionInt = argsDao.getScheduleMonthRestrictionInt();
        if (scheduleMonthRestrictionInt == 1) {
            where_sql += "    DAYOFWEEK(date) in (%s) ";
        } else {
            where_sql += "    DAYOFWEEK(date) in (%s) AND date > now() ";
        }
        
        String u_year = formData.get("u_year").get(0);
        String u_month = formData.get("u_month").toString()
                .replace("[", "").replace("]", "");
        String u_week = formData.get("u_week").toString()
                .replace("[", "").replace("]", "");
        String u_weekday = formData.get("u_weekday").toString()
                .replace("[", "").replace("]", "");

        //update_sql = String.format(update_sql, u_nop, u_peroid, u_people);
        update_sql += "\n";
        
        confirm_sql = String.format(confirm_sql, u_nop, u_peroid, u_nop, u_peroid, u_people);

        where_sql = String.format(where_sql, u_year, u_month, u_week, u_weekday);

        update_sql += where_sql;
        confirm_sql += where_sql;

        String message = formData.toString()
                .replace("u_year", "年份")
                .replace("u_month", "月份")
                .replace("u_weekday", "星期")
                .replace("u_week", "週數")
                .replace("u_peroid", "時段(1:上午、2:下午、3:晚上)")
                .replace("u_nop", "單位(co:公司、ho:診所、ph:藥局、fa:工廠)")
                .replace("u_people", "人數")
                .replace("{", "").replace("}", "");
        
        // 保存設定值
        session.setAttribute("formData", formData);
        
        // 批次經過預覽再寫入資料庫
            // 測試輸出內容
//        return formData + "\n\n" + message + "\n\n" + confirm_sql + "\n\n" + holidayDao.queryConfirm(confirm_sql) + "\n\n" + update_sql;
            // 寫入資料庫
//        model.addAttribute("list", holidayDao.queryConfirm(confirm_sql));
//        model.addAttribute("update_sql", update_sql);
//        model.addAttribute("schedulerItems", schedulerDao.querySchedulerItem());
//        return "calendar_confirm";

        // 批次直接修改資料表(要預覽必須動態產生)
            // 測試輸出內容
//        return formData.toString();
            // 寫入資料庫
        int count = holidayDao.updateBatch(update_sql);
        System.out.println(update_sql);
        model.addAttribute("csId", 10);
        model.addAttribute("count", count);
        model.addAttribute("year", u_year);
        model.addAttribute("month", formData.get("u_month").get(0));
        return "redirect:/mvc/console/calendar/view";
    }
    
    // 行事曆批次修改 2
    @RequestMapping(value = "/update/batch2", produces = "text/plain; charset=utf-8")
    //測試輸出內容，平常要註解掉
//    @ResponseBody
    public String updateBatch2(Model model,
            HttpSession session,
            @RequestBody MultiValueMap<String, String> formData) {
        /*
        UPDATE holiday
        SET nop_ho1 = 0, nop_ho2 = 0, nop_ho3 = 0, nop_ho4 = 0, nop_ho5 = 0, nop_ho6 = 0
        WHERE YEAR(date) = 2021 AND 
              MONTH(date) = 2 AND 
              DAY(date) in (10, 11, 12, 13, 14, 15, 16); 
        */
        String update_sql = "UPDATE Holiday SET \n";
        
        String u_year = formData.get("u_year").get(0); // 年份
        String u_month = formData.get("u_month").get(0); // 月份
        String u_date = formData.get("u_date").toString() // 日期
                .replace("[", "").replace("]", "");
        String u_nop = formData.get("u_nop").get(0); // 單位
        String u_people = formData.get("u_people").get(0); // 人數
        List<String> u_peroids = formData.get("u_peroid"); // 時段
        
        
        for(int i=0;i<u_peroids.size();i++) {
            update_sql += "nop_" + u_nop + u_peroids.get(i) + " = " + u_people;
            if(i != u_peroids.size()-1) {
                update_sql += ", ";
            }
        }
        String where_sql = " WHERE YEAR(date) = %s AND \n" +
                           " MONTH(date) = %s AND \n" +
                           " DAY(date) in (%s) AND date > now() ";
        where_sql = String.format(where_sql, u_year, u_month, u_date);
        
        update_sql += where_sql;
        
        // 批次直接修改資料表2
            // 寫入資料庫
        int count = holidayDao.updateBatch(update_sql);
        model.addAttribute("csId", 10);
        model.addAttribute("count", count);
        model.addAttribute("year", u_year);
        model.addAttribute("month", u_month);
        return "redirect:/mvc/console/calendar/view";
        //return "redirect:/mvc/console/calendar/view?csId=10";   
            // 測試輸出內容
//        return formData + "\n\n\n" + update_sql;
    }

    @RequestMapping(value = "/update/batch/commit", produces = "text/plain; charset=utf-8")
    public String updateBatchCommit(Model model, @RequestBody MultiValueMap<String, String> formData) {

        String update_sql = formData.get("update_sql").get(0);
        // 直接批次修改資料表
        int count = holidayDao.updateBatch(update_sql);
        model.addAttribute("count", count);
        return "redirect:/mvc/console/calendar/view?csId=10";
    }

    // HS班表設定 (人事用) 修改每日需求人數
    @RequestMapping("/update/nop")
    @ResponseBody
    public boolean updateNop(@RequestBody Map<String, String> map) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse(map.get("date"));
            String key = map.get("key");
            int value = Integer.parseInt(map.get("value"));
            return holidayDao.updateNop(date, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/update/name")
    @ResponseBody
    public boolean updateName(@RequestBody Map<String, String> map) {
        Date date = null;
        String name = map.get("name").toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            date = sdf.parse(map.get("date").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return holidayDao.updateName(date, name);
    }

    @RequestMapping("/update/isholiday")
    @ResponseBody
    public boolean updateIsHoliday(@RequestBody Map<String, String> map) {
        Date date = null;
        String isholiday = map.get("isholiday").toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            date = sdf.parse(map.get("date").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return holidayDao.updateIsHoliday(date, isholiday);
    }

    // 排班總表
    @RequestMapping("/scheduler/view")
    public String schedulerView(Model model, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "month", required = false) Integer month) {
        if (year == null || month == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
        }

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("holidays", holidayDao.query(year, month));
        model.addAttribute("nopMap", schedulerDao.getNewNopMap());
        model.addAttribute("employees", schedulerDao.querySchedulerEmployees(year, month));
        return "calendar_scheduler_view";
    }

    private int lastDayOfMonth(int year, int month) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-01");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.roll(Calendar.DAY_OF_MONTH, -1);
            return calendar.get(Calendar.DATE);
        } catch (Exception e) {
        }
        return 31;
    }
}
