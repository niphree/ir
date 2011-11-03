package ir.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("search")
public interface SearcherService extends RemoteService  {

	public List<String> get_query_result(String query, double r1, double r2,double r3, double r4);
}
