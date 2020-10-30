package com.example.sportsnews.network;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A sample showing a custom {@link CallAdapter} which adapts the built-in {@link Call} to a custom
 * version whose callback has more granular methods.
 */

public final class ErrorHandlingAdapter {
    /**
     * A callback which offers granular callbacks for various conditions.
     */
    interface MyCallback<T> {
        /**
         * Called for [200, 300) responses.
         */
        void success(Response<T> response);

        /**
         * Called for [400, 500) response.
         */
        void serverError(Response<?> response);

        /**
         * Called for network errors while making the call.
         */
        void networkError(IOException e);

        /**
         * Called when there is an error in gson generalization // IT's JSON not gson
         */
        void gsonException(String error);

        /**
         * Called when there is a ObjUser that is unauthorized
         */
        void unAuthroized(String error);
    }

    interface MyCall<T> {
        void cancel();

        void enqueue(MyCallback<T> callback);

        MyCall<T> clone();
    }

    public static class ErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

        @Override
        public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations,
                                     Retrofit retrofit) {
            if (getRawType(returnType) != MyCall.class) {
                return null;
            }
            if (!(returnType instanceof ParameterizedType)) {
                throw new IllegalStateException(
                        "MyCall must have generic type (e.g., MyCall<ResponseBody>)");
            }
            Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
            Executor callbackExecutor = retrofit.callbackExecutor();
            return new ErrorHandlingCallAdapter<>(responseType, callbackExecutor);
        }

        private static final class ErrorHandlingCallAdapter<R> implements CallAdapter<R, MyCall<R>> {
            private final Type responseType;
            private final Executor callbackExecutor;

            ErrorHandlingCallAdapter(Type responseType, Executor callbackExecutor) {
                this.responseType = responseType;
                this.callbackExecutor = callbackExecutor;
            }

            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public MyCall<R> adapt(Call<R> call) {
                return new MyCallAdapter<>(call, callbackExecutor);
            }
        }
    }

    /**
     * Adapts a {@link Call} to {@link MyCall}.
     */
    static class MyCallAdapter<T> implements MyCall<T> {
        private final Call<T> call;
        private final Executor callbackExecutor;
        Handler mainHandler;

        MyCallAdapter(Call<T> call, Executor callbackExecutor) {
            this.call = call;
            this.callbackExecutor = callbackExecutor;
            mainHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void cancel() {
            call.cancel();
        }

        @Override
        public void enqueue(final MyCallback<T> callback) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, final Response<T> response) {
                    mainHandler.post(() -> {
                        int code = response.code();
                        if (code >= 200 && code < 300) {
                            callback.success(response);
                        } else if (code == 401) {
                            callback.unAuthroized("you are unauthorized to do this operation");
                        } else if ((code > 401 && code < 409) || (code > 409 && code < 501)) {
                            callback.serverError(response);
                        } else if (code == 409) {
                            try {
                                if ((new JSONObject(response.errorBody().string())).get("errorMessage").toString().equals("Token is Invalid")) {
                                    callback.unAuthroized(response.errorBody().string());
                                } else {
                                    callback.gsonException((new JSONObject(response.errorBody().string())).get("errorMessage").toString());
                                }
                            } catch (JSONException e) {
                                callback.gsonException(e.getMessage());
                            } catch (IOException e) {
                                callback.gsonException(e.getMessage());
                            }
                        }
                    });
                }


                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    if (t instanceof IOException) {
                        callback.networkError((IOException) t);
                    } else {
                        callback.gsonException(t.getMessage());
                    }
                }
            });
        }


        @Override
        public MyCall<T> clone() {
            return new MyCallAdapter<>(call.clone(), callbackExecutor);
        }
    }
}