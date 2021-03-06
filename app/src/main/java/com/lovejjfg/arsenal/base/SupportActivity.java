/*
 *  Copyright (c) 2017.  Joe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.lovejjfg.arsenal.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.google.android.gms.analytics.Tracker;
import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.ui.LoadingDialog;
import com.lovejjfg.arsenal.utils.FragmentsUtil;
import com.lovejjfg.arsenal.utils.KeyBoardUtil;
import com.lovejjfg.arsenal.utils.ToastUtil;
import com.lovejjfg.shake.Shaker;
import com.lovejjfg.shake.ShakerHelper;
import java.util.List;

/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

public abstract class SupportActivity extends AppCompatActivity implements ISupportFragment, ISupportView {
    @Nullable
    private FragmentsUtil fragmentsUtil;
    LoadingDialog progressDialog;
    @Nullable
    private Toolbar mToolbar;

    public Tracker mTracker;
    private Shaker shaker;

    @Override
    public void addToParent(int containerViewId, @NonNull SupportFragment parent, int pos,
        SupportFragment... children) {
        if (fragmentsUtil != null) {
            fragmentsUtil.addToParent(containerViewId, parent, pos, children);
        }
    }

    @Override
    public void replaceToParent(int containerViewId, @NonNull SupportFragment parent, SupportFragment... children) {
        if (fragmentsUtil != null) {
            fragmentsUtil.replaceToParent(containerViewId, parent, children);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTracker = ((App) getApplication()).getDefaultTracker();
        progressDialog = new LoadingDialog();
        fragmentsUtil = new FragmentsUtil(getSupportFragmentManager());
        setContentView(initLayoutRes());
        shaker = ShakerHelper.instance(this);
        try {
            mToolbar = findViewById(R.id.toolbar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shaker.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shaker.onResume();
    }

    public FragmentsUtil getFragmentsUtil() {
        return fragmentsUtil;
    }

    @Override
    public void initFragments(Bundle savedInstanceState, SupportFragment fragment) {
        if (fragmentsUtil != null) {
            fragmentsUtil.initFragments(savedInstanceState, fragment);
        }
    }

    @Nullable
    @Override
    public List<Fragment> getTopFragment() {
        return fragmentsUtil != null ? fragmentsUtil.getTopFragments() : null;
    }

    @Nullable
    @Override
    public <F extends SupportFragment> F findFragment(Class<F> className) {
        return fragmentsUtil != null ? fragmentsUtil.findFragment(className) : null;
    }

    @Nullable
    @Override
    public <F extends SupportFragment> F findFragment(SupportFragment parentFragment, Class<F> className) {
        return fragmentsUtil != null ? fragmentsUtil.findFragment(parentFragment, className) : null;
    }

    @Override
    public void loadRoot(int containerViewId, SupportFragment... root) {
        if (fragmentsUtil != null) {
            fragmentsUtil.loadRoot(containerViewId, 0, root);
        }
    }

    @Override
    public void addToShow(SupportFragment from, SupportFragment to) {
        if (fragmentsUtil != null) {
            fragmentsUtil.addToShow(from, to);
        }
    }

    @Override
    public boolean popTo(Class<? extends SupportFragment> target, boolean includeSelf) {
        return fragmentsUtil != null && fragmentsUtil.popTo(target, includeSelf);
    }

    public boolean popSelf() {
        return fragmentsUtil != null && fragmentsUtil.popSelf();
    }

    @Override
    public void replaceToShow(SupportFragment from, SupportFragment to) {
        if (fragmentsUtil != null) {
            fragmentsUtil.replaceToShow(from, to);
        }
    }

    @Override
    public void onBackPressed() {
        if (!finishInner()) {
            handleFinish();
        }
    }

    @Override
    public void showToast(String toast) {
        ToastUtil.showToast(this, toast);
    }

    @Override
    public void showToast(int stringId) {
        ToastUtil.showToast(this, getString(stringId));
    }

    @Override
    public void showLoadingDialog(String msg) {
        try {
            if (progressDialog.isAdded() || progressDialog.isVisible()) {
                closeLoadingDialog();
            }
            progressDialog.show(getFragmentManager(), "loading");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeLoadingDialog() {
        try {
            progressDialog.dismissAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openKeyBoard() {
        KeyBoardUtil.openKeyBoard(this);
    }

    @Override
    public void openKeyBoard(View focusView) {
        KeyBoardUtil.openKeyBoard(this, focusView);
    }

    @Override
    public void closeKeyBoard() {
        KeyBoardUtil.closeKeyBoard(this);
    }

    @Override
    public boolean finishInner() {
        List<Fragment> topFragments = getTopFragment();
        boolean finish = false;
        if (topFragments != null && !topFragments.isEmpty()) {
            for (Fragment fragment : topFragments) {
                if (fragment instanceof SupportFragment) {
                    finish = finish | ((SupportFragment) fragment).finishInner();
                }
            }
        }
        return finish;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }
//    @Override
//    public void saveToSharedPrefs(String key, Object value) {
//
//    }

    @Override
    public void saveViewData(Bundle bundle) {

    }

    @Override
    public void saveViewData(String key, Bundle bundle) {

    }

    @Override
    public boolean handleFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
        return true;
    }
}
