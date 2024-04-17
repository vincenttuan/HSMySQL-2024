package com.hs.mvc.controller.console;

import com.hs.mvc.entity.Holiday;
import com.hs.mvc.repository.HolidayDao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/console/fileupload")
public class FileUploadController {

    @Autowired
    private HolidayDao holidayDao;
    
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
    public String save(Model model, @RequestParam("fulldata") String fulldata) {
        String newfulldata = "";
        Object message = "匯入成功";
        try {
            newfulldata = new String(fulldata.getBytes("iso-8859-1"), "utf-8");
            importToDB(newfulldata);
        } catch (Exception ex) {
            ex.printStackTrace();
            message = ex;
        }
        model.addAttribute("ex", message);
        return "upload_form";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String uploadForm() {
        return "upload_form";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST,
            produces = "text/plain; charset=utf-8")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file, @RequestParam("ds") String ds) {

        if (!file.isEmpty()) {
            try {
                String fn = file.getOriginalFilename();
                if (fn.substring(fn.length() - 4).equalsIgnoreCase(".csv")) {
                    if (file.getSize() > 0) {
                        byte[] bytes = file.getBytes();
                        String fulldata = new String(bytes, "UTF-8");
                        fulldata = fulldata.replaceAll("\"", "").trim();
                        
                        String[] fulldataRows = fulldata.split("\n");
                        // 判斷年份區間
                        Set<Integer> yearOfSet = Arrays.stream(fulldataRows).filter(row -> !row.contains("date")).map(row -> Integer.parseInt(row.split(",")[0].substring(0, 4))).collect(Collectors.toSet());
                        
                        List<String> rows = new ArrayList<>();
                        for(int year : yearOfSet) {
                            for(int month=1;month<=12;month++) {
                                int max_d = lastDayOfMonth(year, month);
                                for(int d = 1;d<=max_d;d++) {
                                    String row = matchString(year+"/"+month+"/"+d, fulldataRows);
                                    if(row == null) {
                                        row = "%s,%s,%s,%s,%s";
                                        row = String.format(row, year+"/"+month+"/"+d, " ", " ", " ", " ");
                                    }
                                    rows.add(row);
                                }
                            }
                        }
                        model.addAttribute("rows", rows);
                        fulldata = "";
                        for(String row : rows) {
                            fulldata += row + "\n";
                        }
                        model.addAttribute("fulldata", fulldata);
                    } else {
                        throw new Exception("上傳檔案內容是空的.");
                    }
                } else {
                    throw new Exception("上傳檔案必須是 csv.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", e.toString());
            }
        } else {
            model.addAttribute("error", "Empty file !");
        }

        return "upload_file_preview";
    }
    
    private String matchString(String date, String[] rows) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        for(int i=0;i<rows.length;i++) {
            try {
                Date d1 = sdf.parse(date);
                Date d2 = sdf.parse(rows[i].split(",")[0]);
                if(d1.equals(d2)) {
                    return rows[i];
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    private void importToDB(String fulldata) {
        String[] rows = fulldata.split("\n");
        List<Holiday> holidays = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = 0; i < rows.length; i++) {
            // date,name,isHoliday,holidayCategory,description
            String[] row = rows[i].split(",");
            String date = row[0].trim();
            String name = row[1].trim();
            String isHoliday = row[2].trim();
            String holidayCategory = row[3].trim();
            System.out.println(date + ": " + row.length + ": " + rows[i]);
            String description = row[4].trim();
            // 注入到 Holiday 物件
            Holiday holiday = new Holiday();
            try {
                holiday.setDate(sdf.parse(date));
            } catch (Exception e) {
                continue;
            }
            holiday.setName(name);
            holiday.setIsHoliday(isHoliday);
            holiday.setHolidayCategory(holidayCategory);
            holiday.setDescription(description);
            // 加入到 holidays 集合
            holidays.add(holiday);
        }
        //System.out.println(holidays);
        // 加入到 DB
        holidayDao.multiSave(holidays);
        // 將 is_holiday = '是' 批次更改 nop_xxx = 0
        holidayDao.batchUpdateNOP_Zero();
    }
    
    private int lastDayOfMonth(int year, int month) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-01");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.roll(Calendar.DAY_OF_MONTH, -1);
            return calendar.get(Calendar.DATE);
        } catch (Exception e) {
        }
        return 31;
    }
}
