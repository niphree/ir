package test.ir.crawler.parser.feed;

import ir.crawler.parser.data.DeliciousDocumentData;
import ir.crawler.parser.feed.DeliciousDetailFeedReader;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

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
	
	
	@Test
	public void test_del_parse() throws IllegalArgumentException, FeedException, IOException{
		DeliciousDetailFeedReader reader = new DeliciousDetailFeedReader(new File("src/test/ir/crawler/parser/feed/page_feed_del.txt"));
		DeliciousDocumentData data = reader.parse();
		assertEquals(100, data.getUsr_tags().size()); 
	}
	
}
