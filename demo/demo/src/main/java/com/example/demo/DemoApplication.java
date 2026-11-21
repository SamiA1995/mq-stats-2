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
							@RequestParam(value = "queue_manager", defaultValue = "") String queue_manager) {
    	String[] object_search_params = new String[2];
    	object_search_params[0] = object;
    	object_search_params[1] = queue_manager;
    	String result = "";
		try {
			result = ObjectSearch.objectSearch(object_search_params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		
		return result;
    }
    
	@GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "John Doe") String name, 
						@RequestParam(value = "age", defaultValue = "30") String age) {
		System.out.println(name + " " + age);
		return name + " " + age;
    }
}