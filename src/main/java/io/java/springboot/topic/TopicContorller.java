package io.java.springboot.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicContorller {

    @Autowired
    public TopicService serviceTopic;

    @GetMapping("")
    public List<Topic> getAllTopics(){
        System.out.println("in getAllTopics");
        return serviceTopic.getAllTopics();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Topic getTopicById(@PathVariable("id") String topicId){
        System.out.println("getTopicById " +  topicId);
        return serviceTopic.getTopicById(topicId);
    }


}
