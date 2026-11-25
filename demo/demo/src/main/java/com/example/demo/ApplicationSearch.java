package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationSearch {
	public static String applicationSearch(String[] object_search_params) throws SQLException {
		String[][] object_search_results = getObjects(object_search_params);
		object_search_results = getQueueManagers(object_search_results);
		object_search_results = ObjectSearch.getHosts(object_search_results);
		object_search_results = ObjectSearch.getLocations(object_search_results);
		object_search_results = ObjectSearch.getPlatform(object_search_results);
		object_search_results = ObjectSearch.getEnvironment(object_search_results);
		object_search_results = ObjectSearch.getObjectType(object_search_results);
		object_search_results = ObjectSearch.filterResults(object_search_results, object_search_params);
		return JsonResult.createJsonResult(object_search_results);
	}
	
	public static String appIdSearch(String[] object_search_params) throws SQLException {
		String[][] object_search_results = getObjectsByAppId(object_search_params);
		object_search_results = getQueueManagers(object_search_results);
		object_search_results = ObjectSearch.getHosts(object_search_results);
		object_search_results = ObjectSearch.getLocations(object_search_results);
		object_search_results = ObjectSearch.getPlatform(object_search_results);
		object_search_results = ObjectSearch.getEnvironment(object_search_results);
		object_search_results = ObjectSearch.filterResults(object_search_results, object_search_params);
		return JsonResult.createJsonResult(object_search_results);
	}
	
	public static String[][] getObjects(String[] object_search_params) throws SQLException {
		ArrayList<String> queries = new ArrayList<String>();
		queries.add("SELECT object_id FROM object_application WHERE application_id = "
				+ "(SELECT id from application WHERE name = '" + object_search_params[6] + "')");
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		String[][] object_search_results = new String[search_result.size()][9];
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][0] = search_result.get(i);
			object_search_results[i][6] = object_search_params[6];
		}
		
		queries.clear();
		for(int i = 0; i < object_search_results.length; i++) {
			queries.add("SELECT name FROM object WHERE id = " + object_search_results[i][0]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][0] = search_result.get(i);
		}
		
		queries.clear();
		for(int i = 0; i < object_search_results.length; i++ ) {
			queries.add("SELECT app_id FROM application WHERE name = '" + object_search_results[i][6] + "'");
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][7] = search_result.get(i);
		}
		
		return object_search_results;
	}
	
	public static String[][] getObjectsByAppId(String[] object_search_params) throws SQLException {
		ArrayList<String> queries = new ArrayList<String>();
		queries.add("SELECT object_id FROM object_application WHERE application_id = "
				+ "(SELECT id from application WHERE app_id = '" + object_search_params[7] + "')");
		
		ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
		String[][] object_search_results = new String[search_result.size()][9];
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][0] = search_result.get(i);
			object_search_results[i][7] = object_search_params[7];
		}
		
		queries.clear();
		for(int i = 0; i < object_search_results.length; i++) {
			queries.add("SELECT name FROM object WHERE id = " + object_search_results[i][0]);
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][0] = search_result.get(i);
		}
		
		queries.clear();
		for(int i = 0; i < object_search_results.length; i++ ) {
			queries.add("SELECT name FROM application WHERE app_id = '" + object_search_results[i][7] + "'");
		}
		
		search_result = PostgresqlConnector.runSelectQueries(queries);
		for(int i = 0; i < search_result.size(); i++) {
			object_search_results[i][6] = search_result.get(i);
		}
		
		return object_search_results;
	}
	
	public static String[][] getQueueManagers(String[][] object_search_results) throws SQLException {
		for(int i = 0; i < object_search_results.length; i++) {
			ArrayList<String> queries = new ArrayList<String>();
			queries.add("SELECT queue_manager_id FROM object_queue_manager WHERE object_id = "
					+ "(SELECT id from object WHERE name = '" + object_search_results[i][0] + "')");
			
			ArrayList<String> search_result = PostgresqlConnector.runSelectQueries(queries);
			for(int j = 0; j < search_result.size(); j++) {
				object_search_results[i][1] = search_result.get(j);
			}
			
			queries.clear();
			queries.add("SELECT name FROM queue_manager WHERE id = " + object_search_results[i][1]);
			
			search_result = PostgresqlConnector.runSelectQueries(queries);
			for(int j = 0; j < search_result.size(); j++) {
				object_search_results[i][1] = search_result.get(j);
			}
		}
		return object_search_results;
	}
}
