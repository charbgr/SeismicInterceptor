Seismic Interceptor
===================

An [OkHttp interceptor][1] which shows logs from OkHttp request and response data by shaking your device.

Add this to your OkHttpClient setup:
```java
OkHttpClient client = new OkHttpClient.Builder()
  .addInterceptor(SeismicInterceptor.create())
  .build();
```

Add this to your Activities:
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    SeismicInterceptor.start(this);
}
    
@Override
protected void onStop() {
    ...
    SeismicInterceptor.stop();
}
```

You can change your shake sensitivity level when you are calling 
`SeismicInterceptor.start(this, ShakeDetector.SENSITIVITY_LIGHT)`.

**Warning**: You must call `.stop()` whenever your activities are stopped.


Download
--------

Soon in maven central.



 [1]: https://github.com/square/okhttp/wiki/Interceptors