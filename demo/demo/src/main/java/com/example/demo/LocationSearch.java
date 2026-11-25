package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;

public class LocationSearch {
	public static String locationSearch(String[] object_search_params) throws SQLException {
		String[] hosts = getHosts(object_search_params);
		String result = ""; 
		for(int i = 0; i < hosts.length; i++) {
			object_search_params[2] = hosts[i];
			String next_set_of_results = HostSearch.hostSearch(object_search_params);
			if(!next_set_of_results.equals("[]")) {
				if(i > 0 && result.length() > 0) {
					result = result.substring(0, result.length()-1);
					result += ",";
					next_set_of_results = next_set_of_results.substring(1, next_set_of_results.length());
				}
				result += next_set_of_results;
			}
		}

		return JsonResult.cleanJson(result);
	}
	
	private static String[] getHosts(String[] object_search_params) throws SQLException {
		ArrayList<String> queries = new ArrayList<String>();
		queries.add("SELECT host_id FROM host_location WHERE location_id = "
				+ "(SELECT id from location WHERE name = '" + object_search_params[3] + "')");
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		String[] hosts = new String[search_result.size()];
		for(int i = 0; i < search_result.size(); i++) {
			hosts[i] = search_result.get(i);
		}
		
		queries.clear();
		for(int i = 0; i < hosts.length; i++) {
			queries.add("SELECT name FROM host WHERE id = " + hosts[i]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			hosts[i] = search_result.get(i);
		}
		
		return hosts;
	}
}
