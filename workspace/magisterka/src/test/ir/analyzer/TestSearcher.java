package test.ir.analyzer;

import ir.analyzer.SearchDocument;
import ir.analyzer.Searcher;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import test.ir.utils.HibernateTestUtils;
import test.ir.utils.LuceneTestUtils;

public class TestSearcher  extends TestCase{

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
	public void testSearchingAdding(){
		/*
		Document d1 = new Document("Testing documents ");		
		Document d2 = new Document("two : Testing documents ");
		Document d3 = new Document("three : Testing documents ");
		Document d4 = new Document("four : no T word insinde ");
		*/
		Searcher search = new Searcher();
		List<SearchDocument> s_list = search.search("Tests");
		
		assertEquals(3, s_list.size());
		
		
	}

}
