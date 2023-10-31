package com.raees.android;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class wallet extends AppCompatActivity {

    private latobold totalBalance;
    private LinearLayout whatsapp;
    private latobold addMoney;
    private latobold depositBalance;
    private ImageView depositInfo;
    private latobold winnigBalance;
    private ImageView winningInfo;
    private latobold cashBalance;
    private ImageView cashInfo;
    private CardView transactionHistory;
    private EditText amount;
    private latobold depositMoney;

    ViewDialog progressDialog;
    String url = constant.prefix + "get_wallet.php";
    String url2 = constant.prefix + "get_payment2.php";
    String url3 = constant.prefix + "upi_payment2.php";
    String _amount = "0";
    final int UPI_PAYMENT = 0;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    String hash, hashKey;
    String package_name = "";
    private RelativeLayout addCash;
    private ImageView back;
    private ImageView qrCode;
    private ImageView backAdd;
    private ImageView walletColorIcon;
    private latonormal newBalance;
    private latonormal depost500;
    private latonormal depost1000;
    private LinearLayout paytmIcon;
    private LinearLayout gpayIcon;
    private LinearLayout phonepeIcon;
    String selectedApp;
    private LinearLayout upis;
    private latobold cryptoText;
    private LinearLayout coinPayments;
    private LinearLayout razorpay;
    private String qrUrl = constant.project_root+"admin/qr/qr.jpeg";
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 1;

//    private LinearLayout paytmGateway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initViews();
//        Picasso.get().load(qrUrl).memoryPolicy(MemoryPolicy.NO_CACHE).into((ImageView) findViewById(R.id.qrcode));
//        findViewById(R.id.donwloadQR).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Picasso.get().load(qrUrl).memoryPolicy(MemoryPolicy.NO_CACHE).into(new Target() {
//
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        try {
//                            File root = Environment.getExternalStorageDirectory();
//                            if (root.canWrite()) {
//                                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                                String fileName = "Image_" + timeStamp + ".jpg";
//                                File file = new File(root.getAbsolutePath() + "/Download", fileName);
//                                FileOutputStream out = new FileOutputStream(file);
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                                out.flush();
//                                out.close();
//                                Toast.makeText(wallet.this, "QR Code has been Downloaded , Check in your Gallery", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            Toast.makeText(wallet.this, "Can't downloaded", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                        Toast.makeText(wallet.this, "working", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
////                        Toast.makeText(wallet.this, "working", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

//        Glide.with(this).load(qrUrl).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into((ImageView) findViewById(R.id.qrcode));
//
//        findViewById(R.id.donwloadQR).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Glide.with(getApplicationContext()).asBitmap().load(qrUrl).into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                        try {
//                            File root = Environment.getExternalStorageDirectory();
//                            if (root.canWrite()) {
//                                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                                String fileName = "Image_" + timeStamp + ".jpg";
//                                File file = new File(root.getAbsolutePath() + "/Download", fileName);
//                                FileOutputStream out = new FileOutputStream(file);
//                                resource.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                                out.flush();
//                                out.close();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });




        ImageView qrcodeImageView = findViewById(R.id.qrcode);
        findViewById(R.id.donwloadQR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage();
            }
        });

        // Load the image using Glide and disable caching
        Glide.with(this)
                .load(qrUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(qrcodeImageView);



        findViewById(R.id.whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = constant.getWhatsapp(getApplicationContext());

                Uri uri = Uri.parse(url);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(sendIntent);
            }
        });
        findViewById(R.id.whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = constant.getWhatsapp(getApplicationContext());

                Uri uri = Uri.parse(url);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(sendIntent);
            }
        });
        back.setOnClickListener(v -> finish());

        backAdd.setOnClickListener(v -> finish());

        if (getIntent().hasExtra("action")) {
            if (getIntent().getStringExtra("action").equals("deposit")) {
                addCash.setVisibility(View.VISIBLE);
            }
        }


//        paytmGateway.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(wallet.this, "working", Toast.LENGTH_SHORT).show();
//                if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")) {
//                    amount.setError("Enter points");
//                    return;
//                } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"))) {
//                    amount.setError("Enter points above " + getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"));
//                    return;
//                }
//
//                startActivity(new Intent(wallet.this,webview.class)
//                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        .putExtra("amount",amount.getText().toString())
//                        .putExtra("gateway","paytm"));
//            }
//        });


        razorpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")) {
                    amount.setError("Enter points");
                    return;
                } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"))) {
                    amount.setError("Enter points above " + getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"));
                    return;
                }

                startActivity(new Intent(wallet.this,webview.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("amount",amount.getText().toString())
                        .putExtra("gateway","razorpay"));
            }
        });

        coinPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")) {
                    amount.setError("Enter points");
                    return;
                } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"))) {
                    amount.setError("Enter points above " + getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"));
                    return;
                }

                startActivity(new Intent(wallet.this,webview.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("amount",amount.getText().toString())
                        .putExtra("gateway","Coinpayments"));
            }
        });
        paytmIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")) {
                    amount.setError("Enter points");
                    return;
                } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"))) {
                    amount.setError("Enter points above " + getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"));
                    return;
                }

                apicall3("paytm", "paytm");

            }
        });

        gpayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")) {
                    amount.setError("Enter points");
                    return;
                } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"))) {
                    amount.setError("Enter points above " + getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"));
                    return;
                }

                apicall3("gpay", "gpay");
            }
        });
        checkAndRequestStoragePermission();
        phonepeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")) {
                    amount.setError("Enter points");
                    return;
                } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"))) {
                    amount.setError("Enter points above " + getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("min_deposit", "10"));
                    return;
                }

                apicall3("phonepe", "phonepe");
            }
        });


        depositInfo.setOnClickListener(v -> {


            AlertDialog.Builder builder1 = new AlertDialog.Builder(wallet.this);
            LayoutInflater factory = LayoutInflater.from(wallet.this);
            View market_time = factory.inflate(R.layout.msg_popup, null);
            TextView close = market_time.findViewById(R.id.close);
            TextView open_time_view = market_time.findViewById(R.id.msg);
            TextView close_time_view = market_time.findViewById(R.id.title);
            builder1.setView(market_time);
            builder1.setCancelable(true);
            AlertDialog alert11 = builder1.create();
            alert11.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            open_time_view.setText("AMOUNT ADDED");
            close_time_view.setText("This is the amount that you have added in your wallet through payment gateway to play games");

            alert11.show();

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert11.dismiss();
                }
            });

        });

        winningInfo.setOnClickListener(v -> {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(wallet.this);
            LayoutInflater factory = LayoutInflater.from(wallet.this);
            View market_time = factory.inflate(R.layout.msg_popup, null);
            TextView close = market_time.findViewById(R.id.close);
            TextView open_time_view = market_time.findViewById(R.id.msg);
            TextView close_time_view = market_time.findViewById(R.id.title);
            builder1.setView(market_time);
            builder1.setCancelable(true);
            AlertDialog alert11 = builder1.create();
            alert11.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            open_time_view.setText("WINNINGS");
            close_time_view.setText("This is your winning from various games in the application, you can withdraw this money directly to bank account");

            alert11.show();

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert11.dismiss();
                }
            });

        });

        cashInfo.setOnClickListener(v -> {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(wallet.this);
            LayoutInflater factory = LayoutInflater.from(wallet.this);
            View market_time = factory.inflate(R.layout.msg_popup, null);
            TextView close = market_time.findViewById(R.id.close);
            TextView open_time_view = market_time.findViewById(R.id.msg);
            TextView close_time_view = market_time.findViewById(R.id.title);
            builder1.setView(market_time);
            builder1.setCancelable(true);
            AlertDialog alert11 = builder1.create();
            alert11.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            open_time_view.setText("CASH BONUS");
            close_time_view.setText("You earn cash bonus with refer and earn and other various promotional events in the application, you can user upto 10% of CASH BONUS balance while playing a game");

            alert11.show();

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert11.dismiss();
                }
            });

        });

        transactionHistory.setOnClickListener(v -> startActivity(new Intent(wallet.this, transactions.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));

        addMoney.setOnClickListener(v -> addCash.setVisibility(View.VISIBLE));

        depositMoney.setOnClickListener(v -> {

            if (amount.getText().toString().isEmpty()) {
                amount.setError("Enter amount");
            } else {
                //  apicall3();
            }

        });

        depost500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("1000");
            }
        });

        depost1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("2000");
            }
        });

        apicall();
    }



    private void checkAndRequestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE_PERMISSION);
        } else {
            // Permission already granted, proceed with image download
//            downloadImage();
        }
    }

    private void downloadImage() {

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(qrUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        try {
                            File file = new File(path, "QRCode.jpg");
                            FileOutputStream out = new FileOutputStream(file);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                            Toast.makeText(wallet.this, "QR Code has been downloaded , Go to Download folder to see Image", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(wallet.this, "Image can't download", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with image download
                downloadImage();
            } else {
                Toast.makeText(this, "Permission denied. Cannot download image.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
//        if (addCash.getVisibility() == View.VISIBLE) {
//            addCash.setVisibility(View.GONE);
//            return;
//        }
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_PAYMENT) {
            if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.d("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(wallet.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(wallet.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                apicall2();
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(wallet.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                // apicall2();
            } else {
                Toast.makeText(wallet.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(wallet.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }


    private void apicall() {

        progressDialog = new ViewDialog(wallet.this);
        progressDialog.showDialog();

        hashKey = randomString(10);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String response = null;

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response1 -> {
                    Log.e("edsa", "efsdc" + response1);
                    progressDialog.hideDialog();
                    try {
                        JSONObject jsonObject1 = new JSONObject(response1);

                        newBalance.setText(jsonObject1.getString("total") + " ₹");

                        totalBalance.setText(jsonObject1.getString("total") + " ₹");
                        depositBalance.setText(jsonObject1.getString("wallet") + " ₹");
                        winnigBalance.setText(jsonObject1.getString("winning") + " ₹");
                        cashBalance.setText(jsonObject1.getString("bonus") + " ₹");

                        ArrayList<String> gateways = new ArrayList<>();

                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(a);
                            gateways.add(jsonObject.getString("name").toLowerCase(Locale.ROOT));
                        }

//                        if (gateways.contains("paytm")) {
//                            paytmGateway.setVisibility(View.VISIBLE);
//                        }

                        if (gateways.contains("razorpay")) {
                            razorpay.setVisibility(View.VISIBLE);
                        }



                        if (gateways.contains("upi")) {
                            upis.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.hideDialog();
                    }
                },
                error -> {
                    error.printStackTrace();
                    progressDialog.hideDialog();
                    Toast.makeText(wallet.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("mobile", null));
                params.put("session", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));
                params.put("amount", amount.getText().toString());
                params.put("hash_key", hashKey);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void apicall3(String upiApp, String type) {

        progressDialog = new ViewDialog(wallet.this);
        progressDialog.showDialog();

        hashKey = randomString(10);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String response = null;

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url3,
                response1 -> {
                    Log.e("edsa", "efsdc" + response1);
                    progressDialog.hideDialog();
                    try {
                        JSONObject jsonObject1 = new JSONObject(response1);

                        if (jsonObject1.getString("success").equals("1")) {
                            hash = jsonObject1.getString("hash");
                            payUsingUpi(amount.getText().toString(), getString(R.string.upi_name), "Adding coins to wallet", upiApp);
                        } else {
                            Toast.makeText(wallet.this, jsonObject1.getString("msg"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.hideDialog();
                    }
                },
                error -> {
                    error.printStackTrace();
                    progressDialog.hideDialog();
                    Toast.makeText(wallet.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("mobile", null));
                params.put("session", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));
                params.put("amount", amount.getText().toString());
                params.put("hash_key", hashKey);
                params.put("type", type);


                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void apicall2() {

        progressDialog = new ViewDialog(wallet.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String response = null;

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
                response1 -> {
                    Log.e("edsa", "efsdc" + response1);
                    progressDialog.hideDialog();
                    try {
                        JSONObject jsonObject1 = new JSONObject(response1);

                        if (jsonObject1.getString("success").equals("0")) {
                            new androidx.appcompat.app.AlertDialog.Builder(wallet.this)
                                    .setTitle("Payment Received")
                                    .setMessage("We received your payment successfully, We will update your wallet balance in sometime")
                                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(getApplicationContext(), HomeScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                            finish();
                                        }
                                    })
                                    .show();

                        } else {
                            Toast.makeText(wallet.this, "Coins added to wallet", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.hideDialog();
                    }
                },
                error -> {
                    error.printStackTrace();
                    progressDialog.hideDialog();
                    Toast.makeText(wallet.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("mobile", null));
                params.put("hash_key", hashKey);
                params.put("hash", hash);
                params.put("amount", amount.getText().toString());
                params.put("session", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


    void payUsingUpi(String amount, String name, String note, String upiApp) {


        selectedApp = upiApp;


        String upi_id = getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("upi", "");
        String mcc = getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("merchant", "");


        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upi_id)
                .appendQueryParameter("mc", mcc)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount + ".00")
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("tr", "WHATSAPP_QR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        //    Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        Intent chooser = new Intent(Intent.ACTION_VIEW);
        chooser.setData(uri);

        switch (upiApp) {
            case "gpay":
                package_name = getString(R.string.gpay);
                break;
            case "paytm":
                package_name = getString(R.string.paytm);
                break;
            case "phonepe":
                package_name = getString(R.string.phonepe);
                break;
        }

        chooser.setPackage(package_name);

        PackageManager pm = getPackageManager();

        if (!isPackageInstalled(package_name, pm)) {
            new androidx.appcompat.app.AlertDialog.Builder(wallet.this)
                    .setTitle(upiApp + " Not Installed")
                    .setMessage("Your device don't have application installed, Do you want to download now ?")
                    .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + package_name)));
                            } catch (ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + package_name)));
                            }
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Later", null)
                    .show();

            return;
        }
        // check if intent resolves
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(wallet.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }


    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        Log.e("checkingPackage", ":" + packageName);
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void initViews() {
//        Toast.makeText(this, "In act", Toast.LENGTH_SHORT).show();
        totalBalance = findViewById(R.id.total_balance);
        whatsapp = findViewById(R.id.whatsapp);
        addMoney = findViewById(R.id.add_money);
        depositBalance = findViewById(R.id.deposit_balance);
        depositInfo = findViewById(R.id.deposit_info);
        winnigBalance = findViewById(R.id.winnig_balance);
        winningInfo = findViewById(R.id.winning_info);
        cashBalance = findViewById(R.id.cash_balance);
        cashInfo = findViewById(R.id.cash_info);
        transactionHistory = findViewById(R.id.transaction_history);
        amount = findViewById(R.id.amount);
        depositMoney = findViewById(R.id.deposit_money);
        addCash = findViewById(R.id.addCash);
        back = findViewById(R.id.back);
        backAdd = findViewById(R.id.back_add);
        walletColorIcon = findViewById(R.id.wallet_color_icon);
        newBalance = findViewById(R.id.new_balance);
        depost500 = findViewById(R.id.depost_500);
        depost1000 = findViewById(R.id.depost_1000);
        paytmIcon = findViewById(R.id.paytm_icon);
        gpayIcon = findViewById(R.id.gpay_icon);
        phonepeIcon = findViewById(R.id.phonepe_icon);
        upis = findViewById(R.id.upis);
        cryptoText = findViewById(R.id.crypto_text);
        coinPayments = findViewById(R.id.coin_payments);
        cryptoText = findViewById(R.id.crypto_text);
        razorpay = findViewById(R.id.razorpay);
        cryptoText = findViewById(R.id.crypto_text);
//        paytmGateway = findViewById(R.id.paytm_gateway);
    }
}