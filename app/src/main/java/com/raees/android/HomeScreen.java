package com.raees.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {

    private BottomNavigationView bottomBar;
    ActivityResultLauncher<Intent> lockScreenLauncher;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        preferences = getSharedPreferences(constant.prefs, MODE_PRIVATE);
        initViews();

        bottomBar.getMaxItemCount();
        bottomBar.getMenu().add(Menu.NONE,121,Menu.NONE,"Home").setIcon(R.drawable.home);
        bottomBar.getMenu().add(Menu.NONE,122,Menu.NONE,"Charts").setIcon(R.drawable.chart_icon);
        bottomBar.setBackgroundResource(R.drawable.gradient1);

//        bottomBar.setVisibility(ViewStub.GONE);
        bottomBar.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    @Override
    protected void onResume() {
        checkLock();
        super.onResume();
    }

    private void checkLock(){
        if (Betplay.getIsLocked()){
            if (!preferences.getString("is_pin_asked","").equals("true") || !preferences.getString("mpin","").equals("")){
                Intent intent = new Intent(this, LockScreen.class);
                lockScreenLauncher.launch(intent);
            }
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case 121:
                    selectedFragment = new HomeFragment();
                    break;
                case 122 :
                    selectedFragment = new ChartFragment();
                    break;
            }
            // It will help to replace the
            // one fragment to other.

            if (selectedFragment == null){
               return false;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment , "inside")
                    .commit();
            return true;
        }
    };

    private void initViews() {
        bottomBar = findViewById(R.id.bottom_bar);
        lockScreenLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            checkLock();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Fragment frag = getSupportFragmentManager().findFragmentByTag("inside");
        if( frag == null) finish();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new HomeFragment());
        fragmentTransaction.commit();
    }
}