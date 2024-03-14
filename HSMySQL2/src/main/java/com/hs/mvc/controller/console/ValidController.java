package com.hs.mvc.controller.console;

import com.google.gson.Gson;
import com.hs.mvc.repository.ValidIPDao;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/valid")
public class ValidController {

    @Autowired
    private ValidIPDao validIPDao;

    @RequestMapping(value = "/ip", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String ip() {
        return new Gson().toJson(validIPDao.queryAllValidIPs());
    }
    
    @RequestMapping(value = "/ip/check")
    @ResponseBody
    public Boolean ipCheck(@RequestBody Map<String, String> map) {
        String ClientIP = map.get("ip");
        // 比對IP(不分大小寫、去除前後誤輸入的空白)
        return validIPDao.queryAllValidIPs().stream()
                .filter(v -> v.getIp().trim().equalsIgnoreCase(ClientIP))
                .findAny()
                .isPresent();
        //return "Server get: " + map.get("ip");
    }
    
    //非認證設備IP使用預設Gkey
    @RequestMapping(value = "/gkey/check")
    @ResponseBody
    public Boolean gkeyCheck(@RequestBody Map<String, String> map) {
        String gkey = map.get("gkey");
        return gkey.trim().equals("4321");
    }

}
