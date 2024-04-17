package com.hs.mvc.controller;

import com.hs.mvc.entity.ClockOn;
import com.hs.mvc.entity.views.ViewSchedulerStatus;
import com.hs.mvc.repository.ClockOnDao;
import com.hs.mvc.repository.EmployeeDao;
import com.hs.mvc.repository.StatusDao;
import com.hs.mvc.repository.TransferToClockOnReportDao;
import com.hs.mvc.repository.ViewDao;
import static com.sun.org.apache.xalan.internal.lib.ExsltDynamic.map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeArray.map;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.core.convert.TypeDescriptor.map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/clock_on")
public class ClockOnController {
    @Autowired
    private StatusDao statusDao;
    
    @Autowired
    private ClockOnDao clockOnDao;
    
    @Autowired
    private EmployeeDao employeeDao;
    
    @Autowired
    private TransferToClockOnReportDao transferToClockOnReportDao;
    
    @Autowired
    private ViewDao viewDao;
    
    // 員工簽到畫面 I
    @RequestMapping("/input")
    public String input(Model model) {
        ClockOn clockOn = new ClockOn();
        model.addAttribute("clockOn", clockOn);
        model.addAttribute("list_status", statusDao.queryAllList());
        model.addAttribute("empName", "");
        model.addAttribute("list_clockon", null);
        model.addAttribute("statusChecked", 1);
        model.addAttribute("timeout", -1);
        return "clock_on"; // View(jsp page) 的名稱
    }
    
    // 員工簽到 I
    @RequestMapping("/in")
    public String in(Model model, ClockOn clockOn, @RequestBody MultiValueMap<String, String> formData) {
        String empName = employeeDao.getNameByNo(clockOn.getEmpNo());
        //員工打卡編號輸入轉大寫
        clockOn.setEmpNo(clockOn.getEmpNo().toUpperCase());
        if(empName != null) {
            // 簽到紀錄儲存
            // 會在clockon表中產生一筆紀錄
            clockOnDao.save(clockOn);
            // 個人當日紀錄轉檔
            // 會在clockonreport表中產生一筆紀錄
            transferToClockOnReportDao.transferTodayByEmpNo(clockOn.getEmpNo());
        }
        model.addAttribute("clockOn", clockOn);
        model.addAttribute("list_status", statusDao.queryAllList());
        model.addAttribute("empName", empName == null ? "無此員工" : empName);
        model.addAttribute("message", empName == null ? "打卡失敗或員工編號錯誤" : empName + " 打卡成功");
        List<ClockOn> list_clockon = clockOnDao.queryByEmpNoToday(clockOn.getEmpNo());
        model.addAttribute("list_clockon", list_clockon);
        model.addAttribute("statusChecked", 1);
        model.addAttribute("timeout", 30000);
        return "clock_on";
    }
    
    
    // 員工簽到畫面 II
    @RequestMapping("/input2")
    public String input2(Model model) {
        ClockOn clockOn = new ClockOn();
        model.addAttribute("clockOn", clockOn);
        model.addAttribute("list_status", statusDao.queryAllList());
        model.addAttribute("empName", "");
        model.addAttribute("list_clockon", null);
        model.addAttribute("statusChecked", 1);
        model.addAttribute("timeout", -1);
        return "clock_on_2"; // View(jsp page) 的名稱
    }
    
        // 員工簽到 II
    @RequestMapping("/in2")
    public String in2(Model model, ClockOn clockOn, @RequestBody MultiValueMap<String, String> formData) {
        String empName = employeeDao.getNameByNo(clockOn.getEmpNo());
        //員工打卡編號輸入轉大寫
        clockOn.setEmpNo(clockOn.getEmpNo().toUpperCase());
        if(empName != null) {
            //clockOn.setStatusId(null);  // 丟失封包，錯誤(500)測試使用
            // 簽到紀錄儲存
            // 會在clockon表中產生一筆紀錄
            clockOnDao.save(clockOn);
            // 個人當日紀錄轉檔
            // 會在clockonreport表中產生一筆紀錄
            transferToClockOnReportDao.transferTodayByEmpNo(clockOn.getEmpNo());
        }
        model.addAttribute("clockOn", clockOn);
        model.addAttribute("list_status", statusDao.queryAllList());
        model.addAttribute("empName", empName == null ? "無此員工" : empName);
        model.addAttribute("message", empName == null ? "打卡失敗或員工編號錯誤" : empName + " 打卡成功");
        List<ClockOn> list_clockon = clockOnDao.queryByEmpNoToday(clockOn.getEmpNo());
        model.addAttribute("list_clockon", list_clockon);
        model.addAttribute("statusChecked", 1);
        model.addAttribute("timeout", 30000);
        return "clock_on_2";
    }
    
    // 查詢該員工排班資料 & 今日員工的打卡紀錄 (打卡專用)
    @RequestMapping("/query/scheduler/employee")
    @ResponseBody
    public Map<String, List> querySchedulerEmployee (@RequestBody Map<String, String> map) {
        String empNo = map.get("empNo");
        // 查詢該員工排班資料
        List<ViewSchedulerStatus> list = viewDao.queryViewSchedulerStatus(empNo);
        // 今日員工的打卡紀錄
        List<ClockOn> list_clockon = clockOnDao.queryByEmpNoToday(empNo);
        // 將上述二筆 List 封裝成一個 Map
        Map<String, List> resultMap = new LinkedHashMap<>();
        resultMap.put("scheduler_list", list);
        resultMap.put("clockon_list", list_clockon);
        return resultMap;
    }
    
}
