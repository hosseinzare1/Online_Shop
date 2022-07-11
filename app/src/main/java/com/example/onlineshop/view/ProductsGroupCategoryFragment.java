package com.example.onlineshop.view;

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
import com.example.onlineshop.databinding.FragmentProductsCategoryBinding;
import com.example.onlineshop.utils.adapters.GroupsAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

public class ProductsGroupCategoryFragment extends Fragment {

    private static final String TAG = "ProductListFragment";
    FragmentProductsCategoryBinding binding;
    MainActivityViewModel viewModel;
    GroupsAdapter adapter;

    public ProductsGroupCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products_category, container, false);
        if (getActivity() != null)
            this.viewModel = new ViewModelProvider(getActivity(),new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
        adapter = new GroupsAdapter(viewModel, getContext());
        binding.groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.groupsRecyclerView.setAdapter(adapter);
        adapter.getItemCount();

        viewModel.getGroups().observe(getViewLifecycleOwner(), groups -> adapter.setGroups(groups));


        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }
}