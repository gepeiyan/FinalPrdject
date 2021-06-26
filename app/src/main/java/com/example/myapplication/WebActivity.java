package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.transition.Explode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toolbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static android.content.ContentValues.TAG;
public class WebActivity extends AppCompatActivity implements Runnable {
    private SeekBar seekBar;
    Button back;
    TextView article;
    MarqueTextView tit;
    Handler handler;
    String p="";
    String title;
    Message msg1;
    public RadioGroup radioGroup;
    Switch switchMs;
    String in;
    public boolean isClick = false;
    int si=0;
    RadioButton cbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Thread t = new Thread (this);
        t.start ();

        Intent intent = getIntent();
        in=intent.getStringExtra("news_url");
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_web);
        getWindow().setEnterTransition(new Explode().setDuration(2000));
        getWindow().setExitTransition(new Explode ().setDuration(2000));
        back=findViewById (R.id.back1);
        seekBar = findViewById (R.id.seekbar);
        radioGroup=findViewById (R.id.group);
        seekBar.setMax (40);
        seekBar.setProgress (20);
        switchMs = (Switch) findViewById (R.id.switch_2);
        article = (TextView) findViewById (R.id.article);

        article.setMovementMethod (ScrollingMovementMethod.getInstance ());
        tit = (MarqueTextView) findViewById (R.id.title);
        tit.setSelected (true);

        handler = new Handler () {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@Nullable Message msg) {

                if (msg.what == 7) {
                    article.setText ("" + msg.obj);
                }

                if (msg1.what == 9) {
                    tit.setText ("" + msg1.obj);
                }
                super.handleMessage (msg);
                super.handleMessage (msg1);


            }
        };
        switchMs.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    article.setBackgroundColor (Color.parseColor ("#FFE4B5"));
                    tit.setBackgroundColor (Color.parseColor ("#FFE4B5"));
                } else {
                    article.setBackgroundColor (Color.parseColor ("#F8F8FF"));
                    tit.setBackgroundColor (Color.parseColor ("#F8F8FF"));
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                si=progress;
                article.setTextSize (TypedValue.COMPLEX_UNIT_SP,si);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
        back.setOnClickListener(new Button.OnClickListener () {
            @Override
            public void onClick(View v) {
             finish ();
            }
        });
        radioGroup.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                cbutton=(RadioButton)findViewById (checkedId);
                if (!cbutton.isPressed()){
                    return;
                }
                switch (checkedId) {
                    case R.id.b:
                        article.setTextSize (TypedValue.COMPLEX_UNIT_SP,35);
                        break;
                    case R.id.y:
                        article.setTextSize (TypedValue.COMPLEX_UNIT_SP,20);
                        break;
                    case R.id.r:
                        article.setTextSize (TypedValue.COMPLEX_UNIT_SP,15);
                        break;
                    }



            }
        });


    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    public void run() {
       // List<HashMap<String,String>> list1 = new ArrayList<HashMap<String,String>> ();
        try {
            Thread.sleep (100);

           // Document doc = Jsoup.connect("http://yilin.net.cn/news/newsc.aspx?id=4537").get();
            Document doc = Jsoup.connect(in).get();
            Log.i(TAG, "run:title=" + doc.title());
            Element ass=doc.select("div.newsc_content").get (0);
            title=ass.select ("h1.artibodyTitle").get(0).text();
            String author=ass.select("span.media_name").get(1).text();
            Element con=ass.select ("div.newsc_c_l_body").get(0);
            Elements art=con.select ("p");

            for(Element ch:art){
                if(art.size()>0){
                     p=p+"      "+ch.text ()+"\n";
                    //Log.i(TAG, "p=" +p);
                }

            }

            Log.i(TAG, "run:totle:" + title);
            Log.i(TAG, "run:au:" + author);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Log.e(TAG, "run:" + e.toString());
        }
        Message msg = handler.obtainMessage(7);
        msg.obj = p;
        handler.sendMessage(msg);
        msg1 = handler.obtainMessage(9);
        msg1.obj = title;
        handler.sendMessage(msg1);

    }


}