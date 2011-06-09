package ir.gwt.client;

import com.google.gwt.core.client.EntryPoint;
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
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";




	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		VerticalPanel panel = new VerticalPanel();
		
		HorizontalPanel h_panel = new HorizontalPanel();
		h_panel.setWidth("400px");
		h_panel.setSpacing(10);
		final TextBox search_field = new TextBox();
		search_field.setWidth("200px");
		final Button search_button = new Button("Search");
		
		h_panel.add(search_field);
		h_panel.add(search_button);
		
		VerticalPanel v_panel = new VerticalPanel();
		v_panel.add(new Label("center Results! label:"));

		panel.add(h_panel);
		panel.add(v_panel);
		
		RootPanel.get().add(panel);
		
		

	}
}
