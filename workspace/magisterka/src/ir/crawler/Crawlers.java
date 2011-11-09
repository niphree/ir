package ir.crawler;


public class Crawlers {

	public static void main(String[] args) {
		
		//DataBaseUpdate update_tags_cache = new DataBaseUpdate();
		//update_tags_cache.start();
		
		boolean recreate = false;
	//	DeliciousFeedCrawler crawler = new DeliciousFeedCrawler(recreate, CrawlerType.TOP);
	//	crawler.start();
		
		DeliciousFeedCrawler crawler2 = new DeliciousFeedCrawler(false, CrawlerType.NEW, 
				"http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				2);
		crawler2.start();
		System.out.println("WAITING FOR OTHER CRAWLERS TO START");
		/*
		DeliciousFeedCrawler crawler3 = new DeliciousFeedCrawler(false, CrawlerType.NEW, 
				"http://feeds.delicious.com/v2/rss/recent?min=20&count=100",3);
		crawler3.start();
		
		DeliciousFeedCrawler crawler4 = new DeliciousFeedCrawler(false, 
				CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				4);
		crawler4.start();

		DeliciousFeedCrawler crawler5 = new DeliciousFeedCrawler(false, 
				CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				5);
		crawler5.start();
		
		DeliciousFeedCrawler crawler6 = new DeliciousFeedCrawler(false, 
				CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				6);
		crawler6.start();
		
		DeliciousFeedCrawler crawler7 = new DeliciousFeedCrawler(false, 
				CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				7);
		crawler7.start();
		
		DeliciousFeedCrawler crawler8 = new DeliciousFeedCrawler(false, 
				CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				8);
		crawler8.start();
		
		DeliciousFeedCrawler crawler9 = new DeliciousFeedCrawler(false, 
				CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				9);
		crawler9.start();
		*/
		
		UserDeliciousCrawler usr_new = new UserDeliciousCrawler(true, 500000);
		usr_new.start();
		
		UserDeliciousCrawler usr_old = new UserDeliciousCrawler(false, 350000);
		usr_old.start();
	/*	
		
		URLDeliciousCrawler url_new = new URLDeliciousCrawler(true);
		url_new.start();
		
		URLDeliciousCrawler url_old = new URLDeliciousCrawler(false);
		url_old.start();
		*/
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
