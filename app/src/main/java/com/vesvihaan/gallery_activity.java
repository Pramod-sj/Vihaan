package com.vesvihaan;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by pramo on 12/23/2017.
 */

public class gallery_activity extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery, container, false);
        BannerSlider coreBanner=(BannerSlider)view.findViewById(R.id.coreBanner);
        BannerSlider creativeBanner=(BannerSlider)view.findViewById(R.id.creativeBanner);
        BannerSlider marketBanner=(BannerSlider)view.findViewById(R.id.marketBanner);
        BannerSlider digitalBanner=(BannerSlider)view.findViewById(R.id.digitalBanner);
        List<Banner> cbanners=new ArrayList<>();
        cbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/core team/coreteam1.jpg"));
        cbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/core team/coreteam2.jpg"));
        cbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/core team/coreteam3.jpg"));
        List<Banner> crbanners=new ArrayList<>();
        crbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/creative/creatives1.jpg"));
        crbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/creative/creatives3.jpg"));
        crbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/creative/creatives4.jpg"));
        crbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/creative/creatives5.jpg"));
        crbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/creative/creatives6.jpg"));
        crbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/creative/creatives7.jpg"));
        List<Banner> mbanners=new ArrayList<>();
        mbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/marketing/marketing1.jpg"));
        List<Banner> dbanners=new ArrayList<>();
        dbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/digital promotion/digitalpromotion2.jpg"));
        dbanners.add(new RemoteBanner("https://github.com/Pramod-sj/Vihaan/raw/master/image/Team/digital promotion/digitalpromotion1.jpg"));
        coreBanner.setBanners(cbanners);
        creativeBanner.setBanners(crbanners);
        marketBanner.setBanners(mbanners);
        digitalBanner.setBanners(dbanners);
        return view;
    }
}
