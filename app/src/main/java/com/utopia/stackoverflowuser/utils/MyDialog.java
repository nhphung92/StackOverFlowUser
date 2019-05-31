package com.utopia.stackoverflowuser.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import com.utopia.stackoverflowuser.R;

public class MyDialog {

  private static MyDialog INSTANCE = null;
  private Dialog mDialog;

  public static MyDialog getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new MyDialog();
    }
    return INSTANCE;
  }

  public void showProgress(Context context, boolean cancelable) {
    hideProgress();

    mDialog = new Dialog(context, R.style.MyProgressTheme);

    // no tile for the dialog
    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    mDialog.setContentView(R.layout.prograss_bar_dialog);

    ProgressBar mProgressBar = mDialog.findViewById(R.id.progress_bar);
    mProgressBar.setVisibility(View.VISIBLE);
    mProgressBar.setProgress(ProgressDialog.STYLE_SPINNER);
    mProgressBar.setIndeterminate(true);

    // you can change or add this line according to your need
    mDialog.setCancelable(cancelable);
    mDialog.setCanceledOnTouchOutside(cancelable);
    mDialog.show();
  }

  public void hideProgress() {
    if (mDialog != null && mDialog.isShowing()) {
      mDialog.dismiss();
      mDialog = null;
    }
  }
}
