package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;

public class HostSearch {
	public static String hostSearch(String[] object_search_params) throws SQLException {
		String[] queue_managers = getQueueManagers(object_search_params);
		String result = ""; 
		for(int i = 0; i < queue_managers.length; i++) {
			object_search_params[1] = queue_managers[i];
			String next_set_of_results = QueueManagerSearch.queueManagerSearch(object_search_params);
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
	
	private static String[] getQueueManagers(String[] object_search_params) throws SQLException {
		ArrayList<String> queries = new ArrayList<String>();
		queries.add("SELECT queue_manager_id FROM queue_manager_host WHERE host_id = "
				+ "(SELECT id from host WHERE name = '" + object_search_params[2] + "')");
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		String[] queue_manager = new String[search_result.size()];
		for(int i = 0; i < search_result.size(); i++) {
			queue_manager[i] = search_result.get(i);
		}
		
		queries.clear();
		for(int i = 0; i < queue_manager.length; i++) {
			queries.add("SELECT name FROM queue_manager WHERE id = " + queue_manager[i]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			queue_manager[i] = search_result.get(i);
		}
		
		return queue_manager;
	}
}
