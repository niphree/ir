package ir.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
/*		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
*/
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element

		//DockLayoutPanel dock_p = new DockLayoutPanel(Unit.EM);
		
		VerticalPanel panel = new VerticalPanel();
		
		HorizontalPanel h_panel = new HorizontalPanel();
		h_panel.setWidth("400px");
	//	h_panel.setBorderWidth(1);
		h_panel.setSpacing(10);
		final TextBox search_field = new TextBox();
		search_field.setWidth("200px");
		final Button search_button = new Button("Search");
		
		h_panel.add(search_field);
		h_panel.add(search_button);
		
	
		
		VerticalPanel v_panel = new VerticalPanel();
		//v_panel.add(new HTML("Results:"));
		v_panel.add(new Label("center Results! label:"));
		//v_panel.setVisible(true);
		//v_panel.setHeight("100%");
		//v_panel.setSize("400px", "400px");
		
		//dock_p.setHeight("100%");
		//panel.setHeight("100%");
		panel.add(h_panel);
		//dock_p.addSouth(new Label("south Results! label:") , 2);
		panel.add(v_panel);
		
		RootPanel.get().add(panel);
		
		
		
/*

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();
*/
		/*
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
*/
	}
}
