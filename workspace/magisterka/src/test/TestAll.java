package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.ir.crawler.parser.html.TestPageParser;
import test.ir.database.TestDocumentTable;
import test.ir.database.TestTagTable;
import test.ir.database.TestUserTable;
import test.ir.model.TestDocument;
import test.ir.tokenizer.TestTokenizer;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestTokenizer.class,
	TestDocumentTable.class,
	TestTagTable.class,
	TestUserTable.class,
	TestDocument.class,
	TestPageParser.class
})

public class TestAll {
	
}