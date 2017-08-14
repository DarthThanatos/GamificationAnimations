package com.example.vobis.gamificationanimations.listanimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vobis.gamificationanimations.R;
import com.example.vobis.gamificationanimations.config.Config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Vobis on 2017-08-14
 */

class ListAnimationViewAdapter extends RecyclerView.Adapter<ListAnimationViewAdapter.ListAnimationViewHolder>{

    private static final String TAG = ListAnimationViewAdapter.class.getSimpleName();
    private List<FeedItem> feedItemList;
    private Context context;

    private final int paddingUnit = 125;

    ListAnimationViewAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.context = context;
    }

    @Override
    public ListAnimationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row, null);

        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);

        return new ListAnimationViewHolder(view);
    }

    private void animatePositionEntranceWithTimer(ListAnimationViewHolder holder, int position){
        int startingPadding = position * paddingUnit;

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)(holder.rootView).getLayoutParams();
        params.setMargins(startingPadding,0,0,0);
        holder.rootView.setLayoutParams(params);

        Observable
                .interval(Config.EVERY_TEN_MILLIS, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .forEachWhile(aLong -> countDownAnimation(aLong,holder), Throwable::printStackTrace);
    }

    private boolean countDownAnimation(Long aLong, ListAnimationViewHolder holder) {
        int padding = ((RecyclerView.LayoutParams)holder.rootView.getLayoutParams()).leftMargin;
        int moveUnit = 10;
        int moveStep = Math.min(padding, moveUnit);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)(holder.rootView).getLayoutParams();
        params.setMargins(padding - moveStep,0,0,0);
        holder.rootView.setLayoutParams(params);

        return aLong < Config.LIST_ITEM_ENTRANCE_ANIMATION_TIME / Config.EVERY_TEN_MILLIS;
    }

    private void animatePositionEntranceWithVA(ListAnimationViewHolder holder, int position){
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(position * paddingUnit, 0);
        valueAnimator.setDuration(Config.LIST_ITEM_ENTRANCE_ANIMATION_TIME);
        valueAnimator.addUpdateListener(animator -> {
            int calculatedMargin = (Integer) animator.getAnimatedValue();

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)(holder.rootView).getLayoutParams();
            params.setMargins(calculatedMargin,0,0,0);
            holder.rootView.setLayoutParams(params);
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }

    @Override
    public void onBindViewHolder(ListAnimationViewHolder holder, int position) {
        FeedItem feedItem = feedItemList.get(position);

        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Glide.with(context)
                    .load(feedItem.getThumbnail())
                    .into(holder.imageView);
        }

        holder.textView.setText(Html.fromHtml(feedItem.getTitle()));
        animatePositionEntranceWithVA(holder, position);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class ListAnimationViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        View rootView;

        ListAnimationViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imageView = rootView.findViewById(R.id.thumbnail);
            this.textView = rootView.findViewById(R.id.title);
        }
    }
}
