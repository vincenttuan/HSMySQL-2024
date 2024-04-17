package Import_data;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ImportRankLevel {
    
    public static void main(String[] args) throws Exception {
        
        Resource rs = new FileSystemResource("C:\\Users\\kevin\\Documents\\NetBeansProjects\\HSMySQL\\src\\main\\webapp\\WEB-INF\\springmvc-servlet.xml");
        BeanFactory factory = new XmlBeanFactory(rs);
        JdbcTemplate jdbcTemplate = (JdbcTemplate) factory.getBean("jdbcTemplate");
        
        String path = "C:\\Users\\kevin\\Documents\\NetBeansProjects\\HSMySQL\\src\\test\\java\\Import_data\\RankLevel.csv";
        FileInputStream fis = new FileInputStream(path);
        String data = IOUtils.toString(fis, "big5");
        //System.out.println(data);
        
        //id,職銜 Title,職等 Rank,職級 Level,容留數 capacity,薪資 salary,職等 級距 Rank_range,職級 級距 Level_range
        String[] list = data.split("\n");
        System.out.println(list.length);
        
        for (int i = 1; i < list.length ; i++) {
            String[] rows = list[i].split(",");
            //System.out.println(rows[1]);
            
            //資料匯入SQL
            String sql = "insert into RankLevel(id, title, rank_no, level_no, capacity, salary, rank_range, level_range) values (?, ?, ?, ?, ?, ? ,?, ?)";
            jdbcTemplate.update(sql, rows[0], rows[1], rows[2], rows[3], rows[4], rows[5], rows[6], rows[7] );
        }
        System.out.println("OK");
    }
            
}
