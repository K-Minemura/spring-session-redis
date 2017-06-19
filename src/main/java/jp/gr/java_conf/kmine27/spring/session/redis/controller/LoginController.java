package jp.gr.java_conf.kmine27.spring.session.redis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String index() {
        return "login";
    }

}
