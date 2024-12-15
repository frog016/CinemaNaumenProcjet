package org.example.Backend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartPageController {
    @GetMapping("/")
    public String redirectToStartPage() {
        return "redirect:/movies";
    }
}
