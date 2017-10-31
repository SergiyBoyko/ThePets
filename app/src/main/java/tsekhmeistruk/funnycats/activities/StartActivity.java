package tsekhmeistruk.funnycats.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import tsekhmeistruk.funnycats.R;
import tsekhmeistruk.funnycats.activities.cats_activities.CatsActivity;
import tsekhmeistruk.funnycats.models.cats.entities.Image;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onCatButtonPressed(View view) {
        startActivity(new Intent(StartActivity.this, CatsActivity.class));
    }

    public void onDogButtonPressed(View view) {
        Toast.makeText(this, "dog pressed", Toast.LENGTH_SHORT).show();

        // TODO: 31.10.2017 implement and invoke
    }
}
