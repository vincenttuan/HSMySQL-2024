package com.hs.filter;

import com.hs.mvc.repository.ArgsDao;
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
@Order(4)
@WebFilter(urlPatterns = {"/mvc/console/calendar/update/scheduler/employee"})
public class UpdateSchedulerEmployeeFilter extends BaseFilter {

    @Autowired
    private ArgsDao argsDao;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //判斷現在日期是否可以修改
        boolean check = argsDao.isEditEnableByCalender();
        if (check) {
            chain.doFilter(req, res);
        } else {
            res.getWriter().print("-1");
        }
    }
}
