package charbgr.com.seismicretrofit.services;

import com.charbgr.seismicinterceptor.SeismicInterceptor;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static final HttpUrl[] URLS = new HttpUrl[]{
            HttpUrl.parse("http://google.com"),
            HttpUrl.parse("https://www.facebook.com/"),
            HttpUrl.parse("http://google.gr/fail"),
            HttpUrl.parse("http://paokfc.gr/")
    };
    public static final String API_URL = "https://api.github.com";

    public static Retrofit build() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(SeismicInterceptor.create())
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();

        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();
    }

    public static GitHub createGithub() {
        return build().create(GitHub.class);
    }

}
