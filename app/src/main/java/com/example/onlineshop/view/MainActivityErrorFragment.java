package com.example.onlineshop.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentMainactivityErrorBinding;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

public class MainActivityErrorFragment extends Fragment {


    public MainActivityErrorFragment() {
        // Required empty public constructor
    }

    public String TAG = "MainActivityErrorFragment";
    MainActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentMainactivityErrorBinding binding;

    String message;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mainactivity_error, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        message = getArguments().getString("message");
        viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);

        binding.errorTextview.setText(message);
        binding.errorTextview.setOnClickListener(view1 -> {
            Log.i(TAG, "live : "+viewModel.getError().toString());
            Log.i(TAG, "onclick a : " + getActivity().getString(viewModel.getError().getValue()));
            viewModel.getError().setValue(R.string.no_error);
            Log.i(TAG, "onclick a : " + getActivity().getString(viewModel.getError().getValue()));

            Navigation.findNavController(view1).popBackStack();
            Log.i(TAG, "onClick: pop");

        });

    }
}