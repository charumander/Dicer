package com.charu.dicer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final Random RANDOM = new Random();
    private Button rollDices;
    private ImageView die_1, die_2;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollDices = (Button) findViewById(R.id.roll);
        die_1 = (ImageView) findViewById(R.id.die_1);
        die_2 = (ImageView) findViewById(R.id.die_2);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                //tvShake.setText("Shake Action is just detected!!");
                Toast.makeText(MainActivity.this, "Shaked!!!", Toast.LENGTH_SHORT).show();
                final Animation anim1 = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake);
                final Animation anim2 = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake);
                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int val = randomDiceValue();
                        int res = getResources().getIdentifier("dice_"+val, "drawable","com.charu.dicer");

                        if (animation == anim1){
                            die_1.setImageResource(res);
                        }else if (animation == anim2){
                            die_2.setImageResource(res);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };

                anim1.setAnimationListener(animationListener);
                anim2.setAnimationListener(animationListener);

                die_1.startAnimation(anim1);
                die_2.startAnimation(anim2);

            }
        });
//        rollDices.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Animation anim1 = AnimationUtils.loadAnimation(v.getContext(),R.anim.shake);
//                final Animation anim2 = AnimationUtils.loadAnimation(v.getContext(),R.anim.shake);
//                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        int val = randomDiceValue();
//                        int res = getResources().getIdentifier("dice_"+val, "drawable","com.charu.dicer");
//
//                        if (animation == anim1){
//                            die_1.setImageResource(res);
//                        }else if (animation == anim2){
//                            die_2.setImageResource(res);
//                        }
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                };
//
//                anim1.setAnimationListener(animationListener);
//                anim2.setAnimationListener(animationListener);
//
//                die_1.startAnimation(anim1);
//                die_2.startAnimation(anim2);
//            }
//        });
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static int randomDiceValue(){
        return RANDOM.nextInt(6)+1;
    }
}
