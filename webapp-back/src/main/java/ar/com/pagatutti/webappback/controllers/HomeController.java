package ar.com.pagatutti.webappback.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
@CrossOrigin
public class HomeController {

    @RequestMapping(
        value = {"/{path:[^\\.]*}", "/**/{path:^(?!assets).*}/{path:[^\\.]*}", ""},
        method = RequestMethod.GET)
    public String home() {
        return "forward:/index.html";
    }

}

