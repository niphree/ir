package test.ir.crawler.parser;

import ir.crawler.DiggCrawler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class DiggCrawlerTest {

	
	@Test
	public void test_me() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException{
		DiggCrawler digg = new DiggCrawler();
		int val = digg.getValue("http://media.gunaxin.com/ten-things-we-learned-during-season-one-of-game-of-thrones/93853");
		System.out.println(val);
		
	}
}
