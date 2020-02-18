package com.bahaa.github.ui.fragment.adapter.repoAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.github.R;
import com.bahaa.github.databinding.ItemRepoBinding;
import com.bahaa.github.databinding.ItemRepoGrideBinding;
import com.bahaa.github.models.RepoModel;
import com.bahaa.github.util.AppTools;

import java.util.ArrayList;
import java.util.List;


public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private List<RepoModel> cartList;
    private OnMovieClick onMovieClick;
    private int view;

    public ReposAdapter(OnMovieClick onMovieClick, int view) {
        this.cartList = new ArrayList<>();
        this.onMovieClick = onMovieClick;
        this.view = view;
    }

    @NonNull
    @Override
    public ReposAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (view) {
            case R.layout.item_repo:
                ItemRepoBinding itemRepoBinding=
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), view,
                                parent, false);
                return new ViewHolder(itemRepoBinding);
            case R.layout.item_repo_gride:
                ItemRepoGrideBinding itemRepoGrideBinding=
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), view,
                                parent, false);
                return new ViewHolder(itemRepoGrideBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(cartList.get(position));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public void setCartList(List<RepoModel> cartList) {
        this.cartList.addAll(cartList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemRepoBinding itemRepoBinding;
        private ItemRepoGrideBinding itemRepoGrideBinding;

        public ViewHolder(ItemRepoBinding itemRepoBinding) {
            super(itemRepoBinding.getRoot());
            this.itemRepoBinding = itemRepoBinding;
        }

        public ViewHolder(ItemRepoGrideBinding itemRepoGrideBinding) {
            super(itemRepoGrideBinding.getRoot());
            this.itemRepoGrideBinding = itemRepoGrideBinding;
        }

        void bindData(final RepoModel data) {
            ReposViewModel movieViewModel = new ReposViewModel(data);
            switch (view) {
                case R.layout.item_repo:
                    itemRepoBinding.setRepoItem(movieViewModel);
                    break;
                case R.layout.item_repo_gride:
                    itemRepoGrideBinding.setRepoItem(movieViewModel);
                    break;

            }
            movieViewModel.action.observe((LifecycleOwner) itemView.getContext(), action -> {
                switch (action) {
                    case AppTools.OPEN_REPO:
                        onMovieClick.onMovieClickListener(data);
                        break;
                }
            });

        }
    }

    public interface OnMovieClick {
        void onMovieClickListener(RepoModel githubResponse);
    }
}
