package tsekhmeistruk.funnycats.activities.dog_activities;

import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import tsekhmeistruk.funnycats.R;

public class DogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((NavigationView) findViewById(R.id.nav_view))
                .addHeaderView(LayoutInflater.from(this)
                        .inflate(R.layout.header_navigation_drawer_dogs, null));

    }
}
