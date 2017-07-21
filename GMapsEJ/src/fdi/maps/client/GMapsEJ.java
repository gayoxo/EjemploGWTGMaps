package fdi.maps.client;

import java.util.LinkedList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.maps.gwt.client.Geocoder;
import com.google.maps.gwt.client.GeocoderRequest;
import com.google.maps.gwt.client.GeocoderResult;
import com.google.maps.gwt.client.GeocoderStatus;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.GoogleMap.DblClickHandler;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
//import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;


public class GMapsEJ implements EntryPoint {
	private ListBox L;
	LinkedList<Marker> listaMarked;
	private Geocoder fCoder;

	public void onModuleLoad() {
		listaMarked=new LinkedList<>();
		FormPanel panel = new FormPanel();
        panel.setWidth("100%");
        panel.setHeight("600px");
        MapOptions options = MapOptions.create();
        options.setCenter(LatLng.create(40.4169,-3.7033));
        options.setZoom(13);
        options.setMapTypeId(MapTypeId.ROADMAP);
        options.setDraggable(true);
        options.setMapTypeControl(true);
        options.setScaleControl(true);
        options.setScrollwheel(true);
        options.setMapMaker(true);
//        Button btn = new Button();
        GoogleMap gMap = GoogleMap.create(panel.getElement(), options);
        RootPanel.get("centered").add(panel);
//        RootPanel.get("centered").add(btn);

        gMap.addDblClickListener(new DblClickHandler() {
			
			@Override
			public void handle(MouseEvent event) {
//				Window.alert(event.getLatLng().toString());
//		        MarkerImage markerImage = MarkerImage.create();
				
				
				
				
		        MarkerOptions mOpts = MarkerOptions.create();
//		        mOpts.setIcon(markerImage);
		        mOpts.setPosition(event.getLatLng());
		        
		        Marker marker = Marker.create(mOpts);
		        marker.setTitle("Direccion");
		        marker.setMap(gMap);
		        
		        listaMarked.add(marker);
		        L.addItem("Marca->"+event.getLatLng().toString());
		        marker.addDblClickListener(new Marker.DblClickHandler() {
					
					@Override
					public void handle(MouseEvent event) {
						GeocoderRequest GReq = GeocoderRequest.create();
						GReq.setLocation(event.getLatLng());
						fCoder.geocode(GReq, new Geocoder.Callback() {
							
							@Override
							public void handle(JsArray<GeocoderResult> a, GeocoderStatus b) {
								GeocoderResult result = a.shift();
								Window.alert(result.getFormattedAddress());
								
							}
						});
						
					}
				});
				
			}
		});
        
        LatLng centerIcon = LatLng.create(40.4169,-3.7033);
//        MarkerImage markerImage = MarkerImage.create();
        MarkerOptions mOpts = MarkerOptions.create();
//        mOpts.setIcon(markerImage);
        mOpts.setPosition(centerIcon);
        
        Marker marker = Marker.create(mOpts);
        marker.setMap(gMap);
        
//        gMap.addIdleListener(new GoogleMap.IdleHandler() {
//
//            public void handle() {
//                Window.alert("Idle");
//
//            }
//        });
        fCoder = Geocoder.create();
        L=new ListBox();
        L.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int Selecctioado = L.getSelectedIndex();
				if (!listaMarked.isEmpty())
					{
					Marker Marked=listaMarked.get(Selecctioado);
					GoogleMap Nulo=null;
					Marked.setMap(Nulo);
					L.removeItem(Selecctioado);
					listaMarked.remove(Selecctioado);
					}
				
			}
		});
        
        RootPanel.get("centered").add(L);

	}
	
	
//	private void addHandlers() {
//        fMap.addMapDoubleClickHandler(new MapDoubleClickHandler() {
//
//            @Override
//            public void onDoubleClick(MapDoubleClickEvent event) {
//                if (event.getLatLng() != null) {
//                    performReverseLookup(event.getLatLng());
//                }
//            }
//
//        });
//
//        fMarker.addMarkerDragEndHandler(new MarkerDragEndHandler() {
//
//            @Override
//            public void onDragEnd(MarkerDragEndEvent event) {
//                LatLng point = event.getSender().getLatLng();
//                if (point != null) {
//                    performReverseLookup(point);
//                }
//            }
//
//        });
//    }
//
//    private void performReverseLookup(final LatLng point) {
//        fCoder.getLocations(point, new LocationCallback() {
//
//            @Override
//            public void onSuccess(JsArray<Placemark> locations) {
//                if (locations.length() > 0) {
//                    LatLng point = locations.get(0).getPoint();
//                    fMarker.setLatLng(point);
//                    fMarker.setVisible(true);
//                    fMap.getInfoWindow().open(point, new InfoWindowContent(locations.get(0).getAddress()));
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode) {}
//        });
//    }

}