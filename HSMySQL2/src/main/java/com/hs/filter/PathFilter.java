package com.hs.filter;

import com.hs.mvc.entity.Employee;
import com.hs.mvc.entity.Log;
import com.hs.mvc.repository.LogDao;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@WebFilter("/mvc/*")
public class PathFilter extends BaseFilter {

    @Autowired
    private LogDao logDao;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String uri = req.getRequestURI();
        Integer type = 1;
        if ((uri.contains("/queryViewClockOnReportMark") && !uri.contains("/queryViewClockOnReportMark_M")) || uri.contains("/clockonreport/updatememo")) {
            type = 0;
        }

        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }

        try {
            Employee employee = (Employee) req.getSession().getAttribute("employee");
            Log log = new Log();
            log.setEmpNo(employee.getEmpNo());
            log.setFromAddress(ipAddress);
            log.setLogPath(uri);
            log.setType(type);
            logDao.save(log);
        } catch (Exception e) {
        }

        chain.doFilter(req, res);
    }

}
