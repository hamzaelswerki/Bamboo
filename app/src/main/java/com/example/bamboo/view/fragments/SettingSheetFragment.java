package com.example.bamboo.view.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.View;

import com.example.bamboo.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class SettingSheetFragment extends BottomSheetDialogFragment {


    public SettingSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_setting_sheet, null, false);
        dialog.setContentView(rootView);
    }
}