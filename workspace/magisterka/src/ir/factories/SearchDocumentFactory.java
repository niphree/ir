package ir.factories;

import ir.analyzer.ScorerDoc;
import ir.analyzer.SearchDocument;
import ir.connector.ConnectorFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchDocumentFactory {
	
	
	public static SearchDocument getSearchDocument(ScorerDoc score_doc, String text){
		
		//Session session = HibernateUtil.getSession();
	//	Transaction tx = session.beginTransaction();
		
	//	DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, score_doc.id);
	
		
		ConnectorFactory cf = ConnectorFactory.instance();
		//System.out.println("SearchDocument: " + score_doc.id);
		String url = "";
		String cache = "";
		try {
			ResultSet rs = cf.execute("select url, tags_cashe1 from DOCUMENT where id=" + score_doc.id);
			rs.next();
			url = rs.getString(1);
			cache = rs.getString(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	//	List<Object[]> tags = null;
	//	tx.commit();
	//	session.close();
		
		//SearchDocument sd = new SearchDocument(id, text, (String)(users.get(0)[1]), tags , (Long)(users.get(0)[2]));
		//System.out.println(doc.getTags_cashe1());
		SearchDocument sd = new SearchDocument(score_doc, text, cache, url);
		return sd;
	}
	
	
	
}
