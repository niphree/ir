package test.ir.crawler.parser.xml;

import ir.crawler.parser.xml.XPathParser;

import java.util.List;

import org.junit.Test;

public class XPathParserTest {

	
	@Test
	public void test_xpath() throws Exception{
		XPathParser parser = new XPathParser();
		//span class="connect_widget_not_connected_text"
		List<String> l = parser.parse("span/[@class=connect_widget_not_connected_text]", 
				"http://www.facebook.com/plugins/like.php?href=http%3A%2F%2Fmedia.gunaxin.com%2Ften-things-we-learned-during-season-one-of-game-of-thrones%2F93853&layout=box_count");
		System.out.println(l);
	}
}
