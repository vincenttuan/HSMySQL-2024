/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.mvc.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/console/meeting", "/console/fa/meeting"})
public class MeetingController {
    
    @RequestMapping(value = {"/"})
    public String index() {
        return "meeting/index";
    }
    
    @RequestMapping(value = {"/input"})
    public String input() {
        return "meeting/input";
    }
}
