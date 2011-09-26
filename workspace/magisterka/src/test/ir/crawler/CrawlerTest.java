package test.ir.crawler;

import ir.crawler.parser.data.DeliciousDocumentData;

import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class CrawlerTest {
	@Test
	public void test_me() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException{
		
		DeliciousDocumentData doc = new DeliciousDocumentData("url");
		String results = doc.clean_tag("素材");
		Character c = Character.valueOf("素".charAt(0));
		
		//int a = Character."素".charAt(0));
		System.out.println("+" + "a素材".replaceAll("\\p{InCombiningDiacriticalMarks}", "")+"+");
		System.out.println((int)c);
		System.out.println("!"+results+"!");
		System.out.println("-"+Normalizer.normalize("Ü素材", Form.NFD)+"-");
		System.out.println(Character.UnicodeBlock.of('Ü') == Character.UnicodeBlock.BASIC_LATIN);
		/*
		String input = "";
		
		
		int iter = 0;
		List<Character> special = new ArrayList<Character>( Arrays.asList('%', '@', '!', ',', '.', '-', '"', '\'', '$', '#', '^', 
				'&', '*', '(', ')', '+', '=', '_', '{', '}', '[', ']', ';', 
				':', '<', '>', '?', '\\', '/', '|' ));
		     
		while (iter<input.length()){
			
			if ( special.contains(input.charAt(iter)) )
				iter++;
			else break;
		}
		input = input.substring(iter, input.length());
		System.out.println(input);
		System.out.println(iter);
		
		iter = input.length();
		while (iter>0){
			iter--;
			if ( special.contains(input.charAt(iter)) )
				continue;
			else {
				iter++;
				break;
			}
		}
		System.out.println(iter);
		if (iter >= 0)
			input = input.substring(0, iter);
		System.out.println("!");
		System.out.println(input);
		System.out.println("!");
		*/
	}
}
