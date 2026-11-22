package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;

public class QueueManagerSearch {
	public static String queueManagerSearch(String[] object_search_params) throws SQLException {
		String[][] object_search_results = getObjects(object_search_params);
		object_search_results = ObjectSearch.getHosts(object_search_results);
		object_search_results = ObjectSearch.getLocations(object_search_results);
		object_search_results = ObjectSearch.getPlatform(object_search_results);
		object_search_results = ObjectSearch.filterResults(object_search_results, object_search_params);
		return JsonResult.createJsonResult(object_search_results);
	}
	
	public static String[][] getObjects(String[] object_search_params) throws SQLException {
		ArrayList<String> queries = new ArrayList<String>();
		queries.add("SELECT object_id FROM object_queue_manager WHERE queue_manager_id = "
				+ "(SELECT id from queue_manager WHERE name = '" + object_search_params[1] + "')");
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		String[][] object_search_results = new String[search_result.size()][5];
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][0] = search_result.get(i);
			object_search_results[i][1] = object_search_params[1];
		}
		
		queries.clear();
		for(int i = 0; i < object_search_results.length; i++) {
			queries.add("SELECT name FROM object WHERE id = " + object_search_results[i][0]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][0] = search_result.get(i);
		}
		
		return object_search_results;
	}
}
