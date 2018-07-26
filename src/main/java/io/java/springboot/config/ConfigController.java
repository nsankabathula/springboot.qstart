package io.java.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    ConfigService configService;


    public String sayHi(){
        return "Hi";
    }

    @GetMapping("")

    public ResponseEntity<List<ClientConfig>> get() throws Exception {

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(configService.getConfig());


    }

    @RequestMapping(value = "/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<?> getByFileName(@PathVariable("fileName") String fileName) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(configService.getConfigByFileName(fileName));
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/build", method = RequestMethod.PUT)
    public ResponseEntity<?> put() {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(configService.writeAllConfigs());
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{fileName}", method = RequestMethod.PUT)

    public ResponseEntity<ClientConfig> put(@PathVariable("fileName") String fileName, ClientConfig obj) {
        return null;
    }

    @RequestMapping(value = "/{fileName}", method = RequestMethod.POST)

    public ResponseEntity<ClientConfig> post(@PathVariable("fileName") String fileName, ClientConfig obj) {
        return null;
    }

    @RequestMapping(value = "/{fileName}", method = RequestMethod.DELETE)

    public ResponseEntity<ClientConfig> delete(@PathVariable("fileName") String fileName, ClientConfig obj) {
        return null;
    }

    @RequestMapping(value = "/{fileName}/build", method = RequestMethod.GET)
    public ResponseEntity<?> buildConfigFor(@PathVariable("fileName") String fileName) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(configService.writeConfigToFile(fileName));
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
