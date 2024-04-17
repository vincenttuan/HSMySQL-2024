package com.hs.mvc.controller.console;

import com.google.gson.Gson;
import com.hs.mvc.entity.views.ViewClockOnReportMark;
import com.hs.mvc.repository.ClockOnReportDao;
import com.hs.mvc.repository.ConsoleServiceDao;
import com.hs.mvc.repository.ViewDao;
import com.hs.mvc.repository.qa.QADao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ConsoleServiceDao consoleServiceDao;
    
    @Autowired
    private QADao qADao;
    
    @RequestMapping(value = "/qa/group", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String queryQAGroup() {
        return qADao.queryQAGroup().toString();
    }
    
    @RequestMapping(value = "/qa/item", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String queryQAItem() {
        return qADao.queryQAItem().toString();
    }
    
    // 使用者後台權限
    @RequestMapping("/consoleservice/query/{empId}")
    @ResponseBody
    public String query(@PathVariable Integer empId) {
        return consoleServiceDao.queryByEmpId(empId).toString();
    }

    //-----------------------------------------------------------
    @Autowired
    private ViewDao viewDao;

    @Autowired
    private ClockOnReportDao clockOnReportDao;

    // View 報表 ,produces = "text/plain; charset=utf-8"
    @RequestMapping(value = "/queryViewClockOnReportMark", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String queryViewClockOnReportMark() {
        int year = 2020;
        int month = 5;
        String empNo = "HS10360";
        List<ViewClockOnReportMark> list = viewDao.queryViewClockOnReportMark(year, month, empNo);

        return list.toString();
        //return new Gson().toJson(list);
    }

}
