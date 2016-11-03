package groovey.didactic.disco.org.didacticdisco.services;

import android.Manifest;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
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


public class LocationTrackerService extends Service implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
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

    @Override
    public void onCreate() {
        super.onCreate();
        ((DiscoApplication) this.getApplicationContext()).getAppComponent().inject(this);
        mRxBus.register(DrawParameterEvents.class, this::onBoundingBox);
        mGoogleApiClient = getApiClient();
    }

    private void onBoundingBox(DrawParameterEvents bBoxEvent) {
        drawParamEvent = bBoxEvent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mGoogleApiClient.connect();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
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
                .setFastestInterval(1000);
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationEvent loctionEvent = new LocationEvent(location);
        mRxBus.post(loctionEvent);

        Coordinate current = new Coordinate(
                location.getLatitude(),
                location.getLongitude()
        );

        String id = mSession.get(R.string.key_uuid, "");
        String nick = mSession.get(R.string.key_username, "");


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
            mApiManager.postLine(lineRequest);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        boolean isGranted = checkLocationPermission();
        if (isGranted) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    getLocationRequest(),
                    this
            );
        }
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


    public boolean checkLocationPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(mApplication, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }
}
