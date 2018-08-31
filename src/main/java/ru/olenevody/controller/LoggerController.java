package ru.olenevody.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoggerController {

    @RequestMapping("/log")
    public String log() {
        return "redirect:/resources/log.html";
    }

}
