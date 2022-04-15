package com.example.onlineshop.view.commodity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAllCommentsBinding;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.utils.adapters.CommentsAdapter;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;

import java.util.List;

public class AllCommentsFragment extends Fragment {

    FragmentAllCommentsBinding binding;
    CommodityActivityViewModel viewModel;
    AllCommentsFragmentArgs args;

    public AllCommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_comments, container, false);
        viewModel = new ViewModelProvider(getActivity(), new CommodityActivityViewModelFactory(getActivity())).get(CommodityActivityViewModel.class);
        args = AllCommentsFragmentArgs.fromBundle(getArguments());
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        CommentsAdapter commentsAdapter = new CommentsAdapter(CommentsAdapter.AdapterType.VERTICAL);
        binding.allCommentsRecyclerView.setAdapter(commentsAdapter);
        binding.allCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getComments(args.getProductID()).observe(getViewLifecycleOwner(),
                comments -> commentsAdapter.setComments(comments));


    }

}