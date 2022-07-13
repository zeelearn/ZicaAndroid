package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.zeedoc.adapter.FileDisplayFragmentAdapter;
import com.zee.zeedoc.maanager.FileEventManager;
import com.zee.zeedoc.model.Children;
import com.zee.zeedoc.model.FileModel;
import com.zee.zeedoc.videoplayer.YoutubePlayer;
import com.zeelearn.ekidzee.mlzs.BaseFragment;
import com.zeelearn.ekidzee.mlzs.BuildConfig;
import com.zeelearn.ekidzee.mlzs.MyApplication;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitService;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitServiceListener;
import com.zeelearn.ekidzee.mlzs.retrofit.request.LogRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.response.CampusModel;
import com.zeelearn.ekidzee.mlzs.retrofit.response.Data;
import com.zeelearn.ekidzee.mlzs.retrofit.response.ECampusResponse;
import com.zeelearn.ekidzee.mlzs.utils.LocalConstance;
import com.zeelearn.ekidzee.mlzs.utils.ZeePref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ECampusFragment extends BaseFragment implements RetrofitServiceListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final String PREF_IS_CASE_SENSITIVE = "IS_CASE_SENSITIVE";
    private final String PREF_SHOW_HIDDEN_FILES = "SHOW_HIDDEN_FILES";
    private final String PREF_SORT_ORDER = "SORT_ORDER";
    private final String PREF_SORT_BY = "SORT_BY";


    private final String EVENT_ECAMPUS = "eCampus";


    private RecyclerView recyclerView;
    private List<Children> filesAndFolders = new ArrayList<>();
    private List<Children> masterFilesAndFolders = new ArrayList<>();
    private Toolbar toolbar;
    private FileDisplayFragmentAdapter adapter;
    private SharedPreferences prefs;
    private boolean clickAllowed;

    TextView txtDataNotFound;
    TextView txtHeader;

    boolean isListRender = false;

    RetrofitServiceListener mListener;

    public ECampusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ECampusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ECampusFragment newInstance(String param1, String param2) {
        ECampusFragment fragment = new ECampusFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filesAndFolders.clear();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        FileEventManager.getInstance().init(getActivity(), null, filesAndFolders, adapter);
        mListener = this;

    }

    public boolean isDataExists() {
        return filesAndFolders != null && filesAndFolders.size() != 0;
    }

    public void updateRoot() {
        filesAndFolders.clear();
        filesAndFolders.addAll(masterFilesAndFolders);
        adapter = new FileDisplayFragmentAdapter(filesAndFolders, onItemClickListenerCallback, getActivity());
        FileEventManager.getInstance().init(getActivity(), null, filesAndFolders, adapter);

        //FileEventManager.getInstance().getFileManager().initialisePathStackWithAbsolutePath(true,filesAndFolders.get(0).getChildren());

        //recyclerView.setLayoutManager(gridLayoutManager);
        //FileEventManager.getInstance().open(filesAndFolders.get(0).getChildren().get(0));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_display, container, false);
        txtHeader = view.findViewById(R.id.header);
        setRetainInstance(true);

        recyclerView = view.findViewById(R.id.recyclerView);
        /*GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);*/
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        txtDataNotFound = view.findViewById(R.id.txtdatanotfound);
        txtDataNotFound.setVisibility(View.GONE);

        clickAllowed = true;

        showProgressDialog("");
        if(BuildConfig.FLAVOR.equalsIgnoreCase("etest")){
            RetrofitService.getInstance(getActivity()).getECampusTest(ZeePref.getUID(getContext()), mListener);
        }else {
            RetrofitService.getInstance(getActivity()).getECampus(ZeePref.getUID(getContext()), mListener);
        }

//        OverTheAirOkhttp.sendPostRequest(getString(R.string.BASE_URL) + "GetDigitalSupport", requestData, new OnResponseListener() {
//            @Override
//            public void onSuccess(Object mObject) {
//                if (mObject instanceof CampusResponse) {
//                    CampusResponse campusModel = (CampusResponse) mObject;
//                    if (campusModel != null && campusModel.getModelList().size() > 0) {
//                        List<CampusModel> modelList = campusModel.getModelList();
//                        FileModel fileModel = new FileModel();
//                        fileModel.setName("ROOT");
//                        HashMap<String, Integer> subjectMaster = new HashMap<>();
//                        HashMap<String, Integer> dayMaster = new HashMap<>();
//                        HashMap<String, Integer> classMaster = new HashMap<>();
//                        HashMap<String, Integer> supportMaster = new HashMap<>();
//                        HashMap<String, Integer> contentMaster = new HashMap<>();
//
//                        List<Children> classList = new ArrayList<>();
//                        for (int index = 0; index < modelList.size(); index++) {
//
//                            ///LEVEL 1 CLASS
//                            if (!classMaster.containsKey(modelList.get(index).getClassName())) {
//                                classList.add(getChildrenByClass(modelList.get(index), true));
//                                classMaster.put(modelList.get(index).getClassName(), classList.size() - 1);
//                            }
//
//                            int classId = classMaster.get(modelList.get(index).getClassName());
//                            String supportName = modelList.get(index).getSupportName();
//                            String dayName="";
//                            //System.out.println("D/OkHttp supportName found  "+supportName);
//                           if (true || !supportName.trim().equalsIgnoreCase("Week 1")) {
//
//                                if (!supportMaster.containsKey(classId + "_" + supportName)) {
//                                    Children child = getChildrenBySupportName(modelList.get(index), true);
//                                    child.setName(supportName);
//                                    classList.get(classId).getChildren().add(child);
//                                    supportMaster.put(classId + "_" + supportName, classList.get(classId).getChildren().size() - 1);
//                                }
//                                int supportId = supportMaster.get(classId + "_" + supportName);
//
//                                String subjectName = modelList.get(index).getSubjectName();
//                                if (!subjectMaster.containsKey(classId + "_" + supportId + "_" + subjectName)) {
//                                    classList.get(classId).getChildren().get(supportId).getChildren().add(getChildren(modelList.get(index), true));
//                                    subjectMaster.put(classId + "_" + supportId + "_" + subjectName, (classList.get(classId).getChildren().get(supportId).getChildren().size() - 1));
//                                }
//                                int subjectId = -1;
//                                if (subjectMaster.containsKey(classId + "_" + supportId + "_" + subjectName)) {
//                                    subjectId = subjectMaster.get(classId + "_" + supportId + "_" + subjectName);
//                                }
//                                //LEVEL 3 SUBJECT
//                                int dayId = -1;
//                                if (!TextUtils.isEmpty(dayName) && !dayMaster.containsKey(subjectId + "_" + classId + "_" + dayName)) {
//                                    Children child = getChildren(modelList.get(index), true);
//                                    child.setName(dayName.trim());
//                                    if (classId == 0) {
//                                        Log.d("LOGS", "");
//                                    }
//                                    if (subjectId >= 0) {
//                                        classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().add(child);
//                                        dayId = classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().size() - 1;
//                                        dayMaster.put(subjectId + "_" + classId + "_" + dayName, dayId);
//                                        Log.d("DayCare", classId + dayName + "     " + dayId);
//                                    } else {
//                                        classList.get(classId).getChildren().get(supportId).getChildren().add(child);
//                                        dayId = classList.get(classId).getChildren().get(supportId).getChildren().size() - 1;
//                                        dayMaster.put(subjectId + "_" + classId + "_" + dayName, dayId);
//                                        Log.d("DayCare", classId + dayName + "     " + dayId);
//                                    }
//                                }else{
//
//                                }
//                                dayId = -1;
//                                if (dayMaster.containsKey(subjectId + "_" + classId + "_" + dayName)) {
//                                    dayId = dayMaster.get(subjectId + "_" + classId + "_" + dayName);
//                                }
//
//                                int contentId = -1;
//                                if (modelList.get(index).getContentDescription().contains("~")) {
//                                    String[] contentName = modelList.get(index).getContentDescription().split("~");
//                                    String indexName = classId + "_" + supportId + "_" + subjectId + "_" + dayId + "_" + contentName[0].trim();
//
//                                    if (!contentMaster.containsKey(indexName)) {
//                                        Children child = getChildren(modelList.get(index), true);
//                                        child.setName(contentName[0].trim());
//
//                                        if (dayId >= 0 && subjectId >= 0) {
//                                            classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().add(child);
//                                            contentMaster.put(indexName, classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().size() - 1);
//                                        } else {
//                                            if (subjectId >= 0) {
//                                                classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().add(child);
//                                                contentMaster.put(indexName, classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().size() - 1);
//                                            } else {
//                                                classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().add(child);
//                                                contentMaster.put(indexName, classList.get(classId).getChildren().get(supportId).getChildren().size() - 1);
//                                            }
//                                        }
//                                    }
//                                    if (dayId >= 0 && contentMaster.containsKey(indexName)) {
//                                        contentId = contentMaster.get(classId + "_" + supportId + "_" + subjectId + "_" + dayId + "_" + contentName[0].trim());
//                                    }else if(subjectId >=0 && contentMaster.containsKey(indexName)){
//                                        contentId = contentMaster.get(indexName);
//                                    } else if (contentMaster.containsKey(classId + "_" + supportId + "_" + subjectId + contentName[0].trim())) {
//                                        contentId = contentMaster.get(indexName);
//                                    }
//
//
//                                    Children child = getChildren(modelList.get(index), false);
//                                    child.setName(contentName[1].trim());
//                                    if (dayId >= 0 && contentId >= 0 && subjectId >= 0) {
//                                        try {
//                                            classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().get(contentId).getChildren().add(child);
//                                        } catch (Exception e) {
//                                            classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().add(child);
//                                        }
//                                    } else if (dayId >= 0 && subjectId >= 0) {
//                                        classList.get(classId).getChildren().get(supportId).getChildren().get(dayId).getChildren().add(child);
//                                    } else if (dayId >= 0) {
//                                        classList.get(classId).getChildren().get(supportId).getChildren().get(dayId).getChildren().add(child);
//                                    } else {
//                                        try {
//                                            classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(contentId).getChildren().add(child);
//
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                } else {
//                                    if (dayId >= 0 && subjectId >= 0) {
//                                        classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().add(getChildren(modelList.get(index), false));
//                                    } else if (subjectId <= 0 && dayId >= 0) {
//                                        classList.get(classId).getChildren().get(supportId).getChildren().get(dayId).getChildren().add(getChildren(modelList.get(index), false));
//                                    } else if (subjectId < 0) {
//                                        classList.get(classId).getChildren().get(supportId).getChildren().add(getChildren(modelList.get(index), false));
//                                    } else {
//                                        classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().add(getChildren(modelList.get(index), false));
//                                    }
//                                }
//                            }
//                        }
//
//
//                        if (classList == null || classList.get(0) == null || classList.get(0).getChildren().size() <= 0) {
//                            txtDataNotFound.setVisibility(View.VISIBLE);
//                            hideDialog();
//                            showErrormessageAndFinish("Data not Found, Please try again later");
//                        } else {
//
//                            //updateRoot();
//                            new asynUpdateData(classList).execute();
//                        }
//
//                    } else {
//                        showErrormessageAndFinish("Data not Found, Please try again later");
//                        txtDataNotFound.setVisibility(View.VISIBLE);
//                        //showErrorAlert("Error","Unable to load Data Please try again later");
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object mObject) {
//
//            }
//        });

        return view;
    }

    @Override
    public void onRequestStarted(Object mObject) {
        showProgressDialog("");
    }

    @Override
    public void onResponse(Object mObject) {
        if(mObject instanceof ECampusResponse){
            ECampusResponse response = (ECampusResponse) mObject;
            Data campusModel = response.getData();
            if (campusModel != null && campusModel.getModelList().size() > 0) {
                List<CampusModel> modelList = campusModel.getModelList();
                FileModel fileModel = new FileModel();
                fileModel.setName("ROOT");
                HashMap<String, Integer> subjectMaster = new HashMap<>();
                HashMap<String, Integer> dayMaster = new HashMap<>();
                HashMap<String, Integer> classMaster = new HashMap<>();
                HashMap<String, Integer> supportMaster = new HashMap<>();
                HashMap<String, Integer> contentMaster = new HashMap<>();

                List<Children> classList = new ArrayList<>();
                for (int index = 0; index < modelList.size(); index++) {

                    ///LEVEL 1 CLASS
                    if (!classMaster.containsKey(modelList.get(index).getClassName())) {
                        classList.add(getChildrenByClass(modelList.get(index), true));
                        classMaster.put(modelList.get(index).getClassName(), classList.size() - 1);
                    }

                    int classId = classMaster.get(modelList.get(index).getClassName());
                    String supportName = modelList.get(index).getSupportName();
                    String dayName="";
                    //System.out.println("D/OkHttp supportName found  "+supportName);
                    if (true || !supportName.trim().equalsIgnoreCase("Week 1")) {

                        if (!supportMaster.containsKey(classId + "_" + supportName)) {
                            Children child = getChildrenBySupportName(modelList.get(index), true);
                            child.setName(supportName);
                            classList.get(classId).getChildren().add(child);
                            supportMaster.put(classId + "_" + supportName, classList.get(classId).getChildren().size() - 1);
                        }
                        int supportId = supportMaster.get(classId + "_" + supportName);

                        String subjectName = modelList.get(index).getSubjectName();
                        if (!subjectMaster.containsKey(classId + "_" + supportId + "_" + subjectName)) {
                            classList.get(classId).getChildren().get(supportId).getChildren().add(getChildren(modelList.get(index), true));
                            subjectMaster.put(classId + "_" + supportId + "_" + subjectName, (classList.get(classId).getChildren().get(supportId).getChildren().size() - 1));
                        }
                        int subjectId = -1;
                        if (subjectMaster.containsKey(classId + "_" + supportId + "_" + subjectName)) {
                            subjectId = subjectMaster.get(classId + "_" + supportId + "_" + subjectName);
                        }
                        //LEVEL 3 SUBJECT
                        int dayId = -1;
                        if (!TextUtils.isEmpty(dayName) && !dayMaster.containsKey(subjectId + "_" + classId + "_" + dayName)) {
                            Children child = getChildren(modelList.get(index), true);
                            child.setName(dayName.trim());
                            if (classId == 0) {
                                Log.d("LOGS", "");
                            }
                            if (subjectId >= 0) {
                                classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().add(child);
                                dayId = classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().size() - 1;
                                dayMaster.put(subjectId + "_" + classId + "_" + dayName, dayId);
                                Log.d("DayCare", classId + dayName + "     " + dayId);
                            } else {
                                classList.get(classId).getChildren().get(supportId).getChildren().add(child);
                                dayId = classList.get(classId).getChildren().get(supportId).getChildren().size() - 1;
                                dayMaster.put(subjectId + "_" + classId + "_" + dayName, dayId);
                                Log.d("DayCare", classId + dayName + "     " + dayId);
                            }
                        }else{

                        }
                        dayId = -1;
                        if (dayMaster.containsKey(subjectId + "_" + classId + "_" + dayName)) {
                            dayId = dayMaster.get(subjectId + "_" + classId + "_" + dayName);
                        }

                        int contentId = -1;
                        if (modelList.get(index).getContentDescription().contains("~")) {
                            String[] contentName = modelList.get(index).getContentDescription().split("~");
                            String indexName = classId + "_" + supportId + "_" + subjectId + "_" + dayId + "_" + contentName[0].trim();

                            if (!contentMaster.containsKey(indexName)) {
                                Children child = getChildren(modelList.get(index), true);
                                child.setName(contentName[0].trim());

                                if (dayId >= 0 && subjectId >= 0) {
                                    classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().add(child);
                                    contentMaster.put(indexName, classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().size() - 1);
                                } else {
                                    if (subjectId >= 0) {
                                        classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().add(child);
                                        contentMaster.put(indexName, classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().size() - 1);
                                    } else {
                                        classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().add(child);
                                        contentMaster.put(indexName, classList.get(classId).getChildren().get(supportId).getChildren().size() - 1);
                                    }
                                }
                            }
                            if (dayId >= 0 && contentMaster.containsKey(indexName)) {
                                contentId = contentMaster.get(classId + "_" + supportId + "_" + subjectId + "_" + dayId + "_" + contentName[0].trim());
                            }else if(subjectId >=0 && contentMaster.containsKey(indexName)){
                                contentId = contentMaster.get(indexName);
                            } else if (contentMaster.containsKey(classId + "_" + supportId + "_" + subjectId + contentName[0].trim())) {
                                contentId = contentMaster.get(indexName);
                            }


                            Children child = getChildren(modelList.get(index), false);
                            child.setName(contentName[1].trim());
                            if (dayId >= 0 && contentId >= 0 && subjectId >= 0) {
                                try {
                                    classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().get(contentId).getChildren().add(child);
                                } catch (Exception e) {
                                    classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().add(child);
                                }
                            } else if (dayId >= 0 && subjectId >= 0) {
                                classList.get(classId).getChildren().get(supportId).getChildren().get(dayId).getChildren().add(child);
                            } else if (dayId >= 0) {
                                classList.get(classId).getChildren().get(supportId).getChildren().get(dayId).getChildren().add(child);
                            } else {
                                try {
                                    classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(contentId).getChildren().add(child);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            if (dayId >= 0 && subjectId >= 0) {
                                classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().get(dayId).getChildren().add(getChildren(modelList.get(index), false));
                            } else if (subjectId <= 0 && dayId >= 0) {
                                classList.get(classId).getChildren().get(supportId).getChildren().get(dayId).getChildren().add(getChildren(modelList.get(index), false));
                            } else if (subjectId < 0) {
                                classList.get(classId).getChildren().get(supportId).getChildren().add(getChildren(modelList.get(index), false));
                            } else {
                                classList.get(classId).getChildren().get(supportId).getChildren().get(subjectId).getChildren().add(getChildren(modelList.get(index), false));
                            }
                        }
                    }
                }


                if (classList == null || classList.get(0) == null || classList.get(0).getChildren().size() <= 0) {
                    txtDataNotFound.setVisibility(View.VISIBLE);
                    hideDialog();
                    showErrormessageAndFinish("Data not Found, Please try again later");
                } else {

                    //updateRoot();
                    new asynUpdateData(classList).execute();
                }

            }
        }else if (mObject instanceof FileModel){
            FileModel fileModel = (FileModel) mObject;
            Collections.sort(fileModel.getChildren());
            filesAndFolders.clear();
            filesAndFolders.addAll(fileModel.getChildren());
            masterFilesAndFolders.clear();
            masterFilesAndFolders.addAll(fileModel.getChildren());
            updateRoot();
            hideDialog();
        }
    }

    @Override
    public void onFailure(Object mObject, Throwable t) {

    }

    class asynUpdateData extends AsyncTask<Void, Void, Void> {

        List<Children> mList;

        public asynUpdateData(List<Children> classList) {
            mList = classList;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Collections.sort(mList.get(0).getChildren(),Collections.reverseOrder());

            FileModel fileModel = new FileModel();
            fileModel.setChildren(mList.get(0).getChildren());
            filesAndFolders.clear();
            filesAndFolders.addAll(fileModel.getChildren());
            masterFilesAndFolders.clear();
            masterFilesAndFolders.addAll(fileModel.getChildren());
            updateRoot();
            hideDialog();
        }
    }

    public Children getChildren(CampusModel model, boolean isDirectory) {
        Children children = new Children();
        if (model.getUrl().contains("https://youtu.be/")) {
            model.setUrl(model.getUrl().replace("https://youtu.be/", ""));
        }
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

    public Children getChildrenBySupportName(CampusModel model, boolean isDirectory) {
        Children children = new Children();
        children.setPath(model.getUrl());
        children.setChildren(new ArrayList<>());
        if (isDirectory) {
            children.setType("folder");
        } else {
            children.setType(model.getUploadType());
        }
        children.setName(model.getSupportName().trim());
        return children;
    }

    public Children getChildrenByClass(CampusModel model, boolean isDirectory) {
        Children children = new Children();
        children.setPath(model.getUrl());
        children.setChildren(new ArrayList<>());
        if (isDirectory) {
            children.setType("folder");
        } else {
            children.setType(model.getUploadType());
        }
        children.setName(model.getClassName().trim());
        return children;
    }



    private FileDisplayFragmentAdapter.OnItemClickListener onItemClickListenerCallback = new FileDisplayFragmentAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View view, int position) {
            Children singleItem = filesAndFolders.get(position);
            LogRequest logRequest = new LogRequest(EVENT_ECAMPUS,ZeePref.getUserType(getContext()),String.valueOf(ZeePref.getUserId(getContext())),
                    LocalConstance.ACTION_NA,String.valueOf(ZeePref.getUuserCode(getContext())),singleItem.getName(),singleItem.getType());
            if(view==null){
                ((MyApplication) getActivity().getApplication()).saveCampusEvent(singleItem.getName(), singleItem.getType());
                RetrofitService.getInstance(getContext()).insertLogs(logRequest,null);
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
                        youtubeIntent.putExtra("vid",filesAndFolders.get(position).getPath());
                        startActivity(youtubeIntent);
                    }
                },10);

                RetrofitService.getInstance(getContext()).insertLogs(logRequest,null);
            }else {
                if (clickAllowed)
                    FileEventManager.getInstance().open(singleItem);
            }

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }

        @Override
        public void onIconClick(View view, int position) {

        }

        @Override
        public void onError(String s) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (prefs != null) {
            setPrefs();
            FileEventManager.getInstance().refreshCurrentDirectory();
            if(filesAndFolders.size()>0)
                hideDialog();
        }
    }

    private void setPrefs() {

        FileEventManager
                .getInstance()
                .getFileManager()
                .setShowHiddenFiles(
                        prefs.getBoolean(PREF_IS_CASE_SENSITIVE, false)
                );

        FileEventManager
                .getInstance()
                .getFileManager()
                .setSortingStyle(
                        prefs.getString(PREF_SORT_ORDER, FileEventManager.SORT_ORDER_ASC),
                        prefs.getString(PREF_SORT_BY, FileEventManager.SORT_BY_NAME),
                        prefs.getBoolean(PREF_SHOW_HIDDEN_FILES, false)
                );

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
