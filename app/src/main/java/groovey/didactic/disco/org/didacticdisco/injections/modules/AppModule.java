package groovey.didactic.disco.org.didacticdisco.injections.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import groovey.didactic.disco.org.didacticdisco.data.DefaultPersistentData;
import groovey.didactic.disco.org.didacticdisco.data.DiscoSession;
import groovey.didactic.disco.org.didacticdisco.data.PersistentData;
import groovey.didactic.disco.org.didacticdisco.data.Session;


@Module
public class AppModule {

    private Application mApplication;

    public AppModule(final Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return mApplication.getResources();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }

    @Provides
    @Singleton
    PersistentData providePersistentData(final Resources resources,
                                         final SharedPreferences sharedPreferences) {
        return new DefaultPersistentData(resources, sharedPreferences);
    }

    @Provides
    @Singleton
    Session provideSession(final Context context, final PersistentData persistentData) {
        return new DiscoSession(context, persistentData);
    }
}