package com.example.demo;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
    }
    
    @GetMapping("/objectSearch")
    public String objectSearch(@RequestParam(value = "object", defaultValue = "") String object, 
							@RequestParam(value = "queue_manager", defaultValue = "") String queue_manager,
							@RequestParam(value = "host", defaultValue = "") String host,
							@RequestParam(value = "host_location", defaultValue = "") String host_location,
							@RequestParam(value = "platform", defaultValue = "") String platform) {
    	String[] object_search_params = new String[5];
    	object_search_params[0] = object;
    	object_search_params[1] = queue_manager;
    	object_search_params[2] = host;
    	object_search_params[3] = host_location;
    	object_search_params[4] = platform;
    	String result = "";
		try {
			if(!object.equals("")) {
				result = ObjectSearch.objectSearch(object_search_params);
			} else if(!queue_manager.equals("")) {
				result = QueueManagerSearch.queueManagerSearch(object_search_params);
			} else if(!host.equals("")) {
				result = HostSearch.hostSearch(object_search_params);
			} else if(!host_location.equals("")) {
				result = LocationSearch.locationSearch(object_search_params);
			} else if(!platform.equals("")) {
				result = PlatformSearch.platformSearch(object_search_params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
    }
}