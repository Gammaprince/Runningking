package com.raees.android;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class played extends AppCompatActivity {

    protected RecyclerView recyclerview;
    ViewDialog progressDialog;
    String url ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_played);
        initView();
        url = constant.prefix + getString(R.string.games);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        apicall();

    }


    private void apicall() {

        progressDialog = new ViewDialog(played.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            ArrayList<String> date = new ArrayList<>();
                            ArrayList<String> bazar = new ArrayList<>();
                            ArrayList<String> amount = new ArrayList<>();
                            ArrayList<String> bet = new ArrayList<>();
                            ArrayList<String> status = new ArrayList<>();

                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int a= 0; jsonArray.length()>a;a++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(a);

                                date.add(jsonObject.getString("date"));
                                amount.add(jsonObject.getString("amount"));

                                bazar.add(jsonObject.getString("bazar").replace("_"," "));

                                bet.add(jsonObject.getString("number"));

                                if (jsonObject.getString("status").equals("1")){
                                    status.add("WON");
                                } else if (jsonObject.getString("is_loss").equals("1")){
                                    status.add("LOSS");
                                } else {
                                    status.add("PENDING");
                                }
                            }

                            adapterplayed rc = new adapterplayed(played.this,date,bazar,amount,bet,status);
                            recyclerview.setLayoutManager(new GridLayoutManager(played.this, 1));
                            recyclerview.setAdapter(rc);
                            rc.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.hideDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                        progressDialog.hideDialog();
                        Toast.makeText(played.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("mobile",null));


                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


    private void initView() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
    }
}
