package groovey.didactic.disco.org.didacticdisco.services;

import android.Manifest;
import android.app.Application;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GnssMeasurementsEvent;
import android.location.GnssNavigationMessage;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.oscim.core.BoundingBox;

import java.util.ArrayList;

import javax.inject.Inject;

import groovey.didactic.disco.org.didacticdisco.DiscoApplication;
import groovey.didactic.disco.org.didacticdisco.R;
import groovey.didactic.disco.org.didacticdisco.data.Session;
import groovey.didactic.disco.org.didacticdisco.events.DrawParameterEvents;
import groovey.didactic.disco.org.didacticdisco.events.LocationEvent;
import groovey.didactic.disco.org.didacticdisco.managers.RxBus;
import groovey.didactic.disco.org.didacticdisco.network.ApiManager;
import groovey.didactic.disco.org.didacticdisco.network.Coordinate;
import groovey.didactic.disco.org.didacticdisco.network.LineRequest;
import groovey.didactic.disco.org.didacticdisco.utils.NotificationUtils;
import timber.log.Timber;


public class LocationTrackerService extends Service implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GnssListener,
        GoogleApiClient.OnConnectionFailedListener {

    @Inject
    protected Application mApplication;

    @Inject
    protected Session mSession;

    @Inject
    protected RxBus mRxBus;

    @Inject
    protected ApiManager mApiManager;

    private GoogleApiClient mGoogleApiClient;

    private DrawParameterEvents drawParamEvent;

    private Coordinate old;
    private GnssContainer container;

    @Override
    public void onCreate() {
        super.onCreate();
        ((DiscoApplication) this.getApplicationContext()).getAppComponent().inject(this);

        mGoogleApiClient = getApiClient();
    }

    private void onBoundingBox(DrawParameterEvents bBoxEvent) {
        drawParamEvent = bBoxEvent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("service started");
        mGoogleApiClient.connect();
        mRxBus.register(DrawParameterEvents.class, this::onBoundingBox);
        int nId = NotificationUtils.notify(mApplication, NotificationUtils.TRACKING_RUNNING);
        Notification mNotification = NotificationUtils.getCurrentTrackingNotification();
        this.startForeground(nId, mNotification);

        runWithGalileo();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
        if (container != null) {
            container.unregisterAll();
        }
        super.onDestroy();
    }

    private GoogleApiClient getApiClient() {
        return new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private LocationRequest getLocationRequest() {




        return LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(500);
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {

        LocationEvent loctionEvent = new LocationEvent(location);
        mRxBus.post(loctionEvent);

        Coordinate current = new Coordinate(
                location.getLatitude(),
                location.getLongitude()
        );

        Timber.d("lat=%s, lon=%s",
                location.getLatitude(),
                location.getLongitude());

        String id = mSession.get(R.string.key_uuid, "");
        String nick = mSession.get(R.string.key_username, "");

        Timber.d("draw = %s, old = %s", drawParamEvent != null, old);
        if (old != null && drawParamEvent != null) {

            BoundingBox viewBBox = drawParamEvent.getBoundingBox();
            double north = viewBBox.getMaxLatitude();
            double south = viewBBox.getMinLatitude();
            double east = viewBBox.getMaxLongitude();
            double west = viewBBox.getMinLongitude();

            Coordinate bottomLeft = new Coordinate(south, west);
            Coordinate topRight = new Coordinate(north, east);

            ArrayList<Coordinate> box = new ArrayList<>();
            box.add(bottomLeft);
            box.add(topRight);

            ArrayList<Coordinate> coordinates = new ArrayList<>();
            coordinates.add(old);
            coordinates.add(current);
            LineRequest lineRequest = new LineRequest(
                    id,
                    nick,
                    coordinates,
                    box,
                    drawParamEvent.getThickness(),
                    drawParamEvent.getColor()
            );
            Log.e(getClass().getSimpleName(), "Posting line");
            mApiManager.postLine(lineRequest);
        }
        old = current;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onLocationStatusChanged(String provider, int status, Bundle extras) {
        Timber.e("Status changed");
    }

    @Override
    public void onGnssMeasurementsReceived(GnssMeasurementsEvent event) {
        Timber.e("onGnssMeasurementsReceived");
    }

    @Override
    public void onGnssMeasurementsStatusChanged(int status) {
        Timber.e("onGnssMeasurementsStatusChanged");
    }

    @Override
    public void onGnssNavigationMessageReceived(GnssNavigationMessage event) {
        Timber.e("onGnssNavigationMessageReceived");
    }

    @Override
    public void onGnssNavigationMessageStatusChanged(int status) {
        Timber.e("onGnssNavigationMessageStatusChanged");
    }

    @Override
    public void onGnssStatusChanged(GnssStatus gnssStatus) {
        Timber.e("onGnssStatusChanged");
    }

    @Override
    public void onListenerRegistration(String listener, boolean result) {

    }

    @Override
    public void onNmeaReceived(long l, String s) {

    }

    private void runWithGalileo() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Timber.d("Did succeed");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Timber.d("connected");
        //boolean isGranted = checkLocationPermission();
        //Timber.d("%s", isGranted);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Timber.d("Did succeed");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        /*LocationServices.FusedLocationApi.requestLocationUpdates(
                       mGoogleApiClient,
                          getLocationRequest(),
                this
              );*/
    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    /*
    public boolean checkLocationPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(mApplication, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }
    */
}
