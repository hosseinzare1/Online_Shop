package com.example.onlineshop.view.commodity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentCommodityMainBinding;
import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.adapters.CommentsAdapter;
import com.example.onlineshop.utils.adapters.ImageSliderAdapter;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

import java.util.List;

public class CommodityMainFragment extends Fragment {

    FragmentCommodityMainBinding binding;
    CommodityActivityViewModel viewModel;

    ImageSliderAdapter imageSliderAdapter;
    CommentsAdapter commentsAdapter;
    int id;
    private String TAG = "CommodityMainFragment";

    public CommodityMainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_commodity_main, container, false);
        viewModel = new ViewModelProvider(this,new CommodityActivityViewModelFactory(getActivity().getApplicationContext())).get(CommodityActivityViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getInt("id");

        imageSliderAdapter = new ImageSliderAdapter();
        binding.detailsImagesViewPager.setAdapter(imageSliderAdapter);
        binding.setEventListener(new CommodityMainEventListener());

        commentsAdapter = new CommentsAdapter();
        binding.detailsCommentsRecyclerView.setAdapter(commentsAdapter);
        binding.detailsCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, false));

        binding.setMainActivityViewModel(new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory(getActivity().getApplication())).get(MainActivityViewModel.class));


        viewModel.getProduct(id).observe(getViewLifecycleOwner(), product -> {
            binding.setProductModel(product);
//            binding.priceTextview.setText(product.getPrice());

        });


        viewModel.getImages(id).observe(getViewLifecycleOwner(), images -> {
            imageSliderAdapter.setImages(images);

        });

        viewModel.getComments(id).observe(getViewLifecycleOwner(), comments -> {

            commentsAdapter.setComments(comments);
//            Log.i(TAG, "onViewCreated: " + comments.get(0).getText());


        });

    }

    public class CommodityMainEventListener {

        public void onAddToCart(View view, Product product, MainActivityViewModel viewModel) {
            viewModel.addCartItem(new CartItemModel(0,product.getName(), product.getImageUrl(), product.getPrice(),1));
        }

        public void onSeeSpecifications(View view, Product product) {
            Navigation.findNavController(view).navigate(CommodityMainFragmentDirections.
                    actionCommodityDetailsFragmentToAttributesFragment(Integer.parseInt(product.getId())));
        }

        public void onSeeDescriptions(View view, Product product) {
            Log.i(TAG, "onSeeDescriptions: on seeDescription ");
            Navigation.findNavController(view).navigate(
                    CommodityMainFragmentDirections.actionCommodityDetailsFragmentToDescriptionsFragment(product.getDescription()));
        }

        public void onWriteComment(View view, Product product) {

            Navigation.findNavController(view)
                    .navigate(
                            CommodityMainFragmentDirections.actionCommodityDetailsFragmentToWriteCommentFragment(Integer.parseInt(product.getId()))
                    );

        }


    }

}