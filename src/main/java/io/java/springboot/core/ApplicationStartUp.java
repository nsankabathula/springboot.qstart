package io.java.springboot.core;

import io.java.springboot.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartUp implements ApplicationListener<ApplicationReadyEvent>{

    @Autowired
    ConfigService configService;
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        System.out.println("ApplicationStartUp");
        System.out.println(event.getApplicationContext().getEnvironment());
        //System.out.println(event.getApplicationContext().containsBean("ConfigService"));
        try {
            //configService.writeAllConfigs();
        }
        catch (Exception ex){
            System.out.println(ex);
        }

        //super.

        // here your code ...

        return;
    }
}
