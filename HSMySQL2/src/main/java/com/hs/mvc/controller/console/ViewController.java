package com.hs.mvc.controller.console;

import com.hs.mvc.entity.ClockOn;
import com.hs.mvc.entity.Employee;
import com.hs.mvc.entity.views.EmpRankLevel;
import com.hs.mvc.entity.views.ViewClockOnReportMark;
import com.hs.mvc.repository.ArgsDao;
import com.hs.mvc.repository.ClockOnReportDao;
import com.hs.mvc.repository.EmployeeDao;
import com.hs.mvc.repository.LogDao;
import com.hs.mvc.repository.SignatureDao;
import com.hs.mvc.repository.ViewDao;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/console/view")
public class ViewController {
    @Autowired
    private ViewDao viewDao;
    
    @Autowired
    private EmployeeDao employeeDao;
    
    @Autowired
    private ClockOnReportDao clockOnReportDao;
    
    @Autowired
    private LogDao logDao;
    
    @Autowired
    private SignatureDao signatureDao;
    
    
    @RequestMapping(value = "/queryEmpRankLevelList", produces = "text/plain; charset=utf-8")
    public String queryEmpRankLevelList(Model model){
        List<EmpRankLevel> list = viewDao.queryViewEmpRankLevels(); //查詢資料
        model.addAttribute("list", list); //帶入參數
        return "view_emp_ranklevel_list"; //jsp名稱
    }
    
    // View 每日工作回報
    @RequestMapping(value = "/queryViewClickOnMemoReport", produces = "text/plain; charset=utf-8")
    public String queryViewClickOnMemoReport(Model model, 
                            @RequestParam("empNo") String empNo,
                            @RequestParam("d1") String d1,
                            @RequestParam("d2") String d2,
                            @RequestParam(value = "menu_display", defaultValue = "") String menu_display) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 檢查參數
        empNo = empNo == null || empNo.trim().length()==0 ? "":empNo;
        if(d1 == null || d1.trim().length()==0) {
            d1 = sdf.format(new Date());
            d2 = d1;
        }
        
        // 查詢每日工作回報 
        List<ViewClockOnReportMark> list = viewDao.queryViewClockOnMemoReport(empNo, d1, d2);
        model.addAttribute("empNo", empNo);
        model.addAttribute("d1", d1);
        model.addAttribute("d2", d2);
        model.addAttribute("menu_display", menu_display);
        model.addAttribute("list", list);
        model.addAttribute("emps", employeeDao.query()); // 所有員工資料（給下拉選單用）
        return "view_clockon_report_memo";
    }
    
    // View 報表
    @RequestMapping(value = "/queryViewClockOnReportMark", produces = "text/plain; charset=utf-8")
    public String queryViewClockOnReportMark(Model model, HttpSession session) {
        // 抓取目前的年月
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        // 取得登入者的 empNo
        String empNo = ((Employee)session.getAttribute("employee")).getEmpNo();
        // 取得「簽到月報表」資料
        List<ViewClockOnReportMark> list = viewDao.queryViewClockOnReportMark(year, month, empNo);
        // 取得當日 memo
        String memo = clockOnReportDao.getTodayMemo(empNo);
        // 傳給 jsp 的參數內容
        model.addAttribute("list", list);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("empNo", empNo);
        model.addAttribute("emps", employeeDao.query()); // 所有員工資料（給下拉選單用）
        model.addAttribute("memo", memo);
        return "view_clockon_report_mark";
    }
    
    @RequestMapping(value = "/queryViewClockOnReportMark_M", produces = "text/plain; charset=utf-8")
    public String queryViewClockOnReportMark_M(Model model, HttpSession session) {
        // 抓取目前的年月
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        // 取得登入者的 empNo
        String empNo = ((Employee)session.getAttribute("employee")).getEmpNo();
        // 取得「簽到月報表」資料
        List<ViewClockOnReportMark> list = viewDao.queryViewClockOnReportMark(year, month, empNo);
        // 取得當日 memo
        String memo = clockOnReportDao.getTodayMemo(empNo);
        // 取得員工撰寫memo所花費的時間列表
        List<Map<String, Long>> memoSpendtimeList = logDao.queryUpdatememoSpendtime(empNo, year, month);
        // 取得員工撰寫memo所花費的時間細目列表
        List<Map<String, String>> memoSpendtimeDetailList = logDao.queryUpdatememoSpendtimeDetail(empNo, year, month);
        // 傳給 jsp 的參數內容
        model.addAttribute("list", list);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("empNo", empNo);
        model.addAttribute("emps", employeeDao.query()); // 所有員工資料（給下拉選單用）
        model.addAttribute("memo", memo);
        model.addAttribute("memoSpendtimeList", memoSpendtimeList);
        model.addAttribute("memoSpendtimeDetailList", memoSpendtimeDetailList);
        return "view_clockon_report_mark_m";
    }
    
    // View 報表
    @RequestMapping(value = "/submitViewClockOnReportMark", produces = "text/plain; charset=utf-8")
    //@ResponseBody
    public String submitViewClockOnReportMark(Model model, 
                            @RequestParam("year") int year,
                            @RequestParam("month") int month,
                            @RequestParam("empNo") String empNo) {

        List<ViewClockOnReportMark> list = viewDao.queryViewClockOnReportMark(year, month, empNo);
        // 取得當日 memo
        String memo = clockOnReportDao.getTodayMemo(empNo);
        // 傳給 jsp 的參數內容
        model.addAttribute("list", list);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("empNo", empNo);
        model.addAttribute("emps", employeeDao.query());
        model.addAttribute("memo", memo);
        return "view_clockon_report_mark";
    }
    
    @RequestMapping(value = "/submitViewClockOnReportMark_M", produces = "text/plain; charset=utf-8")
    //@ResponseBody
    public String submitViewClockOnReportMark_M(Model model, 
                            @RequestParam("year") int year,
                            @RequestParam("month") int month,
                            @RequestParam("empNo") String empNo) {

        List<ViewClockOnReportMark> list = viewDao.queryViewClockOnReportMark(year, month, empNo);
        // 取得當日 memo
        String memo = clockOnReportDao.getTodayMemo(empNo);
        // 取得員工撰寫memo所花費的時間列表
        List<Map<String, Long>> memoSpendtimeList = logDao.queryUpdatememoSpendtime(empNo, year, month);
        // 取得員工撰寫memo所花費的時間細目列表
        List<Map<String, String>> memoSpendtimeDetailList = logDao.queryUpdatememoSpendtimeDetail(empNo, year, month);
        // 傳給 jsp 的參數內容
        model.addAttribute("list", list);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("empNo", empNo);
        model.addAttribute("emps", employeeDao.query());
        model.addAttribute("memo", memo);
        model.addAttribute("memoSpendtimeList", memoSpendtimeList);
        model.addAttribute("memoSpendtimeDetailList", memoSpendtimeDetailList);
        return "view_clockon_report_mark_m";
    }
    
    @RequestMapping(value = "/exportExcelViewClockOnReportMark_M", produces = "text/plain; charset=utf-8")
    //@ResponseBody
    public String exportExcelViewClockOnReportMark_M(Model model, 
                            @RequestParam("year") int year,
                            @RequestParam("month") int month,
                            @RequestParam("empNo") String empNo) {

        List<ViewClockOnReportMark> list = viewDao.queryViewClockOnReportMark(year, month, empNo);
        // 取得當日 memo
        String memo = clockOnReportDao.getTodayMemo(empNo);
        // 傳給 jsp 的參數內容
        model.addAttribute("list", list);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("empNo", empNo);
        model.addAttribute("emps", employeeDao.query());
        model.addAttribute("memo", memo);
        return "excel_view_clockon_report_mark_m";
    }

    @RequestMapping(value = "/signatureViewClockOnReportMark_M", produces = "text/plain; charset=utf-8")
    public String signatureViewClockOnReportMark_M(Model model, HttpSession session) {
        // 抓取目前的年月
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        // 取得登入者的 empNo
        String empNo = ((Employee)session.getAttribute("employee")).getEmpNo();
        // 取得「簽到月報表」資料
        List<ViewClockOnReportMark> list = viewDao.queryViewClockOnReportMark(year, month, empNo);
        // 取得當日 memo
        String memo = clockOnReportDao.getTodayMemo(empNo);
        // 傳給 jsp 的參數內容
        model.addAttribute("list", list);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("empNo", empNo);
        model.addAttribute("emps", employeeDao.query()); // 所有員工資料（給下拉選單用）
        model.addAttribute("memo", memo);
        model.addAttribute("signature", signatureDao.getSignature(empNo, 1, year, month));
        model.addAttribute("clockOnExceptions", signatureDao.queryClockOnException(empNo, year, month));
        model.addAttribute("end_day", lastDayOfMonth(year, month));
        return "signature_view_clockon_report_mark_m";
    }
    
    @RequestMapping(value = "/signatureSubmitViewClockOnReportMark_M", produces = "text/plain; charset=utf-8")
    //@ResponseBody
    public String signatureSubmitViewClockOnReportMark_M(Model model, 
                            @RequestParam("year") int year,
                            @RequestParam("month") int month,
                            @RequestParam("empNo") String empNo) {

        List<ViewClockOnReportMark> list = viewDao.queryViewClockOnReportMark(year, month, empNo);
        // 取得當日 memo
        String memo = clockOnReportDao.getTodayMemo(empNo);
        // 傳給 jsp 的參數內容
        model.addAttribute("list", list);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("empNo", empNo);
        model.addAttribute("emps", employeeDao.query());
        model.addAttribute("memo", memo);
        model.addAttribute("signature", signatureDao.getSignature(empNo, 1, year, month));
        model.addAttribute("clockOnExceptions", signatureDao.queryClockOnException(empNo, year, month));
        model.addAttribute("end_day", lastDayOfMonth(year, month));
        return "signature_view_clockon_report_mark_m";
    }
    
    private int lastDayOfMonth(int year, int month) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(year+"-" + month + "-01");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.roll(Calendar.DAY_OF_MONTH, -1);
            return calendar.get(Calendar.DATE);
        } catch (Exception e) {
        }
        return 31;
    }
}
