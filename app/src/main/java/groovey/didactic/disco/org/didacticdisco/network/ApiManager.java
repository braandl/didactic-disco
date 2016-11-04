package groovey.didactic.disco.org.didacticdisco.network;


import android.app.Application;

import javax.inject.Inject;

import groovey.didactic.disco.org.didacticdisco.DiscoApplication;
import groovey.didactic.disco.org.didacticdisco.data.Session;
import groovey.didactic.disco.org.didacticdisco.events.NewOnDrawEvent;
import groovey.didactic.disco.org.didacticdisco.events.OnDrawEvent;
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

    public Call<Result> postLine(LineRequest lineRequest) {
        Call<Result> call = mDiscoService.postLine(lineRequest);
        call.enqueue(new DrawingCallback());
        return call;
    }

    ////////////
    // Callbacks
    ////////////

    public class DrawingCallback extends AbstractRetrofitCallback<Result> {

        DrawingCallback() {
            super(mRetrofit);
        }

        @Override
        protected void onSuccess(Result response) {
            mRxBus.post(new NewOnDrawEvent(response));
        }

        @Override
        protected void onFailure(Throwable throwable) {
            mRxBus.post(new OnDrawEvent(throwable));
        }
    }

}
