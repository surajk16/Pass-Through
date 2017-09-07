package com.example.suraj.touchit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.*;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;


public class HomeActivity extends Activity {

    ImageView play, settings, score, tick,inst;
    TextView title1, title2;
    DialogPlus dialog;
    DiscreteSeekBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_home);


        play = (ImageView) findViewById(R.id.imageView2);
        settings = (ImageView) findViewById(R.id.imageView3);
        score = (ImageView) findViewById(R.id.imageView4);
        title1 = (TextView) findViewById(R.id.title);
        title2 = (TextView) findViewById(R.id.title2);
        inst = (ImageView) findViewById(R.id.imageView8);


        android.view.animation.Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounce.setInterpolator(interpolator);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

        settings.startAnimation(rotate);
        score.startAnimation(bounce);
        play.startAnimation(bounce);
        inst.startAnimation(bounce);

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/cubic.ttf");
        title1.setTypeface(typeFace);
        title2.setTypeface(typeFace);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        dialog = DialogPlus.newDialog(this).setContentHolder(new ViewHolder(R.layout.dialog))
                .create();

        tick = (ImageView) dialog.findViewById(R.id.imageView5);
        bar = (DiscreteSeekBar) dialog.findViewById(R.id.bar);
        bar.setProgress((int) (10 - Constants.SENSITIVITY));
        Log.d("dhsd","Bar1 "+bar.getProgress());


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setProgress((int) (10 - Constants.SENSITIVITY));
                Log.d("dhsd","Bar2 "+bar.getProgress());
                dialog.show();
                Log.d("dhsd","Bar3 "+bar.getProgress());
            }
        });

        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SENSITIVITY = 10 - bar.getProgress();
                dialog.dismiss();
            }
        });

        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,InstructionsActivity.class);
                startActivity(i);
            }
        });


    }

}
