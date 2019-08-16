package com.haifeng.example.glidehttps;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;


public class PictureListAdapter extends XiaobaoAdapter<String, PictureListAdapter.ViewHolder> {

    public PictureListAdapter(Context context) {
        super(context);
    }


    @Override
    protected int getItemViewLayout() {
        return R.layout.item_picture;
    }

    @Override
    public void convert(int position, final ViewHolder holder, String s) {
        String pictureUrl = getItem(position);
        holder.loadPic(pictureUrl);


    }


    @Override
    protected Class getHolderClass() {
        return ViewHolder.class;
    }


    static class ViewHolder extends XiaobaoAdapter.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.picture);
        }

        public void loadPic(String url) {

            Log.i("test", "图片地址:" + url);


            Glide.with(imageView).load(url).into(imageView);
        }
    }
}
