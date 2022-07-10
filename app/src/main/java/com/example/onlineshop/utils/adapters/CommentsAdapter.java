package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemCardCommentHorizontalBinding;
import com.example.onlineshop.databinding.ItemCardCommentVerticalBinding;
import com.example.onlineshop.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.Holder> {
    private List<Comment> comments = new ArrayList<>();

    public enum AdapterType {
        HORIZONTAL,
        VERTICAL
    }

    AdapterType adapterType;

    public CommentsAdapter(AdapterType adapterType) {
        this.adapterType = adapterType;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public OnItemClickListener onClickListener;

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (adapterType == AdapterType.HORIZONTAL) {
            ItemCardCommentHorizontalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card_comment_horizontal, parent, false);
            return new Holder(binding);
        } else {
            ItemCardCommentVerticalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card_comment_vertical, parent, false);
            return new Holder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        if (adapterType == AdapterType.HORIZONTAL) {
            holder.horizontalBinding.setModel(comments.get(position));
        } else {
            holder.verticalBinding.setModel(comments.get(position));
        }


    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemCardCommentHorizontalBinding horizontalBinding;
        ItemCardCommentVerticalBinding verticalBinding;

        public Holder(@NonNull ItemCardCommentHorizontalBinding horizontalBinding) {
            super(horizontalBinding.getRoot());
            this.horizontalBinding = horizontalBinding;
            horizontalBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        public Holder(@NonNull ItemCardCommentVerticalBinding verticalBinding) {
            super(verticalBinding.getRoot());
            this.verticalBinding = verticalBinding;

        }
    }

}
