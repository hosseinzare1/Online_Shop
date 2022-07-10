package com.example.onlineshop.view.commodity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentDescriptionsBinding;

import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;

public class DescriptionsFragment extends Fragment {

    CommodityActivityViewModel viewModel;
    FragmentDescriptionsBinding binding;
    DescriptionsFragmentArgs args;

    public DescriptionsFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_descriptions, container, false);
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(),new CommodityActivityViewModelFactory(getActivity())).get(CommodityActivityViewModel.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = DescriptionsFragmentArgs.fromBundle(getArguments());
        binding.descriptionTextView.setText(args.getDescription());

    }
}
