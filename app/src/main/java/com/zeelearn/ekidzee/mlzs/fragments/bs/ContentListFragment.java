package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.adapter.ContentAdapter;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitService;
import com.zeelearn.ekidzee.mlzs.retrofit.request.TipsRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.response.NewsResponse;
import com.zeelearn.news.retrofit.BaseFragment;
import com.zeelearn.news.retrofit.RetrofitServiceListener;
import com.zeelearn.news.retrofit.response.tips.ContentResponse;
import com.zeelearn.news.retrofit.utils.ZeeNewsPref;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link com.zeelearn.news.retrofit.fragments.ContentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentListFragment extends BaseFragment implements RetrofitServiceListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CONTENTTYPE = "NEWS";

    ContentAdapter adapter;
    TextView txtNoData;
    String mContentType;

    private RecyclerView contentRecycler;
    RetrofitServiceListener mServicelistener;

    List<ContentResponse> mContentList = new ArrayList<>();

    public ContentListFragment() {
        // Required empty public constructor
    }

    public static ContentListFragment newInstance(String contentType) {
        ContentListFragment fragment = new ContentListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENTTYPE, contentType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentType = getArguments().getString(ARG_CONTENTTYPE);
        }
        mServicelistener= this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_content_list, container, false);
        txtNoData = view.findViewById(R.id.nodatafound);
        contentRecycler = view.findViewById(com.zeelearn.news.R.id.recycler);
        contentRecycler.setHasFixedSize(true);
        contentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //contentRecycler.addItemDecoration(new VerticalLineDecorator(2));
        adapter = new ContentAdapter(getContext(),mContentList);
        contentRecycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RetrofitService.getInstance(getActivity()).getTips(new TipsRequest(String.valueOf(ZeeNewsPref.getUserId(getContext())),
                ZeeNewsPref.getUserType(getContext()),1,10,mContentType),mServicelistener);
    }

    @Override
    public void onRequestStarted(Object mObject) {
        showProgressDialog("");

    }

    @Override
    public void onResponse(Object mObject) {
        if(mObject instanceof NewsResponse) {
            NewsResponse response = (NewsResponse) mObject;

            List<ContentResponse> list = response.getModelList();
            if (list != null && list.size()>0) {
                mContentList.addAll(list);

                txtNoData.setVisibility(View.GONE);
            }else{
                txtNoData.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        }
        hideDialog();
    }

    @Override
    public void onFailure(Object mObject, Throwable t) {
        hideDialog();
        showErrorAlert("Error","Unable to load list");
    }
}
