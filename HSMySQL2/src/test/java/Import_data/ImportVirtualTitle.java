package Import_data;

import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ImportVirtualTitle {
    
    public static void main(String[] args) throws Exception {
        
        Resource rs = new FileSystemResource("C:\\Users\\kevin\\Documents\\NetBeansProjects\\HSMySQL\\src\\main\\webapp\\WEB-INF\\springmvc-servlet.xml");
        BeanFactory factory = new XmlBeanFactory(rs);
        JdbcTemplate jdbcTemplate = (JdbcTemplate) factory.getBean("jdbcTemplate");
        
        String path = "C:\\Users\\kevin\\Documents\\NetBeansProjects\\HSMySQL\\src\\test\\java\\Import_data\\Virtual_TitleV3.csv";
        FileInputStream fis = new FileInputStream(path);
        String data = IOUtils.toString(fis, "big5");
        //System.out.println(data);
        
        String[] list = data.split("\n");
        System.out.println(list.length);
        
        // id, title, rank_no, level_no, capacity, salary, rank_range, level_range
        for(int i=1;i<list.length;i++) {
            String[] rows = list[i].split(",");
            //System.out.println(rows[4]);
            String sql = "insert into Virtual_title(id,gid,sid,rid,cname,ename,memo) values (?, ?, ?, ?, ?, ? ,?)";
            jdbcTemplate.update(sql, rows[0],rows[1],rows[2],rows[3],rows[4],rows[5],rows[6]);
        }
        System.out.println("OK");
    }
            
}
