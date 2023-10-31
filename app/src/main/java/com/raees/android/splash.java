package com.raees.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class splash extends AppCompatActivity {

    SharedPreferences.Editor myEdit;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        url = constant.prefix + getString(R.string.getconfig);
        Intent intent = new Intent(this,login.class);
//        startActivity(intent);


// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit.commit();
        apicall();
    }


    private void apicall() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            SharedPreferences.Editor editor = getSharedPreferences(constant.prefs, MODE_PRIVATE).edit();

                            if (jsonObject1.has("redirect3")){
                                myEdit.putInt("status", 0).apply();
//                                Log.d("Matka","redirect");
//                                startActivity(new Intent(splash.this, charts.class).putExtra("href","https://nowotech.in/"));
//                                return;
                            }else{
                                myEdit.putInt("status",1).apply();
                            }

                            Log.d("Matka","Not redirect");
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int a= 0; jsonArray.length()>a;a++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(a);
                                editor.putString(jsonObject.getString("data_key"), jsonObject.getString("data")).apply();
                            }

                            FirebaseMessaging.getInstance().subscribeToTopic("all")
                                    .addOnCompleteListener(task -> {
                                        if (getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("mobile",null) != null) {
                                            Intent in = new Intent(getApplicationContext(), HomeScreen.class);
                                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(in);
                                            finish();
                                        } else {
                                            Intent in = new Intent(getApplicationContext(), signup.class);
                                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(in);
                                            finish();
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

                        error.printStackTrace();

                        Toast.makeText(splash.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

}
