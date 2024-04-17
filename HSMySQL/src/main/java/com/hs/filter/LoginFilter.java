package com.hs.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;

@Order(2)
@WebFilter("/mvc/console/*")
public class LoginFilter extends BaseFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);

        // 是否有登入 ?
        if (session != null
                && session.getAttribute("employee") != null) {
            // 是否有帶入 csId 參數 ?
            if (req.getParameter("csId") != null
                    && req.getParameter("csId").trim().length() != 0) {
                session.setAttribute("csId", req.getParameter("csId"));
            }
            chain.doFilter(req, res);
        } else {
            res.sendRedirect("/HSMySQL/login.jsp?flag=true");
        }

    }

}
