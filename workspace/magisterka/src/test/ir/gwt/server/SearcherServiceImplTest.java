package test.ir.gwt.server;

import ir.gwt.server.SearcherServiceImpl;

import java.util.List;

import org.junit.Test;

public class SearcherServiceImplTest {

	@Test
	public void testSearcherImpl(){
		SearcherServiceImpl ssi = new SearcherServiceImpl();
		List<String> results = ssi.get_query_result("sourceforge", 0, 00 ,0 , 1000 );
		
		System.out.println("results:");
		int i = 0;
		for (String res : results){
			//	[TODO] remove this !
			res = res.replace("<BR>", "\n");
			res = res.replace("<B>", "");
			res = res.replace("</B>", "");
			res = res.replace("<b>", "");
			res = res.replace("</b>", "");
//			[TODO] remove this ! END
			i++;
			System.out.println(i + " " + res);
		}
		//System.out.println(results);
		
		//Session session = HibernateUtil.getSession();
		//Transaction tx = session.beginTransaction();
		
		//DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, 20);
		//List<UserTagDocTable> utd_list = doc.getTags_document_list();
		
		//System.out.println("a");
		/*Query q = session.createQuery("select tags.id, tags.tag, count(*) as c " +
				"from UserTagDocTable utd join utd.tags tags join utd.doc doc " +
				"where doc.id = ? " +
				"group by tags.id " +
				"order by c")
			.setMaxResults(10).setLong(0, 20);
		*/
		
		/*

		Query q = session.createQuery("select doc.id, doc.url, count(distinct u.id) " +
				"from UserTagDocTable utd join utd.user u join utd.doc doc " +
				"where doc.id = ? " 
			//	"group by tags.id " +
				//"order by c"
				)
			.setLong(0, 20);
		
		
		List<Object[]> tags = q.list();
		for (Object[] t : tags)
			for (Object a: t)
				System.out.println(a);
		//System.out.println(tags);
		System.out.println("a");
		tx.commit();
		session.close();
		*/
	}
}
