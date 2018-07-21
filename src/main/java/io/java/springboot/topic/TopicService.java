package io.java.springboot.topic;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

    private final List<Topic> allTopics = Arrays.asList(
            new Topic("1", "java", "Java Description"),
            new Topic("2", "oracle", "Oracle Description")
    );

    public  List<Topic> getAllTopics() {
        return allTopics;
    }

    public Topic getTopicById(String id){
        Topic topic = null;
        for (int i = 0; i < allTopics.size(); i ++){
            topic = allTopics.get(i);
            //System.out.println(topic.toString());
            if(topic.getId().equals(id.trim())){
                //System.out.println("Breaking ");
                break;
            }
        }

        return topic;
    }
}
