package ir.analyzer;

import ir.hibernate.HibernateUtil;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Scorer;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomCollector extends Collector {
	
	
	private int docBase;
	private Scorer scorer;
	private int count = 0;
	Session session;
	Transaction tx;
	IndexSearcher isearcher;
	double r1;
	double r2;
	double r3;
	double r4;
	int max_results;
	
	public CustomCollector(IndexSearcher isearcher, double r1, double r2,double r3, double r4, int max_results) {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		this.isearcher = isearcher;
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
		this.r4 = r4;
		this.max_results = max_results;
		
	}
	
	@Override
	public void setScorer(Scorer scorer) throws IOException {
		this.scorer = scorer;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void collect(int doc) throws IOException {
		float lucene_score = scorer.score();
		System.out.println(count + " : " + (doc + docBase) + " - "+ lucene_score);
		Document doc_ob = isearcher.doc(doc + docBase);
		
		List<Object[]> res = session.
			createQuery("select social_page_rank, adapted_page_rank, digg_value from DocumentTable where id=?").
			setLong(0, Long.valueOf(doc_ob.get("id"))).list();
		Object[] ob = res.get(0);
		
		double score_1;
		double score_2;
		double score_3;
		if (ob[0] != null)
			score_1 = (Double)ob[0];
		else score_1 = 0;
		
		if (ob[1] != null)
			score_2 = (Double)ob[1];
		else score_2 = 0;
		
		
		if (ob[2] != null)
			score_3 = ((Integer)ob[2]).doubleValue();
		else score_3 = 0;
		
		double final_score = r1 * lucene_score  + r2 *  score_1 +  r3 * score_2 + r4 * score_3;
		
		
		System.out.println("final score:" + final_score);
		count++;
	}

	@Override
	public void setNextReader(IndexReader reader, int docBase)
			throws IOException {
		this.docBase = docBase;
		
	}

	@Override
	public boolean acceptsDocsOutOfOrder() {
		return true;
	}

	public void end(){
		tx.commit();
		session.close();
	}
}
