package edu.rpi.cs.yacs.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.lang.annotation.Retention;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class ServiceHelper {
    @Retention(RUNTIME)
    @interface Json {
    }

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
     * @return new YACSService that uses the latest "House of YACS" setting
     */
    public YACSService invalidateService() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        // If exists, set to String value
        // Otherwise, set to "unknown"
        String houseOfYACS = preferences.getString("House of YACS", "unknown");

        String baseUrl = convertHouseToURL(houseOfYACS);

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
     * @param house College name
     * @return College YACS URL if College supports YACS. Otherwise, returns "not supported yet"
     */
    private String convertHouseToURL(String house) {
        switch (house) {
            case "Rensselaer Polytechnic Institute":
                return "https://yacs.cs.rpi.edu/api/v5/";
            default :
                return "not supported yet";
        }
    }
}