package ir.crawler;


public class Crawlers {

	public static void main(String[] args) {
		
		//DataBaseUpdate update_tags_cache = new DataBaseUpdate();
		//update_tags_cache.start();
		
		boolean recreate = false;
	//	DeliciousFeedCrawler crawler = new DeliciousFeedCrawler(recreate, CrawlerType.TOP);
	//	crawler.start();
		/*
		DeliciousFeedCrawler crawler2 = new DeliciousFeedCrawler(false, CrawlerType.NEW, 
				"http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				2);
		crawler2.start();
		System.out.println("WAITING FOR OTHER CRAWLERS TO START");
		
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
		
		
		DeliciousFeedCrawler crawler10 = new DeliciousFeedCrawler(false, 
				CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				10);
		crawler10.start();
		
		DeliciousFeedCrawler crawler11 = new DeliciousFeedCrawler(false, 
				CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				11);
		crawler11.start();
		
		
		DeliciousFeedCrawler crawler12 = new DeliciousFeedCrawler(false, 
				CrawlerType.NEW, "http://feeds.delicious.com/v2/rss/recent?min=20&count=100",
				12);
		crawler12.start();
		
		*/
		/*
		UserDeliciousCrawler usr_new = new UserDeliciousCrawler(true, 365800);
		usr_new.start();
		
		UserDeliciousCrawler usr_old = new UserDeliciousCrawler(false, 365800);
		usr_old.start();
		
		UserDeliciousCrawler usr_new1 = new UserDeliciousCrawler(true, 388000);
		usr_new1.start();
		
		UserDeliciousCrawler usr_old1 = new UserDeliciousCrawler(false, 388000);
		usr_old1.start();
		
		UserDeliciousCrawler usr_new2 = new UserDeliciousCrawler(true, 408000);
		usr_new2.start();
		
		UserDeliciousCrawler usr_old2 = new UserDeliciousCrawler(false, 408000);
		usr_old2.start();
		
		UserDeliciousCrawler usr_new3 = new UserDeliciousCrawler(true, 258000);
		usr_new3.start();
		
		UserDeliciousCrawler usr_old3 = new UserDeliciousCrawler(false, 258000);
		usr_old3.start();
		
		
		UserDeliciousCrawler usr_new4 = new UserDeliciousCrawler(true, 278000);
		usr_new4.start();
		
		UserDeliciousCrawler usr_old4 = new UserDeliciousCrawler(false, 278000);
		usr_old4.start();
		*/
	//	URLDeliciousCrawler url_new = new URLDeliciousCrawler(true, 806203);
	//	url_new.start();
		//URLDeliciousCrawler url_new1 = new URLDeliciousCrawler(false, 879324);
		//url_new1.start();
		URLDeliciousCrawler url_new2 = new URLDeliciousCrawler(false, 924202);
		url_new2.start();
		URLDeliciousCrawler url_new3 = new URLDeliciousCrawler(false, 973699);
		url_new3.start();
		//URLDeliciousCrawler url_old = new URLDeliciousCrawler(false, 801408);
		//url_old.start(); 
		
	/*	
		
		
		
		URLDeliciousCrawler url_old = new URLDeliciousCrawler(false);
		url_old.start();
		*/
		/*

		
		
		//http://feeds.delicious.com/v2/rss/recent?min=20&count=100
		//sleep(60*1000);
		*/
		/*
		System.out.println("STARTING OTHER CRAWLERS");
		TwitterCrawler twitter = new TwitterCrawler();
		twitter.start();
		
		DiggCrawler digg = new DiggCrawler();
		digg.start();
		*/
		//FacebookCrawler facebook = new FacebookCrawler();
		//facebook.start();
		
		
	}
	
}
