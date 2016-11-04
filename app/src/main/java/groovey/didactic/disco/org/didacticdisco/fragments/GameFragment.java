package groovey.didactic.disco.org.didacticdisco.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;

import org.oscim.android.MapPreferences;
import org.oscim.android.MapView;
import org.oscim.backend.canvas.Color;
import org.oscim.backend.canvas.Paint;
import org.oscim.core.BoundingBox;
import org.oscim.core.GeoPoint;
import org.oscim.core.MapPosition;
import org.oscim.core.Tile;
import org.oscim.layers.PathLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.map.ViewController;
import org.oscim.theme.IRenderTheme;
import org.oscim.theme.ThemeLoader;
import org.oscim.theme.VtmThemes;
import org.oscim.theme.styles.LineStyle;
import org.oscim.tiling.source.geojson.OsmLanduseJsonTileSource;
import org.oscim.tiling.source.geojson.OsmRoadLabelJsonTileSource;
import org.oscim.tiling.source.geojson.OsmRoadLineJsonTileSource;
import org.oscim.tiling.source.geojson.OsmWaterJsonTileSource;

import javax.inject.Inject;

import groovey.didactic.disco.org.didacticdisco.DiscoApplication;
import groovey.didactic.disco.org.didacticdisco.R;
import groovey.didactic.disco.org.didacticdisco.data.Session;
import groovey.didactic.disco.org.didacticdisco.events.DrawParameterEvents;
import groovey.didactic.disco.org.didacticdisco.events.LocationEvent;
import groovey.didactic.disco.org.didacticdisco.events.OnDrawEvent;
import groovey.didactic.disco.org.didacticdisco.managers.RxBus;
import groovey.didactic.disco.org.didacticdisco.models.Line;
import groovey.didactic.disco.org.didacticdisco.network.Coordinate;
import groovey.didactic.disco.org.didacticdisco.network.DrawResponse;
import groovey.didactic.disco.org.didacticdisco.services.LocationTrackerService;

public class GameFragment extends Fragment implements ColorPicker.OnColorChangedListener {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private Map mMap;
    private MapPreferences mPrefs;
    private MapView mMapView = null;

    private LineStyle currentLineStyle = null;
    private int currentlineWidth = 3;
    private GeoPoint lastLocation = null;

    private PathLayer path;

    private boolean doDraw = true;

    @Inject
    protected Session mSession;

    @Inject
    protected RxBus mRxBus;

    private int currentLineColor;
    private BoundingBox bBox;
    private Intent service;

    public static GameFragment getInstance() {
        return new GameFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState)
    {
        return inflater.inflate(R.layout.gamefragment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DiscoApplication) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        registerInfoBusses();
        addControls();

        startService();

        setupEnvironment();
        currentLineStyle = new LineStyle(10, "", currentLineColor, 10, Paint.Cap.ROUND, true, 0, Color.TRANSPARENT, 0, 2, 0.3f, true, null, false);
    }

    private void startService() {

        if (hasLocationPermission()) {
            service = new Intent(getActivity(), LocationTrackerService.class);
            getActivity().startService(new Intent(getActivity(), LocationTrackerService.class));
        } else {
            getLocationPermissions();
        }
    }

    public void stopService() {
        getActivity().stopService(service);
    }

    private void addControls() {
        addColorPickerControl();
        addSwitchButtons();
        addWidthSlider();
    }

    private void addSwitchButtons() {
        Switch s = (Switch) getActivity().findViewById(R.id.doDrawSwitch);
        s.setOnClickListener(view -> {
            doDraw = s.isChecked();
            lastLocation = null;
            if (doDraw) {
                startService();
                restartWithColor(currentLineColor);
            } else {
                stopService();
            }
        });
        doDraw = s.isChecked();
    }

    public void addWidthSlider() {
        SeekBar s = (SeekBar) getActivity().findViewById(R.id.widthSlider);
        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentlineWidth = Math.max(1, i);
                restartWithColor(currentLineColor);
                Log.e("SEEKBAR", "value = " +i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void addColorPickerControl() {
        ColorPicker picker = (ColorPicker) getActivity().findViewById(R.id.picker);
        picker.addOpacityBar((OpacityBar) getActivity().findViewById(R.id.opacitybar));
        picker.addSaturationBar((SaturationBar) getActivity().findViewById(R.id.saturationbar));
        picker.addSVBar((SVBar) getActivity().findViewById(R.id.svbar));
        picker.setOnColorChangedListener(this);
        picker.setShowOldCenterColor(false);

        currentLineColor = picker.getColor();
    }

    /**
     * Register Info-Channel for Events that are needed in the Game Design.
     */
    public void registerInfoBusses() {
        mRxBus.register(LocationEvent.class, this::onNewLocation);
        mRxBus.register(DrawResponse.class, this::onNewDrawEvent);
    }

    public void testPathDrawing() {
        this.mMap.addTask(() -> {
            for (int i = 0; i < 10000; i++) {
                GeoPoint p = new GeoPoint(52.5444644 + ((double)i / 10000), 13.3532383 - (double)(i / 10000));
                lastLocation = p;
                if (doDraw) {
                    path.addPoint(p);
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mMap.updateMap(true);

            }
        });
    }

    public void onNewLocation(LocationEvent e) {
        Log.e("TAG", "we have a new position " + e.toString());
        this.mMap.addTask(() -> {
            Location t = e.getLocation();
            GeoPoint p = new GeoPoint(t.getLatitude(), t.getLongitude());
            path.addPoint(p);
            mMap.updateMap(true);
        });
    }

    public void onNewDrawEvent(DrawResponse response) {
        this.mMap.addTask(() -> {
            for (Line l : response.getLines()) {
                path = new PathLayer(mMap, l.getColor(), (int)l.getThickness());
                mMap.layers().add(path);

                for (Coordinate c : l.getLine()) {
                    path.addPoint(new GeoPoint(c.getLat(), c.getLon()));
                }
            }
            mMap.updateMap(true);
        });
    }

    public void onPause() {
        super.onPause();
    }

    private void setupEnvironment() {

        mMapView = (MapView)getActivity().findViewById(R.id.vtmMapView);
        mMap = mMapView.map();
        mPrefs = new MapPreferences(this.getClass().getName(), getActivity().getApplicationContext());
        //setup map
        ViewController vc = mMap.viewport();
        vc.setTilt(0);
        vc.setMaxZoomLevel(21);

        IRenderTheme theme = ThemeLoader.load(VtmThemes.NEWTRON);

        mMap.setBaseMap(new VectorTileLayer(mMap, new OsmLanduseJsonTileSource()));
        mMap.setTheme(theme);


        VectorTileLayer l = new VectorTileLayer(mMap, new OsmWaterJsonTileSource());
        l.setRenderTheme(theme);
        l.tileRenderer().setOverdrawColor(0);
        mMap.layers().add(l);

        l = new VectorTileLayer(mMap, new OsmRoadLineJsonTileSource());
        l.setRenderTheme(theme);
        l.tileRenderer().setOverdrawColor(0);
        mMap.layers().add(l);

        l = new VectorTileLayer(mMap, new OsmRoadLabelJsonTileSource());
        l.setRenderTheme(theme);
        l.tileRenderer().setOverdrawColor(0);
        mMap.layers().add(l);
        mMap.layers().add(new LabelLayer(mMap, l));

        path = new PathLayer(mMap, currentLineColor, 3);
        mMap.layers().add(path);

        /*
        l = new VectorTileLayer(mMap, new OsmBuildingJsonTileSource());
        l.setRenderTheme(theme);
        l.tileRenderer().setOverdrawColor(0);
        mMap.layers().add(l);
        mMap.layers().add(new BuildingLayer(mMap, l));
        */

        mPrefs.clear();

        //FIXME: Dynamic Start Position
        MapPosition pos = new MapPosition(52.5444644, 13.3532383, 1);
        bBox = new BoundingBox(52.543315481374954, 13.350890769620161, 52.54528069248375,13.354436963538177);
        pos.setByBoundingBox(bBox, Tile.SIZE * 4, Tile.SIZE * 4);
        mRxBus.post(new DrawParameterEvents(bBox, currentLineColor, currentlineWidth));
        mMap.setMapPosition(pos);



        //testPathDrawing();
    }

    private void restartWithColor(int color) {
        currentLineColor = color;
        path = new PathLayer(mMap, color, currentlineWidth);
        mMap.layers().add(path);
        if (lastLocation != null) {
            path.addPoint(lastLocation);
        }
        mRxBus.post(new DrawParameterEvents(bBox, currentLineColor, currentlineWidth));
    }

    @Override
    public void onColorChanged(int color) {
        restartWithColor(color);
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getLocationPermissions() {
        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_FINE_LOCATION);
    }

}
