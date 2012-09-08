package ir.analyzer;

import java.util.Formatter;
import java.util.List;

public class SearchDocument {
	Long id;
	String doc;
	String url;
	List<Object[]> tags;
	int users;
	String source = "DEL"; //for now, change if more
	String cache;
	ScorerDoc score_doc;
	
	public SearchDocument(ScorerDoc score_doc, String doc,  List<Object[]> tags, long users ) {
		this.id = score_doc.id;
		this.doc = doc;
		this.score_doc = score_doc;
		this.tags = tags;
		//this.users = users;
	}
	
	
	public SearchDocument(ScorerDoc score_doc, String doc, String cache, String url) {
		this.id = score_doc.id;
		this.doc = doc;
		this.cache = cache;
		this.url = url;
		this.score_doc = score_doc;


	}
	@Override
	public String toString() {
		String result = "";
		try {
		Formatter formatter = new Formatter();
		formatter.format(cache, doc);
		result = formatter.toString();
		result = ""+score_doc.social_sim_rank+";";
		result = url;
		}catch (Exception e) {
			return ""+score_doc.social_sim_rank+";";
		/*	result = "" + id  + " +   " +
					//, " + url +
			" sum score: " + score_doc.score + 
			", lucene: " + score_doc.lucene_score +
			", social PR: " + score_doc.spr_score +
			", adapted PR: " + score_doc.apr_score +
			", other: " + score_doc.s_score +
			", ss_gain: " + score_doc.social_sim_rank;
			*/
			
			
		/*	result = "" + id  + ",<br>" + cache + "<br> " +
					"<div style = 'ranks'>" +
					" sum score: " + score_doc.score + 
					", lucene: " + score_doc.lucene_score +
					", Social PageRank: " + score_doc.spr_score +
					", Adapted pageRank: " + score_doc.apr_score +
					", Social Rank: " + score_doc.s_score +
					"</div>";
					*/
		}
		return result;
	}
	
	
	public String toString2() {
		String res = "id: " + id + " ,doc: " + doc;
		res = res + " <b>TAGS: </b> ";
		//System.out.println("tags:");
		if (tags != null)
			for (Object[] tag : tags){ 
				res += "[ " + tag[1] +" (x " + tag[2]+ ") ]";
			}
		
		return res;
	}
}
