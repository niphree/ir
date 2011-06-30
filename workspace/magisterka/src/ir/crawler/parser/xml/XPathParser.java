package ir.crawler.parser.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathParser {


	public List<String> parse(String path, String document) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{

		
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		
		domFactory.setNamespaceAware(true);
		
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		
		Document doc = builder.parse(document);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		// XPath Query for showing all nodes value
		XPathExpression expr = xpath.compile(path);
		
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		
		NodeList nodes = (NodeList) result;
		List<String> nodes_values = new ArrayList<String>();
		
		for (int i = 0; i < nodes.getLength(); i++) {
			System.out.println(nodes.item(i).getNodeValue());
			nodes_values.add(nodes.item(i).getNodeValue()); 
		}
		
		return nodes_values;
	}
}
