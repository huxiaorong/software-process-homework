package com.example.msl.rainbow1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;


public class PublishedDialog extends Dialog {

    public PublishedDialog(@NonNull Context context) {
        super(context);
        this.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);
    }
}
