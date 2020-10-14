package com.example.rickandmortycase.util;

import androidx.core.content.ContextCompat;

import com.example.rickandmortycase.R;

public abstract class AppUtils {
    public static int getStatusColor(String status) {

        if (status.equals("Alive")) {
            return R.color.green;
        } else if (status.equals("Dead")) {
            return R.color.red;
        } else {
            return R.color.gray500;
        }
    }
}
