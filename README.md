Seismic Interceptor
===================
![SeismicInterceptorLogo](https://raw.githubusercontent.com/charbgr/SeismicInterceptor/master/seismicinterceptor/src/main/res/drawable-xxhdpi/ic_launcher.png)

An [OkHttp interceptor][1] which shows logs from OkHttp request and response data by shaking your device.
![SeismicInterceptorSample](https://raw.githubusercontent.com/charbgr/SeismicInterceptor/master/seismic_interceptor_sample.gif)

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

```gradle
 dependencies {
   debugCompile 'com.charbgr.seismicinterceptor:seismicinterceptor:1.1'
   releaseCompile 'com.charbgr.seismicinterceptor:seismicinterceptor-noop:1.1'
   testCompile 'com.charbgr.seismicinterceptor:seismicinterceptor-noop:1.1'
 }
```

Licence
-------
The MIT License (MIT)
Copyright (c) 2016 Charalampakis Vasilis

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.



 [1]: https://github.com/square/okhttp/wiki/Interceptors
