package com.bahaa.github.ui.fragment.adapter.repoAdapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bahaa.github.models.RepoModel;
import com.bahaa.github.util.AppTools;
import com.squareup.picasso.Picasso;

public class ReposViewModel extends ViewModel {
    private RepoModel repo;
    public ObservableField<String> imageObservable;
    public ObservableField<String> repoNameObservable;
    public ObservableField<String> repoTypeObservable;
    public ObservableField<String> repoAdminObservable;
    public ObservableField<String> repoLanguageObservable;
    public ObservableField<String> repoWatcherObservable;
    public MutableLiveData<String> action;

    public ReposViewModel(RepoModel repo) {
        this.repo = repo;
        imageObservable = new ObservableField<>();
        repoNameObservable = new ObservableField<>();
        repoTypeObservable = new ObservableField<>();
        repoAdminObservable = new ObservableField<>();
        repoLanguageObservable = new ObservableField<>();
        repoWatcherObservable = new ObservableField<>();
        action = new MutableLiveData<>();
        updateUI();
    }

    private void updateUI() {
        imageObservable.set(repo.getOwner().getAvatarUrl());
        repoNameObservable.set(repo.getOwner().getLogin());
        repoTypeObservable.set(repo.getOwner().getType());
        repoAdminObservable.set(repo.getOwner().getSiteAdmin().toString());
        repoLanguageObservable.set(repo.getLanguage());
        repoWatcherObservable.set(repo.getWatchers().toString());
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    public void setrepo(RepoModel repo) {
        this.repo = repo;
    }

    public void onItemClick() {
        action.setValue(AppTools.OPEN_REPO);
    }
}
