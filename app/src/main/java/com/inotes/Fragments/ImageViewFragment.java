package com.inotes.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inotes.Adapters.CustomPagerAdapter;
import com.inotes.Adapters.NotesViewAdapter;
import com.inotes.Adapters.UploadImagesAdapter;
import com.inotes.Globalfunctions;
import com.inotes.Models.ImagePreview;
import com.inotes.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class ImageViewFragment extends Fragment {

    PhotoView imageView;
    RelativeLayout rv;
    TextView bak;
    boolean toolbarcheck = false;
    ViewPager viewPager;

    CustomPagerAdapter mCustomPagerAdapter;

    public List<ImagePreview> urls = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_preview_dialog, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setIcon(null);
        //  ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        //Window w;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        String uris = getArguments().getString("uris");
        int pos = getArguments().getInt("pos");

        Log.e("Size of ORDER", "kkkk" + UploadImagesAdapter.urls.size());
        if(Globalfunctions.presViewIndex==2) {
            for (int i = 0; i < UploadImagesAdapter.urls.size(); i++) {
                ImagePreview model = new ImagePreview();
                Log.e("Size of ORDER", "kkkk" + UploadImagesAdapter.urls.get(i).getUri());
                model.setUrls(UploadImagesAdapter.urls.get(i).getUri());
                urls.add(model);
            }
        }
        if(Globalfunctions.presViewIndex==1){
            for (int i = 0; i < NotesViewAdapter.newList.size(); i++) {
                ImagePreview model = new ImagePreview();
                Log.e("Size of ORDER", "kkkk" + NotesViewAdapter.newList.get(i).getUrl());
                model.setUrls(NotesViewAdapter.newList.get(i).getUrl());
                urls.add(model);
            }
        }


        imageView = (PhotoView) view.findViewById(R.id.imagepreview);
        rv = (RelativeLayout) view.findViewById(R.id.rv);
        bak = (TextView) view.findViewById(R.id.bak);
        viewPager = (ViewPager) view.findViewById(R.id.pager);

        Log.e("positionkyahai", "" + pos);
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity(), urls, rv, pos);
        viewPager.setAdapter(mCustomPagerAdapter);
        mCustomPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(pos);

        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        urls.clear();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        urls.clear();

    }

    @Override
    public void onResume() {
        super.onResume();

        //  ((NavigationActivity) getActivity()).showActionBar(4, null);

        Log.e("Urisssize", "" + urls.size());
    }
}
