package groovey.didactic.disco.org.didacticdisco;


import android.app.Application;

import groovey.didactic.disco.org.didacticdisco.injections.components.AppComponent;
import groovey.didactic.disco.org.didacticdisco.injections.components.DaggerAppComponent;
import groovey.didactic.disco.org.didacticdisco.injections.modules.AppModule;
import groovey.didactic.disco.org.didacticdisco.injections.modules.NetModule;

import static groovey.didactic.disco.org.didacticdisco.data.Constants.URL;

public class DiscoApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        initComponents();
    }

    public void initComponents() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(URL))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
