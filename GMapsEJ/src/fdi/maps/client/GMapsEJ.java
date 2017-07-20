package fdi.maps.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;


public class GMapsEJ implements EntryPoint {
	public void onModuleLoad() {
		FormPanel panel = new FormPanel();
        panel.setWidth("800px");
        panel.setHeight("600px");
        MapOptions options = MapOptions.create();
        options.setCenter(LatLng.create(23,-151));
        options.setZoom(2);
        options.setMapTypeId(MapTypeId.ROADMAP);
        options.setDraggable(true);
        options.setMapTypeControl(true);
        options.setScaleControl(true);
        options.setScrollwheel(true);
        Button btn = new Button();
        GoogleMap gMap = GoogleMap.create(panel.getElement(), options);
        RootPanel.get().add(panel);
        RootPanel.get().add(btn);

        gMap.addIdleListener(new GoogleMap.IdleHandler() {

            public void handle() {
                Window.alert("Idle");

            }
        });
    
	}

}