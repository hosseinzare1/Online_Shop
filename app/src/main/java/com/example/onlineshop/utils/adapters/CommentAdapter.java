package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CardCommentHorizontalBinding;
import com.example.onlineshop.databinding.CardCommentVerticalBinding;
import com.example.onlineshop.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Holder> {
    private List<Comment> comments = new ArrayList<>();

    public enum AdapterType {
        HORIZONTAL,
        VERTICAL
    }

    AdapterType adapterType;

    public CommentAdapter(AdapterType adapterType) {
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
            CardCommentHorizontalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_comment_horizontal, parent, false);
            return new Holder(binding);
        } else {
           CardCommentVerticalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_comment_vertical, parent, false);
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
        CardCommentHorizontalBinding horizontalBinding;
        CardCommentVerticalBinding verticalBinding;

        public Holder(@NonNull CardCommentHorizontalBinding horizontalBinding) {
            super(horizontalBinding.getRoot());
            this.horizontalBinding = horizontalBinding;
            horizontalBinding.getRoot().setOnClickListener(view -> onClickListener.onItemClick(getAdapterPosition()));
        }

        public Holder(@NonNull CardCommentVerticalBinding verticalBinding) {
            super(verticalBinding.getRoot());
            this.verticalBinding = verticalBinding;

        }
    }

}
