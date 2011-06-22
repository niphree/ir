package ir.crawler;

public class Crawlers {

	public static void main(String[] args) {
		boolean recreate = true;
		DeliciousFeedCrawler crawler = new DeliciousFeedCrawler(recreate);
		crawler.start();
	}
	
}
