package org.debugroom.wedding.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SorryPageController {

    @RequestMapping(value="login", method=RequestMethod.GET)
    public String login(){
        return "/sorry.html";
    }

}
