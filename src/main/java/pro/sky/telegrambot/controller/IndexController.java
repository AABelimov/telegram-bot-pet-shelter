package pro.sky.telegrambot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "redirect:pets/all/all?page=0";
    }
}
