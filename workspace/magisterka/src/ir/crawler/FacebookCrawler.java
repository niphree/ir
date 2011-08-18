package ir.crawler;

import ir.crawler.parser.html.PageParser;
import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class FacebookCrawler extends AbstractCrawler{

	private static String MAIN_LINK = "http://www.facebook.com/plugins/like.php?href=";
	private static String END_LINK =  "&layout=standard&locale=en_US";//"&layout=box_count";
	private static String NAME = "FACEBOOK";
	
	
	@Override
	protected void save(DocumentTable doc, int val) {
		int old_val = doc.getFacebook_value();
		if (old_val < val) {
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			@SuppressWarnings("unchecked")
			DocumentTable doc_obj = (DocumentTable) session.get(DocumentTable.class, doc.getId());
			doc_obj.setFacebook_value(val);

			tx.commit();
			session.close();
		}
		
	}

	//http://www.facebook.com/plugins/like.php?href=http%3A%2F%2Fmedia.gunaxin.com%2Ften-things-we-learned-during-season-one-of-game-of-thrones%2F93853&layout=box_count");
	@Override
	protected int get_value(String url) {
		try {
			//System.out.println(MAIN_LINK + url + END_LINK);
			PageParser parser = new PageParser(MAIN_LINK + url + END_LINK);
			int val = parser.get_facebook_values();
			return val;
		} catch (Exception e) {
			System.out.println(get_name() + " VALUE GET EXCEPTION!!");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	protected String get_name() {
		return NAME;
	}

}
