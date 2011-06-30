package ir.crawler.parser.json;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class ReadJSON {

	//{"count":1,"url":"http://www.blogussion.com/favorites/display-twitter-rss-count/"}
	public int getJSON_from_twitter(String json){
		//JsonObject json_ob = new JsonObject();
		//JSONValue val = JSONParser.parseLenient(json);
		//System.out.println(val.isObject().get("count"));
		//json_ob.p
		try {
			Object ob = JSONValue.parse(json);

			if (ob instanceof JSONObject) {
				JSONObject json_obj = (JSONObject) ob;
				Object count_obj = json_obj.get("count");
				
				if (count_obj instanceof String) {
					String count = (String)count_obj;
					try {
						Integer.valueOf(count);
					} catch (NumberFormatException e) {
						System.out.println("DATA FROM TWETTER CORRUPTED");
						return 0;
					}
				}
				if ((count_obj instanceof Long) || (count_obj instanceof Integer) ){
					int count = ((Long)count_obj).intValue();
					return count;
					
				}
			}
		}catch (Exception e) {
			// just in case exception...
			System.out.println("DATA FROM TWETTER CORRUPTED");
			return 0;
		}
		System.out.println("DATA FROM TWITTER IS NOT JSON OBJECT");
		return 0;

	}
}
