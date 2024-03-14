package com.hs.mvc.controller;

import com.hs.mvc.entity.ConsoleServiceSetup;
import com.hs.mvc.entity.Employee;
import com.hs.mvc.repository.ConsoleServiceDao;
import com.hs.mvc.repository.EmployeeDao;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private EmployeeDao employeeDao;
    
    @Autowired
    private ConsoleServiceDao consoleServiceDao;
    
    // 員工簽到畫面
    @RequestMapping("/")
    public String input(@RequestBody MultiValueMap<String, String> formData, HttpSession session) {
        String username = formData.getFirst("username");
        String password = formData.getFirst("password");
        Employee employee = employeeDao.login(username, password);
        System.out.println(employee);
        if (employee == null) {
            session.invalidate();
            if(username.trim().length() == 0 || password.trim().length() == 0) {
                return "redirect:/login.jsp?flag=true";
            } else {
                return "redirect:/login.jsp?flag=false";
            }
        }
        
        // 取得使用者權限設定
        List<ConsoleServiceSetup> cssList = consoleServiceDao.queryByEmpId(employee.getEmpId());
        
        // 建立 session 物件
        session.setAttribute("employee", employee);
        session.setAttribute("cssList", cssList);
        // 登入成功後所跑的第一頁
        return "redirect:/mvc/console/view/queryViewClockOnReportMark?csId=3";
    }
    
    // 員工登出畫面
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // 讓 session 失效
        session.invalidate();
        // 重導到 login 畫面
        return "redirect:/login.jsp?flag=true";
    }
}
