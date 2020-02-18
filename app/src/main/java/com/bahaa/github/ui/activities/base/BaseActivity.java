package com.bahaa.github.ui.activities.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bahaa.github.R;
import com.bahaa.github.data.network.NetworkEvent;
import com.bahaa.github.data.network.interfaces.ErrorResponse;
import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;


public class BaseActivity extends AppCompatActivity implements ErrorResponse {

    private static BaseActivity instance;
    private ProgressDialog mProgressDialog;
    private NavController navController;
    private BottomNavigationView navView;

    public void preOnCreate() {
        instance = this;
    }
    public static BaseActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preOnCreate();
        super.onCreate(savedInstanceState);
        hideToolbar();
        setContentView(R.layout.activity_base);
        initActivity(savedInstanceState);
    }

    public void initActivity(Bundle savedInstanceState) {
        navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        initBottomContainer();
    }

    public void initBottomContainer() {
        int f = navController.getCurrentDestination().getId();
        if (f == R.id.navHome) {
            navView.setVisibility(View.VISIBLE);
        } else {
            navView.setVisibility(View.GONE);
        }
    }

    public void hideToolbar() {
        if (getSupportActionBar() != null) getSupportActionBar().hide();
    }

    public ProgressDialog showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
        }

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(getString(R.string.message_loading));
            mProgressDialog.show();
        }

        return mProgressDialog;
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideProgressDialog();

    }

    public void showDialog(String title, String message) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtTitle;
        TextView txtMessage;
        Button btnOK;
        Button btnNO;

        txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        txtMessage = dialog.findViewById(R.id.txtMessage);
        txtMessage.setText(message);

        btnOK = dialog.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnNO = dialog.findViewById(R.id.btnNO);
        btnNO.setVisibility(View.GONE);

        dialog.show();

    }

    public Dialog showDialog(String title, String message, String buttonOK, String buttonNO, int image, View.OnClickListener onOkClickListener) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView imageIcon;
        TextView txtTitle;
        TextView txtMessage;
        Button btnOK;
        Button btnNO;


        txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        txtMessage = dialog.findViewById(R.id.txtMessage);
        txtMessage.setText(message);

        imageIcon = dialog.findViewById(R.id.imageIcon);
        if (image != 0) {
            imageIcon.setImageDrawable(getDrawable(image));
        } else {
            imageIcon.setVisibility(View.GONE);
        }


        btnOK = dialog.findViewById(R.id.btnOK);
        btnOK.setText(buttonOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onOkClickListener != null) onOkClickListener.onClick(v);
            }
        });

        btnNO = dialog.findViewById(R.id.btnNO);
        if (buttonNO != null) {
            btnNO.setText(buttonNO);
            btnNO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else {
            btnNO.setVisibility(View.GONE);
        }

        dialog.show();
        return dialog;
    }

    public void showSnackBar(String massage) {
        Snackbar.with(this, null)
                .type(Type.ERROR)
                .message(massage)
                .duration(Duration.INFINITE)
                .fillParent(true)
                .textAlign(Align.CENTER)
                .show();
    }

    @Override
    public void onAlreadyLoggedIn() {

    }

    @Override
    public void onNoInternet() {

    }

    @Override
    public void onNotAuthorized() {

    }

    @Override
    public void onNotAllowedMethod() {

    }

    @Override
    public void onApiNotFound() {

    }

    @Override
    public void onBadRequest(JSONObject object) {

    }

    @Override
    public void onServerSideError() {

    }

    @Override
    public void onForbidden() {

    }

    @Override
    public void onMaintenance() {

    }
    public void navigate(int layoutId, Fragment fragment) {
        NavHostFragment.findNavController(fragment).navigate(layoutId);
    }

    public void navigateWithBundle(View view, Bundle bundle, int actionId) {
        Navigation.findNavController(view).navigate(actionId, bundle);

    }
    @Override
    protected void onResume() {
        super.onResume();
        instance = this;
        instance = this;
        NetworkEvent.getInstance().register(this,
                networkState -> {
                    switch (networkState) {
                        case NO_INTERNET:
//                            navController.navigate(R.id.offlineFragment);
                            break;
                        case NO_RESPONSE:
                        case SERVER_ERROR:
                            onServerSideError();
                            break;
                        case API_NOT_FOUND:
                            onApiNotFound();
                            break;
                        case UNAUTHORISED:
//                            openLoginPage();
                            break;
                        case NOT_ALLOWED_METHOD:
                            onNotAllowedMethod();
                            break;
                        case ALREADY_LOGIN:
                            onAlreadyLoggedIn();
                            break;
                        case MAINTENANCE:
                            onMaintenance();
                            break;
                        case BAD_REQUEST:
//                            onBadRequest(new JSONObject(AppUtils.getFromSharedPreference(MyApplication.getContext(), "error")));
                            break;
                    }
                });

        initBottomContainer();
    }
    public void customBackClick() {
        navController.popBackStack();
    }
    @Override
    protected void onStop() {
        super.onStop();
        NetworkEvent.getInstance().unregister(this);
    }

}
