package ir.factories;

import ir.analyzer.SearchDocument;
import ir.hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SearchDocumentFactory {
	
	
	public static SearchDocument getSearchDocument(long id, String text){
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		//DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, id);
		
		Query doc_q = session.createQuery("select doc.id, doc.url, count(distinct u.id) " +
				"from UserTagDocTable utd join utd.user u join utd.doc doc " +
				"where doc.id = ? " )
			.setLong(0, id);
		
		List<Object[]> doc = doc_q.list();
		
		Query q = session.createQuery("select tags.id, tags.tag, count(*) as c " +
				"from UserTagDocTable utd join utd.tags tags join utd.doc doc " +
				"where doc.id = ? " +
				"group by tags.id " +
				"order by c")
			.setMaxResults(10).setLong(0, id);
		
		List<Object[]> tags = q.list();
		
		tx.commit();
		session.close();
		
		SearchDocument sd = new SearchDocument(id, text, (String)(doc.get(0)[1]), tags , (Long)(doc.get(0)[2]));
		return sd;
	}
	
	
	
}
