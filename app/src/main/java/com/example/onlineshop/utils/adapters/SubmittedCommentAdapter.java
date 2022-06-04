package com.example.onlineshop.utils.adapters;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemSubmittedCommentBinding;
import com.example.onlineshop.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class SubmittedCommentAdapter extends RecyclerView.Adapter<SubmittedCommentAdapter.SubmittedCommentViewHolder> {

    List<Comment> comments = new ArrayList<>();

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    public interface EventListener {

        void onDeleteCommentListener(int id);

        void onEditCommentListener(int id);


    }

    public EventListener eventListener;

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public SubmittedCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubmittedCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_submitted_comment, parent, false);

        return new SubmittedCommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubmittedCommentViewHolder holder, int position) {
        holder.binding.setModel(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class SubmittedCommentViewHolder extends RecyclerView.ViewHolder {
        ItemSubmittedCommentBinding binding;

        public SubmittedCommentViewHolder(@NonNull ItemSubmittedCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            if (eventListener != null) {

                binding.deleteComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eventListener.onDeleteCommentListener(comments.get(getAdapterPosition()).getId());
                    }
                });


                binding.editComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eventListener.onEditCommentListener(comments.get(getAdapterPosition()).getId());
                    }
                });

            }


        }


    }

}
