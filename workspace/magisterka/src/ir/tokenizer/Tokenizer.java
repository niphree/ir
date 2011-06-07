package ir.tokenizer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;




public class Tokenizer {

	Analyzer analyzer;
	public Tokenizer(){
		analyzer = new SnowballAnalyzer(Version.LUCENE_30, "Porter");
		
	}
	
	
	
	public List<String> getTerms(String document){
		StringReader reader = new StringReader(document);
		TokenStream tokenStream = analyzer.tokenStream("*", reader); //dont know why "*", for me, anything work
		
		TermAttribute termAtt = (TermAttribute) tokenStream.getAttribute(TermAttribute.class); 
		//TypeAttribute typeAtt = (TypeAttribute) tokenStream.getAttribute(TypeAttribute.class);
		
		List <String> tokens = new ArrayList<String>();
		
		try {
			while (tokenStream.incrementToken()) { 
				//System.out.print(termAtt.term());
				
				tokens.add(termAtt.term());
				//System.out.print(' '); 
				//System.out.println(typeAtt.type()); 
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// TODO log me
			e.printStackTrace();
		}
		
		return tokens;
	}
	
}
