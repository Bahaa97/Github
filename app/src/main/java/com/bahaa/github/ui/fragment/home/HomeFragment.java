package com.bahaa.github.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bahaa.github.MyApplication;
import com.bahaa.github.R;
import com.bahaa.github.databinding.FragmentHomeBinding;
import com.bahaa.github.models.RepoModel;
import com.bahaa.github.ui.fragment.adapter.repoAdapter.ReposAdapter;
import com.bahaa.github.ui.fragment.base.BaseFragment;
import com.bahaa.github.util.AppUtils;
import com.bahaa.github.util.PaginationListener;

import java.util.List;


public class HomeFragment extends BaseFragment implements ReposAdapter.OnMovieClick, SwipeRefreshLayout.OnRefreshListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding fragmentHomeBinding;
    private ReposAdapter movieAdapter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeViewModel = new HomeViewModel(MyApplication.getApplication());
        fragmentHomeBinding.swipe.setOnRefreshListener(this);
        fragmentHomeBinding.setHomeViewModle(homeViewModel);
        initRecycler();
        homeViewModel.moviesResponseMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<RepoModel>>() {
            @Override
            public void onChanged(List<RepoModel> repos) {
                movieAdapter.setCartList(repos);
            }
        });

        return fragmentHomeBinding.getRoot();

    }

    private void initRecycler() {
        GridLayoutManager gridLayoutManager = AppUtils.initVerticalRV(getContext(), fragmentHomeBinding.recyclerMovies, 1, 10);
        movieAdapter = new ReposAdapter(this, R.layout.item_repo);
        fragmentHomeBinding.recyclerMovies.setAdapter(movieAdapter);
        fragmentHomeBinding.recyclerMovies.addOnScrollListener(new PaginationListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                homeViewModel.isLoading = true;
                homeViewModel.page.setValue(homeViewModel.page.getValue() + 1);
                homeViewModel.getData();
            }

            @Override
            public boolean isLastPage() {
                return homeViewModel.isLastPage;
            }

            @Override
            public boolean isLoading() {
                return homeViewModel.isLoading;
            }
        });
    }

    @Override
    public void onMovieClickListener(RepoModel repo) {

    }

    @Override
    public void onRefresh() {
        fragmentHomeBinding.swipe.setRefreshing(false);
        homeViewModel.getData();
    }
}