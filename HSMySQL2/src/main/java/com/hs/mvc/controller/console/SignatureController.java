package com.hs.mvc.controller.console;

import com.hs.mvc.entity.ClockOnException;
import com.hs.mvc.entity.Employee;
import com.hs.mvc.entity.Signature;
import com.hs.mvc.repository.SignatureDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/console/signature")
public class SignatureController {
    
    @Autowired
    private SignatureDao signatureDao;
    
    // 簽名存檔
    @RequestMapping(value = "/save", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String save(@RequestBody Map<String, String> map) {
        String empNo = map.get("empNo").trim();
        int year = Integer.parseInt(map.get("year").trim());
        int month = Integer.parseInt(map.get("month").trim());
        String image = map.get("signature_result").trim();
        
        Signature signature = new Signature();
        signature.setEmpNo(empNo);
        signature.setSignType(1);
        signature.setSignYear(year);
        signature.setSignMonth(month);
        signature.setSignImage(image);
        
        signatureDao.saveSignature(signature);
        return "OK";
    }
    
    // 異常存檔
    @RequestMapping(value = "/add_clockon_exception", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String add_clockon_exception(@RequestBody Map<String, String> map, HttpSession session) {
        try {
            String empNo = map.get("empNo").trim();
            String authorEmpno = ((Employee)session.getAttribute("employee")).getEmpNo();
            Date reportDate = new SimpleDateFormat("yyyy/MM/dd").parse(map.get("reportDate").trim());
            String exceptionMemo = map.get("exceptionMemo").trim();
            boolean exceptionCheck = Boolean.valueOf(map.get("exceptionCheck").trim());
            ClockOnException ce = new ClockOnException();
            ce.setEmpNo(empNo);
            ce.setAuthorEmpno(authorEmpno);
            ce.setReportDate(reportDate);
            ce.setExceptionMemo(exceptionMemo);
            ce.setExceptionCheck(exceptionCheck);
            signatureDao.saveClockOnException(ce);
            return "OK";
        } catch (Exception e) {
            return "Error: " + e;
        }
    }
    
    
    
}
