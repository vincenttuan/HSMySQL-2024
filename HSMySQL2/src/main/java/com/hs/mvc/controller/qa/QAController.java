package com.hs.mvc.controller.qa;

import com.hs.mvc.repository.qa.QADao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/qa")
public class QAController {
    @Autowired
    private QADao qADao;
    
    @RequestMapping(value = "/query/group", produces = "text/plain; charset=utf-8")
    public String queryQAGroup(Model model) {
        model.addAttribute("groups", qADao.queryQAGroup());
        return "qa_group";
    }
    
    @RequestMapping(value = "/query/item", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String queryQAItem() {
        return qADao.queryQAItem().toString();
    }
}
