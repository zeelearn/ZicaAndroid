package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.andremion.music.MusicCoverView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zee.zeedoc.ui.music.ProgressView;
import com.zee.zeedoc.ui.music.music.PlayerService;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.iface.OnDialogClickListener;
import com.zeelearn.ekidzee.mlzs.utils.LocalConstance;


public class AudioPlayerBottomSheet extends BottomSheetDialogFragment {
    String message, title;
    private boolean mBound = false;
    OnDialogClickListener mListener;
    private MediaPlayer mediaplayer;
    private MusicCoverView mCoverView;
    boolean isPlaying;
    private PlayerService mService;
    private TextView mTimeView;
    private TextView mDurationView;
    private ProgressView mProgressView;
    ProgressDialog mProgressBar = null;
    RelativeLayout root;
    private void initDialog() {
        if ( isAdded() && isVisible()) {
            mProgressBar = new ProgressDialog(getContext());
            mProgressBar.setCancelable(false);//you can cancel it by pressing back button
        }
    }

    /**
     * Show Progress Dialog
     */
    public void showProgressDialog(String message) {
        if (true) {
            if (mProgressBar == null) {
                initDialog();
            }
            mProgressBar.setMessage(message);
            if (!mProgressBar.isShowing())
                mProgressBar.show();
        }
    }

    /**
     * hide Progress Dialog
     */
    public void hideDialog() {
        if (true || (isAdded() && isVisible())) {
            if (mProgressBar != null && mProgressBar.isShowing()) {
                mProgressBar.dismiss();
            }
        }
    }

    private final Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(mService!=null && mediaplayer!=null) {
                final int position = mService.getPosition();
                if (mediaplayer.getDuration() > 0)
                    mService.setDuration(mediaplayer.getDuration() / 1000);
                final int duration = mService.getDuration();

                onUpdateProgress(position, duration);
                sendEmptyMessageDelayed(0, DateUtils.SECOND_IN_MILLIS);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(LocalConstance.CONST_MESSAGE);
            title = getArguments().getString(LocalConstance.CONST_TITLE);
            mListener = (OnDialogClickListener) getArguments().getSerializable(LocalConstance.CONST_LISTENER);
        }

    }
    FloatingActionButton buttonStart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bs_audiolayout, container, false);
        ((TextView) view.findViewById(R.id.title_play)).setText(title);
        //message = "https://www.ekidzee.com/Uploads/EKidzee/PG/VIRTUAL CLASS/HIGHER ORDER ENGLISH LANGUAGE VOCABULARY/WEEK 1- 3RD AUGUST TO 7TH AUGUST 2020/RHYME/Elate-rhyme.m4a";
        //((TextView) view.findViewById(R.id.dialog_message)).setText(message);
        //BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) view.getParent());
        //mBehavior.setPeekHeight(440);
        buttonStart = view.findViewById(R.id.fab);
        root = view.findViewById(R.id.root);

        mTimeView = view.findViewById(R.id.time);
        mDurationView = view.findViewById(R.id.duration);
        mProgressView = view.findViewById(R.id.progress);
        mCoverView = view.findViewById(R.id.cover);
        mediaplayer = new MediaPlayer();
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlaying){
                    isPlaying = false;
                    mediaplayer.pause();
                    mCoverView.stop();
                    pause();
                    buttonStart.setImageResource(R.drawable.ic_play_animatable);
                }else {
                    try {
                        //
                        buttonStart.setImageResource(R.drawable.ic_pause_animatable);
                        play();
                        mediaplayer.start();
                        mCoverView.start();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    isPlaying = true;
                }
            }
        });
        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
               // getDialog().dismiss();
            }
        });

        mCoverView.setCallbacks(new MusicCoverView.Callbacks() {
            @Override
            public void onMorphEnd(MusicCoverView coverView) {
                // Nothing to do
            }

            @Override
            public void onRotateEnd(MusicCoverView coverView) {
                //supportFinishAfterTransition();
            }
        });

        new load().execute();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //init();
        onBind();

    }

    class load extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            init();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                root.setVisibility(View.VISIBLE);
                mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaplayer.setDataSource(message);
                mediaplayer.prepare();
                buttonStart.setImageResource(R.drawable.ic_pause_animatable);
                play();
                mediaplayer.start();
                mCoverView.start();
            }catch (Exception e){
                e.printStackTrace();
            }
            hideDialog();

        }
    }


    private void init() {

        Intent intent = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);




    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                FrameLayout bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setPeekHeight(250); // Remove this line to hide a dark background if you manually hide the dialog.
            }
        });
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to PlayerService, cast the IBinder and get PlayerService instance
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            onBind();
        }

        @Override
        public void onServiceDisconnected(ComponentName classname) {
            mBound = false;
            onUnbind();
        }
    };

    private void onBind() {
        mUpdateProgressHandler.sendEmptyMessage(0);
    }

    private void onUnbind() {
        mUpdateProgressHandler.removeMessages(0);
    }

    public void play() {
        mService.play();
    }

    public void pause() {
        mService.pause();
    }

    public void stop() {
        mService.stop();
    }

    private void onUpdateProgress(int position, int duration) {
        if (mTimeView != null) {
            mTimeView.setText(DateUtils.formatElapsedTime(position));
        }
        if (mDurationView != null) {
            mDurationView.setText(DateUtils.formatElapsedTime(duration));
        }
        if (mProgressView != null) {
            if(position>0 && duration >0) {
                if(position==duration){
                    //getActivity().finish();
                    try{
                        getDialog().dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    int speed = (position * 100) / duration;
                    mProgressView.setProgress(speed);
                    Log.d("MP_SPEED", "MP SPEED " + speed + " Duration " + duration + "  POS " + position);
                }
            }
            else{
                mProgressView.setProgress(0);
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        stop();
        mediaplayer.stop();
        onUnbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null && context instanceof OnDialogClickListener) {
            mListener = (OnDialogClickListener) context;
        }
    }

}