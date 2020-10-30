/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.sportsnews.network;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

public class ProgressBarService {
    ProgressBar progressBar;
    Context mContext;

    public ProgressBarService(Context mContext) {
        this.mContext = mContext;
        this.progressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleLarge);

    }


    public void startProgressBar() {
        this.progressBar.setIndeterminate(true);
        this.progressBar.setVisibility(View.VISIBLE);
    }

    public void stopProgressBar() {
        this.progressBar.setIndeterminate(false);
        this.progressBar.setVisibility(View.GONE);
    }
}
