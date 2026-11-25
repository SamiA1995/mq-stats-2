package com.example.demo;

public class JsonResult {
	public static String createJsonResult(String[][] object_search_results) {
		String result = "[";
		for(int i = 0; i < object_search_results.length; i++) {
			if(object_search_results[i][0] == "skip") {
				continue;
			}
			result += "{\"object\":\"" + object_search_results[i][0] + "\"";
			result += ", \"queue_manager\":\"" + object_search_results[i][1] + "\"";
			result += ", \"host\":\"" + object_search_results[i][2] + "\"";
			result += ", \"host_location\":\"" + object_search_results[i][3] + "\"";
			result += ", \"platform\":\"" + object_search_results[i][4] + "\"";
			result += ", \"environment\":\"" + object_search_results[i][5] + "\"";
			result += ", \"application\":\"" + object_search_results[i][6] + "\"";
			result += ", \"app_id\":\"" + object_search_results[i][7] + "\"";
			result += ", \"object_type\":\"" + object_search_results[i][8] + "\"";
			result += "},";
		}
		if(result.length() > 1) {
			result = result.substring(0, result.length()-1);
		}
		result += "]";
		return result;
	}
	
	public static String cleanJson(String result) {
		if(result.length() == 0) {
			result = "[]";
		} else {
			result = result.substring(0, result.length()-1);
			result += "]";
		}
		return result;
	}
}
