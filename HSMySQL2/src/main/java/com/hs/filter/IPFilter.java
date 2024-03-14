package com.hs.filter;
/*
對進入IP進行管理
*/

import com.hs.mvc.entity.Employee;
import com.hs.mvc.entity.Log;
import com.hs.mvc.repository.LogDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
//@Order(1)
//@WebFilter("/*")
public class IPFilter extends BaseFilter {

    private static List<String> ips = new ArrayList<>();
    static{
        ips.add("127.0.0.1");
        ips.add("localhost");
        ips.add("0:0:0:0:0:0:0:1");
    }
    
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String ip = req.getRemoteAddr();
        boolean ok = ips.stream().filter(ips -> ips.equals(ip)).findFirst().isPresent();
        if (ok) {
            chain.doFilter(req, res);
        }else{
            res.getWriter().print(ip + "");
        }
    }
}
