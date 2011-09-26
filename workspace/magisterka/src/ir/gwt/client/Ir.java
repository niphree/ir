package ir.gwt.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ir implements EntryPoint {

	
	
	public void getSearchResults(final VerticalPanel panel, final TextBox search_field) {
		SearcherServiceAsync searcherService = (SearcherServiceAsync)GWT.create(SearcherService.class);
		
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> result) {
				panel.clear();
				HTML h_results = new HTML("<div class='results_div'>Results:</div>");
				panel.add(h_results);
				for (String elem: result){
					System.out.println(elem);
					HTML html = new HTML(elem);
					panel.add(html);
				}
				
			}
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail");
				System.out.println(caught);
				caught.printStackTrace();

				
			}
		};
		searcherService.get_query_result(search_field.getValue(), callback);
		
	}
	
	public void onModuleLoad() {

		VerticalPanel panel = new VerticalPanel();
		panel.setStyleName("mainTable");
		
		
		HorizontalPanel h_panel = new HorizontalPanel();
		//h_panel.setWidth("400px");
		//h_panel.setSpacing(10);
		h_panel.addStyleName("search-field");
		h_panel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		
		final TextBox search_field = new TextBox();
		search_field.setWidth("600px");
		search_field.setStyleName("searchInput");
		final Button search_button = new Button("Search");
		search_button.setStyleName("sendButton");
		
		
		
		h_panel.add(search_field);
		h_panel.add(search_button);
		
		final VerticalPanel v_panel = new VerticalPanel();
		//v_panel.add(new Label("center Results! label:"));

		v_panel.addStyleName("results-panel");
		v_panel.setSpacing(2);
		
		
		panel.add(h_panel);
		panel.setWidth("100%");
		panel.add(v_panel);
		
		search_button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getSearchResults(v_panel, search_field);
			}
		});
		
		RootPanel.get().add(panel);
		
		

	}
}
