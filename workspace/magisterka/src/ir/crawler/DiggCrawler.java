package ir.crawler;

import ir.crawler.parser.xml.XPathParser;
import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DiggCrawler extends AbstractCrawler{  
	
	
	private static String MAIN_LINK = "http://services.digg.com/stories?link=";
	private static String XPATH = "/stories/story/@diggs";
	private static String NAME = "DIGG";

	public String get_query(){
		return "from DocumentTable m where  id>(select max(id) from DocumentTable where digg_value > 0 )";
	}
	
	public int get_value(String link)  {
		try {
			XPathParser parser = new XPathParser();
			List<String> node_values = parser.parse(XPATH, MAIN_LINK + link);
			if (node_values.size() == 0)
				return 0;
			String val = node_values.get(0);

			int int_val= Integer.valueOf(val);
			return int_val;
		} catch (Exception e) {
			System.out.println(get_name() + " VALUE GET EXCEPTION!! " + link);
			try {
				sleep(4000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//	e.printStackTrace();
		}
		return 0;
	}


	@Override
	protected void save(DocumentTable doc, int val) {
		int old_val = doc.getDigg_value();
		if (val == 0) return;
		if (old_val < val) {
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			@SuppressWarnings("unchecked")
			DocumentTable doc_obj = (DocumentTable) session.get(DocumentTable.class, doc.getId());
			doc_obj.setDigg_value(val);

			tx.commit();
			session.close();
		}
		
	}


	@Override
	protected String get_name() {
		return NAME;
	}
}
