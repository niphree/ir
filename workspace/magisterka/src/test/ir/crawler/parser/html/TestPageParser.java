package test.ir.crawler.parser.html;

import ir.crawler.parser.html.PageParser;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

public class TestPageParser {

	
	@Ignore
	@Test
	public void testTokenizer() throws IOException{
		PageParser pp = new PageParser("http://www.delicious.com/");
		System.out.println(pp.get_cleaned_page());
		System.out.println(pp.get_page());
	}
	
	@Test
	public void test_facebook() throws IOException{
		PageParser pp = new PageParser("http://www.facebook.com/plugins/like.php?href=http%3A%2F%2Fmedia.gunaxin.com%2Ften-things-we-learned-during-season-one-of-game-of-thrones%2F93853&layout=box_count");
		System.out.println(pp.get_facebook_values());
	}
}
