package com.example.joginderpal.railway_enquiry;

import android.app.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;import android.support.design.widget.TabLayout;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 20-01-2017.
 */
public class Train_Route extends AppCompatActivity {

    RequestQueue requestQueue;
    TextView tx,tx1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_route);
        tx= (TextView) findViewById(R.id.tx);
        tx1= (TextView) findViewById(R.id.tx1);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
      //  recyclerView= (RecyclerView) findViewById(R.id.rv);
        requestQueue= Volley.newRequestQueue(this);
        final ProgressDialog progressDialog=ProgressDialog.show(this,"","LOADING ...",false,false);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://api.railwayapi.com/route/train/12046/apikey/i3sspfkh/", null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject=response.getJSONObject("train");
                            String name=jsonObject.getString("name");
                            String number=jsonObject.getString("number");
                            tx.setText(name);
                            tx1.setText(number);


                       /*     layoutManager=new LinearLayoutManager(Train_Route.this);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter=new RecyclerAdapter(Train_Route.this);
                            recyclerView.setAdapter(adapter);
*/

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
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new classes(), "Classes");
        adapter.addFragment(new Days(), "Days");
        adapter.addFragment(new Routes() , "Route");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }



}





