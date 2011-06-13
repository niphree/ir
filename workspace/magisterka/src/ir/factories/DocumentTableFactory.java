package ir.factories;

import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DocumentTableFactory {

	public static DocumentTable getDocumentTable(long id){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, id);
		
		tx.commit();
		session.close();
		return doc;
	}
}
