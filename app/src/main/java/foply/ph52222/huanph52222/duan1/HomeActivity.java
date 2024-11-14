package foply.ph52222.huanph52222.duan1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import foply.ph52222.huanph52222.duan1.Adapter.BannerAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPager bannerViewPager;
    private BannerAdapter bannerAdapter;
    private List<Integer> bannerImages;
    private int currentPage = 0;
    private Handler handler = new Handler();

    // Map to store menu item IDs and corresponding fragments
    private final Map<Integer, Fragment> fragmentMap = new HashMap<>();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the fragment map with menu item IDs and fragments
        initializeFragmentMap();

        // Set up the ViewPager for the banner
        bannerViewPager = findViewById(R.id.bannerViewPager);
        bannerImages = Arrays.asList(R.drawable.ban3, R.drawable.ban2, R.drawable.baner4);
        bannerAdapter = new BannerAdapter(this, bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);
        handler.postDelayed(slideRunnable, 3000);

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        loadFragment(fragmentMap.get(R.id.home)); // Load HomeFragment by default

        // Handle BottomNavigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = fragmentMap.get(item.getItemId());
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            } else {
                Log.w("HomeActivity", "Unknown menu item selected: " + item.getItemId());
                return false;
            }
        });
    }


    private void initializeFragmentMap() {
        fragmentMap.put(R.id.home, new HomeFragment());
        fragmentMap.put(R.id.giohang, new AddShopFragment());
        fragmentMap.put(R.id.thongbao, new Notice());
        fragmentMap.put(R.id.account, new ProfileFragment());
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private final Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentPage == bannerImages.size()) {
                currentPage = 0;
            }
            bannerViewPager.setCurrentItem(currentPage++, true);
            handler.postDelayed(this, 3000);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(slideRunnable, 3000);
    }
}
