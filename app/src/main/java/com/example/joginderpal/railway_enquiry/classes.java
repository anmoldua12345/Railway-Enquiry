package com.example.joginderpal.railway_enquiry;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 20-01-2017.
 */
public class classes  extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    RequestQueue requestQueue;
    List<String> avail;
    List<String> classcod;

    public classes(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_classes, container, false);
        recyclerView= (RecyclerView) v.findViewById(R.id.rv);
        final ProgressDialog progressDialog=ProgressDialog.show(getContext(),"","Loading",false,false);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://api.railwayapi.com/route/train/12046/apikey/i3sspfkh/", null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            progressDialog.dismiss();
                            avail=new ArrayList<>();
                            classcod=new ArrayList<>();
                            JSONObject jsonObject=response.getJSONObject("train");
                            JSONArray jsonArray=jsonObject.getJSONArray("classes");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject a=jsonArray.getJSONObject(i);
                                String available=a.getString("available");
                                avail.add(available);
                                String classcode=a.getString("class-code");
                                classcod.add(classcode);
                            }

                            layoutManager=new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            adapter=new RecyclerAdapter(avail,classcod);
                            recyclerView.setAdapter(adapter);

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
