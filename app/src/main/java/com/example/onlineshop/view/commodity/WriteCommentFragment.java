package com.example.onlineshop.view.commodity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentWriteCommentBinding;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.utils.Utility;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NonNls;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WriteCommentFragment extends DialogFragment {
    public WriteCommentFragment() {
    }

    FragmentWriteCommentBinding binding;
    WriteCommentFragmentArgs args;
    CommodityActivityViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_comment, container, false);
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


                @NonNls Comment comment = new Comment(
                        viewModel.comment_text.getValue(),
                        viewModel.comment_title.getValue(),
                        viewModel.comment_rating.getValue().intValue(),
                        viewModel.getUserName(),
                        viewModel.getUserNumber(),
                        args.getProductID(), Utility.getCurrentSolarHijri(),
                        new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())
                );

                viewModel.submitComment(comment).observe(getViewLifecycleOwner(), s -> showResult(s, view));
            }

        }

        public void showResult(String resultCode, View view) {
            switch (resultCode) {
                case "200":
                    if (getParentFragment() != null) {
                        Snackbar.make(getParentFragment().getView(), getString(R.string.comment_submitting_successful), Snackbar.LENGTH_LONG).show();
                        NavHostFragment.findNavController(getParentFragment()).popBackStack();
                    }
                    break;
                case "400":
                    Snackbar.make(view, getString(R.string.comment_submitting_failed), Snackbar.LENGTH_LONG).show();
                    break;

            }
        }


    }


}
