package ir.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ResultsGetterServiceAsync {

	void get_query_result(String query, AsyncCallback<List<String>> callback);

}
