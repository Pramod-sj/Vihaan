package com.vesvihaan;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;
import ss.com.bannerslider.views.indicators.IndicatorShape;

/**
 * Created by pramod_sj on 23/11/17.
 */

public class sponsers_activity extends android.support.v4.app.Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sponsers, container, false);
        BannerSlider bannerSlider = (BannerSlider)view.findViewById(R.id.sbanner);
        BannerSlider bannerSlider1 = (BannerSlider)view.findViewById(R.id.web_banner);
        BannerSlider bannerSlider2 = (BannerSlider)view.findViewById(R.id.mbanner);
        List<Banner> banners=new ArrayList<>();
        List<Banner> banners1=new ArrayList<>();
        List<Banner> banners2=new ArrayList<>();
        //banners.add(new RemoteBanner("Put banner image url here ..."));
        //add banner using resource drawable
        banners2.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/sponsers/times.jpg"));
        banners1.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/sponsers/vapourhost.jpg"));
        banners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/sponsers/coffee.jpg"));
        banners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/sponsers/raj.jpg"));
        banners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/sponsers/senergy.jpg"));
        banners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/sponsers/squad.jpg"));
        bannerSlider2.setBanners(banners2);
        bannerSlider.setBanners(banners);
        bannerSlider1.setBanners(banners1);
        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                Uri uri;
                switch (position){
                    case 0:
                        Toast.makeText(getActivity().getApplicationContext(),"No Website",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity().getApplicationContext(),"No Website",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        uri = Uri.parse("http://www.senergy.net.in");
                        Intent i2 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i2);
                        break;
                    case 3:
                        uri = Uri.parse("http://www.squadinfotech.in");
                        Intent i3 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i3);
                        break;
                    default:
                        Toast.makeText(getActivity().getApplicationContext(),"improper selection",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
        bannerSlider1.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                Uri uri1;
                switch (position){
                    case 0:
                        uri1 = Uri.parse("http://www.vapourhost.com");
                        Intent i2 = new Intent(Intent.ACTION_VIEW, uri1);
                        startActivity(i2);
                        break;
                    default:
                        Toast.makeText(getActivity().getApplicationContext(),"improper selection",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return view;
    }
}
