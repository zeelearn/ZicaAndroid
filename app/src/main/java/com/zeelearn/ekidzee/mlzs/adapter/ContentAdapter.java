package com.zeelearn.ekidzee.mlzs.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.news.retrofit.ContentGallery;
import com.zeelearn.news.retrofit.response.tips.ContentInfo;
import com.zeelearn.news.retrofit.response.tips.ContentResponse;
import com.zeelearn.news.retrofit.utils.ZeeNewsPref;

import java.io.Serializable;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.NewsHolder> {

    public final int TYPE_NEWS = 0;
    public final int TYPE_LOAD = 1;

    Context context;
    List<ContentResponse> mContentList;
    LinearLayout ll_news_ContentGallery;
    LinearLayout.LayoutParams llp;


    public ContentAdapter(Context context, List<ContentResponse> list) {
        this.context = context;
        this.mContentList = list;

        //Code for image gallery starts
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;

        Resources r = context.getResources();
        int screenHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics());
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, r.getDisplayMetrics());
        llp = new LinearLayout.LayoutParams(screenWidth - px, screenHeight);
        llp.setMargins(10, 5, 10, 5);

    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public ContentAdapter.NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new NewsHolder(inflater.inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        ContentResponse response = mContentList.get(position);
        holder.tv_news_Title.setText(response.getSubject());
        holder.tv_news_Desc.setMovementMethod(LinkMovementMethod.getInstance());

        holder.tv_news_Desc.setText(Html.fromHtml(response.getDescription()));
        //tv_news_Desc.setText(Html.fromHtml(userTip_model.getUser_Tip_Description()));
        holder.tv_news_Desc.setLinksClickable(true);
        holder.tv_news_Desc.setAutoLinkMask(Linkify.ALL); //to open links
        holder.tv_news_Date.setText(response.getDate());
        if(!TextUtils.isEmpty(response.getProfileImage()))
            Picasso.get().load(response.getProfileImage()).error(com.zeelearn.news.R.drawable.logo).into(holder.profileimage);

        final List<ContentInfo> list = response.getTip_Content_List();
        if (response.getTip_Content_List() != null && response.getTip_Content_List().size()> 0) {
            for (int index=0;index<response.getTip_Content_List().size();index++) {
                ContentInfo cinfo = response.getTip_Content_List().get(index);
                String contentType = cinfo.getContentType();
                if (contentType.equals("IMG")) {

                    ImageView imageView = new ImageView(context);
                    imageView.setId(index);
                    //contentModelArrayList.add(new UserTip_ContentModel(contentID, contentType, contentPath, contentDate));
                    Picasso.get().load(cinfo.getContentPath()).error(com.zeelearn.news.R.drawable.logo).into(imageView);
                    holder.ll_news_ContentGallery.addView(imageView);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userTip_content", (Serializable) list);
                            int imagClicked = view.getId();
                            bundle.putInt("position", imagClicked);
                            Intent intent = new Intent(context, ContentGallery.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                    //Log.e("User Tip Image" + i, contentPath);
                }else if (contentType.equals("YT_VID")) {

                    String videoPath = cinfo.getContentPath();
                    String videoID = videoPath.substring(videoPath.lastIndexOf("=") + 1);

                    View view = LayoutInflater.from(context).inflate(com.zeelearn.news.R.layout.youtubethumbnail_with_button, null);
                    view.setId(index);
                    view.setLayoutParams(llp);
                    view.setPadding(5, 5, 5, 5);
                    YouTubeThumbnailView youTubeThumbnailView = view.findViewById(com.zeelearn.news.R.id.ytv_userTip_item);
                    youTubeThumbnailView.setId(index);
                    youTubeThumbnailView.setTag(videoID);
                    youTubeThumbnailView.initialize(ZeeNewsPref.getYoutubeKey(context), holder.mlistener);

                    /*contentModelArrayList.add(new UserTip_ContentModel(contentID, contentType, contentPath, contentDate));
                    contentHashMap.put(getAdapterPosition(), contentModelArrayList);*/

                    ll_news_ContentGallery.addView(view);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userTip_content", (Serializable) list);
                            int imagClicked = view.getId();
                            bundle.putInt("position", imagClicked);
                            Intent intent = new Intent(context, ContentGallery.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }else if (contentType.equals("VID")) {
                    String videoID = cinfo.getContentPath();
                    Bitmap vidThumbnail = ThumbnailUtils.createVideoThumbnail(videoID, MediaStore.Video.Thumbnails.MICRO_KIND);

                    View view = LayoutInflater.from(context).inflate(com.zeelearn.news.R.layout.youtubethumbnail_with_button, null);
                    view.setId(index);
                    view.setLayoutParams(llp);
                    view.setPadding(5, 5, 5, 5);
                    ImageView iv_thumbnail = view.findViewById(com.zeelearn.news.R.id.iv_userTip_video);
                    iv_thumbnail.setVisibility(View.VISIBLE);
                    iv_thumbnail.setId(index);
                    if (vidThumbnail != null) {
                        iv_thumbnail.setImageBitmap(vidThumbnail);
                    } else {
                        iv_thumbnail.setBackgroundColor(ContextCompat.getColor(context, com.zeelearn.news.R.color.black));
                    }

                    ll_news_ContentGallery.addView(view);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userTip_content", (Serializable) list);
                            int imagClicked = view.getId();
                            bundle.putInt("position", imagClicked);
                            Intent intent = new Intent(context, ContentGallery.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        }
    }


    class NewsHolder extends RecyclerView.ViewHolder implements YouTubeThumbnailView.OnInitializedListener{

        TextView tv_news_Title;
        TextView tv_news_Desc;
        TextView tv_news_Date;
        HorizontalScrollView hsv_news_ContentGallery;

        LinearLayout ll_news_Item;
        ProgressBar pb_news_Video;
        ImageView iv_news_PlayPause;
        RelativeLayout rl_news_Video;
        View v_newsDivider;
        String videoID;
        ImageView profileimage;
        LinearLayout ll_news_ContentGallery;
        YouTubeThumbnailView.OnInitializedListener mlistener;

        public NewsHolder(View view) {
            super(view);
            tv_news_Title = view.findViewById(com.zeelearn.news.R.id.tv_news_Title);
            tv_news_Desc = view.findViewById(com.zeelearn.news.R.id.tv_news_Desc);
            tv_news_Date = view.findViewById(com.zeelearn.news.R.id.tv_news_Timestamp);
            v_newsDivider = view.findViewById(com.zeelearn.news.R.id.v_news_Divider);
            hsv_news_ContentGallery = view.findViewById(com.zeelearn.news.R.id.hsv_news_ContentGallery);
            ll_news_ContentGallery = view.findViewById(com.zeelearn.news.R.id.ll_news_Images);
            ll_news_Item = view.findViewById(com.zeelearn.news.R.id.ll_news_Item);
            rl_news_Video = view.findViewById(com.zeelearn.news.R.id.relativeLayout_over_youtube_thumbnail);
            pb_news_Video = view.findViewById(com.zeelearn.news.R.id.pb_news_Video);
            profileimage = view.findViewById(com.zeelearn.news.R.id.niv_newsAuthorProfile);
            mlistener = this;
        }


        @Override
        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

        }

        @Override
        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

        }
    }

    private class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View view) {
            super(view);
        }
    }
}
