package ir.analyzer;
import ir.util.Properties;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * @TODO FIX THIS SHIT
 * @author JRMY
 *
 */
public class Searcher {
	
	private Analyzer analyzer;
	private Directory dir;
	private IndexSearcher isearcher;
	
	private static String TEXT_FIELD = "text";
	private static String ID_FIELD = "id";
	
	public Searcher() {

		analyzer = new SnowballAnalyzer(Version.LUCENE_30, "Porter", StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		//dir = FSDirectory.open(new File(Properties.INDEX_DIR));
		//isearcher = new IndexSearcher(dir);
	
	}
	
	public List<SearchDocument> search(String query){
		try {
			dir = FSDirectory.open(new File(Properties.INDEX_DIR));
			isearcher = new IndexSearcher(dir, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		QueryParser qp = new QueryParser(Version.LUCENE_30, TEXT_FIELD, analyzer);
		Query q = null;
		try {
			q = qp.parse(query);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		TopDocs top = null;
		try {
			top = isearcher.search(q, 1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ScoreDoc[] hits = top.scoreDocs;
		System.out.println(hits.length);
		List<SearchDocument> docs = new ArrayList<SearchDocument>();
		
		for (int i = 0 ; i<hits.length; i++){
			Document doc = null;
			try {
				doc = isearcher.doc(hits[i].doc);
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			SearchDocument sd = new SearchDocument(Long.valueOf(doc.get(ID_FIELD)), doc.get(TEXT_FIELD));
			docs.add(sd);
		}
		return docs;
	}
}
