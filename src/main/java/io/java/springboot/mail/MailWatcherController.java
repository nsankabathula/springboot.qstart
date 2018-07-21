package io.java.springboot.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import java.util.List;

@RestController
@RequestMapping("/mail")
public class MailWatcherController {

    @Autowired
    MailWatcherService mailWatcherService;

    @GetMapping("")
    public List<Message> getMessages(){
        System.out.println("in getMessages");
        return mailWatcherService.getMessages();
    }
}
