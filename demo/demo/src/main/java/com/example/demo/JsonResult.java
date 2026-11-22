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
			result += "},";
		}
		if(result.length() > 1) {
			result = result.substring(0, result.length()-1);
		}
		result += "]";
		return result;
	}
}
