package io.java.springboot.topic;

public class Topic {

    String id, topic, topicDescription;

    public Topic(String id, String topic, String topicDescription) {
        this.id = id;
        this.topic = topic;
        this.topicDescription = topicDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id='" + id + '\'' +
                ", topic='" + topic + '\'' +
                ", topicDescription='" + topicDescription + '\'' +
                '}';
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }
}
