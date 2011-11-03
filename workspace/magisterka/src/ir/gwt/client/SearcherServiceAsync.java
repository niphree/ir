package ir.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearcherServiceAsync {

	void get_query_result(String query,double r1, double r2,double r3, double r4, AsyncCallback<List<String>> callback);

}
