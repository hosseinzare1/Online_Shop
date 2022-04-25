package com.example.onlineshop.utils.adapters;

import android.provider.ContactsContract;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemProfileInformationBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ProfileInformationAdapter extends RecyclerView.Adapter<ProfileInformationAdapter.ProfileInformationViewHolder> {

    List<String> titles = new ArrayList<>();
    List<String> texts = new ArrayList<>();

    public void setData(ArrayList<String> titles, ArrayList<String> texts) {
        this.titles = titles;
        this.texts = texts;
    }

    @NonNull
    @Override
    public ProfileInformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProfileInformationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_profile_information, parent, false);
        return new ProfileInformationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileInformationViewHolder holder, int position) {
        holder.binding.profileItemTitle.setText(texts.get(position));
        holder.binding.profileItemText.setText(texts.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ProfileInformationViewHolder extends RecyclerView.ViewHolder {
        ItemProfileInformationBinding binding;

        public ProfileInformationViewHolder(@NonNull ItemProfileInformationBinding binding) {
            super(binding.getRoot());
        }
    }
}
