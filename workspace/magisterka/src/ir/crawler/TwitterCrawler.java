package ir.crawler;

import ir.crawler.parser.html.HTMLReader;
import ir.crawler.parser.json.ReadJSON;
import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TwitterCrawler  extends AbstractCrawler{

	private static String NAME = "TWITTER";
	private static String MAIN_LINK = "http://urls.api.twitter.com/1/urls/count.json?url=";
	
	
	@Override
	protected void save(DocumentTable doc, int val) {
		int old_val = doc.getTwitter_value();
		if (old_val < val) {
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			@SuppressWarnings("unchecked")
			DocumentTable doc_obj = (DocumentTable) session.get(DocumentTable.class, doc.getId());
			doc_obj.setTwitter_value(val);

			tx.commit();
			session.close();
		}

	}

	@Override
	protected int get_value(String url) {

		try {
			HTMLReader reader = new HTMLReader();
			String page = reader.returnHTML(MAIN_LINK + url);
			ReadJSON parser = new ReadJSON();
			int val = parser.getJSON_from_twitter(page);
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
