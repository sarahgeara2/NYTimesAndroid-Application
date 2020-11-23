package com.example.nytimesapplication.core.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.nytimesapplication.common.GlobalFunctions;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Request;
import okhttp3.internal.http2.StreamResetException;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetryCallAdapterFactory extends CallAdapter.Factory {
    private static final String TAG = "RetryCallAdapterFactory";

    Context context;

    public RetryCallAdapterFactory(Context context) {
        this.context = context;
    }

    public static RetryCallAdapterFactory create(Context context) {
        return new RetryCallAdapterFactory(context);
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations,
                                 @NonNull Retrofit retrofit) {

        /**
         * You can setup a default max retry count for all connections.
         */
        int itShouldRetry = 0;
        boolean checkInternetConnection = false;

        final Retry retry = getRetry(annotations);
        final NoInternetConnection noInternetConnection = getNoInternetConnection(annotations);
        if (retry != null) {
            itShouldRetry = retry.max();
        }
        if (noInternetConnection != null) {
            checkInternetConnection = true;
        }
        Log.d(TAG, "Starting a CallAdapter with {} retries." + itShouldRetry);
        return new RetryCallAdapter<>(
                retrofit.nextCallAdapter(this, returnType, annotations),
                itShouldRetry, context, checkInternetConnection
        );
    }

    private Retry getRetry(@NonNull Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            if (annotation instanceof Retry) {
                return (Retry) annotation;
            }
        }
        return null;
    }

    private NoInternetConnection getNoInternetConnection(@NonNull Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            if (annotation instanceof NoInternetConnection) {
                return (NoInternetConnection) annotation;
            }
        }
        return null;
    }

    static final class RetryCallAdapter<R, T> implements CallAdapter<R, T> {

        private final CallAdapter<R, T> delegated;
        private final int maxRetries;
        Context context;
        boolean checkInternetConnection;

        public RetryCallAdapter(CallAdapter<R, T> delegated, int maxRetries, Context context, boolean checkInternetConnection) {
            this.delegated = delegated;
            this.maxRetries = maxRetries;
            this.context = context;
            this.checkInternetConnection = checkInternetConnection;
        }

        @Override
        public Type responseType() {
            return delegated.responseType();
        }

        @Override
        public T adapt(final Call<R> call) {
            return delegated.adapt((maxRetries > 0 || checkInternetConnection) ? new RetryingCall<>(call, maxRetries, context, checkInternetConnection) : call);
        }
    }

    static final class RetryingCall<R> implements Call<R> {

        private final Call<R> delegated;
        private final int maxRetries;
        Context context;
        boolean checkInternetConnection;

        public RetryingCall(Call<R> delegated, int maxRetries, Context context, boolean checkInternetConnection) {
            this.delegated = delegated;
            this.maxRetries = maxRetries;
            this.context = context;
            this.checkInternetConnection = checkInternetConnection;
        }

        @Override
        public Response<R> execute() throws IOException {
            return delegated.execute();
        }

        @Override
        public void enqueue(@NonNull Callback<R> callback) {
            delegated.enqueue(new RetryCallback<>(delegated, callback, maxRetries, context, checkInternetConnection));
        }

        @Override
        public boolean isExecuted() {
            return delegated.isExecuted();
        }

        @Override
        public void cancel() {
            delegated.cancel();
        }

        @Override
        public boolean isCanceled() {
            return delegated.isCanceled();
        }

        @Override
        public Call<R> clone() {
            return new RetryingCall<>(delegated.clone(), maxRetries, context, checkInternetConnection);
        }

        @Override
        public Request request() {
            return delegated.request();
        }
    }

    static final class RetryCallback<T> implements Callback<T> {

        private final Call<T> call;
        final Callback<T> callback;
        private final int maxRetries;
        Context context;
        boolean checkInternetConnection;

        public RetryCallback(Call<T> call, Callback<T> callback, int maxRetries, Context context, boolean checkInternetConnection) {
            this.call = call;
            this.callback = callback;
            this.maxRetries = maxRetries;
            this.context = context;
            this.checkInternetConnection = checkInternetConnection;
        }

        private final AtomicInteger retryCount = new AtomicInteger(0);

        @Override
        public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
//            if (!response.isSuccessful() && retryCount.incrementAndGet() <= maxRetries) {
//                retryCall();
//            } else {
            callback.onResponse(call, response);
//            }
        }

        @Override
        public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
            t.printStackTrace();
            if (checkInternetConnection && !ConnectionUtils.CheckNetwork(context)) {
                callback.onFailure(call,
                        new NotConnectedException("Device doesn't have an available network connection turned on"));
                showNoInternetConnection();
            } else {

                if ((retryCount.incrementAndGet() <= maxRetries) &&
                        !((t instanceof IOException) && t.getMessage().equals("Canceled")) && !(t instanceof SocketException)
                        && !(t instanceof StreamResetException)) {
                    retryCall(call);
                } else if (maxRetries > 0) {
                    callback.onFailure(call,
                            new TimeoutException(String.format("No retries left after %s attempts.", maxRetries)));
                } else {
                    callback.onFailure(call, t);
                }
            }
        }

        private void retryCall(Call call) {
            Log.w(TAG, "" + retryCount.get() + "/" + maxRetries + " " + " Retrying...");
            ((Activity) context).runOnUiThread(() -> showRetryDialog(RetryCallback.this, call));

        }

        void showRetryDialog(Callback callback, Call call) {
            try {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Please check your internet connection");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Retry", (dialog1, which) -> RetryCallback.this.call.clone().enqueue(callback));
                dialog.setNegativeButton("Cancel", (dialog12, which) -> RetryCallback.this.callback.onFailure(call,
                        new TimeoutException("Retry cancelled")));

                AlertDialog alertDialog = dialog.create();
                if (!((Activity) context).isFinishing())
                    alertDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void showNoInternetConnection() {
            try {
                ((Activity) context).runOnUiThread(() ->
                        GlobalFunctions.showDialog((Activity) context,
                                "No internet connection. Please check your network settings and try again"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
