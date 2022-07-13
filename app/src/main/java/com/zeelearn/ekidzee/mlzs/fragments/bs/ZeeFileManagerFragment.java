package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.zeedoc.adapter.FileDisplayFragmentAdapter;
import com.zee.zeedoc.maanager.FileEventManager;
import com.zee.zeedoc.model.Children;
import com.zee.zeedoc.model.FileModel;
import com.zee.zeedoc.videoplayer.YoutubePlayer;
import com.zeelearn.ekidzee.mlzs.BaseFragment;
import com.zeelearn.ekidzee.mlzs.MyApplication;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.model.DigitalSupportModel;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitService;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitServiceListener;
import com.zeelearn.ekidzee.mlzs.retrofit.request.DigitalSupportRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.request.ECampusLogRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.response.DigitalSupportResponse;
import com.zeelearn.ekidzee.mlzs.utils.Utility;
import com.zeelearn.ekidzee.mlzs.utils.ZeePref;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZeeFileManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZeeFileManagerFragment extends BaseFragment implements RetrofitServiceListener {

    RetrofitServiceListener mServiceListener;
    RelativeLayout layoutNoData;
    private RecyclerView recyclerView;
    private TextView mPath;
    private FileDisplayFragmentAdapter adapter;
    int mCategoryId;

    private List<Children> filesAndFolders = new ArrayList<>();
    private List<Children> masterFilesAndFolders = new ArrayList<>();


    public ZeeFileManagerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ZeeFileManagerFragment newInstance(int category) {
        ZeeFileManagerFragment fragment = new ZeeFileManagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category",category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceListener = this;
        if(getArguments()!=null){
            mCategoryId = getArguments().getInt("category");
        }
        FileEventManager.getInstance().clearPath();
    }

    public boolean isDataExists() {
        return filesAndFolders != null && filesAndFolders.size() != 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_zee_file_manager, container, false);
        ImageView imgHome = view.findViewById(R.id.action_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileEventManager.getInstance().clearPath();
                DigitalSupportRequest request=new DigitalSupportRequest(ZeePref.getUserId(getContext()),toInt(getString(R.string.appid)),mCategoryId);
                request.setSupportPath("");
                RetrofitService.getInstance(getContext()).getDigitalSupportV3(request,mServiceListener);
            }
        });
        DigitalSupportRequest request=new DigitalSupportRequest(ZeePref.getUserId(getContext()),toInt(getString(R.string.appid)),mCategoryId);
        request.setSupportPath("");
        RetrofitService.getInstance(getContext()).getDigitalSupportV3(request,mServiceListener);

        recyclerView = view.findViewById(R.id.recyclerView);
        mPath = view.findViewById(R.id.path);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutNoData = view.findViewById(R.id.layout_nodata);
        layoutNoData.setVisibility(View.GONE);

        
        return view;
    }

    @Override
    public void onFailure(Object obj, Throwable th) {
        hideDialog();
        FileEventManager.getInstance().popPath();
        mPath.setText(FileEventManager.getInstance().getPath());
        /*ConnectionDetector detector = new ConnectionDetector(getContext());
        if(!detector.isConnectedToInternet()){
            showMessageNoInternet();
        }*/

    }

    @Override
    public void onRequestStarted(Object obj) {
        showProgressDialog("Please wait..");
    }

    @Override
    public void onResponse(Object obj) {
        //FileEventManager.getInstance().clearPath();
        if(obj instanceof DigitalSupportResponse){
            DigitalSupportResponse mContentList = (DigitalSupportResponse) obj;
            if(mContentList!=null && mContentList.getmContentList()!=null && mContentList.getmContentList().size()>0){

                generateTree(mContentList.getmContentList());
                layoutNoData.setVisibility(View.GONE);
            }else{

                filesAndFolders.clear();
                updateRoot();
                layoutNoData.setVisibility(View.VISIBLE);
                showAlertMessage(getString(R.string.data_not_found));
            }
        }
    }



    /**************************************
    MUST CHANGE
    **************************************/
    private synchronized void generateTree(List<DigitalSupportModel> contentList){
        if(masterFilesAndFolders==null){
            masterFilesAndFolders = new ArrayList<>();
        }else{
            masterFilesAndFolders.clear();
        }
        Children root = getChildrenBySupportName(contentList.get(0),true);
        masterFilesAndFolders.add(root);
        mPath.setText("");
        HashMap<String,String> objects = new HashMap<>();
        for (int index=0;index<contentList.size();index++){
            //insertNode(contentList.get(index));
            insertNode(contentList.get(index));
            /*if(contentList.get(index).getSupportName().equalsIgnoreCase("No Screen Day")){
                String path = contentList.get(index).getClassName() + "" + contentList.get(index).getSupportName() + "" + contentList.get(index).getRootPath();
                if (!objects.containsKey(path)) {
                    objects.put(path, path);
                    insertNode(contentList.get(index));
                }
            }else {
                insertNode(contentList.get(index));
            }*/
        }

        for (int index = 0; index < masterFilesAndFolders.size(); index++) {
            for (int jIndex = 0; jIndex < masterFilesAndFolders.get(index).getChildren().size(); jIndex++) {
                Collections.sort(masterFilesAndFolders.get(index).getChildren().get(jIndex).getChildren());
                for (int kIndex = 0; kIndex < masterFilesAndFolders.get(index).getChildren().get(jIndex).getChildren().size(); kIndex++) {
                    Collections.sort(masterFilesAndFolders.get(index).getChildren().get(jIndex).getChildren().get(kIndex).getChildren());
                    for (int lIndex = 0; lIndex < masterFilesAndFolders.get(index).getChildren().get(jIndex).getChildren().get(kIndex).getChildren().size(); lIndex++) {
                        Collections.sort(masterFilesAndFolders.get(index).getChildren().get(jIndex).getChildren().get(kIndex).getChildren().get(lIndex).getChildren());
                    }
                }
            }
        }

        //insertNode(contentList.get(0));
        FileModel fileModel = new FileModel();
        fileModel.setChildren(masterFilesAndFolders.get(0).getChildren());
        filesAndFolders.clear();
        filesAndFolders.addAll(fileModel.getChildren());
        masterFilesAndFolders.addAll(fileModel.getChildren());
        updateRoot();
        Children children = new Children();
        children.setName("Root");
        children.setDetailId("");
        children.setChildren(masterFilesAndFolders.get(0).getChildren());
        FileEventManager.getInstance().insertRoot(children);
        hideDialog();
    }

    public void onLoadData(){
        DigitalSupportRequest request=new DigitalSupportRequest(ZeePref.getUserId(getContext()),toInt(getString(R.string.appid)),mCategoryId);
        request.setSupportPath(FileEventManager.getInstance().getPath());
        RetrofitService.getInstance(getContext()).getDigitalSupportV3(request,mServiceListener);
    }

    public void updateRoot() {
        mPath.setText(FileEventManager.getInstance().getPath());
        adapter = new FileDisplayFragmentAdapter(filesAndFolders, onItemClickListenerCallback, getActivity());
        FileEventManager.getInstance().init(getActivity(), null, filesAndFolders, adapter);
        recyclerView.setAdapter(adapter);

    }


    public void insertNode(DigitalSupportModel model){
        // model.setRootPath("C/ONE/TWO/THREE/FOUR/FIVE/SIX");
        if (masterFilesAndFolders == null || masterFilesAndFolders.size() == 0) {
            Children root = getChildrenBySupportName(model, true);
            root.setName(model.getSupportName());
            masterFilesAndFolders.add(root);
        }
        Children children = masterFilesAndFolders.get(0);

        if(TextUtils.isEmpty(model.getUploadType())){
            //DIRECTORY
            children.getChildren().add(getChildrenBySupportName(model, true));
        }else{
            //Actual Data
            Children directory = getChildrenBySupportName(model, false);
            directory.setName(model.getContentDescription());
            children.getChildren().add(directory);
        }

    }

    private int isDirectoryFound(Children children, String directoryName){
        int childIndex = -1;
        if(children!=null && children.getChildren()!=null){
            for (int index = 0; index < children.getChildren().size(); index++) {
                if(children.getChildren().get(index).getName().equalsIgnoreCase(directoryName)){
                    childIndex = index;
                    break;
                }
            }
        }
        return childIndex;
    }

    public Children getChildren(DigitalSupportModel model, boolean isDirectory) {
        Children children = new Children();
        if (model.getUrl().contains("https://youtu.be/")) {
            model.setUrl(model.getUrl().replace("https://youtu.be/", ""));
        }
        children.setDate(model.getSupportDate());
        children.setPath(model.getUrl());
        children.setChildren(new ArrayList<>());
        if (isDirectory) {
            children.setType("folder");
            children.setName(model.getSubjectName().trim());
        } else {
            children.setType(model.getUploadType());
            children.setName(model.getContentDescription().trim());
        }

        return children;
    }

    public Children getChildrenBySupportName(DigitalSupportModel model, boolean isDirectory) {
        Children children = new Children();
        children.setPath(model.getUrl());
        children.setDate(model.getSupportDate());
        children.setChildren(new ArrayList<>());
        if (isDirectory) {
            children.setType("folder");
        } else {
            children.setType(model.getUploadType());
        }
        children.setName(model.getContentDescription().trim());
        return children;
    }



    private FileDisplayFragmentAdapter.OnItemClickListener onItemClickListenerCallback = new FileDisplayFragmentAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View view, int position) {
            Children singleItem = filesAndFolders.get(position);
            FileEventManager.getInstance().pushPath(singleItem);
            mPath.setText(FileEventManager.getInstance().getPath());
            String url =  "";//
            if(filesAndFolders!=null)
            url =  filesAndFolders.get(position).getPath();
            if (view == null) {
                ((MyApplication) getActivity().getApplication()).saveCampusEvent(singleItem.getName(), singleItem.getType());
            } else if (FileDisplayFragmentAdapter.isAudio(singleItem.getType())) {
                Utility.showAudioFile(getChildFragmentManager(),singleItem.getName(),singleItem.getPath());
            }else if (singleItem.getType().equalsIgnoreCase("youtube")) {
                ((MyApplication) getActivity().getApplication()).saveCampusEvent(singleItem.getName(), singleItem.getType());
                showProgressDialog("");
                new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message message) {
                        return false;
                    }
                }).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent youtubeIntent = new Intent(getContext(), YoutubePlayer.class);
                        youtubeIntent.putExtra("vid", filesAndFolders.get(position).getPath());
                        startActivity(youtubeIntent);
                    }
                }, 1000);


            } else {
                    //FileEventManager.getInstance().open(singleItem);
                    onLoadData();
            }
            if(url!=null && !TextUtils.isEmpty(url)){
                ECampusLogRequest request = new ECampusLogRequest(ZeePref.getUserId(getContext()),
                        ZeePref.getUserType(getContext()),
                        toInt(getString(R.string.appid)),
                        Utility.getLocalIpAddress(),
                        Utility.getSource(),
                        url);
                RetrofitService.getInstance(getContext()).sendAuditLogEvent(request,null);
            }

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }

        @Override
        public void onIconClick(View view, int position) {

        }

        @Override
        public void onError(String message) {
            showErrorMessage(message);
        }
    };

    public void pop() {
        FileEventManager.getInstance().popPath();
        mPath.setText(FileEventManager.getInstance().getPath());
    }
}