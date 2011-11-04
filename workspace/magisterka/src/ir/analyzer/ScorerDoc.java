package ir.analyzer;

public class ScorerDoc implements Comparable<ScorerDoc>{

	public double score;
	public long id;
	public double lucene_score;
	public double spr_score;
	public double apr_score;
	public double s_score;
	public int lucene_id;
	
	
	
	public ScorerDoc(long id, int lucene_id, double score, double lucene_score, double spr_score, 
			double apr_score, double s_score) {
		
		this.score = score;
		this.id = id;
		this.apr_score = apr_score;
		this.s_score = s_score;
		this.spr_score = spr_score;
		this.lucene_score = lucene_score;
	}
	
	@Override
	public String toString() {
		return "ob: " + score;
	}
	
	@Override
	// {(x, y) such that x.compareTo(y) <= 0}.
	public int compareTo(ScorerDoc o) {
		if (score == o.get_score()) return 0;
		if (score < o.get_score()) return -1;
		if (score > o.get_score()) return 1;
		return 0;
	}
	
	
	public double get_score(){
		return score;
	}
	
	

}
