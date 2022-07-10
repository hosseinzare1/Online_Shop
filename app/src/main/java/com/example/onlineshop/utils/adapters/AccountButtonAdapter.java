package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.AccountButtonItemBinding;

import java.util.ArrayList;
import java.util.List;

public class AccountButtonAdapter extends RecyclerView.Adapter<AccountButtonAdapter.ButtonViewHolder> {

    List<Integer> icons = new ArrayList<>();
    List<String> texts = new ArrayList<>();

    public void setData(List<Integer> icons, List<String> texts) {
        this.icons = icons;
        this.texts = texts;
        notifyDataSetChanged();
    }

    public interface AccountEventListener {
        void onItemClickListener(String itemText);
    }

    AccountEventListener eventListener;

    public void setOnClickListener(AccountEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AccountButtonItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.account_button_item
                , parent
                , false);
        return new ButtonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        holder.binding.itemText.setText(texts.get(position));
        holder.binding.itemIcon.setImageResource(icons.get(position));
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder {
        AccountButtonItemBinding binding;

        public ButtonViewHolder(@NonNull AccountButtonItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(view -> eventListener.onItemClickListener(texts.get(getAdapterPosition())));
        }
    }

}
