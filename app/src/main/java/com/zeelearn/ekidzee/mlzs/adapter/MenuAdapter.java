package com.zeelearn.ekidzee.mlzs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.iface.OnMenuClicked;
import com.zeelearn.ekidzee.mlzs.model.MenuInfo;

import java.util.List;


public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<MenuInfo> mFilters;
    private Context mContext;

    private static int TYPE_DEAL = 1;
    private static int TYPE_EVENT = 2;

    OnMenuClicked mListener;

    public static final String defPath =
            "https://developers.google.com/maps/documentation/javascript/examples/full/images/parking_lot_maps.png";


    public MenuAdapter(Context ctx, List<MenuInfo> object, OnMenuClicked listener) {
        inflater = LayoutInflater.from(ctx);
        this.mFilters = object;
        this.mContext = ctx;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.menu_row, parent, false);
        return new MyViewHolder(view);
        /*if (viewType == TYPE_EVENT) { // for call layout
            View view = inflater.inflate(R.layout.image_view_row, parent, false);
            return new EventViewHolder(view);

        } else { // for email layout
            View view = inflater.inflate(R.layout.menu_row, parent, false);
            return new MyViewHolder(view);
        }*/
    }


    public void updateAllData() {
        notifyDataSetChanged();
    }

  /*  @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if (mFilters.get(position) == null) {
            return TYPE_EVENT;

        } else {
            return TYPE_DEAL;
        }
    }*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder viewHolder, final int position) {

            MyViewHolder holder = (MyViewHolder) viewHolder;
            final MenuInfo mFilterInfo = mFilters.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mFilterInfo != null) {
                        if (mListener != null) {
                            mListener.onMenuClick(mFilterInfo);
                        }
                        for (int index = 0; index < getItemCount(); index++) {
                            if (mFilters.get(index) != null) {
                                if (position == index) {
                                    mFilters.get(index).setSelection(true);
                                } else {
                                    mFilters.get(index).setSelection(false);
                                }
                            }
                        }
                        updateAllData();
                    }
                }
            });
            if (mFilterInfo != null) {
                try {
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setShape(GradientDrawable.OVAL);
                    if (!TextUtils.isEmpty(mFilters.get(position).getBgColor())) {
                        drawable.setColor(Color.parseColor(mFilters.get(position).getBgColor().trim()));
                    } else {
//                        drawable.setColor(Color.parseColor("#FFEB3B"));
                        drawable.setColor(Color.parseColor(mFilters.get(position).getBgColor().trim()));
                    }

                    if (mFilters.get(position).isSelection()) {
//                        drawable.setStroke(3,Color.parseColor(mFilters.get(position).getFgColor().trim()));
                       drawable.setStroke(3, Color.RED);

                    } else {
//                        drawable.setStroke(3, R.color.circle_gray);
                        drawable.setStroke(3,Color.parseColor(mFilters.get(position).getFgColor().trim()));
                    }
                    holder.bgColor.setBackground(drawable);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.iv.setBackgroundResource(mFilters.get(position).getBackgoundIconPath());
                /*if (TextUtils.isEmpty(mFilters.get(position).getBackgoundIconPath())) {
                    if (mFilters.get(position).getDefImage() > 0) {
                        holder.iv.setBackgroundResource(mFilters.get(position).getDefImage());
                    } else {
                        Picasso.get().load(defPath).into(holder.iv);
                    }
                } else {
                    Picasso.get().load(mFilters.get(position).getBackgoundIconPath()).into(holder.iv);
                }*/
                holder.title.setText(mFilters.get(position).getTitle());
            }


    }

    private void setSelection(int position) {
        //circle.getPaint().setStrokeWidth(12);
        /*for (int index=0;index<mFilters.size();index++){
            View view = get
        }*/

    }

    @Override
    public int getItemCount() {
        return mFilters.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView iv;
        ImageView bgColor;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.img);
            bgColor = itemView.findViewById(R.id.img_bg);
            title = itemView.findViewById(R.id.txtView);

        }

    }

    static class EventViewHolder extends RecyclerView.ViewHolder {


        public EventViewHolder(View itemView) {
            super(itemView);

        }

    }
}
