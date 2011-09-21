package ir.crawler;

import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DataBaseUpdate extends Thread{   //this should be thread
	public static Long WAIT_TIME = Long.valueOf(30 * 60 * 1000); //in secundes
	@Override
	public void run() {
		while(true){
			try {
				update_cache();
				sleep(WAIT_TIME);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update_cache(){
		System.out.println("Starting Document table cache update");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		List<DocumentTable> docs = session.createQuery("from DocumentTable m").list();
		tx.commit();
		session.close();
		
		for (DocumentTable doc1 : docs){
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 
			DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, doc1.getId());
			Query users_q = session.createQuery("select doc.id, doc.url, count(distinct u.id), doc.facebook_value, doc.digg_value, doc.twitter_value " +
					"from UserTagDocTable utd join utd.user u join utd.doc doc " +
					"where doc.id = ? " )
				.setLong(0, doc.getId());
			
			List<Object[]> users = users_q.list();
			
			Query q = session.createQuery("select tags.id, tags.tag, count(*) as c " +
				"from UserTagDocTable utd join utd.tags tags join utd.doc doc " +
				"where doc.id = ? " +
				"group by tags.id " +
				"order by c desc")
				.setMaxResults(10).setLong(0, doc.getId());

			String res = "";
			res="<div class='results-search-block'>" +
					"<div class='results-search-title'>" +
					"<a href='"+users.get(0)[1]+"'>"+users.get(0)[1]+
                    " </a>"+
                    "</div>"+
                    "<div class='results-search-link'>"+
                    users.get(0)[1]+"</div>"+
                    "<div class='results-search-text'> %s "+
                    "</div>"+
                    "<div class='results-search-tags'>";
			
			List<Object[]> tags = q.list();
			for (Object[] tag : tags){
				res += "<a> " + tag[1] +" x" + tag[2]+ "</a>";
			}
			res +="</div>";
			res +="<div class='results-search-services'>";
			res +=("<a><b>added by:</b> "+ users.get(0)[2]+"</a> " +
				   "<a><b>facebook:</b> "+ users.get(0)[3]+" </a> " +
				   "<a><b>digg:</b> "+users.get(0)[4]+" </a>" +
                   "<a><b>twitter:</b>" + users.get(0)[5]+ "</a>");
			res +="</div>";
			res +="</div>";
			
			
			//System.out.println(res);
			doc.setTags_cashe1(res);
			System.out.println(doc.getId());
			session.save(doc);
			tx.commit();
			session.close();
			
		}
		
		tx.commit();
		session.close();
		System.out.println("Ending Document table cache update. going to sleep. Goodnight.");
	}
	
	public static void main(String[] args) {
		DataBaseUpdate u = new DataBaseUpdate();
		u.update_cache();
	}
}
