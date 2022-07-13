package com.zeelearn.ekidzee.mlzs.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.squareup.picasso.Picasso;
import com.zee.zeedoc.BaseActivity;
import com.zee.zeedoc.maanager.FileEventManager;
import com.zeelearn.ekidzee.mlzs.BuildConfig;
import com.zeelearn.ekidzee.mlzs.MyApplication;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.adapter.MenuAdapter;
import com.zeelearn.ekidzee.mlzs.fragments.bs.BSLogoutDialogFragment;
import com.zeelearn.ekidzee.mlzs.fragments.bs.ContactUsFragment;
import com.zeelearn.ekidzee.mlzs.fragments.bs.ECampusFragment;
import com.zeelearn.ekidzee.mlzs.fragments.bs.EStudioFragment;
import com.zeelearn.ekidzee.mlzs.fragments.bs.MyCenterFragment;
import com.zeelearn.ekidzee.mlzs.fragments.bs.NewsFragment;
import com.zeelearn.ekidzee.mlzs.fragments.bs.ProfileFragment;
import com.zeelearn.ekidzee.mlzs.fragments.bs.ZeeFileManagerFragment;
import com.zeelearn.ekidzee.mlzs.iface.OnDialogClickListener;
import com.zeelearn.ekidzee.mlzs.iface.OnMenuClicked;
import com.zeelearn.ekidzee.mlzs.model.GenericResponse;
import com.zeelearn.ekidzee.mlzs.model.MenuInfo;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitServiceListener;
import com.zeelearn.ekidzee.mlzs.retrofit.request.login.MenuModel;
import com.zeelearn.ekidzee.mlzs.utils.ForceUpdateChecker;
import com.zeelearn.ekidzee.mlzs.utils.LocalConstance;
import com.zeelearn.ekidzee.mlzs.utils.ZeePref;
import com.zeelearn.news.retrofit.ZeeNewsSdk;
import com.zeelearn.news.retrofit.fragments.ContentListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.zeelearn.ekidzee.mlzs.utils.LocalConstance.ACTION_NEWS;
import static com.zeelearn.ekidzee.mlzs.utils.LocalConstance.ACTION_PLACEMENT;
import static com.zeelearn.ekidzee.mlzs.utils.LocalConstance.ACTION_USER_TYPE_ALL;
import static com.zeelearn.ekidzee.mlzs.utils.LocalConstance.IS_LOYOUT;


public class DashboardNew extends BaseActivity implements OnMenuClicked, RetrofitServiceListener, OnDialogClickListener, PopupMenu.OnMenuItemClickListener {

    public static String TAG = DashboardNew.class.getCanonicalName();
    OnMenuClicked mMenuClickListener;
    List<MenuInfo> mFilterList = new ArrayList<>();
    MenuAdapter mFilterAdapter;


    FragmentManager fm;
    final String DISPLAY_FRAGMENT_TAG = "displayFragment";
    ECampusFragment displayFragment;

    int mCount = 0;
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_new);

        mMenuClickListener = this;

        ((MyApplication)getApplicationContext()).registerTopic(ACTION_USER_TYPE_ALL);
        ((MyApplication)getApplicationContext()).registerTopic(ZeePref.getUserType(getApplicationContext()));

        ((MyApplication)getApplicationContext()).saveEvent(ZeePref.getUserType(getApplicationContext()));

        ((TextView)findViewById(R.id.username)).setText(getString(R.string.app_name));
        ((TextView)findViewById(R.id.usertype)).setText(ZeePref.getUserTypeName(this));

        ((ImageView)(findViewById(R.id.action_home))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHomeScreen();
            }
        });
        initMenu();
        ((TextView) findViewById(R.id.footer)).setText("Version Name : "+BuildConfig.VERSION_NAME);

        setHomeScreen();

        ZeeNewsSdk.init(getApplicationContext(),ZeePref.getUserId(this),ZeePref.getUserType(this), LocalConstance.YOUTUBE_API_KEY);

        getFCMToken();
        
        extractIntent();

        checkFirebase();
    }

    public void checkFirebase(){
        //HashMap<String, Object> firebaseDefaultMap;
        //firebaseDefaultMap = new HashMap<>();
        //firebaseDefaultMap.put(ForceUpdateChecker.KEY_CURRENT_VERSION, BuildConfig.VERSION_CODE);
        //mFirebaseRemoteConfig.setDefaults(firebaseDefaultMap);

        mFirebaseRemoteConfig.setConfigSettings(
                new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG)
                        .build());

        mFirebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mFirebaseRemoteConfig.activateFetched();
                    Log.d(TAG, "Fetched value: " + mFirebaseRemoteConfig.getString(ForceUpdateChecker.KEY_CURRENT_VERSION));
                    //calling function to check if new version is available or not
                    checkForUpdate();
                } else {
                    Toast.makeText(DashboardNew.this, "Someting went wrong please try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkForUpdate() {
        int latestAppVersion = (int) mFirebaseRemoteConfig.getDouble(ForceUpdateChecker.KEY_CURRENT_VERSION);
        if (latestAppVersion > BuildConfig.VERSION_CODE) {
            new AlertDialog.Builder(DashboardNew.this).setTitle("Please Update the App : "+latestAppVersion)
                    .setMessage("A new version of this app is available. Please update it")
                    .setCancelable(false)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    }).show();
        }
    }

    private void extractIntent() {

        try {
            if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().hasExtra("action")){
                Bundle extras = getIntent().getExtras();
                String imagePath = extras.getString("image");
                String action = extras.getString("action");
                String title = extras.getString("Title");
                String message = extras.getString("Message");
                String URL = extras.getString("URL");
                showImageDialog(title, message, imagePath, URL,action);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showImageDialog(String title, String message, String imagePath, String url,String action) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.promotional_dialog);
        RelativeLayout header = dialog.findViewById(R.id.header);
        LinearLayout content = dialog.findViewById(R.id.content);
        TextView txtMessage = dialog.findViewById(R.id.txt_message);
        txtMessage.setText(message);
        TextView text_title = dialog.findViewById(R.id.text_title);
        ImageView img_bg = dialog.findViewById(R.id.img_bg);
        text_title.setText(title);
        Button btnClickHere = dialog.findViewById(R.id.buttonOk);
        if (!TextUtils.isEmpty(imagePath)) {
            Picasso.get().load(imagePath).into(img_bg);
            header.setVisibility(View.GONE);
            img_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (action.equalsIgnoreCase(ACTION_NEWS)) {
                        setFragment(com.zeelearn.ekidzee.mlzs.fragments.bs.ContentListFragment.newInstance("NEWS"));
                    }else if(action.equalsIgnoreCase(ACTION_PLACEMENT)){
                        setFragment(com.zeelearn.ekidzee.mlzs.fragments.bs.ContentListFragment.newInstance("JOBS"));
                    }
                    dialog.dismiss();

                }
            });
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(message)) {
                btnClickHere.setVisibility(View.GONE);
            }
            header.setVisibility(View.VISIBLE);
            btnClickHere.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(url)) {
                btnClickHere.setVisibility(View.VISIBLE);
                btnClickHere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        dialog.dismiss();
                    }
                });
            } else {
                btnClickHere.setVisibility(View.GONE);
            }
            header.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    dialog.dismiss();
                    return true;
                }
            });
        }
        dialog.show();
    }

    public void getFCMToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("ZEEFirebaseMessaging", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        ZeePref.setFCMToken(getApplicationContext(),token);
                        // Log and toast
                        Log.d("ZEEFirebaseMessaging", token);
                        //Toast.makeText(DashboardNew.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static final int MENU_MYCENTER = 0;
    public static final int MENU_ENQUIRY = 1;

    public static final int MENU_NOTICE = 2;
    public static final int MENU_ESTUDIO = 3;
    public static final int MENU_NEWS = 4;
    public static final int MENU_TIPS = 5;
    public static final int MENU_PROFILE = 6;
    public static final int MENU_ENQUIRY_NEW = 7;
    public static final int MENU_CAMPUS = 8;
    public static final int MENU_CONTACTUS = 9;
    public static final int MENU_PLACEMENT = 10;
    public void initMenu(){
        if(mFilterList==null){
            mFilterList = new ArrayList<>();
        }
        mFilterList.clear();

        List<MenuModel> menus = new ArrayList<>();
        /*for (int index=0;index<menus.size();index++){
            switch (menus.get(index).getMenu_Id()){
                case 5:
                    mFilterList.add(new MenuInfo(menus.get(index).getMenuText(),"#E8F5E9", "#E8F5E9",R.mipmap.conversation, MENU_ENQUIRY));
                    break;
                case 2:
                    mFilterList.add(new MenuInfo(menus.get(index).getMenuText(),"#d1f2eb", "#d1f2eb",R.mipmap.ic_estudio, MENU_ESTUDIO));
                    break;
                case 39:
                    mFilterList.add(new MenuInfo(menus.get(index).getMenuText(),"#d1f2eb", "#d1f2eb",R.mipmap.ic_estudio, MENU_ESTUDIO));
                    break;
            }
        }*/

        //mFilterList.add(new MenuInfo("FOLLOWUP","#F5EEF8", "#F5EEF8",R.mipmap.ic_followup, MENU_FOLLlOW));

        //mFilterList.add(new MenuInfo("Enquiry","#E8F5E9", "#E8F5E9",R.mipmap.conversation, MENU_ENQUIRY));

        if(BuildConfig.FLAVOR.equalsIgnoreCase("etest")) {
            //mFilterList.add(new MenuInfo("Campus", "#fadbd8", "#fadbd8", R.drawable.elearning, MENU_CAMPUS));
        }else{
            mFilterList.add(new MenuInfo("eCampus", "#E0F7FA", "#E0F7FA", R.drawable.elearning, MENU_CAMPUS));
            if(BuildConfig.FLAVOR.equalsIgnoreCase("MLZS")) {
                mFilterList.add(new MenuInfo("Home","#d1f2eb", "#d1f2eb",R.mipmap.ic_estudio, MENU_ESTUDIO));
                mFilterList.add(new MenuInfo("Events", "#ebdef0", "#ebdef0", R.mipmap.ic_news, MENU_NEWS));
                mFilterList.add(new MenuInfo("Contact Us","#d1f2eb", "#d1f2eb",R.mipmap.ic_estudio, MENU_CONTACTUS));
            }else{
                mFilterList.add(new MenuInfo("E-Studio","#E8F5E9", "#E8F5E9",R.drawable.estudio, MENU_ESTUDIO));
                //mFilterList.add(new MenuInfo("Notice", "#ebdef0", "#ebdef0", R.drawable.ic_notice, MENU_TIPS));
                mFilterList.add(new MenuInfo("News", "#FBE9E7", "#FBE9E7", R.drawable.newspaper, MENU_NEWS));
                mFilterList.add(new MenuInfo("Placement","#E1F5FE", "#E1F5FE",R.drawable.ic_placement, MENU_PLACEMENT));
                mFilterList.add(new MenuInfo("Profile","#FEF9E7", "#fadbd8",R.drawable.ic_profile, MENU_PROFILE));
                mFilterList.add(new MenuInfo("Contact Us","#E1F5FE", "#d1f2eb",R.drawable.mail, MENU_CONTACTUS));
                RecyclerView mRecycler = findViewById(R.id.rec_menu);
                mFilterAdapter = new MenuAdapter(getApplicationContext(), mFilterList, mMenuClickListener);
                mRecycler.setAdapter(mFilterAdapter);
            /*FilterAdapter adapter = new FilterAdapter(getContext(), mFilterList,null);
            mRecycler.setAdapter(adapter);*/

                mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                mFilterList.get(0).setSelection(true);
                mFilterAdapter.notifyDataSetChanged();
            }
        }
        //mFilterList.add(new MenuInfo("My Center","#fcf3cf", "#fcf3cf",R.mipmap.ic_center, MENU_MYCENTER));


        //mFilterList.add(new MenuInfo("Tips","#fdebd0", "#fdebd0",R.mipmap.ic_tips, MENU_TIPS));





    }

    public void setHomeScreen(){
        fm = getSupportFragmentManager();
        if(fm.findFragmentById(R.id.home_fragment) == null) {
            if(BuildConfig.FLAVOR.equalsIgnoreCase("etest")){
                displayFragment = new ECampusFragment();
            }else {
                displayFragment = new ECampusFragment();
            }
            fm.beginTransaction()
                    .add(R.id.home_fragment, displayFragment,DISPLAY_FRAGMENT_TAG)
                    .addToBackStack(DISPLAY_FRAGMENT_TAG)
                    .commit();
        }

        //DashboardFragment fragmentDemo = new DashboardFragment();
        //above part is to determine which fragment is in your frame_container
        if(displayFragment.isAdded() && displayFragment.isVisible()){
            displayFragment.updateRoot();
        }else {
            setFragment(displayFragment);
            if (mFilterList != null) {
                for (int index = 0; index < mFilterList.size(); index++) {
                    mFilterList.get(index).setSelection(false);
                }
            }
        }
        if(mFilterAdapter!=null){
            mFilterAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onBackPressed() {
        try {
            if (displayFragment.isVisible()) {
                int count = FileEventManager
                        .getInstance()
                        .getFileManager()
                        .getPathStackItemsCount();

                if (count == 0) {
                    //finish();
                    mCount--;

                    if(mCount<0){
                        showLogOutDialog();
                    }else{
                        displayFragment.updateRoot();
                    }
                } else if (count == 1) {
                    // super.onBackPressed();
                    displayFragment.updateRoot();
                    mCount = count;
                } else {
                    FileEventManager
                            .getInstance()
                            .moveUpDirectory();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    // This could be moved into an abstract BaseActivity
    // class for being re-used by several instances
    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment, fragment);
        fragmentTransaction.commit();
    }




    public void showLogOutDialog(){
        BSLogoutDialogFragment bottomSheetFragment = new BSLogoutDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LocalConstance.CONST_TITLE, getString(R.string.app_name));
        bundle.putString(LocalConstance.CONST_MESSAGE, getString(R.string.txt_logout_message));
        bundle.putSerializable(LocalConstance.CONST_LISTENER, this);
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onMenuClick(MenuInfo filtersInfo) {
        if(filtersInfo.getMenuId()!=MENU_ENQUIRY_NEW) {
            if(mFilterList!=null && mFilterList.size()>0) {
                for (int index=0;index<mFilterList.size();index++) {
                    if(mFilterList.get(index).getMenuId() == filtersInfo.getMenuId()) {
                        mFilterList.get(index).setSelection(true);
                        mFilterAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
        switch (filtersInfo.getMenuId()){
            case MENU_CAMPUS:
                setHomeScreen();
                break;
            case MENU_MYCENTER:
                setFragment(new MyCenterFragment());
                break;
             /*case MENU_FOLLlOW:
                setFragment(new EnquiryListFragment());
                break;
            case MENU_ENQUIRY:
                setFragment(new EnquiryListFragment());
                break;
            case MENU_ENQUIRY_NEW:
                mFilterList.get(MENU_ENQUIRY).setSelection(true);
                setFragment(new EnquiryFragment());
                break;*/
            case MENU_ESTUDIO:
                setFragment(new EStudioFragment());
                break;
            case MENU_CONTACTUS:
                setFragment(new ContactUsFragment());
                break;
            case MENU_NOTICE:
                setFragment(new NewsFragment());
                break;
             case MENU_PLACEMENT:
                 setFragment(com.zeelearn.ekidzee.mlzs.fragments.bs.ContentListFragment.newInstance("JOBS"));
                break;
            case MENU_NEWS:

                if(BuildConfig.FLAVOR.equalsIgnoreCase("MLZS")) {
                    setFragment(new NewsFragment());
                }else if(BuildConfig.FLAVOR.equalsIgnoreCase("zica")) {
                    //setFragment(new NewsFragment());
                    setFragment(com.zeelearn.ekidzee.mlzs.fragments.bs.ContentListFragment.newInstance("NEWS"));
                }else {
                    setFragment(com.zeelearn.news.retrofit.fragments.ContentListFragment.newInstance("NEWS"));
                }
                break;
            case MENU_TIPS:
                setFragment(com.zeelearn.ekidzee.mlzs.fragments.bs.ContentListFragment.newInstance("TIPS"));
                break;
            case MENU_PROFILE:
                setFragment(new ProfileFragment());
                break;

        }
    }

    @Override
    public void onRequestStarted(Object mObject) {

    }

    @Override
    public void onResponse(Object mObject) {

    }

    @Override
    public void onFailure(Object mObject, Throwable t) {

    }

    @Override
    public void onOkClicked(Object object) {
        if(object instanceof GenericResponse){
            GenericResponse response = (GenericResponse) object;
            if(((GenericResponse) object).getRequestCode()==IS_LOYOUT){
                ZeePref.clear(getApplicationContext());
                //navigateLogin();
                finish();
            }
        }
    }

    @Override
    public void onCancelClicked() {

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.action_policy){
            startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));
        }else {
            showLogOutDialog();
        }
        return false;
    }

    public void showprofile(View view) {
        //showProfile();
    }

    public void onaboutus(View view) {
        //showAboutUs();
    }
}
