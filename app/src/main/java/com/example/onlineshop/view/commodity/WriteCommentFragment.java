package com.example.onlineshop.view.commodity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentWriteCommentsBinding;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.utils.Utility;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new CommodityActivityViewModelFactory(getActivity())).get(CommodityActivityViewModel.class);

        args = WriteCommentFragmentArgs.fromBundle(getArguments());

        binding.setEventListener(new EventListener());
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public class EventListener {
        private static final String TAG = "EventListener";

        public void onSendCommentListener(View view) {
            viewModel.isCommentFormValid();
            if (viewModel.isCommentFormValid()) {


                Comment comment = new Comment(
                        viewModel.comment_text.getValue(),
                        viewModel.comment_title.getValue(),
                        viewModel.comment_rating.getValue().intValue(),
                        viewModel.getUserName(),
                        viewModel.getUserNumber(),
                        args.getProductID(), Utility.getCurrentShamsidate(),
                        new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())
                );
                Log.i(TAG, "onSendCommentListener: date :"+comment.getSubmit_date());
                Log.i(TAG, "onSendCommentListener: time :"+comment.getSubmit_time());

                viewModel.submitComment(comment).observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        showResult(s, view);
                    }
                });
            } else {
                //show Snackbar of Rating invalid
                Snackbar.make(view, getString(R.string.RATING_INVALID), Snackbar.LENGTH_LONG).show();
            }

        }

        public void showResult(String resultCode, View view) {
            switch (resultCode) {
                case "200":
                    if (getParentFragment() != null) {
                        Snackbar.make(getParentFragment().getView(), getString(R.string.comment_submitting_200), Snackbar.LENGTH_LONG).show();
                        NavHostFragment.findNavController(getParentFragment()).popBackStack();
                        viewModel.clearCommentForm();
                    }
                    break;
                case "400":
                    Snackbar.make(view, getString(R.string.comment_submitting_400), Snackbar.LENGTH_LONG).show();
                    break;

            }
        }


    }


}
