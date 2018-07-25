package io.java.springboot.core;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartUp implements ApplicationListener<ApplicationReadyEvent>{

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        System.out.println("ApplicationStartUp");
        System.out.println(event.getApplicationContext().getEnvironment());

        //super.

        // here your code ...

        return;
    }
}
