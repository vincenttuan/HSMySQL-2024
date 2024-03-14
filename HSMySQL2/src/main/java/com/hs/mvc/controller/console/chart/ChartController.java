//http://localhost:8080/HSMySQL/mvc/console/chart/query/employee/attendant#
package com.hs.mvc.controller.console.chart;

import com.hs.mvc.repository.ChartDao;
import com.hs.mvc.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/console/chart")
public class ChartController {

    @Autowired
    private ChartDao chartDao;
    
    @Autowired
    private EmployeeDao employeeDao;
    
    @RequestMapping(value = "/query/employee/attendant", 
                    produces = "text/plain; charset=utf-8")
//    @ResponseBody
    public String queryEmployeeAttendant(Model model,
            @RequestParam("emp_no") String emp_no,
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) {
        String emp_name = employeeDao.getNameByNo(emp_no);
        model.addAttribute("list", chartDao.queryStaffAttendant(emp_no, year, month));
        model.addAttribute("emp_no", emp_no);
        model.addAttribute("emp_name", emp_name);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        return "chart/employeeAttendant";
//        return chartDao.queryStaffAttendent(emp_no, year, month).toString();
    }
}
