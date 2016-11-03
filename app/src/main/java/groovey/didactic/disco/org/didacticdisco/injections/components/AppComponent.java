package groovey.didactic.disco.org.didacticdisco.injections.components;


import javax.inject.Singleton;

import dagger.Component;
import groovey.didactic.disco.org.didacticdisco.injections.modules.AppModule;
import groovey.didactic.disco.org.didacticdisco.injections.modules.NetModule;
import groovey.didactic.disco.org.didacticdisco.network.ApiManager;
import groovey.didactic.disco.org.didacticdisco.services.LocationTrackerService;


@Singleton
@Component(modules={
        AppModule.class,
        NetModule.class})
public interface AppComponent {

    // services
    void inject(LocationTrackerService locationTrackerService);
    void inject(ApiManager apiManager);
}
