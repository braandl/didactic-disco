package groovey.didactic.disco.org.didacticdisco.network;


import android.app.Application;

import javax.inject.Inject;
import javax.inject.Named;

import groovey.didactic.disco.org.didacticdisco.DiscoApplication;
import groovey.didactic.disco.org.didacticdisco.data.Session;
import retrofit2.Retrofit;


public class ApiManager {

    @Inject
    protected Session mSession;

    @Inject
    @Named("CiboRetrofit")
    protected Retrofit mRetrofit;

    @Inject
    protected DiscoService mDiscoService;

    public ApiManager(Application app) {
        ((DiscoApplication) app).getAppComponent().inject(this);
    }

}
