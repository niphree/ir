package ir.analyzer;
import ir.database.DocumentTable;
import ir.factories.DocumentTableFactory;
import ir.factories.TagFactory;
import ir.factories.UserFactory;
import ir.util.Properties;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
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
			
			File file = new File(Properties.INDEX_DIR);
			dir = FSDirectory.open(file);
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
		Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter(), new QueryScorer(q));
		
		ScoreDoc[] hits = top.scoreDocs;
		System.out.println(hits.length);
		List<SearchDocument> docs = new ArrayList<SearchDocument>();
		
		for (int i = 0 ; i<hits.length; i++){
			



			  
			    //highlighter.getBestFragments(tokenStream, text, 3, "...");
			    

			
			Document doc = null;
			String txt = null;
			String hig_txt = "";
			try {
				int id = hits[i].doc;
				doc = isearcher.doc(id);
				txt = doc.get(TEXT_FIELD);
				TokenStream tokenStream = TokenSources.getAnyTokenStream(isearcher.getIndexReader(), id, TEXT_FIELD, analyzer);
				hig_txt = highlighter.getBestFragments(tokenStream, txt, 3, " ... ");
				/*TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, txt, true, 30);
				for (int j = 0; j < frag.length; j++) {
				      if ((frag[j] != null) && (frag[j].getScore() > 0)) {
				        hig_txt = hig_txt + " ... " + frag[j];
				      }
				    }
				*/
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (InvalidTokenOffsetsException e) {
				e.printStackTrace();
			}
			
			//System.out.println(doc.getFields());
			//List<Fieldable> fields = doc.getFields();
			//fields.get(0);
			
			//System.out.println(fields.get(1));
			 
			DocumentTable dt = DocumentTableFactory.getDocumentTable(Long.valueOf(doc.get(ID_FIELD)));
			
			SearchDocument sd = new SearchDocument(Long.valueOf(doc.get(ID_FIELD)), hig_txt, dt.getUrl(), 
					TagFactory.get_tags_to_client(dt.getId()), UserFactory.get_user_to_client(dt.getId()) );
			
			docs.add(sd);
		}
		return docs;
	}
}
