package com.hanulhan.controller;

import com.hanulhan.debug.HttpServletRequestDebug;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class WelcomeController {

    // inject via application.properties
    @Value("${welcome.message}")
    private String message;
    @Autowired
    private HttpServletRequest context;

    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @GetMapping("/")
    public String main(Model model) {
        HttpServletRequestDebug.debugRequest(context);
        
        model.addAttribute("message", message);
        model.addAttribute("header", context.getHeaderNames());
        //model.addAttribute("tasks", tasks);

        return "welcome"; //view
    }

    // /hello?name=kotlin
    @GetMapping("/secret")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {

        //model.addAttribute("message", name);
        model.addAttribute("message", "Secure Page");

        return "welcome"; //view
    }

}
