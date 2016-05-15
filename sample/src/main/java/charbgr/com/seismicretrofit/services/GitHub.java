package charbgr.com.seismicretrofit.services;

import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GitHub {
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET
    Call<Object> get(@Url HttpUrl url);
}
