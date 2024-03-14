package com.hs.mvc.controller.console.setup;

import com.hs.mvc.controller.console.*;
import com.google.gson.Gson;
import com.hs.mvc.entity.views.ViewClockOnReportMark;
import com.hs.mvc.repository.ArgsDao;
import com.hs.mvc.repository.ClockOnReportDao;
import com.hs.mvc.repository.ConsoleServiceDao;
import com.hs.mvc.repository.EmployeeDao;
import com.hs.mvc.repository.ViewDao;
import com.hs.mvc.repository.qa.QADao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/console/setup")
public class SetupController {

    @Autowired
    private EmployeeDao employeeDao;
    
    @Autowired
    private ArgsDao argsDao;
    
    
    
    @RequestMapping(value = "/employee/query", produces = "text/plain; charset=utf-8")
    public String queryEmployee(Model model) {
        model.addAttribute("list", employeeDao.queryAll());
        return "setup";
    }
    
    @RequestMapping(value = "/employee/changeActive", produces = "text/plain; charset=utf-8")
    public String changeActiveEmployee(Model model, @RequestParam String empNo, @RequestParam int empActive) {
        employeeDao.changeActive(empNo, empActive);
        model.addAttribute("list", employeeDao.queryAll());
        return "setup";
    }
    
    @RequestMapping(value = "/args", produces = "text/plain; charset=utf-8")
    public String queryArgs(Model model) {
        model.addAttribute("list", argsDao.queryAllArgs());
        return "args";
    }
    
    @RequestMapping(value = "/args/update")
    @ResponseBody
    public Integer updateArgs(Model model, @RequestBody Map<String, String> map, HttpServletRequest request ) {
        Integer id = Integer.parseInt(map.get("id"));
        String columnName = map.get("columnName");
        String value = map.get("value");
        int rowcount = argsDao.updateArgs(id, columnName, value);
        // 更新ServletContext args參數資料
        request.getServletContext().setAttribute("args", argsDao.queryAllArgs());
        return rowcount;
    }
}
