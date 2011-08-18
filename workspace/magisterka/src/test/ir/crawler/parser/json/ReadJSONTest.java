package test.ir.crawler.parser.json;

import ir.crawler.parser.json.ReadJSON;

import org.junit.Assert;
import org.junit.Test;

public class ReadJSONTest {

	@Test
	public void test_json() {
		ReadJSON reader = new ReadJSON();
		int val = reader.getJSON_from_twitter("{\"count\":1,\"url\":\"http://www.blogussion.com/favorites/display-twitter-rss-count/\"}");
		Assert.assertEquals(1, val);
	}
}
