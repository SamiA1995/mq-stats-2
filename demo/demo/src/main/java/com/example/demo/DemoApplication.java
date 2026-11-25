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
							@RequestParam(value = "platform", defaultValue = "") String platform,
							@RequestParam(value = "environment", defaultValue = "") String environment,
							@RequestParam(value = "application", defaultValue = "") String application,
							@RequestParam(value = "app_id", defaultValue = "") String app_id,
							@RequestParam(value = "object_type", defaultValue = "") String object_type) {
    	String[] object_search_params = new String[9];
    	object_search_params[0] = object;
    	object_search_params[1] = queue_manager;
    	object_search_params[2] = host;
    	object_search_params[3] = host_location;
    	object_search_params[4] = platform;
    	object_search_params[5] = environment;
    	object_search_params[6] = application;
    	object_search_params[7] = app_id;
    	object_search_params[8] = object_type;
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
			} else if(!environment.equals("")) {
				result = EnvironmentSearch.environmentSearch(object_search_params);
			} else if(!application.equals("")) {
				result = ApplicationSearch.applicationSearch(object_search_params);
			} else if(!app_id.equals("")) {
				result = ApplicationSearch.appIdSearch(object_search_params);
			} else if(!object_type.equals("")) {
				result = ObjectTypeSearch.objectTypeSearch(object_search_params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
    }
}