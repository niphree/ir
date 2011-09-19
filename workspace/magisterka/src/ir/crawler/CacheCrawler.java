package ir.crawler;

import ir.hibernate.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;




public class CacheCrawler {
	
	
	
	public static void main(String[] args) {
		CacheCrawler crawler = new CacheCrawler();
		
		
		
		//crawler.document_user_count();
		crawler.document_dist_tag_count();
		crawler.document_tag_count();
		crawler.user_doc_count();
		crawler.user_tag_count();
		crawler.user_tag_dist_count();
		crawler.tag_user_count();
		crawler.tag_doc_dist_count();
		crawler.tag_doc_count();
		
	}
	private void tag_doc_count(){
		System.out.println("CACHE: tag_doc_count()");
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createSQLQuery(
			    " update" +
		        " TAG" + 
		    " set" +
		       " doc_count=(select" +
		           " count(usertagdoc1_.doc_id)" + 
		       " from" +
		           " USERTAGDOC usertagdoc1_ " +
		       " inner join" +
		           " USERTAGDOC_TAG tags2_ " +
		               " on usertagdoc1_.id=tags2_.USERTAGDOC_id" + 
		       " where" +
		           " tags2_.tags_id=TAG.id)"); 
		q.executeUpdate();
		tx.commit();
		session.close();
		System.out.println("CACHE END: tag_doc_count()");
	}
	
	
	private void tag_doc_dist_count(){
		System.out.println("CACHE: tag_doc_dist_count()");
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createSQLQuery(
			    " update" +
		        " TAG" + 
		    " set" +
		       " doc_dist_count=(select" +
		           " count(distinct usertagdoc1_.doc_id)" + 
		       " from" +
		           " USERTAGDOC usertagdoc1_ " +
		       " inner join" +
		           " USERTAGDOC_TAG tags2_ " +
		               " on usertagdoc1_.id=tags2_.USERTAGDOC_id" + 
		       " where" +
		           " tags2_.tags_id=TAG.id)"); 
		q.executeUpdate();
		tx.commit();
		session.close();
		System.out.println("CACHE END: tag_doc_dist_count()");
	}
	
	
	private void tag_user_count(){
		System.out.println("CACHE: tag_user_count()");
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createSQLQuery(
			    " update" +
		        " TAG" + 
		    " set" +
		       " user_count=(select" +
		           " count(distinct usertagdoc1_.user_id)" + 
		       " from" +
		           " USERTAGDOC usertagdoc1_ " +
		       " inner join" +
		           " USERTAGDOC_TAG tags2_ " +
		               " on usertagdoc1_.id=tags2_.USERTAGDOC_id" + 
		       " where" +
		           " tags2_.tags_id=TAG.id)"); 
		q.executeUpdate();
		tx.commit();
		session.close();
		System.out.println("CACHE END: tag_user_count()");
	}
	
	
	
	private void user_tag_dist_count(){
		System.out.println("CACHE: user_tag_dist_count()");
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createQuery(
				" update UserTable as u" +
				" set tag_dist_count=(" +
					" select count(distinct tag.id)" +
					" from UserTagDocTable as utd " +
					" join utd.tags as tag " +
					" where utd.user = u.id" +
					" )" 
							);
		q.executeUpdate();
		tx.commit();
		session.close();
		System.out.println("CACHE END: user_tag_dist_count()");
	}
	
	
	private void user_tag_count(){
		System.out.println("CACHE: user_tag_count()");
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createQuery(
				" update UserTable as u" +
				" set tag_count=(" +
					" select count(tag.id)" +
					" from UserTagDocTable as utd " +
					" join utd.tags as tag " +
					" where utd.user = u.id" +
					" )" 
							);
		q.executeUpdate();
		tx.commit();
		session.close();
		System.out.println("CACHE END: user_tag_count()");
	}
	
	
	private void user_doc_count(){
		System.out.println("CACHE: user_doc_count()");
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createQuery(
				" update UserTable as u" +
				" set doc_count=(" +
					" select count(distinct utd.doc)" +
					" from UserTagDocTable as utd " +
					//" join utd.doc as doc " +
					" where utd.user = u.id" +
					" )" 
							);
		q.executeUpdate();
		tx.commit();
		session.close();
		System.out.println("CACHE END: user_doc_count()");
	}
	
	
	
	private void document_tag_count(){
		System.out.println("CACHE: document_tag_count()");
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createQuery(
				" update DocumentTable as d" +
				" set tag_count=(" +
					" select count(tag.id)" +
					" from UserTagDocTable as utd " +
					" join utd.tags as tag " +
					" where utd.doc = d.id" +
					" )" 
							);
		q.executeUpdate();
		tx.commit();
		session.close();
		System.out.println("CACHE END: document_tag_count()");
	}
	
	private void document_dist_tag_count(){
		System.out.println("CACHE: document_dist_tag_count()");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createQuery(
				" update DocumentTable as d" +
				" set dist_tag_count=(" +
					" select count(distinct tag.id)" +
					" from UserTagDocTable as utd " +
					" join utd.tags as tag " +
					" where utd.doc = d.id" +
					" )" 
				);
				
							
		q.executeUpdate();
		tx.commit();
		session.close();
		System.out.println("CACHE END: document_dist_tag_count()");
	}
	
	private void document_user_count(){
		System.out.println("CACHE: document_user_count()");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createQuery(
							" update DocumentTable as d" +
							" set user_count=(" +
								" select count(distinct utd.user)" +
								" from UserTagDocTable as utd " +
								//" join utd.user as u " +
								" where utd.doc = d.id" +
								" )" 
							);
		q.executeUpdate();
		tx.commit();
		session.close();
		System.out.println("CACHE END: document_user_count()");
	}
	
	
}
