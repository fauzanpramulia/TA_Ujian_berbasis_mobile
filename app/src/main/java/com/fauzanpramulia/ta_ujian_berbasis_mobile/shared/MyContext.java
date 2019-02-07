package com.fauzanpramulia.ta_ujian_berbasis_mobile.shared;

import android.app.Application;
import android.os.Bundle;

public class MyContext extends Application{
    Bundle mySavedData = null;

    public void setSavedData(Bundle data){
        mySavedData = data;
    }

    public Bundle getSavedData() {
        return mySavedData;
    }
}
