package com.inotes.Adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.inotes.Models.ImagePreview;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    SessionManager manager;
    List<ImagePreview> image_url;
    RelativeLayout rv;
    boolean toolbarcheck=false;
    ImagePreview imagePreview;
    int pos;

    public CustomPagerAdapter(Context context, List<ImagePreview> image_url, RelativeLayout rv, int pos) {
        mContext = context;
        this.image_url = image_url;
        Log.e("sizffff","  "+image_url.size());
        manager = new SessionManager();
        this.rv=rv;
        this.pos=pos;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return image_url.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override

    public Object instantiateItem(ViewGroup container, int position) {


            View itemView = mLayoutInflater.inflate(R.layout.previewpager, container, false);
        try {
            Log.e("Imageurlss",""+image_url.get(position));
            PhotoView img = itemView.findViewById(R.id.img);
            imagePreview=image_url.get(position);
            Glide.with(mContext)
                    .load(imagePreview.getUrls())
                    .into(img);
            img.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    if (!toolbarcheck) {
                        rv.setVisibility(View.GONE);
                        toolbarcheck = true;
                    } else {
                        rv.setVisibility(View.VISIBLE);
                        toolbarcheck = false;
                    }


                }
            });




          //  ViewPager viewPager = (ViewPager) container;
            //  viewPager.setCurrentItem(itemView,pos);
            container.addView(itemView);
          //  viewPager.addView(itemView, position);
            //position++;


        }catch (Exception e){
            e.printStackTrace();
        }
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }

}
