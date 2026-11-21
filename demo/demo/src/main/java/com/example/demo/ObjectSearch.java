package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;

public class ObjectSearch {
	public static String objectSearch(String[] object_search_params) throws SQLException {
		ArrayList<String> queries = new ArrayList();
		queries.add("SELECT queue_manager_id FROM object_queue_manager WHERE object_id = "
				+ "(SELECT id from object WHERE name = '" + object_search_params[0] + "')");
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		String[][] object_search_results = new String[search_result.size()][2];
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][0] = object_search_params[0];
			object_search_results[i][1] = search_result.get(i);
		}
		
		queries.clear();
		for(int i = 0; i < object_search_results.length; i++ ) {
			queries.add("SELECT name FROM queue_manager WHERE id = " + object_search_results[i][1]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][1] = search_result.get(i);
		}
		
		String result = "[";
		for(int i = 0; i < object_search_results.length; i++) {
			result += "{\"object\":\"" + object_search_results[i][0] + "\"";
			result += ", \"queue_manager\":\"" + object_search_results[i][1] + "\"";
			result += "}";
			if(i != object_search_results.length - 1) {
				result += ",";
			}
		}
		result += "]";
		
		return result;
	}
}