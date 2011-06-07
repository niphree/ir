package test.ir.crawler.parser.feed;

import ir.crawler.parser.data.DeliciousDocumentData;
import ir.crawler.parser.feed.DeliciousDetailFeedReader;
import ir.crawler.parser.feed.IFeedReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

import test.ir.utils.HibernateTestUtils;
import test.ir.utils.LuceneTestUtils;

import com.sun.syndication.io.FeedException;

public class FeedReaderTester extends TestCase{


	@Override
	protected void setUp() throws Exception {
		HibernateTestUtils.cleanUp();
		LuceneTestUtils.cleanUp();
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateTestUtils.cleanUp();
		LuceneTestUtils.cleanUp();
		super.tearDown();
	}
	
	@Ignore
	@Test
	public void reader_test() throws IllegalArgumentException, FeedException, IOException {
		String feed = "http://feeds.delicious.com/v2/rss/?count=15";
		//String feed = "http://feeds.feedburner.com/thecookingblog";
		URL url = new URL(feed);
		IFeedReader reader = new DeliciousDetailFeedReader(url);
	}
	
	@Test
	public void test_del_parse() throws IllegalArgumentException, FeedException, IOException{
		DeliciousDetailFeedReader reader = new DeliciousDetailFeedReader(new File("src/test/ir/crawler/parser/feed/page_feed_del.txt"));
		DeliciousDocumentData data = reader.parse();
		assertEquals(100, data.getUsr_tags().size()); 
	}
	
}
