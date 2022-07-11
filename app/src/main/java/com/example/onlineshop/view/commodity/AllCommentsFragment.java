package com.example.onlineshop.view.commodity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAllCommentsBinding;
import com.example.onlineshop.utils.adapters.CommentAdapter;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;

public class AllCommentsFragment extends Fragment {

    FragmentAllCommentsBinding binding;
    CommodityActivityViewModel viewModel;
    AllCommentsFragmentArgs args;

    public AllCommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_comments, container, false);
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new CommodityActivityViewModelFactory(getActivity())).get(CommodityActivityViewModel.class);
        args = AllCommentsFragmentArgs.fromBundle(getArguments());
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        CommentAdapter commentAdapter = new CommentAdapter(CommentAdapter.AdapterType.VERTICAL);
        binding.allCommentsRecyclerView.setAdapter(commentAdapter);
        binding.allCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getComments(args.getProductID()).observe(getViewLifecycleOwner(),
                comments -> {
                    commentAdapter.setComments(comments);
//                    try {
                        binding.allCommentsRecyclerView.scrollToPosition(args.getRecyclerPosition());
//                    }catch
        });


    }

}