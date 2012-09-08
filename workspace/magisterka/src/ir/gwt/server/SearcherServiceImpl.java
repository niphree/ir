package ir.gwt.server;

import ir.analyzer.SearchDocument;
import ir.analyzer.Searcher;
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
	public SearcherServiceImpl(){}
	@Override
	public List<String> get_query_result(String query, double lucene, double social_w,double adapted_w, double popular_w) {
		System.out.println("get results!!!!");
		Searcher search = new Searcher(); 
		List<SearchDocument> s_list = search.search(query, lucene, social_w, adapted_w, popular_w);
		List<String> r_list = new ArrayList<String>();
		System.out.println("results returned, loop");
		for (SearchDocument sd : s_list){
			r_list.add(sd.toString());
		}
		/*for (String r: r_list){
			System.out.println(r);
		}
		System.out.println("loop end");
		*/
		
		/*List<String> r_list = new ArrayList<String>();
		r_list.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus volutpat, leo quis ornare sagittis, quam justo laoreet arcu, eu pellentesque mauris justo et leo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec ac felis nibh. In vitae dictum dui. Mauris risus arcu, euismod non luctus sed, malesuada suscipit dui. Aliquam tempor mattis velit a iaculis. Ut accumsan tincidunt varius. Nulla facilisi. Nam at enim augue, quis vestibulum arcu. Fusce lacus dolor, molestie id consequat vel, varius a lacus. Morbi non consequat nunc. Vivamus fringilla, lacus interdum malesuada consectetur, lacus lorem placerat sem, in iaculis neque arcu quis erat. Maecenas sed tincidunt enim. Pellentesque sed leo sem. Maecenas mattis feugiat odio non adipiscing. Nulla facilisis imperdiet arcu nec porttitor. Vestibulum cursus, risus vitae convallis tempus, leo leo egestas nisl, eget accumsan nisi arcu id purus. ");
		r_list.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus volutpat, leo quis ornare sagittis, quam justo laoreet arcu, eu pellentesque mauris justo et leo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec ac felis nibh. In vitae dictum dui. Mauris risus arcu, euismod non luctus sed, malesuada suscipit dui. Aliquam tempor mattis velit a iaculis. Ut accumsan tincidunt varius. Nulla facilisi. Nam at enim augue, quis vestibulum arcu. Fusce lacus dolor, molestie id consequat vel, varius a lacus. Morbi non consequat nunc. Vivamus fringilla, lacus interdum malesuada consectetur, lacus lorem placerat sem, in iaculis neque arcu quis erat. Maecenas sed tincidunt enim. Pellentesque sed leo sem. Maecenas mattis feugiat odio non adipiscing. Nulla facilisis imperdiet arcu nec porttitor. Vestibulum cursus, risus vitae convallis tempus, leo leo egestas nisl, eget accumsan nisi arcu id purus. ");
		r_list.add("Etiam feugiat, sem nec convallis dignissim, orci ante gravida elit, sit amet ultricies metus justo ut mauris. Aliquam cursus luctus fermentum. Quisque commodo nisi orci. Quisque vel metus vitae lorem commodo placerat. Phasellus ipsum massa, consectetur vel mattis consectetur, euismod sollicitudin nisi. Praesent interdum nulla id purus consequat id faucibus dui fermentum. Phasellus nisl magna, ornare in malesuada et, tempor at diam. Nulla eget magna vel ante vestibulum commodo. Ut in felis nisi, sed pulvinar libero. Nullam eu turpis et tellus dapibus luctus. Donec sollicitudin molestie laoreet. Sed consectetur sodales imperdiet. Quisque luctus justo sed arcu rhoncus non dignissim diam vulputate. Integer orci purus, lacinia sed varius et, pellentesque sit amet mi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. ");
		r_list.add("asd4");*/
		return r_list;
	}

}
