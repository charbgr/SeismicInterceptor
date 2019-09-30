package charbgr.com.seismicretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.charbgr.seismicinterceptor.SeismicInterceptor;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import charbgr.com.seismicretrofit.services.API;
import charbgr.com.seismicretrofit.services.Contributor;
import charbgr.com.seismicretrofit.services.GitHub;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AtomicInteger callIdx = new AtomicInteger(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.call);

        if (view != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GitHub g = API.createGithub();

                    for (int i = 0; i < API.URLS.length; i++) {
                        g.get(API.URLS[i])
                                .enqueue(new Callback<Object>() {
                                    @Override
                                    public void onResponse(Call<Object> call, Response<Object> response) {
                                        System.out.println("response : " + response.isSuccessful());
                                    }

                                    @Override
                                    public void onFailure(Call<Object> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                    }

                    g.contributors("square", "retrofit").enqueue(new Callback<List<Contributor>>() {
                        @Override
                        public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {

                        }

                        @Override
                        public void onFailure(Call<List<Contributor>> call, Throwable t) {

                        }
                    });


                    Toast.makeText(view.getContext(), "START SHAKING", Toast.LENGTH_LONG).show();


                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SeismicInterceptor.start(this, 11);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SeismicInterceptor.stop();
    }
}
