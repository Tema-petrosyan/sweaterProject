package org.example.Controllers;

import org.example.Repositories.MessageRepository;
import org.example.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/home")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        Iterable<Message> all = messageRepository.findAll();

        model.addAttribute("name", name);
        model.addAttribute("messages", all);
        return "home";
    }

    @PostMapping("/home")
    public String postMessage(@RequestParam(name="name", required=false, defaultValue="World") String name,
                              @RequestParam String text,
                              @RequestParam String tags, Model model) {
        model.addAttribute("name", name);
        if(!text.equals("") && !tags.equals("")) {
            Message message = new Message(text, tags);
            messageRepository.save(message);
        }

        Iterable<Message> all = messageRepository.findAll();
        model.addAttribute("messages", all);
        return "redirect:/home"; //без редиректа каждый раз при обновлении страницы заного отправляет форму
    }

    @PostMapping( "/home/filter")
    public String filterMessages(@RequestParam(name="name", required=false, defaultValue="World") String name,
                                 @RequestParam String filter, Model model){
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("name", name);
        if(!filter.isEmpty()){
            messages = messageRepository.findByTags(filter);
        }
        model.addAttribute("messages", messages);
        return "filter";
    }
}