package ir.gwt.server;

import ir.gwt.client.SearcherService;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearcherServiceImpl 
	extends RemoteServiceServlet 
	implements SearcherService  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7869462967960789083L;

	@Override
	public List<String> get_query_result(String query) {
		/*
		Searcher search = new Searcher();
		List<SearchDocument> s_list = search.search("Tests");
		List<String> r_list = new ArrayList<String>();
		for (SearchDocument sd : s_list){
			r_list.add(sd.toString());
		}
		*/
		List<String> r_list = new ArrayList<String>();
		r_list.add("asd1");
		r_list.add("asd2");
		r_list.add("asd3");
		r_list.add("asd4");
		return r_list;
	}

}
