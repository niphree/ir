package ir.crawler;

public class Crawlers {

	public static void main(String[] args) {
		boolean recreate = true;
		DeliciousFeedCrawler crawler = new DeliciousFeedCrawler(recreate);
		crawler.start();
		System.out.println("WAITING FOR OTHER CRAWLERS TO START");
		
		
		//sleep(60*1000);
		
		System.out.println("STARTING OTHER CRAWLERS");
		TwitterCrawler twitter = new TwitterCrawler();
		twitter.start();
		DiggCrawler digg = new DiggCrawler();
		digg.start();
		FacebookCrawler facebook = new FacebookCrawler();
		facebook.start();
		
	}
	
}
