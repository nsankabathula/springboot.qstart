package io.java.springboot.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigRouteBuilder extends RouteBuilder {

    @Autowired
    ConfigService configService;

    @Override
    public void configure() throws Exception {
        //System.out.println(configService.writeAllConfigs());
        final String name = getClass().getName();
        from("direct:" + name)
        //from("timer://trigger?repeatCount=1")
                .noAutoStartup()
                .routeId(name)
                .tracing()
                .bean(ConfigService.class, "writeAllConfigs")

                //.to("bean:configService?method=writeAllConfigs")
                .to("log:" + name)
                .end();



    }
}
