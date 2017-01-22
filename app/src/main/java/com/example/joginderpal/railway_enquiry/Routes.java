package com.example.joginderpal.railway_enquiry;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 20-01-2017.
 */
public class Routes  extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    RequestQueue requestQueue;
    List<Double> lat1;
    List<Double> lon1;
    List<String> fullname;
    List<String> arrival;
    public Routes(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue= Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_maps, container, false);
        mMapView= (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://api.railwayapi.com/route/train/12046/apikey/i3sspfkh/", null,


                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            lat1=new ArrayList<>();
                            lon1=new ArrayList<>();
                            fullname=new ArrayList<>();
                            arrival=new ArrayList<>();
                            JSONArray jsonArray=response.getJSONArray("route");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                double lat=jsonObject.getDouble("lat");
                                lat1.add(lat);
                                double lng=jsonObject.getDouble("lng");
                                lon1.add(lng);
                                String full=jsonObject.getString("fullname");
                                fullname.add(full);
                                String arr=jsonObject.getString("scharr");
                                arrival.add(arr);
                            }
                            mMapView.onResume();
                            try {
                                MapsInitializer.initialize(getActivity().getApplicationContext());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            mMapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap mMap) {
                                    googleMap = mMap;

                                    // For showing a move to my location button
                                    //  googleMap.setMyLocationEnabled(true);

                                    // For dropping a marker at a point on the Map
                                       LatLng sydney = new LatLng(lat1.get(0),lon1.get(0));

                                    // For zooming automatically to the location of the marker
                                      CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                                      googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                    for (int i=0;i<lat1.size()-1;i++){
                                        googleMap.addMarker(new MarkerOptions().position(new LatLng(lat1.get(i),lon1.get(i))).title("Address : "+fullname.get(i)).snippet("Arrival : "+arrival.get(i)));
                                        PolylineOptions rectoptions=new PolylineOptions().add(new LatLng(lat1.get(i),lon1.get(i))).add(new LatLng(lat1.get(i+1),lon1.get(i+1))).color(Color.BLUE).geodesic(true);

                                        mMap.addPolyline(rectoptions);
                                    }
                                    googleMap.addMarker(new MarkerOptions().position(new LatLng(lat1.get(lat1.size()-1),lon1.get(lat1.size()-1))).title("Address : "+fullname.get(lat1.size()-1)).snippet("Arrival : "+arrival.get(lat1.size()-1)));
                                }
                            });




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }


        );

       requestQueue.add(jsonObjectRequest);


        return v;
    }

}
