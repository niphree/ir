package test.ir.tokenizer;

import ir.tokenizer.Tokenizer;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestTokenizer {

	
	@Test
	public void testTokenizer(){
		Tokenizer tokenizer = new Tokenizer();
		
		String document = "Commons Digester is a subproject of the Commons project";
		
		List<String> terms = tokenizer.getTerms(document);
		System.out.println(terms);
		Assert.assertEquals(9, terms.size());
		
		
	}
}
