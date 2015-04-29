package tn.rnu.enis.myprojectmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mohamed on 30/04/2015.
 */
public class SplashScreen extends AppCompatActivity {

    AppCompatActivity activity = this ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(activity,MainActivity.class);
                startActivity(i);
                activity.finish();
            }
        },3000);

    }
}
