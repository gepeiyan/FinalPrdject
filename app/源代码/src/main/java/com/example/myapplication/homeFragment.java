package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

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
import static android.content.Intent.getIntent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends
        DialogFragment implements Runnable,AdapterView.OnItemClickListener  {
    Handler handler;
    ArrayList<Map<String,String>> listItem;
    SimpleAdapter listItemAdapter;
    ListView listView;
    Switch switchMsg;
    List list;
    private ViewPager viewPager;
    private Banner banner;
    private List<Integer> imageViewList=new ArrayList<> ();
    private List<String> imTitle=new ArrayList<>();






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
    private void putData(){
        imageViewList.add(R.drawable.e);
        imageViewList.add(R.drawable.f);
        imageViewList.add(R.drawable.g);
        imTitle.add("每日一题");
        imTitle.add("每日一题");
        imTitle.add("每日一题");
    }
    private void putView(){

            banner.setIndicatorGravity(BannerConfig.CENTER);

            banner.setImageLoader(new MyImageLoader());

            banner.setImages(imageViewList);

            banner.setBannerAnimation(Transformer.Default);

            banner.isAutoPlay(true);
            banner.setBannerTitles(imTitle);

            banner.setDelayTime(3000);

            banner.setOnBannerListener(this::OnBannerClick);

            banner.start();


    }
    public void OnBannerClick(int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder (getActivity());
        AlertDialog text= builder.setTitle ("猜猜看").setMessage ("哪个地方的人手机最爱关机")
        .setPositiveButton("武汉", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity (), "再猜！",Toast.LENGTH_SHORT).show();
            }
        })
        .setNeutralButton("成都", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity (),"你还是不晓得哦",Toast.LENGTH_SHORT).show();
            }
        })
        .setNegativeButton("宁波", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity (),"确实。您拨（宁波）打的电话已关机",Toast.LENGTH_SHORT).show();
            }
        }).create ();
        text.show();
        Button x = text.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button y = text.getButton(AlertDialog.BUTTON_POSITIVE);

        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) x.getLayoutParams();
        positiveButtonLL.weight = 1;
        x.setLayoutParams(positiveButtonLL);

        LinearLayout.LayoutParams mNegativeButtonLL = (LinearLayout.LayoutParams) y.getLayoutParams();
        mNegativeButtonLL.weight = 1;
        y.setLayoutParams(mNegativeButtonLL);


    }
    private class MyImageLoader extends ImageLoader {

        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);

        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_home,container,false);
        listView=view.findViewById (R.id.android_list);
        switchMsg=(Switch)view.findViewById (R.id.switch_1);
        banner=view.findViewById (R.id.banner);
        putData ();
        putView ();


        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        Thread t = new Thread (this);
        t.start ();

        super.onActivityCreated(savedInstanceState);
        switchMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    listView.setBackgroundColor (Color.parseColor ("#FFE4B5"));
                }else {
                    listView.setBackgroundColor (Color.parseColor ("#F8F8FF"));
                }
            }
        });

    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        Thread t = new Thread (this);

        t.start ();
        if (getArguments () != null) {
            mParam1 = getArguments ().getString (ARG_PARAM1);
            mParam2 = getArguments ().getString (ARG_PARAM2);
        }

        handler = new Handler () {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@Nullable Message msg) {

                if (msg.what == 7){

                    List<HashMap<String,String>> list2 = (List<HashMap<String,String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(getActivity (),list2,
                            R.layout.news_list,
                            new String[]{"title","info","href"},
                            new int[]{R.id.title,R.id.info,R.id.herf});
                    listView.setAdapter (listItemAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String,String> map1=(HashMap<String,String>)listView.getItemAtPosition(position);
                            Intent intent = new Intent(getActivity (),WebActivity.class);
                            String herf=map1.get("href");
                            intent.putExtra("news_url",herf);
                            //startActivity(intent);
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity ()).toBundle());
                        }
                    });


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
            Thread.sleep (10);
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
                    String href="http://yilin.net.cn/news/"+a.get(0).attr("href");
                    Log.i(TAG, "run:title=" + href);
                    String image=a.get(1).select("img").attr("src");
                    Log.i(TAG, "run:img=" + image);
                    HashMap<String,String> map=new HashMap<String, String> ();
                    map.put("title",title);
                    map.put("info",info);
                    map.put("href",href);
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


