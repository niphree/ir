package test.ir.utils;

import ir.util.Properties;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneTestUtils {
	/*
	 * STATICs ARE EVIL,
	 * FOR TEST USE ONLY!!
	 * 
	 * ALSO UTILS CLASS ARE EVIL... OH WELL... NVRMND...
	 * 
	 * COPIED... RIGHT
	 * 
	 */
	public static void cleanUp() throws IOException {
		Analyzer analyzer = new SnowballAnalyzer(Version.LUCENE_30, "Porter", StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		Directory dir = FSDirectory.open(new File(Properties.INDEX_DIR));
		IndexWriter index_writer = new IndexWriter(dir, analyzer, false, MaxFieldLength.UNLIMITED);
		index_writer.deleteAll();
		index_writer.close();
		dir.close();
	}
	
}
