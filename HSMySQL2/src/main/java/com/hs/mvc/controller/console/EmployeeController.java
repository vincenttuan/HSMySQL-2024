package com.hs.mvc.controller.console;

import com.hs.mvc.entity.Employee;
import com.hs.mvc.repository.ConsoleServiceDao;
import com.hs.mvc.repository.ConsoleServiceSetupDao;
import com.hs.mvc.repository.DeptDao;
import com.hs.mvc.repository.EmployeeDao;
import java.util.ArrayList;
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
@RequestMapping("/console/employee")
public class EmployeeController {
    
    @Autowired
    private EmployeeDao employeeDao;
    
    @Autowired
    private ConsoleServiceSetupDao consoleServiceSetupDao;
    
    @Autowired
    private ConsoleServiceDao consoleServiceDao;
    
    @Autowired
    private DeptDao deptDao;
    
    // 取得最員工編號
    @RequestMapping(value = "/getNextEmpNo", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String getNextEmpNo(@RequestBody Map<String, String> map) {
        String deptId = map.get("deptId").toString();
        return "HS" + employeeDao.getNextEmpNo(Integer.parseInt(deptId));
    }
    
    // 新增員工主頁面
    @RequestMapping(value = "/input", produces = "text/plain; charset=utf-8")
    public String input(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("employeeList", employeeDao.query());
        model.addAttribute("deptList", deptDao.queryAll());
        model.addAttribute("consoleServiceSetupList", new ArrayList());
        model.addAttribute("consoleServiceList", consoleServiceDao.queryAll());
        model.addAttribute("actionText", "新增");
        model.addAttribute("actionPath", "save");
        return "employee_update";
    }
    
    // 查找員工
    @RequestMapping(value = "/find", produces = "text/plain; charset=utf-8")
    public String find(Model model, @RequestParam("empId") Integer empId) {
        Employee employee = employeeDao.getEmployeeByEmpId(empId);
        if(employee == null) {
            employee = new Employee();
        }
        model.addAttribute("employee", employee);
        model.addAttribute("employeeList", employeeDao.query());
        model.addAttribute("deptList", deptDao.queryAll());
        model.addAttribute("consoleServiceSetupList", consoleServiceSetupDao.getByEmpId(empId));
        model.addAttribute("consoleServiceList", consoleServiceDao.queryAll());
        model.addAttribute("actionText", "修改");
        model.addAttribute("actionPath", "update");
        return "employee_update";
    }
    
    // 修改員工資料
    @RequestMapping(value = "/update", produces = "text/plain; charset=utf-8")
    public String update(Model model, @RequestBody MultiValueMap<String, String> formData) {
        String emp_no       = formData.getFirst("emp_no");
        String emp_name     = "";
        // emp_name 中文編碼
        try {
            emp_name = new String(formData.getFirst("emp_name").getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            emp_name = "合順員工";
        }
        String emp_active   = "1"; // 預設 = 1
        String emp_rfid     = formData.getFirst("emp_rfid");
        String agent1_id    = formData.getFirst("agent1_id");
        String emp_priority = formData.getFirst("emp_priority");
        String dept_id1     = formData.getFirst("dept_id1");
        String dept_id2     = formData.getFirst("dept_id2");
        int rowCount = employeeDao.update(emp_no, emp_name, emp_active, emp_rfid, agent1_id, emp_priority, dept_id1, dept_id2);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        // 取得該筆員工 id
        Integer emp_id = employeeDao.getIdByNo(emp_no);
        
        // 刪除原有使用功能權限
        consoleServiceSetupDao.deleteByEmpId(emp_id);
        
        // 新增使用功能權限
        if(emp_id != null) {
            List<String> cs_ids = formData.get("cs_id");
            List<String> css_priorities = formData.get("css_priority");
            if(cs_ids.size() == css_priorities.size()) {
                for(int i=0;i<cs_ids.size();i++) {
                    consoleServiceSetupDao.save( 
                            Integer.parseInt(cs_ids.get(i)), 
                            emp_id,
                            Integer.parseInt(css_priorities.get(i)));
                }
            }
        }
        
        //return input(model);
        return String.format("redirect: ./find?empId=%d&action=%s", emp_id, "update");
    }
    
    // 新增員工資料
    @RequestMapping(value = "/save", produces = "text/plain; charset=utf-8")
    public String save(Model model, @RequestBody MultiValueMap<String, String> formData) {
        String emp_no       = formData.getFirst("emp_no");
        String emp_name     = "";
        // emp_name 中文編碼
        try {
            emp_name = new String(formData.getFirst("emp_name").getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            emp_name = "合順員工";
        }
        String emp_active   = "1"; // 預設 = 1
        String emp_rfid     = formData.getFirst("emp_rfid");
        String agent1_id    = formData.getFirst("agent1_id");
        String emp_priority = formData.getFirst("emp_priority");
        String dept_id1     = formData.getFirst("dept_id1");
        String dept_id2     = formData.getFirst("dept_id2");
        int rowCount = employeeDao.save(emp_no, emp_name, emp_active, emp_rfid, agent1_id, emp_priority, dept_id1, dept_id2);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        
        // 新增使用功能權限
        // 取得該筆員工 id
        Integer emp_id = employeeDao.getIdByNo(emp_no);
        if(emp_id != null) {
            List<String> cs_ids = formData.get("cs_id");
            List<String> css_priorities = formData.get("css_priority");
            if(cs_ids.size() == css_priorities.size()) {
                for(int i=0;i<cs_ids.size();i++) {
                    consoleServiceSetupDao.save( 
                            Integer.parseInt(cs_ids.get(i)), 
                            emp_id,
                            Integer.parseInt(css_priorities.get(i)));
                }
            }
        }
        
        //return input(model);
        return String.format("redirect: ./find?empId=%d&action=%s", emp_id, "save");
    }
    
    // 變更密碼主頁面
    @RequestMapping(value = "/inputPwd", produces = "text/plain; charset=utf-8")
    public String inputPwd() {
        return "employee_changepwd";
    }
    
    // 變更密碼
    @RequestMapping(value = "/updatePwd", produces = "text/plain; charset=utf-8")
    public String updatePwd(Model model, HttpSession session, 
                            @RequestBody MultiValueMap<String, String> formData) {
        String oldPwd = formData.getFirst("oldPwd").trim();
        String newPwd1 = formData.getFirst("newPwd1").trim();
        // 取得登入者的 empNo
        String empNo = ((Employee)session.getAttribute("employee")).getEmpNo();
        // 修改密碼
        int rowCount = employeeDao.changePwd(empNo, oldPwd, newPwd1);
        // 判斷密碼是否變更成功 ?
        if(rowCount == 0) {
            model.addAttribute("resp_msg", "密碼變更失敗");
        } else {
            model.addAttribute("resp_msg", "密碼變更成功");
        }
        return "employee_changepwd";
    }
    
}
