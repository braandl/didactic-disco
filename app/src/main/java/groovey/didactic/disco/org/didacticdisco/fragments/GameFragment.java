package groovey.didactic.disco.org.didacticdisco.fragments;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.map.ViewController;
import org.oscim.theme.IRenderTheme;
import org.oscim.theme.ThemeLoader;
import org.oscim.theme.VtmThemes;
import org.oscim.theme.styles.LineStyle;
import org.oscim.tiling.source.geojson.OsmBuildingJsonTileSource;
import org.oscim.tiling.source.geojson.OsmLanduseJsonTileSource;
import org.oscim.tiling.source.geojson.OsmRoadLabelJsonTileSource;
import org.oscim.tiling.source.geojson.OsmRoadLineJsonTileSource;
import org.oscim.tiling.source.geojson.OsmWaterJsonTileSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import groovey.didactic.disco.org.didacticdisco.DiscoApplication;
import groovey.didactic.disco.org.didacticdisco.R;
import groovey.didactic.disco.org.didacticdisco.data.Session;
import groovey.didactic.disco.org.didacticdisco.events.DrawParameterEvents;
import groovey.didactic.disco.org.didacticdisco.events.LocationEvent;
import groovey.didactic.disco.org.didacticdisco.managers.RxBus;

public class GameFragment extends Fragment implements ColorPicker.OnColorChangedListener {
    private Map mMap;
    private MapPreferences mPrefs;
    private MapView mMapView = null;

    private LineStyle currentLineStyle = null;
    private GeoPoint lastLocation = null;

    private PathLayer path;

    @Inject
    protected Session mSession;

    @Inject
    protected RxBus mRxBus;

    public static GameFragment getInstance() {
        return new GameFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState)
    {
        StrictMode.allowThreadDiskWrites(); //TODO: StrictMode gone after dev!
        StrictMode.allowThreadDiskReads();
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

        currentLineStyle = new LineStyle(10, "", Color.RED, 10, Paint.Cap.BUTT, false, 0, Color.TRANSPARENT, 0, 0, 0, false, null, false);

        setupEnvironment();
        registerInfoBusses();
        addControls();


    }

    private void addControls() {
        addColorPickerControl();
    }

    private void addColorPickerControl() {
        ColorPicker picker = (ColorPicker) getActivity().findViewById(R.id.picker);
        picker.addOpacityBar((OpacityBar) getActivity().findViewById(R.id.opacitybar));
        picker.addSaturationBar((SaturationBar) getActivity().findViewById(R.id.saturationbar));
        picker.addSVBar((SVBar) getActivity().findViewById(R.id.svbar));
        picker.setOnColorChangedListener(this);
        picker.setShowOldCenterColor(false);
    }

    /**
     * Register Info-Channel for Events that are needed in the Game Design.
     */
    public void registerInfoBusses() {
        mRxBus.register(LocationEvent.class, this::onNewLocation);
    }

    public void testPathDrawing() {
        this.mMap.addTask(() -> {
            for (int i = 0; i < 10000; i++) {
                GeoPoint p = new GeoPoint(52.5444644 + ((double)i / 10000), 13.3532383 - ((double)i / 10000));
                lastLocation = p;
                path.addPoint(p);
                Log.e("PathDrawer", "Did draw Path particle @ " + p.getLatitude() + "," + p.getLongitude());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mMap.updateMap(true);

            }
        });
    }

    //FIXME: Type
    public void onNewLocation(LocationEvent e) {
        /*Location t = e;
        GeoPoint p = new GeoPoint(t.getLatitude(), t.getLongitude());
        path.addPoint(p);*/
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

        IRenderTheme theme = ThemeLoader.load(VtmThemes.DEFAULT);

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


        int c = Color.fade(Color.rainbow((float) (53 + 90) / 180), 0.5f);
        path = new PathLayer(mMap, c, 3);
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
        BoundingBox bBox = new BoundingBox(52.543315481374954, 13.350890769620161, 52.54528069248375,13.354436963538177);
        pos.setByBoundingBox(bBox, Tile.SIZE * 4, Tile.SIZE * 4);
        mRxBus.post(new DrawParameterEvents(bBox));
        mMap.setMapPosition(pos);

        testPathDrawing();
    }

    @Override
    public void onColorChanged(int color) {
        path = new PathLayer(mMap, color, 3);
        mMap.layers().add(path);
        path.addPoint(lastLocation);
        Log.e("COLOR", "color was changed to " + color);
    }
}
