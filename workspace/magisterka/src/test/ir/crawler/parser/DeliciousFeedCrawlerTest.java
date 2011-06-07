package test.ir.crawler.parser;

import ir.crawler.DeliciousFeedCrawler;
import junit.framework.TestCase;

import org.junit.Test;

import test.ir.utils.HibernateTestUtils;
import test.ir.utils.LuceneTestUtils;

public class DeliciousFeedCrawlerTest extends TestCase{


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
	public void test_deli_crawler() throws InterruptedException{
		DeliciousFeedCrawler crawler = new DeliciousFeedCrawler();
		crawler.start_crawler();
	}
}
