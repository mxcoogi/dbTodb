package com.mxcoogi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuiController {
    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "DB to DB Migration Console");
        return "index";
    }
    @GetMapping("/table-render")
    public String tableRender(Model model) {
        return "table_render";
    }
    @GetMapping("/job")
    public String goJob(Model model) {
        return "job";
    }
}
