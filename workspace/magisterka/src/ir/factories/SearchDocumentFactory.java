package ir.factories;

import ir.analyzer.ScorerDoc;
import ir.analyzer.SearchDocument;
import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class SearchDocumentFactory {
	
	
	public static SearchDocument getSearchDocument(ScorerDoc score_doc, String text){
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, score_doc.id);
	
		List<Object[]> tags = null;
		tx.commit();
		session.close();
		
		//SearchDocument sd = new SearchDocument(id, text, (String)(users.get(0)[1]), tags , (Long)(users.get(0)[2]));
		//System.out.println(doc.getTags_cashe1());
		SearchDocument sd = new SearchDocument(score_doc, text, doc.getTags_cashe1(), doc.getUrl());
		return sd;
	}
	
	
	
}
