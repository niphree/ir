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
		
		
		for (DocumentTable doc : docs){
			Query users_q = session.createQuery("select doc.id, doc.url, count(distinct u.id), doc.facebook_value, doc.digg_value, doc.twitter_value " +
					"from UserTagDocTable utd join utd.user u join utd.doc doc " +
					"where doc.id = ? " )
				.setLong(0, doc.getId());
			
			List<Object[]> users = users_q.list();
			
			Query q = session.createQuery("select tags.id, tags.tag, count(*) as c " +
				"from UserTagDocTable utd join utd.tags tags join utd.doc doc " +
				"where doc.id = ? " +
				"group by tags.id " +
				"order by c")
				.setMaxResults(10).setLong(0, doc.getId());
			
			String res = "";
			res += " <B>url:</B> " + users.get(0)[1];
			res += "<BR><B>users:</B> " + users.get(0)[2];
			res += " <B>facebook:</B> " + users.get(0)[3];
			res += " <B>digg:</B> "  + users.get(0)[4];
			res += " <B>twitter:</B> " + users.get(0)[5];
			
			res += "<BR><b>TAGS: </b> ";
			List<Object[]> tags = q.list();
			for (Object[] tag : tags){
				res += "[ " + tag[1] +" x" + tag[2]+ "]";
			}
			
			
			doc.setTags_cashe1(res);
			session.save(doc);
			
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
