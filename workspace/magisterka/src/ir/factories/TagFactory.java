package ir.factories;

import ir.gwt.shared.DatabaseResults;

import com.google.common.collect.LinkedHashMultiset;



public class TagFactory {

	
	public static LinkedHashMultiset<DatabaseResults> get_tags_to_client(long doc_id){
		LinkedHashMultiset<DatabaseResults> results = LinkedHashMultiset.create();
		
		/*Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, doc_id);
		List<UserTagDocTable> utd_list = doc.getTags_document_list();
		
		for (UserTagDocTable utd : utd_list){
			List<TagTable> tags = utd.getTags();
			for (TagTable tag : tags){
				results.add(new DatabaseResults(tag.getTag(), tag.getId()));
			}
		}
		
		tx.commit();
		session.close();
		*/
		return results;
	}
}
