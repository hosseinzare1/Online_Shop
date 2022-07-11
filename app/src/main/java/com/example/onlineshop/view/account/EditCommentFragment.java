package com.example.onlineshop.view.account;

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
import com.example.onlineshop.databinding.FragmentEditCommentBinding;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.utils.Utility;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditCommentFragment extends DialogFragment {
    public EditCommentFragment() {
    }

    FragmentEditCommentBinding binding;
    EditCommentFragmentArgs args;
    MainActivityViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_comment, container, false);
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);

        args = EditCommentFragmentArgs.fromBundle(getArguments());

        binding.setEventListener(new EventListener());
        binding.setViewModel(viewModel);


        viewModel.comment_title.setValue(args.getComment().getTitle());
        viewModel.comment_text.setValue(args.getComment().getText());
        viewModel.comment_rating.setValue((float) args.getComment().getRating());

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
                        args.getComment().getId(),
                        Utility.getCurrentSolarHijri(),
                        new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())
                );
                comment.setId(args.getComment().getId());

                viewModel.editComment(comment).observe(getViewLifecycleOwner(), resultCode -> showResult(resultCode, view));

            }

        }

        public void showResult(Integer resultCode, View view) {
            switch (resultCode) {
                case 201:
                    if (getParentFragment() != null) {
                        Snackbar.make(getParentFragment().getView(), getString(R.string.comment_submitting_200), Snackbar.LENGTH_LONG).show();
                        NavHostFragment.findNavController(getParentFragment()).popBackStack();
                    }
                    break;
                case 400:
                    Snackbar.make(view, getString(R.string.comment_submitting_400), Snackbar.LENGTH_LONG).show();
                    break;

            }
        }


    }


}
