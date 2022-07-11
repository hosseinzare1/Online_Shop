package com.example.onlineshop.view.commodity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentCommodityMainBinding;
import com.example.onlineshop.model.CartProduct;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.adapters.CommentAdapter;
import com.example.onlineshop.utils.adapters.ProductHorizontalAdapter;
import com.example.onlineshop.utils.adapters.ImageSliderAdapter;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

public class CommodityMainFragment extends Fragment {

    FragmentCommodityMainBinding binding;
    CommodityActivityViewModel viewModel;

    ImageSliderAdapter imageSliderAdapter;
    CommentAdapter commentAdapter;

    ProductHorizontalAdapter sameProductsAdapter;
    int id;
    private final String TAG = "CommodityMainFragment";

    public CommodityMainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_commodity_main, container, false);
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new CommodityActivityViewModelFactory(getActivity())).get(CommodityActivityViewModel.class);
        return binding.getRoot();
    }

    private void loadData(int id,View view){


        imageSliderAdapter = new ImageSliderAdapter();
        binding.detailsImagesViewPager.setAdapter(imageSliderAdapter);
        binding.setEventListener(new CommodityMainEventListener());

        binding.detailsImageViewpageIndicator.setViewPager(binding.detailsImagesViewPager);


        commentAdapter = new CommentAdapter(CommentAdapter.AdapterType.HORIZONTAL);
        binding.detailsCommentsRecyclerView.setAdapter(commentAdapter);
        binding.detailsCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, true));

        sameProductsAdapter = new ProductHorizontalAdapter();
        binding.detailsSameCommodityRecyclerView.setAdapter(sameProductsAdapter);
        binding.detailsSameCommodityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        binding.setMainActivityViewModel(new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class));

        binding.orgPriceCommodityMain.setPaintFlags(
                binding.orgPriceCommodityMain.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
        );


        viewModel.getProduct(id).observe(getViewLifecycleOwner(), product -> {
            binding.setModel(product);


            viewModel.addHistoryItem(new Product(id, 0,
                    product.getName(),
                    product.getDescription(),
                    product.getImageUrl(),
                    product.getPrice(),
                    product.getDiscount(),
                    product.getGroup(),
                    product.getCategory()));

        });

        viewModel.getImages(id).observe(getViewLifecycleOwner(), images -> imageSliderAdapter.setImages(images));

        viewModel.getComments(id).observe(getViewLifecycleOwner(), comments -> {

            // show noComments textView if comments is empty
            if (comments.size() > 0) {
                binding.detailsNoCommentsTextView.setVisibility(View.GONE);
                commentAdapter.setComments(comments);
            } else {
                binding.detailsNoCommentsTextView.setVisibility(View.VISIBLE);
            }

            binding.detailsCommentsCountTextView.setText(comments.size() + " نظر ");


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
            for (CartProduct item : cartItemModels)
                if (item.getId() == id) {
                    isItemExistInCart = true;
                    break;
                }
            binding.detailsAddToCartButton.setVisibility(isItemExistInCart ? View.GONE : View.VISIBLE);
            binding.detailsExistInCartTextView.setVisibility(isItemExistInCart ? View.VISIBLE : View.GONE);

        });

        sameProductsAdapter.setOnClickListener(id1 -> viewModel.selectedProductLiveData.setValue(id1));

        commentAdapter.setOnItemClickListener(position -> Navigation.findNavController(view).navigate(
                CommodityMainFragmentDirections.actionCommodityDetailsFragmentToAllCommentsFragment(id,position)
        ));


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.selectedProductLiveData.observe(getViewLifecycleOwner(), id -> {
            loadData(id,view);
            binding.commodityScrollView.fullScroll(ScrollView.FOCUS_UP);
        });


        viewModel.selectedProductLiveData.setValue(getArguments().getInt("id"));
    }

    public static class CommodityMainEventListener {

        public void onAddToCart(View view, Product product, MainActivityViewModel viewModel) {
            viewModel.addCartItem(
                    new CartProduct(product.getId(),
                            product.getName(),
                            product.getImageUrl(),
                            product.getPrice(),
                            1,
                            product.getDiscount()));

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
                    CommodityMainFragmentDirections.actionCommodityDetailsFragmentToAllCommentsFragment(product.getId(),0)
            );

        }

        public void onSeeRelatedGroupProducts(View view, Product product) {
            Navigation.findNavController(view).navigate(CommodityMainFragmentDirections.actionCommodityDetailsFragmentToProductListFragment(
                    "", product.getGroup()
            ));
        }

        public void onSeeRelatedCategoryProducts(View view, Product product) {
            Navigation.findNavController(view).navigate(CommodityMainFragmentDirections.actionCommodityDetailsFragmentToProductListFragment(
                    product.getCategory(), ""
            ));
        }


    }

}