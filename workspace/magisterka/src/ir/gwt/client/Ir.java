package ir.gwt.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ir implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */



	/**
	 * This is the entry point method.
	 */
	public void getSearchResults() {
		SearcherServiceAsync searcherService = (SearcherServiceAsync)GWT.create(SearcherService.class);
		
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> result) {
				System.out.println("SUCCESS");
				Window.alert("GREAT SUCCESS");
				
			}
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("FAILUER");
				System.out.println(caught);

				
			}
		};
		searcherService.get_query_result("ASD", callback);
		
	}
	
	public void onModuleLoad() {

		VerticalPanel panel = new VerticalPanel();
		
		
		
		HorizontalPanel h_panel = new HorizontalPanel();
		h_panel.setWidth("400px");
		h_panel.setSpacing(10);
		final TextBox search_field = new TextBox();
		search_field.setWidth("200px");
		final Button search_button = new Button("Search");
		search_button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getSearchResults();
				
			}
		});
		
		
		h_panel.add(search_field);
		h_panel.add(search_button);
		
		VerticalPanel v_panel = new VerticalPanel();
		v_panel.add(new Label("center Results! label:"));

		panel.add(h_panel);
		panel.add(v_panel);
		
		RootPanel.get().add(panel);
		
		

	}
}
