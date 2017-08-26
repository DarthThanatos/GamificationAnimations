package com.example.vobis.gamificationanimations.listanimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.vobis.gamificationanimations.R;
import com.example.vobis.gamificationanimations.config.Config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Vobis on 2017-08-14
 */

class ListAnimationViewAdapter extends RecyclerView.Adapter<ListAnimationViewAdapter.ListAnimationViewHolder>{

    private static final String TAG = ListAnimationViewAdapter.class.getSimpleName();
    private List<FeedItem> feedItemList;
    private Boolean[] imagesAlreadyLoaded;
    private Context context;

    private final int paddingUnit = 125;

    private Disposable[] disposables;

    ListAnimationViewAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.context = context;
        disposables = new Disposable[feedItemList.size()];
        imagesAlreadyLoaded = new Boolean[feedItemList.size()];
        Stream.range(0, feedItemList.size()).forEach(i -> imagesAlreadyLoaded[i] = Boolean.FALSE);
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

    private void animatePositionEntranceWithAnimation(ListAnimationViewHolder holder, int position){
        int startOffset = position * Config.LIST_ITEM_ENTRANCE_ANIMATION_TIME / 4;
        Log.d(TAG, "animating: " + position + " with start offset: " + startOffset);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        animation.setStartOffset(startOffset);
        animation.setDuration(Config.LIST_ITEM_ENTRANCE_ANIMATION_TIME);
        Log.d(TAG, "setting visibility of holder + " +  holder.getAdapterPosition() + ", position: " + position);
        holder.rootView.setVisibility(View.VISIBLE);
        holder.rootView.startAnimation(animation);
    }

    private void dispose(int position){
        Disposable disposable = disposables[position];
        if(disposable != null && !disposable.isDisposed()){
            Log.d(TAG, "disposing " + position);
            disposable.dispose();
        }
    }

    private boolean imagesBelowLoaded(int position){
        return Stream.of(imagesAlreadyLoaded)
                .filterIndexed((index, value) -> index < position)
                .allMatch(value -> value);
    }

    private void checkAllLoadedInBackground(ListAnimationViewHolder holder){
        int position = holder.getAdapterPosition();
        dispose(position);
        disposables[position] = Observable.interval(10, TimeUnit.MILLISECONDS)
            .forEachWhile(aLong -> printReducedArrayToPosition(position),Throwable::printStackTrace,() -> onAllLoaded(holder));
    }

    private void onAllLoaded(ListAnimationViewHolder holder) {
        dispose(holder.getAdapterPosition());
        ((Activity)context).runOnUiThread(() -> animatePositionEntranceWithAnimation(holder, holder.getAdapterPosition()));

    }


    private boolean printReducedArrayToPosition(int position) {
        Log.d(TAG, "position: " + position + ", not all loaded, array: ");
        if (position == 0) return false;
        String reducedArray = Stream.of(ListAnimationViewAdapter.this.imagesAlreadyLoaded)
                .filterIndexed((index, value) -> index < position)
                .map(Object::toString)
                .reduce((value1, value2) -> value1 + ", " + value2)
                .get();
        Log.d(TAG, reducedArray);
        return !imagesBelowLoaded(position);
    }

    @Override
    public void onBindViewHolder(ListAnimationViewHolder holder, int position) {
        FeedItem feedItem = feedItemList.get(position);
        imagesAlreadyLoaded[position] = Boolean.FALSE;
        holder.rootView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Glide.with(context)
                    .load(feedItem.getThumbnail())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.textView.setText(Html.fromHtml(feedItem.getTitle() + " [" +holder.getAdapterPosition() +"]" ));
                            imagesAlreadyLoaded[holder.getAdapterPosition()] = Boolean.TRUE;
                            checkAllLoadedInBackground(holder);
                            return false;
                        }
                    })
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    void disposeAll() {
        Stream.range(0, disposables.length).forEach(this::dispose);
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
