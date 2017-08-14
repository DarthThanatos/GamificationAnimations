package com.example.vobis.gamificationanimations.listanimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vobis.gamificationanimations.R;

import java.util.List;

/**
 * Created by Vobis on 2017-08-14
 */

public class ListAnimationViewAdapter extends RecyclerView.Adapter<ListAnimationViewAdapter.ListAnimationViewHolder>{

    private List<FeedItem> feedItemList;
    private Context context;

    public ListAnimationViewAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.context = context;
    }

    @Override
    public ListAnimationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row, null);
        return new ListAnimationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAnimationViewHolder holder, int position) {
        FeedItem feedItem = feedItemList.get(position);

        //Render image using Picasso library
        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Glide.with(context)
                    .load(feedItem.getThumbnail())
                    .into(holder.imageView);
        }

        //Setting text view title
        holder.textView.setText(Html.fromHtml(feedItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class ListAnimationViewHolder extends RecyclerView.ViewHolder{
        protected ImageView imageView;
        protected TextView textView;

        public ListAnimationViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }
}
