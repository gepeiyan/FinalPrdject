package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Switch;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends
        ListFragment implements Runnable,AdapterView.OnItemClickListener {
    Handler handler;
    ArrayList<Map<String,String>> listItem;
    SimpleAdapter listItemAdapter;
    ListView listView;
    List list;
    private Switch mode=null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment ();
        Bundle args = new Bundle ();
        args.putString (ARG_PARAM1, param1);
        args.putString (ARG_PARAM2, param2);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if (getArguments () != null) {
            mParam1 = getArguments ().getString (ARG_PARAM1);
            mParam2 = getArguments ().getString (ARG_PARAM2);
        }
        Thread t = new Thread (this);
        t.start ();
        handler = new Handler () {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@Nullable Message msg) {

                if (msg.what == 7){
                    List<HashMap<String,String>> list2 = (List<HashMap<String,String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(getActivity (),list2,
                            R.layout.news_list,
                            new String[]{"title","info"},
                            new int[]{R.id.title,R.id.info});
                    setListAdapter (listItemAdapter);


                }
                super.handleMessage (msg);

            }
        };

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate (R.layout.fragment_home, container, false);
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
                    String href=a.get(0).attr("href");
                    Log.i(TAG, "run:title=" + href);
                    String image=a.get(1).select("img").attr("src");
                    Log.i(TAG, "run:img=" + image);
                    HashMap<String,String> map=new HashMap<String, String> ();
                    map.put("title",title);
                    map.put("info",info);
                    map.put("href",href);
                    map.put("image",image);
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