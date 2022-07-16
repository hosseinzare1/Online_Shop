package com.example.onlineshop.view.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSubmittedCommentsBinding;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.utils.adapters.SubmittedCommentAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class SubmittedCommentsFragment extends Fragment {

    FragmentSubmittedCommentsBinding binding;
    MainActivityViewModel viewModel;
    SubmittedCommentAdapter adapter;

    public static final String TAG = "SubmittedCommentsFragment";

    public SubmittedCommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_submitted_comments, container, false);
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SubmittedCommentAdapter();
        binding.submittedCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.submittedCommentsRecyclerView.setAdapter(adapter);

        viewModel.getUserComments(viewModel.getUserNumber()).observe(getViewLifecycleOwner(), adapter::setComments);
Fragment fragment = this;
        adapter.setEventListener(new SubmittedCommentAdapter.EventListener() {
            @Override
            public void onDeleteCommentListener(int id) {
                viewModel.deleteComment(id).observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        Log.i(TAG, "onChanged: "+integer);
                        if (integer == 200) {
                            Snackbar.make(view, R.string.successful_comment_delete_message, Snackbar.LENGTH_LONG).show();
                            viewModel.getUserComments(viewModel.getUserNumber()).observe(getViewLifecycleOwner(), adapter::setComments);
                        } else
                            Snackbar.make(view, R.string.unsuccessful_comment_delete_message, Snackbar.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onEditCommentListener(Comment comment) {
                Navigation.findNavController(view).navigate(
                        SubmittedCommentsFragmentDirections.actionSubmittedCommentsFragmentToEditCommentFragment(comment));
            }
        });
    }
}