package com.lingxiao.webmastertool;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lingxiao on 17-11-17.
 */

public class MainRecycleAdapter extends RecyclerView.Adapter{
    private int[] mImages = new int[]{
            R.drawable.ic_img_seo,R.drawable.ic_img_ranking,
            R.drawable.ic_img_whois,R.drawable.ic_img_icp,
            R.drawable.ic_img_ip,R.drawable.ic_img_press,
            R.drawable.ic_img_blogroll,R.drawable.ic_img_link
    };
    private String[] mStr = new String[]{"seo查询","百度排名","whois查询",
            "网站备案","IP查询","PR查询","友情链接","反链查询"};
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.main_item,null);
        MainViewHolder holder = new MainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MainViewHolder)holder).textView.setText(mStr[position]);
        ((MainViewHolder)holder).imageView.setImageResource(mImages[position]);
        ((MainViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStr.length;
    }

    private class MainViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MainViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_menu_item);
            textView = itemView.findViewById(R.id.tv_menu_item);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View View, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
