package com.example.vobis.gamificationanimations.listanimation;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.vobis.gamificationanimations.R;
import com.example.vobis.gamificationanimations.commonviews.ResourceReadyListener;
import com.example.vobis.gamificationanimations.config.Config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Vobis on 2017-08-14
 */

class ListAnimationViewAdapter extends RecyclerView.Adapter<ListAnimationViewAdapter.ListAnimationViewHolder>{

    private static final String TAG = ListAnimationViewAdapter.class.getSimpleName();
    private List<FeedItem> feedItemList;
    private Boolean[] imagesAlreadyLoaded;
    private Context context;

    private Disposable[] disposables;
    private boolean scrolledDown = true;
    private static final int MAX_ANIMATED_POSITIONS = 5;

    ListAnimationViewAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.context = context;
        initDisposablesArray();
        initImagesAlreadyLoadedArray();
    }

    private void initDisposablesArray(){
        disposables = new Disposable[feedItemList.size()];
    }

    private void initImagesAlreadyLoadedArray(){
        imagesAlreadyLoaded = new Boolean[feedItemList.size()];
        Stream.range(0, feedItemList.size()).forEach(i -> imagesAlreadyLoaded[i] = Boolean.FALSE);
    }

    @Override
    public ListAnimationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row, null);
        return new ListAnimationViewHolder(view);
    }

    private void animatePositionEntranceWithAnimation(ListAnimationViewHolder holder, int position){
        if( position >= MAX_ANIMATED_POSITIONS - 1) {holder.rootView.setVisibility(View.VISIBLE); return;}
        int startOffset = (scrolledDown ? position : MAX_ANIMATED_POSITIONS - position) * Config.ENTRANCE_ANIMATION_TIME / 4;
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        animation.setStartOffset(startOffset);
        animation.setDuration(Config.ENTRANCE_ANIMATION_TIME);
        holder.rootView.setVisibility(View.VISIBLE);
        holder.rootView.startAnimation(animation);
    }

    private void dispose(int position){
        Disposable disposable = ListAnimationViewAdapter.this.disposables[position];
        if(disposable != null && !disposable.isDisposed()){
            Log.d(TAG, "disposing " + position);
            disposable.dispose();
        }
        else{
            if(disposable!=null){
                Log.d(TAG, "disposable " + position + " already disposed");
            }
        }
    }

    private boolean imagesBelowLoaded(int position) {
        return !scrolledDown || Stream.of(imagesAlreadyLoaded).filterIndexed((index, value) -> index < position).allMatch(value -> value);
    }

    private void checkInBackgroundIfAllImagesBelowLoadedThenAnimate(ListAnimationViewHolder holder){
        int position = holder.getAdapterPosition();
        if(position < 0) return; //position index can be invalid at this point
        dispose(position);
        disposables[position] = Observable.interval(10, TimeUnit.MILLISECONDS)
            .forEachWhile(aLong -> positionNotReadyToAnimate(position),Throwable::printStackTrace,() -> onAllLoaded(holder));
    }

    private void onAllLoaded(ListAnimationViewHolder holder) {
        if(holder.getAdapterPosition() < 0) return; //position index can be invalid at this point
        dispose(holder.getAdapterPosition());
        ((Activity)context).runOnUiThread(() -> animatePositionEntranceWithAnimation(holder, holder.getAdapterPosition()));
    }


    private boolean positionNotReadyToAnimate(int position) {
        if (position <= 0) return false;
        Log.d(TAG, "position: " + position + ", not all loaded, array: ");
        printArrayUntilPosition(position);
        return !imagesBelowLoaded(position);
    }

    private void printArrayUntilPosition(int position){
        String reducedArray = Stream.of(ListAnimationViewAdapter.this.imagesAlreadyLoaded)
                .filterIndexed((index, value) -> index < position)
                .map(Object::toString)
                .reduce((value1, value2) -> value1 + ", " + value2)
                .get();
        Log.d(TAG, reducedArray);
    }

    @Override
    public void onBindViewHolder(ListAnimationViewHolder holder, int position) {
        FeedItem feedItem = feedItemList.get(position);
        imagesAlreadyLoaded[position] = Boolean.FALSE;
        holder.rootView.setVisibility(View.GONE);
        holder.textView.setText(Html.fromHtml(feedItem.getTitle() + " [" +holder.getAdapterPosition() +"]" ));
        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            loadImageThenTryAnimatingEntrance(holder, feedItem);
        }
    }

    private void loadImageThenTryAnimatingEntrance(ListAnimationViewHolder holder, FeedItem feedItem){
        Glide.with(context)
        .load(feedItem.getThumbnail())
        .listener(new ResourceReadyListener() {
            @Override
            protected void reactOnResourceReady() {
                onImageLoaded(holder);
            }
        })
        .into(holder.imageView);
    }

    private void onImageLoaded(ListAnimationViewHolder holder){
        imagesAlreadyLoaded[holder.getAdapterPosition()] = Boolean.TRUE;
        if(holder.getAdapterPosition() < 0) return; // position index can be invalid at this point
        if(holder.getAdapterPosition() < MAX_ANIMATED_POSITIONS && scrolledDown){
            checkInBackgroundIfAllImagesBelowLoadedThenAnimate(holder);
        }
        else{
            holder.rootView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    void disposeAll() {
        Stream.range(0, disposables.length).forEach(this::dispose);
    }

    void reactOnScrollDirectionChange(int dy) {
        scrolledDown = dy >= 0;
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
