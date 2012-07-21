package ir.crawler.parser.json;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class ReadJSON {

	public List<Object[]>  getJSON_test_data(String json){
		List<Object[]> ret_list = new ArrayList<Object[]>();
		
		try {
			Object ob = JSONValue.parse(json);
			//System.out.println(ob.getClass().getCanonicalName());
			if (ob instanceof JSONObject) {
				JSONObject json_obj = (JSONObject) ob;
				JSONArray array_obj = (JSONArray)json_obj.get("data");
				for (Object elem : array_obj){
					JSONObject json_elem = (JSONObject) elem;
					String a = (String)json_elem.get("file");
					String b = (String)json_elem.get("keywords");
					String c = (String)json_elem.get("review");
					Integer cc = null;
					if (c == null){
						cc = 0;
					}
					else if (c.equals("")){
						cc = 0;
					}
					else cc = Integer.parseInt(c);
					Object[] abc = {a,b, cc};
					ret_list.add(abc);
				}
				
			}
		}catch (Exception e) {
			// just in case exception...
			e.printStackTrace();
			System.out.println("TEST DATA IS CORRUPTED");
			return ret_list;
		}
		//System.out.println("TEST DATA IS NOT JSON OBJECT");
		return ret_list;
		
	}
	
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
