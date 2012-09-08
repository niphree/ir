package ir.factories;

import ir.gwt.shared.DatabaseResults;

import com.google.common.collect.LinkedHashMultiset;

public class UserFactory {
	
	public static LinkedHashMultiset<DatabaseResults> get_user_to_client(long doc_id){
		LinkedHashMultiset<DatabaseResults> results = LinkedHashMultiset.create();
		
		/*Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, doc_id);
		List<UserTagDocTable> utd_list = doc.getTags_document_list();
		
		for (UserTagDocTable utd : utd_list){
			UserTable ut = utd.getUser();
			results.add(new DatabaseResults(ut.getName(), ut.getId()));
			
		}
		
		tx.commit();
		session.close();
		*/
		return results;
	}
}
