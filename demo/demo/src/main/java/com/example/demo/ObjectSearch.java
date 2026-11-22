package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;

public class ObjectSearch {
	public static String objectSearch(String[] object_search_params) throws SQLException {
		String[][] object_search_results = getQueueManager(object_search_params);
		object_search_results = getHosts(object_search_results);
		object_search_results = getLocations(object_search_results);
		object_search_results = getPlatform(object_search_results);
		object_search_results = filterResults(object_search_results, object_search_params);
		return JsonResult.createJsonResult(object_search_results);
	}
	
	private static String[][] getQueueManager(String[] object_search_params) throws SQLException {
		ArrayList<String> queries = new ArrayList<String>();
		queries.add("SELECT queue_manager_id FROM object_queue_manager WHERE object_id = "
				+ "(SELECT id from object WHERE name = '" + object_search_params[0] + "')");
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		String[][] object_search_results = new String[search_result.size()][5];
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
		
		return object_search_results;
	}
	
	public static String[][] getHosts(String[][] object_search_results) throws SQLException {
		ArrayList<String> queries = new ArrayList();
		for(int i = 0; i < object_search_results.length; i++) {
			queries.add("SELECT host_id FROM queue_manager_host WHERE queue_manager_id = "
					+ "(SELECT id from queue_manager WHERE name = '" + object_search_results[i][1] + "')");
		}
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][2] = search_result.get(i);
		}
		
		queries.clear();
		for(int i = 0; i < object_search_results.length; i++ ) {
			queries.add("SELECT name FROM host WHERE id = " + object_search_results[i][2]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][2] = search_result.get(i);
		}
		
		return object_search_results;
	}
	
	public static String[][] getLocations(String[][] object_search_results) throws SQLException {
		ArrayList<String> queries = new ArrayList();
		for(int i = 0; i < object_search_results.length; i++) {
			queries.add("SELECT location_id FROM host_location WHERE host_id = "
					+ "(SELECT id from host WHERE name = '" + object_search_results[i][2] + "')");
		}
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][3] = search_result.get(i);
		}
		
		queries.clear();
		for(int i = 0; i < object_search_results.length; i++ ) {
			queries.add("SELECT name FROM location WHERE id = " + object_search_results[i][3]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][3] = search_result.get(i);
		}
		
		return object_search_results;
	}
	
	public static String[][] getPlatform(String[][] object_search_results) throws SQLException {
		ArrayList<String> queries = new ArrayList();
		for(int i = 0; i < object_search_results.length; i++) {
			queries.add("SELECT platform_id FROM location_platform WHERE location_id = "
					+ "(SELECT id from location WHERE name = '" + object_search_results[i][3] + "')");
		}
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][4] = search_result.get(i);
		}
		
		queries.clear();
		for(int i = 0; i < object_search_results.length; i++ ) {
			queries.add("SELECT name FROM platform WHERE id = " + object_search_results[i][4]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][4] = search_result.get(i);
		}
		
		return object_search_results;
	}
	
	public static String[][] filterResults(String[][] object_search_results, String[] object_search_params) throws SQLException {
		for(int i = 0; i < object_search_results.length; i++) {
			for(int j = 1; j < object_search_params.length; j++) {
				if(!object_search_params[j].equals("")) {
					if(!object_search_results[i][j].equals(object_search_params[j])) {
						object_search_results[i][0] = "skip";
					}
				}
			}
		}
		return object_search_results;
	}
}