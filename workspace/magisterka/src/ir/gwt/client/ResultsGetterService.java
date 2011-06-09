package ir.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("resultsGetter")
public interface ResultsGetterService extends RemoteService  {

	public List<String> get_query_result(String query);
}
