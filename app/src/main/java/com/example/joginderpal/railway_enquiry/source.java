package com.example.joginderpal.railway_enquiry;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by joginderpal on 20-01-2017.
 */
public class source extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText ed1;
    Button b1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source);
        ed1= (EditText) findViewById(R.id.ed1);
        b1= (Button) findViewById(R.id.search_source);
        requestQueue= Volley.newRequestQueue(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,"http://api.railwayapi.com/suggest_station/name/"+ Uri.parse(ed1.getText().toString())+"/apikey/i3sspfkh/",null,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {



                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }

                );
               requestQueue.add(jsonObjectRequest);

            }
        });
    }
}
