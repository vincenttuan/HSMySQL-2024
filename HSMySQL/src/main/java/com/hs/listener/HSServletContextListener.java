package com.hs.listener;

import com.hs.mvc.entity.Args;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

//@Component  //指定為Spring管理物件
@WebListener     //指定為伺服器一開始就執行的監聽器物件
public class HSServletContextListener implements ServletContextListener{
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 抓取 spring 在 web.xml 中的配置檔 (springmvc-servlet.xml)
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        // 抓取 springmvc-servlet.xml 中的 JdbcTemplate 的 bean 物件
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        String sql = "select * from args order by id";
        List<Args> args = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Args.class));
        // 放入到 Web 全域變數(ServletContext)中
        sce.getServletContext().setAttribute("args", args);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    } 
}
