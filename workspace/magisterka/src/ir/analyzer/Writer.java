package ir.analyzer;

import ir.util.Properties;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Writer {
/*
	Analyzer analyzer = new SnowballAnalyzer(Version.LUCENE_30, "English", StopAnalyzer.ENGLISH_STOP_WORDS_SET);
	
	
	Directory dir = FSDirectory.open(new File(Properties.INDEX_DIR));
	IndexWriter iw = new IndexWriter(dir, analyzer, true, MaxFieldLength.UNLIMITED);
	*/
	
	private Analyzer analyzer;
	private Directory dir;
	private IndexWriter index_writer;
	
	private static String TEXT_FIELD = LuceneProperties.TEXT_FIELD;
	private static String ID_FIELD = LuceneProperties.ID_FIELD;
	
	
	// @TODO change to injector
	public Writer(boolean recreate) throws IOException {
		analyzer = new SnowballAnalyzer(Version.LUCENE_30, "Porter", StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		dir = FSDirectory.open(new File(Properties.INDEX_DIR));
		index_writer = new IndexWriter(dir, analyzer, recreate, MaxFieldLength.UNLIMITED);
		
		//dir = FSDirectory.open(new File(Properties.INDEX_DIR));
		//index_writer = new IndexWriter(dir, analyzer, true, MaxFieldLength.UNLIMITED);
			
		
	}
	
	//@Inject 
	public void setup(Analyzer  analyzer, Directory dir, IndexWriter index_writer){
		
		/*this.analyzer = analyzer;
		this.dir = dir;
		this.index_writer = index_writer;*/
	}
	
	
	public void addDocument(String doc, Long id){
		
		Document document = new Document();
		document.add(new Field(TEXT_FIELD, doc, Store.YES, Index.ANALYZED));
		document.add(new Field(ID_FIELD, id.toString(), Store.YES, Index.ANALYZED));
		
		try {
			index_writer.addDocument(document);
			index_writer.commit();
			//index_writer.close();
			//analyzer.close();
			//dir.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
