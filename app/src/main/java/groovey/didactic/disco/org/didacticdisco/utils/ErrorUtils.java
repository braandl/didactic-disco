package groovey.didactic.disco.org.didacticdisco.utils;


import java.io.IOException;
import java.lang.annotation.Annotation;

import groovey.didactic.disco.org.didacticdisco.network.ApiError;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ErrorUtils {

    public static Throwable parseError(Response<?> response, Retrofit retrofit) {
        Converter<ResponseBody, ApiError> converter =
                retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
        try {
            return converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }
    }
}