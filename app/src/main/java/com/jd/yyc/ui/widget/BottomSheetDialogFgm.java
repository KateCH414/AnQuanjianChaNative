package com.jd.yyc.ui.widget;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public abstract class BottomSheetDialogFgm extends BottomSheetDialogFragment {

    public BottomSheetDialogFgm() {

    }

    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog dialog = new BottomSheetDialog(getContext(), getTheme());

        dialog.setContentView(getContentView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.setCanceledOnTouchOutside(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return dialog;
    }

    @Override
    public final void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        setupDialog(dialog);
    }

    protected void setupDialog(Dialog dialog) {

    }

    @NonNull
    protected abstract View getContentView();

}
