package groovey.didactic.disco.org.didacticdisco.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonsware.cwac.colormixer.ColorMixer;
import com.commonsware.cwac.colormixer.ColorMixerDialog;

import org.oscim.android.MapPreferences;
import org.oscim.android.MapView;
import org.oscim.backend.canvas.Color;
import org.oscim.core.BoundingBox;
import org.oscim.core.MapPosition;
import org.oscim.core.Tile;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.map.ViewController;
import org.oscim.theme.IRenderTheme;
import org.oscim.theme.ThemeLoader;
import org.oscim.theme.VtmThemes;
import org.oscim.tiling.source.geojson.OsmBuildingJsonTileSource;
import org.oscim.tiling.source.geojson.OsmLanduseJsonTileSource;
import org.oscim.tiling.source.geojson.OsmRoadLabelJsonTileSource;
import org.oscim.tiling.source.geojson.OsmRoadLineJsonTileSource;
import org.oscim.tiling.source.geojson.OsmWaterJsonTileSource;
import org.oscim.tiling.source.mapfile.MapInfo;

import javax.inject.Inject;

import groovey.didactic.disco.org.didacticdisco.DiscoApplication;
import groovey.didactic.disco.org.didacticdisco.R;
import groovey.didactic.disco.org.didacticdisco.data.Session;
import groovey.didactic.disco.org.didacticdisco.events.BoundingBoxEvent;
import groovey.didactic.disco.org.didacticdisco.events.LocationEvent;
import groovey.didactic.disco.org.didacticdisco.managers.RxBus;

public class GameFragment extends Fragment implements ColorMixer.OnColorChangedListener {
    private Map mMap;
    private MapPreferences mPrefs;
    private MapView mMapView = null;

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
        ((DiscoApplication) getActivity().getApplicationContext()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        setupEnvironment();
        registerInfoBusses();


        //new ColorMixerDialog(getActivity(), Color.WHITE, this).show();

    }

    /**
     * Register Info-Channel for Events that are needed in the Game Design.
     */
    public void registerInfoBusses() {
        mRxBus.register(LocationEvent.class, this::onNewLocation);
    }

    //FIXME: Type
    private <T> void onNewLocation(T t) {

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


        /*l = new VectorTileLayer(mMap, new OsmBuildingJsonTileSource());
        l.setRenderTheme(theme);
        l.tileRenderer().setOverdrawColor(0);
        mMap.layers().add(l);
        mMap.layers().add(new BuildingLayer(mMap, l));*/
        mPrefs.clear();

        //FIXME: Dynamic Start Position
        MapPosition pos = new MapPosition(52.5444644, 13.3532383, 1);
        BoundingBox bBox = new BoundingBox(52.543315481374954, 13.350890769620161, 52.54528069248375,13.354436963538177);
        pos.setByBoundingBox(bBox, Tile.SIZE * 4, Tile.SIZE * 4);

        mRxBus.post(new BoundingBoxEvent(bBox));

        mMap.setMapPosition(pos);
    }

    @Override
    public void onColorChange(int i) {

    }

}
