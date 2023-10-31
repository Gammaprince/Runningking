package com.raees.android;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {

    protected ScrollView scrollView;
    protected TextView balance, balance_home;
    protected CardView single;
    protected CardView jodi;
    protected CardView singlepatti;
    protected CardView doublepatti;
    protected CardView tripepatti;
    protected CardView halfsangam;
    protected CardView fullsangam;
    protected latonormal hometext;
    protected CardView crossing;
    protected CardView exit;
    protected CardView logout;
    protected CardView refresh;
    protected TextView supportno;
    protected CardView support;
    LinearLayout wallet_block;

    RecyclerView recyclerview;
    SharedPreferences preferences;
    String url;
    String is_gateway = "0";
    SwipeRefreshLayout swiperefresh;

    ImageView loading_gif;
    private ImageView loadingGif;
    private RelativeLayout toolbar;
    private CircleImageView appIcon;
    private ImageView timeInfo;
    private latobold top;
    private RelativeLayout back;
    private ImageView coin;
    private latobold play_starline;

    ViewDialog viewDialog2;

    SliderView sliderView;
    private SliderAdapter adapter;
    private ImageView whatsappIcon2;
    private RelativeLayout Back;
    private ImageView walletIcon;
    private latonormal balanceHome;
    private LinearLayout walletBlock;
    private LinearLayout walletView;
    private latobold homeTitle;
    private latonormal homeTag;
    private latobold depositButton;
    private latobold withdrawButton;
    private SliderView imageSlider;
    private LinearLayout userProfile;
    private LinearLayout depositMoney;
    private LinearLayout withdrawMoney;
    private LinearLayout openChart;
    private LinearLayout refer;
    private LinearLayout share;
    private latobold playStarline;
    private LinearLayout root;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        url = constant.prefix + getString(R.string.home);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().clear().apply();
                Intent in = new Intent(getActivity(), login.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                getActivity().finish();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();
                apicall();
            }
        });


        if (preferences.getString("wallet", null) != null) {
            balance.setText(preferences.getString("wallet", null));
        } else {
            balance.setText("Loading");
        }

        if (preferences.getString("homeline", null) != null) {
            if (preferences.getString("homeline", "").equals("")) {
                hometext.setVisibility(View.GONE);
            } else {
                hometext.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    hometext.setText(Html.fromHtml(preferences.getString("homeline", ""), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    hometext.setText(Html.fromHtml(preferences.getString("homeline", null)));
                }
            }
        } else {
            hometext.setText("Loading...");
        }


        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), bazar.class).putExtra("game", "single").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        jodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), bazar.class).putExtra("game", "jodi").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        crossing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), bazar.class).putExtra("game", "crossing").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        singlepatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), bazar.class).putExtra("game", "singlepatti").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        doublepatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), bazar.class).putExtra("game", "doublepatti").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        tripepatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), bazar.class).putExtra("game", "tripepatti").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        halfsangam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), bazar.class).putExtra("game", "halfsangam").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        fullsangam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), bazar.class).putExtra("game", "fullsangam").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        crossing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), bazar.class).putExtra("game", "crossing").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "Oxygen-Bold.ttf");

        PrimaryDrawerItem home = new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.home).withIdentifier(999).withTypeface(face);
        PrimaryDrawerItem account = new PrimaryDrawerItem().withName("My Profile").withIcon(R.drawable.user_icon).withIdentifier(1).withTypeface(face);
        PrimaryDrawerItem charts = new PrimaryDrawerItem().withName("Charts").withIdentifier(101).withIcon(R.drawable.chart_new).withTypeface(face);
        PrimaryDrawerItem rate = new PrimaryDrawerItem().withName("Game Rate").withIdentifier(2).withIcon(R.drawable.star_rate).withTypeface(face);
        PrimaryDrawerItem earn = new PrimaryDrawerItem().withName("Refer and Earn").withIcon(R.drawable.exchange).withIdentifier(21).withTypeface(face);
        PrimaryDrawerItem notice = new PrimaryDrawerItem().withName("Notice").withIcon(R.drawable.notice_new).withIdentifier(3).withTypeface(face);
//        PrimaryDrawerItem deposit = new PrimaryDrawerItem().withName("Deposit").withIcon(R.drawable.deposit_new).withIdentifier(4).withTypeface(face);
//        PrimaryDrawerItem withdraw = new PrimaryDrawerItem().withName("Withdrawal").withIcon(R.drawable.withdraw_new).withIdentifier(41).withTypeface(face);
        PrimaryDrawerItem ledger = new PrimaryDrawerItem().withName("Winning History").withIcon(R.drawable.trophy).withIdentifier(6).withTypeface(face);
        PrimaryDrawerItem transaction = new PrimaryDrawerItem().withName("Transaction History").withIcon(R.drawable.wallet_color_icon).withIdentifier(8).withTypeface(face);
        PrimaryDrawerItem played = new PrimaryDrawerItem().withName("Played Match").withIcon(R.drawable.history).withIdentifier(9).withTypeface(face);
        PrimaryDrawerItem howto = new PrimaryDrawerItem().withName("How to Play").withIcon(R.drawable.question).withIdentifier(10).withTypeface(face);
        PrimaryDrawerItem share = new PrimaryDrawerItem().withName("Share").withIcon(R.drawable.share_icon).withIdentifier(11).withTypeface(face);
        PrimaryDrawerItem logout = new PrimaryDrawerItem().withName("Logout").withIcon(R.drawable.logout_icon).withIdentifier(7).withTypeface(face);
        PrimaryDrawerItem settings = new PrimaryDrawerItem().withName("App Setting").withIcon(R.drawable.settings).withIdentifier(71).withTypeface(face);



        final Drawer drawer = new DrawerBuilder()
                .withHeaderDivider(true)
                .withActivity(getActivity())
                .withSliderBackgroundColor(getResources().getColor(R.color.md_white_1000))
                .withTranslucentStatusBar(true)
                .withHeader(R.layout.header)
                .withFooter(R.layout.footer)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(
                        home, played, ledger, earn,rate, howto, transaction, settings, share
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.equals(71)) {
                            startActivity(new Intent(getActivity(), settings.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        if (drawerItem.equals(1)) {
                            startActivity(new Intent(getActivity(), profile.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        if (drawerItem.equals(101)) {
                            startActivity(new Intent(getActivity(), chart_menu.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        if (drawerItem.equals(2)) {
                            startActivity(new Intent(getActivity(), rate.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        if (drawerItem.equals(21)) {
                            startActivity(new Intent(getActivity(), earn.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        if (drawerItem.equals(3)) {
                            startActivity(new Intent(getActivity(), notice.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        if (drawerItem.equals(4)) {
                            if (is_gateway.equals("1")) {
                                startActivity(new Intent(getActivity(), deposit_money.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                openWhatsApp();
                            }
                        }
                        if (drawerItem.equals(41)) {
                            openWhatsApp();
                        }
                        if (drawerItem.equals(10)) {
                            startActivity(new Intent(getActivity(), howot.class));
                        }
                        if (drawerItem.equals(11)) {

                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT,
                                    "Download " + getString(R.string.app_name) + " and earn coins at home, Download link - " + constant.link);
                            sendIntent.setType("text/plain");
                            startActivity(sendIntent);
                        }
                        if (drawerItem.equals(7)) {
                            preferences.edit().clear().apply();
                            Intent in = new Intent(getActivity(), login.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);
                            getActivity().finish();
                        }
                        if (drawerItem.equals(6)) {
                            startActivity(new Intent(getActivity(), ledger.class));
                        }
                        if (drawerItem.equals(8)) {
                            startActivity(new Intent(getActivity(), transactions.class));
                        }
                        if (drawerItem.equals(9)) {
                            startActivity(new Intent(getActivity(), played.class));
                        }

                        return false;
                    }
                })
                .build();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int val = sharedPreferences.getInt("status",0);
        int log = sharedPreferences.getInt("log",0);
        if(val==0 || log==0){
            wallet_block.setVisibility(View.GONE);
            drawer.removeItem(rate.getIdentifier());
            drawer.removeItem(earn.getIdentifier());
            drawer.removeItem(howto.getIdentifier());
            drawer.removeItem(transaction.getIdentifier());
            drawer.removeItem(ledger.getIdentifier());
            drawer.removeItem(played.getIdentifier());
        }

        TextView name = drawer.getHeader().findViewById(R.id.name);
        name.setText(preferences.getString("name", ""));
        TextView mobile = drawer.getHeader().findViewById(R.id.mobile);
        mobile.setText(preferences.getString("mobile", ""));


        drawer.getFooter().findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().clear().apply();
                Intent in = new Intent(getActivity(), login.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                getActivity().finish();
            }
        });


//        drawer.getHeader().findViewById(R.id.deposit_money).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (is_gateway.equals("1")) {
//                    startActivity(new Intent(getActivity(), wallet.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            .putExtra("action","deposit"));
//                } else {
//                    openWhatsApp();
//                }
//            }
//        });
//
//        drawer.getHeader().findViewById(R.id.withdraw_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), withdraw.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//            }
//        });
        drawer.getHeader().findViewById(R.id.view_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), profile.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen()) {
                    drawer.closeDrawer();
                } else {
                    drawer.openDrawer();
                }
            }
        });

        return view;

    }


    private void apicall() {

        viewDialog2 = new ViewDialog((AppCompatActivity) getActivity());
//        viewDialog2.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        try {

                            JSONObject jsonObject1 = new JSONObject(response);

                            if (jsonObject1.getString("active").equals("0")) {
                                Toast.makeText(getActivity(), "Your account temporarily disabled by admin", Toast.LENGTH_SHORT).show();

                                preferences.edit().clear().apply();
                                Intent in = new Intent(getActivity(), login.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                getActivity().finish();
                            }

                            if (!jsonObject1.getString("session").equals(getActivity().getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", ""))) {
                                Toast.makeText(getActivity(), "Session expired ! Please login again", Toast.LENGTH_SHORT).show();

                                preferences.edit().clear().apply();
                                Intent in = new Intent(getActivity(), login.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                getActivity().finish();
                            }

                            balance.setText(jsonObject1.getString("wallet"));
                            balance_home.setText(jsonObject1.getString("wallet"));

                            if (jsonObject1.getString("homeline").equals("")) {
                                hometext.setVisibility(View.GONE);
                            } else {
                                hometext.setVisibility(View.VISIBLE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    hometext.setText(Html.fromHtml(jsonObject1.getString("homeline"), Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    hometext.setText(Html.fromHtml(jsonObject1.getString("homeline")));
                                }
                            }


                            ArrayList<String> name = new ArrayList<>();
                            ArrayList<String> result = new ArrayList<>();


                            ArrayList<String> is_open = new ArrayList<>();
                            ArrayList<String> open_time = new ArrayList<>();
                            ArrayList<String> close_time = new ArrayList<>();
                            ArrayList<String> open_av = new ArrayList<>();

                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(a);

                                open_time.add(jsonObject.getString("open_time"));
                                close_time.add(jsonObject.getString("close_time"));
                                name.add(jsonObject.getString("market"));
                                result.add(jsonObject.getString("result"));
                                is_open.add(jsonObject.getString("is_open"));
                                open_av.add(jsonObject.getString("is_close"));

                            }


                            adapter_result rc = new adapter_result(getActivity(), name, result, is_open, open_time, close_time, open_av);
                            recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                            recyclerview.setAdapter(rc);
                            rc.notifyDataSetChanged();

                            adapter = new SliderAdapter(getActivity());

                            if (jsonObject1.has("images")) {
                                JSONArray jsonArrayx = jsonObject1.getJSONArray("images");
                                for (int a = 0; a < jsonArrayx.length(); a++) {
                                    JSONObject jsonObject = jsonArrayx.getJSONObject(a);

                                    SliderItem sliderItem1 = new SliderItem();
                                    sliderItem1.setImageUrl(constant.project_root + "admin/" + jsonObject.getString("image"));
                                    adapter.addItem(sliderItem1);

                                }


                                sliderView.setSliderAdapter(adapter);
                            } else{
                                sliderView.setVisibility(View.GONE);
                            }


                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("wallet", jsonObject1.getString("wallet")).apply();
                            editor.putString("winning", jsonObject1.getString("winning")).apply();
                            editor.putString("bonus", jsonObject1.getString("bonus")).apply();
                            editor.putString("homeline", jsonObject1.getString("homeline")).apply();
                            editor.putString("code", jsonObject1.getString("code")).apply();
                            editor.putString("is_gateway", jsonObject1.getString("gateway")).apply();
                            editor.putString("whatsapp", jsonObject1.getString("whatsapp")).apply();
                            is_gateway = jsonObject1.getString("gateway");


                            Log.e("ss", "sd");

                            if (swiperefresh.isRefreshing()) {
                                swiperefresh.setRefreshing(false);
                            }


                            if (swiperefresh.getVisibility() == View.GONE) {
                                Glide.with(getActivity()).load(R.drawable.logo).into(loading_gif);
                                loading_gif.setVisibility(View.GONE);
                                swiperefresh.setVisibility(View.VISIBLE);
                            }

//                            viewDialog2.hideDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            viewDialog2.hideDialog();
                            Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

//                        viewDialog2.hideDialog();
                        Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("app", "kalyanpro");
                params.put("mobile", preferences.getString("mobile", null));
                params.put("session", getActivity().getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


    @Override
    public void onResume() {
        apicall();
        super.onResume();
    }

    private void openWhatsApp() {
        String url = constant.getWhatsapp(getActivity());

        Uri uri = Uri.parse(url);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(sendIntent);
    }


    private void initViews(View view) {

        preferences = getActivity().getSharedPreferences(constant.prefs, MODE_PRIVATE);
        balance = view.findViewById(R.id.balance);
        hometext = view.findViewById(R.id.hometext);
        root = view.findViewById(R.id.gradientM);
        single = view.findViewById(R.id.single);
        jodi = view.findViewById(R.id.jodi);
        crossing = view.findViewById(R.id.crossing);
        singlepatti = view.findViewById(R.id.singlepatti);
        doublepatti = view.findViewById(R.id.doublepatti);
        tripepatti = view.findViewById(R.id.tripepatti);
        halfsangam = view.findViewById(R.id.halfsangam);
        fullsangam = view.findViewById(R.id.fullsangam);
        exit = view.findViewById(R.id.exit);
        logout = view.findViewById(R.id.logout);
        refresh = view.findViewById(R.id.refresh);
        scrollView = view.findViewById(R.id.scrollView);
        recyclerview = view.findViewById(R.id.recyclerview);
        balance_home = view.findViewById(R.id.balance_home);
        wallet_block = view.findViewById(R.id.wallet_block);

        swiperefresh = view.findViewById(R.id.swiperefresh);
        loading_gif = view.findViewById(R.id.loading_gif);
        back = view.findViewById(R.id.back);
        coin = view.findViewById(R.id.coin);
        play_starline = view.findViewById(R.id.play_starline);

        play_starline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), starline_markets.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        wallet_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), wallet.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        sliderView = view.findViewById(R.id.imageSlider);
        swiperefresh.setVisibility(View.GONE);
        Glide.with(getActivity()).load(R.drawable.loading_animation).into(loading_gif);
        loading_gif.setVisibility(View.VISIBLE);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apicall();
            }
        });

        hometext.setMovementMethod(LinkMovementMethod.getInstance());

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        toolbar = view.findViewById(R.id.toolbar);
        appIcon = view.findViewById(R.id.app_icon);
        timeInfo = view.findViewById(R.id.time_info);
        top = view.findViewById(R.id.top);


        top.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        top.setMarqueeRepeatLimit(-1);
        top.setSingleLine(true);
        top.setSelected(true);
        whatsappIcon2 = view.findViewById(R.id.whatsapp_icon2);
        Back = view.findViewById(R.id._back);
        walletIcon = view.findViewById(R.id.wallet_icon);
        walletView = view.findViewById(R.id.wallet_view);
        homeTitle = view.findViewById(R.id.home_title);
        homeTag = view.findViewById(R.id.home_tag);
        depositButton = view.findViewById(R.id.deposit_button);
        withdrawButton = view.findViewById(R.id.withdraw_button);
        userProfile = view.findViewById(R.id.user_profile);
        depositMoney = view.findViewById(R.id.deposit_money);
        withdrawMoney = view.findViewById(R.id.withdraw_money);
        openChart = view.findViewById(R.id.open_chart);
        refer = view.findViewById(R.id.refer);
        share = view.findViewById(R.id.share);
        supportno = view.findViewById(R.id.supportno);
        support = view.findViewById(R.id.support);

        depositMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),wallet.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("action","deposit")
                );
            }
        });

        withdrawMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),withdraw.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            }
        });


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),profile.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            }
        });


        openChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),rate.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            }
        });


        refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),earn.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            }
        });

        whatsappIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Download " + getString(R.string.app_name) + " and earn coins at home, Download link - https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int val = sharedPreferences.getInt("status",0);
        int log = sharedPreferences.getInt("log",0);

        if(val==0 || log==0) {
            depositMoney.setVisibility(View.GONE);
            withdrawMoney.setVisibility(View.GONE);
            openChart.setVisibility(View.GONE);
            refer.setVisibility(View.GONE);
        }
    }
}
