package com.hs.mvc.controller.console;

import com.hs.mvc.entity.Employee;
import com.hs.mvc.entity.views.ViewClockOnReportMark;
import com.hs.mvc.repository.ClockOnReportDao;
import com.hs.mvc.repository.EmployeeDao;
import com.hs.mvc.repository.ViewDao;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/console/clockonreport")
public class ClockOnReportController {
    @Autowired
    private ClockOnReportDao clockOnReportDao;
    
    // 修改 memo 欄位
    @RequestMapping(value = "/updatememo")
    public String updateMemo(@RequestParam("memo") String memo, HttpSession session) {
        // memo 中文編碼
        try {
            memo = new String(memo.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
        }
        // 取得登入者的員工編號
        String empNo = ((Employee)session.getAttribute("employee")).getEmpNo();
        // 修改 memo 欄位
        clockOnReportDao.updateMemo(empNo, memo);
        // 重新導向至 HS 簽到月報表 頁面
        return "redirect: ../view/queryViewClockOnReportMark?csId=3";
    }
    
    // 主管回覆內容修改
    @RequestMapping(value = "/updateReply", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String updateReply(@RequestBody Map<String, String> map, HttpSession session) {
        System.out.println(map);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date reportDate = sdf.parse(map.get("reportDate").toString());
            String empNo = map.get("empNo");
            int replyScore = Integer.parseInt(map.get("replyScore").toString());
            String replyContent = map.get("replyContent");
            // 取得登入者的員工編號
            String replayEmpno = ((Employee)session.getAttribute("employee")).getEmpNo();
            return "" + clockOnReportDao.replyUpdate(reportDate, empNo, replyScore, replyContent, replayEmpno);
        } catch (Exception e) {
            return e.toString();
        }
        
    }
    
    // 主管考核內容修改
    @RequestMapping(value = "/updateAssessment", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String updateAssessment(@RequestBody Map<String, String> map, HttpSession session) {
        System.out.println(map);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date reportDate = sdf.parse(map.get("reportDate").toString());
            String empNo = map.get("empNo");
            int assessmentScore = Integer.parseInt(map.get("assessmentScore").toString());
            String assessmentContent = map.get("assessmentContent");
            // 取得登入者的員工編號
            String assessmentEmpno = ((Employee)session.getAttribute("employee")).getEmpNo();
            return "" + clockOnReportDao.assessmentUpdate(reportDate, empNo, assessmentScore, assessmentContent, assessmentEmpno);
        } catch (Exception e) {
            return e.toString();
        }
    }
}
