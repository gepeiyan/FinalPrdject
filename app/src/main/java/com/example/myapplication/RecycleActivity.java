package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RecycleActivity extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener{
    Handler handler;
    ArrayList<Map<String,String>> listItem;
    SimpleAdapter listItemAdapter;
    ListView listView;
    List list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_recycle);
        listView = findViewById (R.id.mylist3);
        ProgressBar progressBar = findViewById (R.id.progressBar);
        Thread t = new Thread (this);
        t.start ();
        handler = new Handler () {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@Nullable Message msg) {

                if (msg.what == 7){
                    List<HashMap<String,String>> list2 = (List<HashMap<String,String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(RecycleActivity.this,list2,
                            R.layout.news_list,
                            new String[]{"title","info"},
                            new int[]{R.id.title,R.id.info});
                    listView.setAdapter (listItemAdapter);

                    progressBar.setVisibility (View.GONE);
                    listView.setVisibility (View.VISIBLE);

                }
                super.handleMessage (msg);

            }
        };

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void run() {
        List<HashMap<String,String>> list1 = new ArrayList<HashMap<String,String>> ();
        try {
            Thread.sleep (100);
            Document doc = Jsoup.connect("http://yilin.net.cn/news/newslist.aspx?id=15").get();
            Log.i(TAG, "run:title=" + doc.title());
            Element table = doc.getElementsByTag("ul").get(1);
            Elements trs = table.getElementsByTag("li");
            for (Element tr : trs) {
                Elements a = tr.getElementsByTag("a");
                if (a.size() > 0) {
                    String title = a.get(0).text();
                    Log.i(TAG, "run:title=" + title);
                    String info = a.get(2).text();
                    Log.i(TAG, "run:title=" + info);
                    String url=a.get(0).attr("href");
                    Log.i(TAG, "run:title=" + url);
                    HashMap<String,String> map=new HashMap<String, String> ();
                    map.put("title",title);
                    map.put("info",info);
                    list1.add(map);

                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Log.e(TAG, "run:" + e.toString());
        }
        Message msg = handler.obtainMessage(7);
        msg.obj = list1;
        handler.sendMessage(msg);

    }
}