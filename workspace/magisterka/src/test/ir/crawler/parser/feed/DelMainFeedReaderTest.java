package test.ir.crawler.parser.feed;

import ir.crawler.parser.data.DeliciousMainData;
import ir.crawler.parser.feed.DeliciousMainFeedReader;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.sun.syndication.io.FeedException;

public class DelMainFeedReaderTest {
	
	@Test
	public void test_del_parse() throws IllegalArgumentException, FeedException, IOException{
		DeliciousMainFeedReader reader = new DeliciousMainFeedReader(new File("src/test/ir/crawler/parser/feed/delicious_main.txt"));
		DeliciousMainData data = reader.parse();
		
	}
}
