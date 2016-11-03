package groovey.didactic.disco.org.didacticdisco.network;


import groovey.didactic.disco.org.didacticdisco.utils.ErrorUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public abstract class AbstractRetrofitCallback<T> implements Callback<T> {

    private final Retrofit mRetrofit;

    public AbstractRetrofitCallback(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onFailure(ErrorUtils.parseError(response, mRetrofit));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        onFailure(throwable);
    }

    protected abstract void onSuccess(final T t);

    protected abstract void onFailure(final Throwable throwable);
}
