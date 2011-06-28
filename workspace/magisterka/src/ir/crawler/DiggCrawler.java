package ir.crawler;

import ir.crawler.parser.xml.XPathParser;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class DiggCrawler extends Thread{  
	
	private static Long WAIT_TIME = Long.valueOf(30 * 60 * 1000); //in secundes
	private static String MAIN_LINK = "http://services.digg.com/stories?link=";
	private static String XPATH = "/stories/story/@diggs";
	
	@Override
	public void run() {
		while(true){
			try {
				start_crawler();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void start_crawler() throws InterruptedException {
		System.out.println("digg crawler, start of tick");
		Calendar start_time = Calendar.getInstance();
		
		//actions
		crawl();
		
		System.out.println("digg crawler, sleeping, good night");
		Calendar end_time = Calendar.getInstance();
		long wait = WAIT_TIME - (end_time.getTimeInMillis() - start_time.getTimeInMillis());
		if (wait < 0)
			wait = 0;

		sleep(wait);

	}

	private void crawl() {
		
		
	}
	
	public int getValue(String link) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
		XPathParser parser = new XPathParser();
		List<String> node_values = parser.parse(XPATH, MAIN_LINK + link);
		if (node_values.size() == 0)
			return 0;
		String val = node_values.get(0);

		int int_val= Integer.valueOf(val);
		return int_val;
	}
}
