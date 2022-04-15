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

import android.graphics.Paint;
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
import com.example.onlineshop.utils.adapters.HorizontalProductsAdapter;
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

    HorizontalProductsAdapter sameProductsAdapter;
    int id;
    private String TAG = "CommodityMainFragment";

    public CommodityMainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_commodity_main, container, false);
        viewModel = new ViewModelProvider(getActivity(), new CommodityActivityViewModelFactory(getActivity())).get(CommodityActivityViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getInt("id");

        imageSliderAdapter = new ImageSliderAdapter();
        binding.detailsImagesViewPager.setAdapter(imageSliderAdapter);
        binding.setEventListener(new CommodityMainEventListener());

        binding.detailsImageViewpageIndicator.setViewPager(binding.detailsImagesViewPager);


        commentsAdapter = new CommentsAdapter(CommentsAdapter.AdapterType.HORIZONTAL);
        binding.detailsCommentsRecyclerView.setAdapter(commentsAdapter);
        binding.detailsCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, true));

        sameProductsAdapter = new HorizontalProductsAdapter();
        binding.detailsSameCommodityRecyclerView.setAdapter(sameProductsAdapter);
        binding.detailsSameCommodityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        binding.setMainActivityViewModel(new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory(getActivity().getApplication())).get(MainActivityViewModel.class));

        binding.orgPriceCommodityMain.setPaintFlags(
                binding.orgPriceCommodityMain.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
        );


        viewModel.getProduct(id).observe(getViewLifecycleOwner(), product -> {
            binding.setModel(product);

// create new object because id must be 0.
            viewModel.addHistoryItem(new Product(id, 0,
                    product.getName(),
                    product.getDescription(),
                    product.getImageUrl(),
                    product.getPrice(),
                    product.getDiscount()));

        });

        viewModel.getImages(id).observe(getViewLifecycleOwner(), images -> {
            imageSliderAdapter.setImages(images);
        });

        viewModel.getComments(id).observe(getViewLifecycleOwner(), comments -> {

            // show noComments textView if comments is empty
            if (comments.size() > 0) {
                binding.detailsNoCommentsTextView.setVisibility(View.GONE);
                commentsAdapter.setComments(comments);
            } else {
                binding.detailsNoCommentsTextView.setVisibility(View.VISIBLE);
            }

            binding.deatilsCommentsCountTextView.setText(comments.size() + " نظر ");


        });

        viewModel.getSameProducts(id).observe(getViewLifecycleOwner(), products -> {

            // Make the list invisible if empty
            if (products.size() > 0) {
                binding.sameCommodityLayout.setVisibility(View.VISIBLE);
                sameProductsAdapter.setItems(products);
            } else {
                binding.sameCommodityLayout.setVisibility(View.GONE);
            }
        });

        viewModel.getCartItems().observe(getViewLifecycleOwner(), cartItemModels -> {

            boolean isItemExistInCart = false;
            for (CartItemModel item : cartItemModels)
                if (item.getId() == id) {
                    isItemExistInCart = true;
                    break;
                }
            binding.detailsAddToCartButton.setVisibility(isItemExistInCart ? View.GONE : View.VISIBLE);
            binding.detailsExistInCartTextView.setVisibility(isItemExistInCart ? View.VISIBLE : View.GONE);

        });

    }

    public class CommodityMainEventListener {

        public void onAddToCart(View view, Product product, MainActivityViewModel viewModel) {
            viewModel.addCartItem(new CartItemModel(product.getId(), product.getName(), product.getImageUrl(), product.getPrice(), 1, product.getDiscount()));
        }

        public void onSeeSpecifications(View view, Product product) {
            Navigation.findNavController(view).navigate(CommodityMainFragmentDirections.
                    actionCommodityDetailsFragmentToAttributesFragment(product.getId()));
        }

        public void onSeeDescriptions(View view, Product product) {
            Navigation.findNavController(view).navigate(
                    CommodityMainFragmentDirections.actionCommodityDetailsFragmentToDescriptionsFragment(product.getDescription()));
        }

        public void onWriteComment(View view, Product product) {

            Navigation.findNavController(view).navigate(
                    CommodityMainFragmentDirections.actionCommodityDetailsFragmentToWriteCommentFragment(product.getId())
            );

        }

        public void onSeeAllComments(View view, Product product) {
            Navigation.findNavController(view).navigate(
                    CommodityMainFragmentDirections.actionCommodityDetailsFragmentToAllCommentsFragment(product.getId())
            );

        }


    }

}