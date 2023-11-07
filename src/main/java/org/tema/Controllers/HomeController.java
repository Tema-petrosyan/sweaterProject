package org.tema.Controllers;

import org.tema.Repositories.MessageRepository;
import org.tema.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HomeController {
    private MessageRepository messageRepository;
    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/home")
    public String userAccess(Model model, Principal principal) {
        if(principal == null)
            return null;

        Iterable<Message> all = messageRepository.findAll();

        model.addAttribute("name", principal.getName());
        model.addAttribute("messages", all);
        return "home";
    }

    @PostMapping("/home")
    public String postMessage(@RequestParam String text, @RequestParam String tags, Model model) {
        if(!text.isEmpty() && !tags.isEmpty()) {
            Message message = new Message(text, tags);
            messageRepository.save(message);
        }

//        Iterable<Message> all = messageRepository.findAll();
//        model.addAttribute("messages", all);
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