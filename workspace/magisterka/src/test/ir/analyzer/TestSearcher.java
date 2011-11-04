package test.ir.analyzer;

import ir.analyzer.SearchDocument;
import ir.analyzer.Searcher;

import java.util.List;

import org.junit.Test;

public class TestSearcher {
/*
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
	*/
	
	@Test
	public void testSearchingAdding(){
		/*
		Document d1 = new Document("Testing documents ");		
		Document d2 = new Document("two : Testing documents ");
		Document d3 = new Document("three : Testing documents ");
		Document d4 = new Document("four : no T word insinde ");
		*/
		Searcher search = new Searcher();
		List<SearchDocument> s_list = search.search("Tests", 0.008 ,1 ,1, 0.25);
		//System.out.println(s_list);
		for (SearchDocument doc : s_list){
			System.out.println(doc);
		}
		//TestCase.assertEquals(3, s_list.size());
		
		
	}

}
