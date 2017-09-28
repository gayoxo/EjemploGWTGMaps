package fdi.maps.client;

import java.util.LinkedList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.dblclick.DblClickMapEvent;
import com.google.gwt.maps.client.events.dblclick.DblClickMapHandler;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.client.services.DirectionsRenderer;
import com.google.gwt.maps.client.services.DirectionsRendererOptions;
import com.google.gwt.maps.client.services.DirectionsRequest;
import com.google.gwt.maps.client.services.DirectionsResult;
import com.google.gwt.maps.client.services.DirectionsResultHandler;
import com.google.gwt.maps.client.services.DirectionsService;
import com.google.gwt.maps.client.services.DirectionsStatus;
import com.google.gwt.maps.client.services.DirectionsWaypoint;
import com.google.gwt.maps.client.services.Geocoder;
import com.google.gwt.maps.client.services.GeocoderRequest;
import com.google.gwt.maps.client.services.GeocoderRequestHandler;
import com.google.gwt.maps.client.services.GeocoderResult;
import com.google.gwt.maps.client.services.GeocoderStatus;
import com.google.gwt.maps.client.services.TravelMode;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;



public class GMapsEJ implements EntryPoint {
	private ListBox L;
	LinkedList<Marker> listaMarked;
	private Geocoder fCoder;
	private MapWidget gMap;

	public void onModuleLoad() {
		listaMarked=new LinkedList<>();
		FormPanel panel = new FormPanel();
        panel.setWidth("100%");
        panel.setHeight("600px");
        MapOptions options = MapOptions.newInstance();
        options.setCenter(LatLng.newInstance(40.4169,-3.7033));
        options.setZoom(13);
        options.setMapTypeId(MapTypeId.ROADMAP);
        options.setDraggable(true);
        options.setMapTypeControl(true);
        options.setScaleControl(true);
        options.setScrollWheel(true);
        options.setMapMaker(true);
//        Button btn = new Button();
        gMap = new MapWidget(options);
        panel.add(gMap);
        RootPanel.get("centered").add(panel);
//        RootPanel.get("centered").add(btn);

        gMap.addDblClickHandler(new DblClickMapHandler() {
			
        	
        	


			@Override
			public void onEvent(DblClickMapEvent event) {
				GeocoderRequest GReq = GeocoderRequest.newInstance();
				GReq.setLocation(event.getMouseEvent().getLatLng());
				fCoder.geocode(GReq, new GeocoderRequestHandler() {
					
					
					
					private Marker marker;
					private int Position;


					@Override
					public void onCallback(JsArray<GeocoderResult> results, GeocoderStatus status) {
						GeocoderResult result = results.shift();
//						Window.alert(result.getFormattedAddress());
						 MarkerOptions mOpts = MarkerOptions.newInstance();
//					        mOpts.setIcon(markerImage);
					        mOpts.setPosition(result.getGeometry().getLocation());
					        
					        marker = Marker.newInstance(mOpts);
					        marker.setTitle(result.getFormatted_Address());
					        marker.setMap(gMap);
					        
					        listaMarked.add(marker);
					        L.addItem(result.getFormatted_Address());
					        Position=L.getItemCount()-1;
					        marker.addDblClickHandler(new DblClickMapHandler() {
								
								@Override
								public void onEvent(DblClickMapEvent event) {
									BorrarPunto(marker,Position);
									
								}
							});

						
					}
				});
				
			}
		});
        
        LatLng centerIcon = LatLng.newInstance(40.4169,-3.7033);
//        MarkerImage markerImage = MarkerImage.create();
        MarkerOptions mOpts = MarkerOptions.newInstance();
//        mOpts.setIcon(markerImage);
        mOpts.setPosition(centerIcon);
        
        Marker marker = Marker.newInstance(mOpts);
        marker.setMap(gMap);
        
//        gMap.addIdleListener(new GoogleMap.IdleHandler() {
//
//            public void handle() {
//                Window.alert("Idle");
//
//            }
//        });
        fCoder = Geocoder.newInstance();
        L=new ListBox();
        L.setVisibleItemCount(5);
        L.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int Selecctioado = L.getSelectedIndex()-1;
				if (!listaMarked.isEmpty())
					{
					
					Marker Marked=listaMarked.get(Selecctioado);
					BorrarPunto(Marked,Selecctioado);
					}
				
			}
		});
        
        
        
        
        
        
        
        RootPanel.get("centered").add(L);
        
        
        FormPanel panel2 = new FormPanel();
        panel2.setWidth("100%");
        panel2.setHeight("600px");
        MapOptions options3 = MapOptions.newInstance();
        options3.setCenter(LatLng.newInstance(40.4169,-3.7033));
        options3.setZoom(13);
        options3.setMapTypeId(MapTypeId.ROADMAP);
        options3.setDraggable(true);
        options3.setMapTypeControl(true);
        options3.setScaleControl(true);
        options3.setScrollWheel(true);
        options3.setMapMaker(true);
//        Button btn = new Button();
        MapWidget gMap2 = new MapWidget(options3);
        panel2.add(gMap2);
        RootPanel.get("centered").add(panel2);
//      
        
        
        
        DirectionsRendererOptions options2 = DirectionsRendererOptions.newInstance();
        final DirectionsRenderer directionsDisplay = DirectionsRenderer.newInstance(options2);
        directionsDisplay.setMap(gMap2);
        
        DirectionsRequest DR = DirectionsRequest.newInstance();
        DR.setOrigin(LatLng.newInstance(40.4169,-3.7033));
        DR.setDestination(LatLng.newInstance(37.8550964,-4.7086738));
        DR.setTravelMode(TravelMode.WALKING);
        
        DirectionsWaypoint DW=DirectionsWaypoint.newInstance();
        
        DW.setLocation(LatLng.newInstance(39.8676536,-4.0098788));
        DW.setStopOver(true);

        DirectionsWaypoint DW2=DirectionsWaypoint.newInstance();
        
        DW2.setLocation(LatLng.newInstance(38.9554156,-3.9809874));
        DW2.setStopOver(true);
        
        JsArray<DirectionsWaypoint> waypoints = JsArray.createArray().cast();
        waypoints.push(DW);
        waypoints.push(DW2);
        
        
        DR.setWaypoints(waypoints);
        
        DirectionsService DS=DirectionsService.newInstance();
        
        DS.route(DR, new DirectionsResultHandler() {
			

			@Override
			public void onCallback(DirectionsResult result, DirectionsStatus status) {
				if (status == DirectionsStatus.OK) {
			          directionsDisplay.setDirections(result);
			        } else if (status == DirectionsStatus.INVALID_REQUEST) {
			        	Window.alert(status.toString());
			        } else if (status == DirectionsStatus.MAX_WAYPOINTS_EXCEEDED) {
			        	Window.alert(status.toString());
			        } else if (status == DirectionsStatus.NOT_FOUND) {
			        	Window.alert(status.toString());
			        } else if (status == DirectionsStatus.OVER_QUERY_LIMIT) {
			        	Window.alert(status.toString());
			        } else if (status == DirectionsStatus.REQUEST_DENIED) {
			        	Window.alert(status.toString());
			        } else if (status == DirectionsStatus.UNKNOWN_ERROR) {
			        	Window.alert(status.toString());
			        } else if (status == DirectionsStatus.ZERO_RESULTS) {
			        	Window.alert(status.toString());
			        }
				
			}
		});
  
        
	}

	protected void BorrarPunto(Marker Marked, int Selecctioado) {
		
		if (Window.confirm("Esta seguro de que desea borrar el punto"))
		{
			MapWidget Nulo=null;
		Marked.setMap(Nulo);
		L.removeItem(Selecctioado);
		listaMarked.remove(Selecctioado);
		}
		
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