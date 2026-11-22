package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlatformSearch {
	public static String platformSearch(String[] object_search_params) throws SQLException {
		String[] locations = getLocations(object_search_params);
		String result = ""; 
		for(int i = 0; i < locations.length; i++) {
			object_search_params[3] = locations[i];
			String next_set_of_results = LocationSearch.locationSearch(object_search_params);
			if(!next_set_of_results.equals("[]")) {
				if(i > 0) {
					result = result.substring(0, result.length()-1);
					result += ",";
					next_set_of_results = next_set_of_results.substring(1, next_set_of_results.length());
				}
				result += next_set_of_results;
			}
		}
		if(result.length() == 0) {
			result = "[]";
		} else {
			result = result.substring(0, result.length()-1);
			result += "]";
		}
		return result;
	}
	
	private static String[] getLocations(String[] object_search_params) throws SQLException {
		ArrayList<String> queries = new ArrayList<String>();
		queries.add("SELECT location_id FROM location_platform WHERE platform_id = "
				+ "(SELECT id from platform WHERE name = '" + object_search_params[4] + "')");
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		String[] locations = new String[search_result.size()];
		for(int i = 0; i < search_result.size(); i++) {
			locations[i] = search_result.get(i);
		}
		
		queries.clear();
		for(int i = 0; i < locations.length; i++) {
			queries.add("SELECT name FROM location WHERE id = " + locations[i]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			locations[i] = search_result.get(i);
		}
		
		return locations;
	}
}
