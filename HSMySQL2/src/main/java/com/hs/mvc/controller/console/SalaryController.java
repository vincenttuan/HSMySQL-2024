package com.hs.mvc.controller.console;

import com.hs.mvc.repository.EmployeeDao;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/console/salary")
public class SalaryController {
    
    @Autowired
    private EmployeeDao emeployeeDao;
    
    @RequestMapping("/master")
    public String Master(Model model,
            @RequestParam(value = "year", required = false) Integer year, 
            @RequestParam(value = "month", required = false) Integer month) {

        if (year == null || month == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());//現在日期
            cal.add(Calendar.MONTH, 1);//點選班表設定預設現在月份加1
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
        }
        
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("emps", emeployeeDao.queryAll());
        return "salary/salary_master";
    }
    
    @RequestMapping("/detial")
    public String detial(Model model) {
        return "salary/salary_detial";
    }
}
