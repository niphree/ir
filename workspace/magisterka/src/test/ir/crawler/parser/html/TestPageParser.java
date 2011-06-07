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
}
