package io.java.springboot.camel;

import javafx.scene.transform.Rotate;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Route;
import org.apache.camel.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/camel")
public class CamelController  {

    @Autowired
    ProducerTemplate producerTemplate;


    @GetMapping("")
    public ResponseEntity<?> get(){
        try{
        CamelContext camelContext =  producerTemplate.getCamelContext();
        List<?> routes = camelContext.getRoutes().stream().map(route -> route.getId() + " -> " +  route.getEndpoint()+ " -> " + camelContext.getRouteStatus(route.getId()) ).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(routes);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(){
        try{
            CamelContext camelContext =  producerTemplate.getCamelContext();


            return ResponseEntity.status(HttpStatus.ACCEPTED).body(camelContext.getStatus());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex);
        }
    }

    @PutMapping("/start")
    public ResponseEntity<?> start(){
        try
        {
        CamelContext camelContext =  producerTemplate.getCamelContext();
        if(camelContext.getStatus().isStopped()){
            camelContext.start();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(camelContext.getStatus());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(camelContext.getStatus());
        }
        }
        catch (Exception ex){
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex);
        }
    }

    @PutMapping("/stop")
    public ResponseEntity<?> stop() throws Exception{
        try
        {
        CamelContext camelContext =  producerTemplate.getCamelContext();
        if(camelContext.getStatus().isStoppable()){
            camelContext.stop();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(camelContext.getStatus());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(camelContext.getStatus());
        }
        }
        catch (Exception ex){
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex);
        }

    }

    @RequestMapping(value = "/{route}/", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable("route") String id){
        try
        {
        CamelContext camelContext =  producerTemplate.getCamelContext();

        List<?> routes = camelContext.getRoutes().stream().filter(route -> route.getId().equals(id))
                .map(route -> route.getId() + " -> " +  route.getEndpoint()+ " -> " +  route.getServices()+  " -> " + camelContext.getRouteStatus(route.getId()) )
                .collect(Collectors.toList());


        return ResponseEntity.status(HttpStatus.ACCEPTED).body( routes);
        }
        catch (Exception ex){
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex);
        }
    }
    @RequestMapping(value = "/{route}/status", method = RequestMethod.GET)
    public ResponseEntity<?> getStatus(@PathVariable("route") String id){
        try{
        CamelContext camelContext =  producerTemplate.getCamelContext();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(camelContext.getRouteStatus(id));
        }
        catch (Exception ex){
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex);
        }
    }

    @PutMapping("/{route}/start")
    public ResponseEntity<?> start(@PathVariable("route") String route) {
        try {
            CamelContext camelContext = producerTemplate.getCamelContext();
            System.out.println("camelContext start " + route + " " + camelContext.getRouteStatus(route) );
            if (camelContext.getRouteStatus(route).isStopped()) {
                System.out.println("Starting route" + route);
                camelContext.getRoute(route).getEndpoint().start();
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(camelContext.getRouteStatus(route));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(camelContext.getRouteStatus(route));
            }
        }
        catch (Exception ex){
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex);
        }


    }

    @PutMapping("/{route}/stop")
    public ResponseEntity<?> stop(@PathVariable("route") String route) {
        try{
        CamelContext camelContext =  producerTemplate.getCamelContext();
        if(camelContext.getRouteStatus(route).isStoppable()){
            camelContext.getRoute(route).getEndpoint().stop();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(camelContext.getRouteStatus(route));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(camelContext.getRouteStatus(route));
        }
    }
        catch (Exception ex){
            System.out.println(ex);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex);
    }

    }

}
