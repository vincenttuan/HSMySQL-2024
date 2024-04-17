package com.hs.filter;
/*
對進入網站以網址(末端帶特殊字元)進行管理
*/

import com.hs.mvc.entity.Employee;
import com.hs.mvc.entity.Log;
import com.hs.mvc.repository.ArgsDao;
import com.hs.mvc.repository.LogDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@WebFilter(urlPatterns = {"/mvc/clock_on/*"})
public class AuthFilter extends BaseFilter {

    static String authString;
    
    @Autowired
    private ArgsDao argsDao;
    
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //getQueryString()直接找出網址?後面的字元，並且印出.print(req.getQueryString())
        //res.getWriter().print(req.getQueryString());
        
        // 對於localhost，此Filter不過濾，直接執行下去(以免卡住自己)
        if(req.getRequestURL().toString().contains("localhost")) {
            chain.doFilter(req, res);
            return;
        }
        
        // 從資料庫取得授權碼
        String authString = argsDao.getAuthStringt();

        // 1.先檢查是否有授權session
        boolean check = false;
        try {
            check = req.getSession().getAttribute("auth").toString().equals(authString);
        } catch (Exception e) {
        }
        
        // 2.若check = false 則看有無帶入QueryString()參數
        if (!check) {
                try {
                check = req.getQueryString().toString().equals(authString);
            } catch (Exception e) {
            }
        } 
                
        // 3. 驗證判斷
        if (check) {
            // 驗證成功則往下執行
            req.getSession().setAttribute("auth", authString);
            if (req.getQueryString() != null) {
                res.sendRedirect("/HSMySQL/mvc/clock_on/input2");
            } else {
                chain.doFilter(req, res);
            }           
        }else {
            // 驗證不成功
            res.sendRedirect("https://hsunite.tw");
        }
    }
}
