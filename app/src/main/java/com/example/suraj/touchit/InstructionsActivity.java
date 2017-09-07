package com.example.suraj.touchit;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class InstructionsActivity extends Activity {
    TextView instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_instructions);

        instructions = (TextView) findViewById(R.id.textView9);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/cubic.ttf");
        instructions.setTypeface(typeFace);
    }
}
