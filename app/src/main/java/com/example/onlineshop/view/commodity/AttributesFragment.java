package com.example.onlineshop.view.commodity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAttributesBinding;
import com.example.onlineshop.utils.adapters.AttributesAdapter;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;

public class AttributesFragment extends Fragment {

    FragmentAttributesBinding binding;
    CommodityActivityViewModel viewModel;
    AttributesAdapter adapter;
    AttributesFragmentArgs args;
    int productID;
    public  final String TAG ="AttributesFragment" ;

    public AttributesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_attributes, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity(),new CommodityActivityViewModelFactory(getActivity())).get(CommodityActivityViewModel.class);
        adapter = new AttributesAdapter();
        args = AttributesFragmentArgs.fromBundle(getArguments());
        productID = args.getProductId();
        binding.specificationsRecyclerView.setAdapter(adapter);
        binding.specificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getAttributes(productID).observe(getViewLifecycleOwner(),
                attributes -> {
                    adapter.setAttributes(attributes);
                    Log.i(TAG, "onViewCreated:a "+attributes.get(0).getAttribute());
                    Log.i(TAG, "onViewCreated:v "+attributes.get(0).getAttribute_value());
        }
        );
//        adapter.setAttributes();
    }
}