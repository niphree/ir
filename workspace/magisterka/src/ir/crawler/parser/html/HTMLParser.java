package ir.crawler.parser.html;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.ParserException;

public class HTMLParser {
	
	String adress;
	
	public void setLink(String adress){
		this.adress = adress;
	}
	
	
	public void parse() throws IOException, ParserException{
		URL url = new URL(adress);

		Parser parser = new Parser(new Lexer(url.openConnection()));
		System.out.println(parser.getURL());
	
		LinkVisitor visitor = new LinkVisitor(url);
		parser.visitAllNodesWith(visitor);
		
		List<String> links = visitor.getFilteredLinks();
		
		Iterator<String> it = links.iterator();
		while (it.hasNext()){
			System.out.println(it.next());
		}
	}
	
	public List<String> getLinks(){
		return null;
	}
}

