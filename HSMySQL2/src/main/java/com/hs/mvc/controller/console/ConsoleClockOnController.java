package com.hs.mvc.controller.console;

import com.hs.mvc.entity.ClockOn;
import com.hs.mvc.entity.Employee;
import com.hs.mvc.repository.ClockOnDao;
import com.hs.mvc.repository.EmployeeDao;
import com.hs.mvc.repository.StatusDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/console/clock_on")
public class ConsoleClockOnController {
    @Autowired
    private StatusDao statusDao;
    
    @Autowired
    private ClockOnDao clockOnDao;
    
    @Autowired
    private EmployeeDao employeeDao;
    
    // 查詢簽到(ClockOn)資料 - 主畫面
    @RequestMapping("/queryClockOn")
    public String queryClockOn(Model model, HttpSession session) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = sdFormat.format(new Date());
        String empNo = ((Employee)session.getAttribute("employee")).getEmpNo();
        
        model.addAttribute("d1", todayString);
        model.addAttribute("d2", todayString);
        model.addAttribute("empNo", empNo);
        model.addAttribute("emps", employeeDao.query());
        return "clock_on_list";
    }
    
    // 查詢簽到(ClockOn)資料 - 根據員工編號 + 起始日 + 結束日
    @RequestMapping("/queryBetween")
    public String queryBetween(Model model, 
                               @RequestBody MultiValueMap<String, String> formData) {
        
        List<ClockOn> list = clockOnDao.queryAllBetween(formData.getFirst("empNo"), 
                                                        formData.getFirst("d1"), 
                                                        formData.getFirst("d2"));
        model.addAttribute("d1", formData.getFirst("d1"));
        model.addAttribute("d2", formData.getFirst("d2"));
        model.addAttribute("list", list);
        model.addAttribute("empNo", formData.getFirst("empNo"));
        model.addAttribute("emps", employeeDao.query());
        return "clock_on_list";
    }
    
}
