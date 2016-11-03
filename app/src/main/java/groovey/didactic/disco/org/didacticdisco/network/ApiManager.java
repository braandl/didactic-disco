package groovey.didactic.disco.org.didacticdisco.network;


import android.app.Application;

import javax.inject.Inject;

import groovey.didactic.disco.org.didacticdisco.DiscoApplication;
import groovey.didactic.disco.org.didacticdisco.data.Session;
import groovey.didactic.disco.org.didacticdisco.managers.RxBus;
import retrofit2.Call;
import retrofit2.Retrofit;


public class ApiManager {

    @Inject
    protected Session mSession;

    @Inject
    protected RxBus mRxBus;

    @Inject
    protected Retrofit mRetrofit;

    @Inject
    protected DiscoService mDiscoService;

    public ApiManager(Application app) {
        ((DiscoApplication) app).getAppComponent().inject(this);
    }

    ///////////
    // Requests
    ///////////

    public Call<DrawingsResponse> postLine(Line line) {
        Call<DrawingsResponse> call = mDiscoService.postLine(line);
        call.enqueue(new DrawingCallback());
        return call;
    }

    ////////////
    // Callbacks
    ////////////

    public class DrawingCallback extends AbstractRetrofitCallback<DrawingsResponse> {

        DrawingCallback() {
            super(mRetrofit);
        }

        @Override
        protected void onSuccess(DrawingsResponse response) {
            mRxBus.post(new OnDrawingsEvent(response));
        }

        @Override
        protected void onFailure(Throwable throwable) {
            mRxBus.post(new OnDrawingsEvent(throwable));
        }
    }

}
