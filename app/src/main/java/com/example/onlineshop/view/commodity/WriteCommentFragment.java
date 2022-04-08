package com.example.onlineshop.view.commodity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentWriteCommentsBinding;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.model.User;
import com.example.onlineshop.utils.Repository;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;

import retrofit2.Response;

public class WriteCommentFragment extends DialogFragment {
    public WriteCommentFragment() {
    }

    FragmentWriteCommentsBinding binding;
    WriteCommentFragmentArgs args;
    CommodityActivityViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_comments, container, false);
        args = WriteCommentFragmentArgs.fromBundle(getArguments());
        int productID = args.getProductID();

        Repository repository=  new Repository(getContext());


        //TODO get name&number from ViewModel
        String userNumber = repository.getUserNumber(getContext());
        String userName = repository.getUserName(getContext());

        Comment comment = new Comment();

        comment.setUser(userNumber);
        comment.setUser_name(userName);
        comment.setProduct(productID);

        binding.setEventListener(new EventListener());
        binding.setCommentModel(comment);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this,new CommodityActivityViewModelFactory(getActivity().getApplicationContext())).get(CommodityActivityViewModel.class);

    }

    public class EventListener {
        private static final String TAG = "EventListener";

        public void onSendCommentListener(View view, Comment comment) {
            viewModel.submitComment(comment);

        }


    }


}
