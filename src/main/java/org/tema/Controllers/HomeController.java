package org.tema.Controllers;

import org.tema.Repositories.MessageRepository;
import org.tema.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

    @GetMapping("/home")
    public String home(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
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
        return "redirect:/home"; //без редиректа каждый раз при обновлении страницы заново отправляет форму
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