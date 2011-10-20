package ir.crawler;

public class Crawlers {

	public static void main(String[] args) {
		
		//DataBaseUpdate update_tags_cache = new DataBaseUpdate();
		//update_tags_cache.start();
		
		boolean recreate = false;
	//	DeliciousFeedCrawler crawler = new DeliciousFeedCrawler(recreate, CrawlerType.TOP);
	//	crawler.start();
		
		DeliciousFeedCrawler crawler2 = new DeliciousFeedCrawler(false, CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100");
		crawler2.start();
		System.out.println("WAITING FOR OTHER CRAWLERS TO START");
		
		DeliciousFeedCrawler crawler3 = new DeliciousFeedCrawler(false, CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100");
		crawler3.start();
		
		DeliciousFeedCrawler crawler4 = new DeliciousFeedCrawler(false, CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100");
		crawler4.start();
		
		DeliciousFeedCrawler crawler5 = new DeliciousFeedCrawler(false, CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100");
		crawler5.start();
		
		//UserDeliciousCrawler usr_new = new UserDeliciousCrawler(true);
		//usr_new.start();
		
//		UserDeliciousCrawler usr_old = new UserDeliciousCrawler(false);
//		usr_old.start();
		
		//URLDeliciousCrawler url_new = new URLDeliciousCrawler(true);
		//url_new.start();
		
//		URLDeliciousCrawler url_old = new URLDeliciousCrawler(false);
//		url_old.start();
		
		/*

		UserDeliciousCrawler usr_old = new UserDeliciousCrawler(false);
		usr_old.start();
		URLDeliciousCrawler url_old = new URLDeliciousCrawler(false);
		url_old.start();
		
		//http://feeds.delicious.com/v2/rss/recent?min=20&count=100
		//sleep(60*1000);
		
		System.out.println("STARTING OTHER CRAWLERS");
		TwitterCrawler twitter = new TwitterCrawler();
		twitter.start();
		
		DiggCrawler digg = new DiggCrawler();
		digg.start();
		
		FacebookCrawler facebook = new FacebookCrawler();
		facebook.start();
		*/
		
	}
	
}
