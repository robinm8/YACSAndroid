package edu.rpi.cs.yacs.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class ServiceHelper {

    private YACSService service;
    private Context context;

    public ServiceHelper(Context context) {
        this.context = context;

        invalidateService();
    }

    public YACSService getService() {
        return service;
    }

    /**
     * This method is used when we need to invalidate the YACSService
     * @return new YACSService that uses the latest "College" setting
     */
    public YACSService invalidateService() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        // If exists, set to String value
        // Otherwise, set to "unknown"
        String college = preferences.getString("College", "unknown");

        String baseUrl = convertCollegeToURL(college);

        Log.d("Persistent BaseUrl", baseUrl);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(YACSService.class);

        return service;
    }

    /**
     * This method returns the YACS URL of the input college name.
     * @param college name of college
     * @return College YACS URL if College supports YACS, return RPI's YACS URL otherwise
     */
    private String convertCollegeToURL(String college) {
        switch (college) {
            case "Rensselaer Polytechnic Institute":
                return "https://yacs.cs.rpi.edu/api/v5/";
            default :
                return "https://yacs.cs.rpi.edu/api/v5/";
        }
    }
}