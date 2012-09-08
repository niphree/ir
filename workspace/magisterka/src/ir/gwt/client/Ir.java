package ir.gwt.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ir implements EntryPoint {

	
	
	public void getSearchResults(final VerticalPanel panel, final TextBox search_field, 
			final TextBox r1, final TextBox r2, final TextBox r3, final TextBox r4) {
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
		searcherService.get_query_result(search_field.getValue(), 
				Double.valueOf(r1.getValue()), Double.valueOf(r2.getValue()), 
				Double.valueOf(r3.getValue()), Double.valueOf(r4.getValue()), callback);
		
	}
	
	public void onModuleLoad() {

		VerticalPanel panel = new VerticalPanel();
		panel.setStyleName("mainTable");
		
		VerticalPanel panel2 = new VerticalPanel();
		panel.setStyleName("headerPannel");
		
		
		HorizontalPanel h_panel = new HorizontalPanel();
		HorizontalPanel h_panel2 = new HorizontalPanel();
		//h_panel.setWidth("400px");
		//h_panel.setSpacing(10);
		h_panel.addStyleName("search-field");
		h_panel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		
		h_panel2.addStyleName("data-field");
		h_panel2.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		
		final TextBox search_field = new TextBox();
		search_field.setWidth("600px");
		search_field.setStyleName("searchInput");
		
		
		
		final Button search_button = new Button("Search");
		search_button.setStyleName("sendButton");
		
		
		
		
		final Label t1 = new Label("Lucene:");
		final TextBox r1 = new TextBox();
		r1.setWidth("50px");
		r1.setStyleName("searchData");
		r1.setValue("1.0");
		
		
		final Label t2 = new Label("SocialPageRank:");
		final TextBox r2 = new TextBox();
		r2.setWidth("50px");
		r2.setStyleName("searchData");
		r2.setValue("1.0");
		
		final Label t3 = new Label("AdaptedPageRank:");
		final TextBox r3 = new TextBox();
		r3.setWidth("50px");
		r3.setStyleName("searchData");
		r3.setValue("1.0");
		
		final Label t4 = new Label("Other:");
		final TextBox r4 = new TextBox();
		r4.setWidth("50px");
		r4.setStyleName("searchData");
		r4.setValue("1.0");
		
		
		h_panel.add(search_field);
		h_panel.add(search_button);
		
		
		h_panel2.add(t1);
		h_panel2.add(r1);
		h_panel2.add(t2);
		h_panel2.add(r2);
		h_panel2.add(t3);
		h_panel2.add(r3);
		h_panel2.add(t4);
		h_panel2.add(r4);
		
		final VerticalPanel v_panel = new VerticalPanel();
		//v_panel.add(new Label("center Results! label:"));

		v_panel.addStyleName("results-panel");
		v_panel.setSpacing(2);
		
		
		panel2.add(h_panel);
		panel2.add(h_panel2);
		
		panel.add(panel2);
		panel.setWidth("100%");
		panel.add(v_panel);
		
		
		search_field.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					getSearchResults(v_panel, search_field, r1, r2, r3, r4);
			        } 
				
			}
		});
		
		search_button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getSearchResults(v_panel, search_field, r1, r2, r3, r4);
			}
		});
		
		RootPanel.get().add(panel);
		
		

	}
}
