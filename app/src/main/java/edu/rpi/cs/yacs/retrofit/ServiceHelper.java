package edu.rpi.cs.yacs.retrofit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Mark Robinson on 9/24/16.
 */

class ServiceHelper {

    @Retention(RUNTIME)
    @interface Json {
    }

    private YACSService service;
    private Activity activity;

    private ServiceHelper(Activity activity) {
        this.activity = activity;

        SharedPreferences preferences = activity.getSharedPreferences("API", Context.MODE_PRIVATE);

        // If exists, return value
        // Otherwise, set to RPI YACS base url
        String baseURL = preferences.getString("BASE_URL", "https://yacs.cs.rpi.edu/api/v5/");

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(new JsonStringConverterFactory(GsonConverterFactory.create()))
            .build();

        service = retrofit.create(YACSService.class);
    }

    public YACSService getService() {
        return service;
    }

    // Used when we need to invalidate the old service
    // Replaces old service with new service that uses the latest "House of YAX" setting.
    public YACSService invalidateService() {
        SharedPreferences preferences = activity.getSharedPreferences("API", Context.MODE_PRIVATE);

        // If exists, return value
        // Otherwise, set to RPI YACS base url
        String baseURL = preferences.getString("BASE_URL", "https://yacs.cs.rpi.edu/api/v5/");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(new JsonStringConverterFactory(GsonConverterFactory.create()))
                .build();

        service = retrofit.create(YACSService.class);

        return service;
    }
}

class JsonStringConverterFactory extends Converter.Factory {
    private final Converter.Factory delegateFactory;

    JsonStringConverterFactory(Converter.Factory delegateFactory) {
        this.delegateFactory = delegateFactory;
    }

    @Override public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
                                                          Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof ServiceHelper.Json) {
                Converter<?, RequestBody> delegate =
                        delegateFactory.requestBodyConverter(type, annotations, new Annotation[0], retrofit);
                return new DelegateToStringConverter<>(delegate);
            }
        }
        return null;
    }

    private static class DelegateToStringConverter<T> implements Converter<T, String> {
        private final Converter<T, RequestBody> delegate;

        DelegateToStringConverter(Converter<T, RequestBody> delegate) {
            this.delegate = delegate;
        }

        @Override public String convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            delegate.convert(value).writeTo(buffer);
            return buffer.readUtf8();
        }
    }
}