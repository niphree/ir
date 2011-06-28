package ir.crawler.parser.json;

import com.google.gwt.dev.json.JsonObject;
import com.google.gwt.json.client.JSONParser;

public class ReadJSON {

	
	public String getJSON_from_twitter(String json){
		JsonObject json_ob = new JsonObject();
		JSONParser.parseLenient(json);
		return null;
	}
}
