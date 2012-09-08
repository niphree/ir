package ir.analyzer;

import ir.connector.ConnectorFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Scorer;

public class CustomCollector extends Collector {
	
	
	private int docBase;
	private Scorer scorer;
	private int count = 0;

	IndexSearcher isearcher;
	double lucene_w;
	double social_w;
	double adapted_w;
	double popular_w;
	int max_results;
	ScorerDocContainer container;
	ConnectorFactory cf;
	
	public CustomCollector(IndexSearcher isearcher, double lucene_w, double social_w,double adapted_w, double popular_w, int max_results) {

		this.isearcher = isearcher;
		this.lucene_w = lucene_w;
		this.social_w = social_w;
		this.adapted_w = adapted_w;
		this.popular_w = popular_w;
		this.max_results = max_results;
		container = new ScorerDocContainer(max_results);
		this.cf = ConnectorFactory.instance();
	}
	
	
	
	@Override
	public void setScorer(Scorer scorer) throws IOException {
		this.scorer = scorer;
		
	}

	public double value_or_zero(Double val){
		double d=0;
		if (val != null)
			d = val;
		return d;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void collect(int doc) throws IOException {
		float lucene_score = scorer.score();
		
		//System.out.println(count + " : " + (doc + docBase) + " - "+ lucene_score);
		Document doc_ob = isearcher.doc(doc + docBase);
		long id = Long.valueOf(doc_ob.get("id"));
		//System.out.println("CustomCollector: " + id);
		double social=0;
		double adapted=0;
		double score_3=0;
		double popular=0;
		
		//String sql = "select social_page_rank, adapted_page_rank, digg_value+facebook_value+twitter_value, social_sim_rank from DOCUMENT where id="+id;
		try {
			ResultSet rs = cf.executePrep(id);
			rs.next();
			social = value_or_zero(rs.getDouble(1));
			adapted = value_or_zero(rs.getDouble(2));
			score_3 = value_or_zero(rs.getDouble(3));
			popular = value_or_zero(rs.getDouble(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		



	
		double final_score = lucene_w * lucene_score  + social_w *  social +  adapted_w * adapted + popular_w * popular;
		container.add_elem(new ScorerDoc(id, doc, final_score, lucene_score, social, adapted, score_3, popular));
		
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
		try {
			cf.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<ScorerDoc> get_results(){
		return container.get_results();
	}
}
