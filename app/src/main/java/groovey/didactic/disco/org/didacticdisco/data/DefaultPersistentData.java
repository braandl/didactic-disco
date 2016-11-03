package groovey.didactic.disco.org.didacticdisco.data;

import android.content.SharedPreferences;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultPersistentData implements PersistentData, SharedPreferences.OnSharedPreferenceChangeListener {

    private final Resources mResources;

    private final SharedPreferences mSharedPreferences;

    private final List<OnPersistentDataChangeListener> mPersistentDataChangeListeners;

    public DefaultPersistentData(final Resources resources, final SharedPreferences sharedPreferences) {
        mResources = resources;
        mSharedPreferences = sharedPreferences;
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        mPersistentDataChangeListeners = Collections.synchronizedList(new ArrayList<OnPersistentDataChangeListener>());
    }

    // Contains

    @Override
    public boolean contains(final String key) {
        return mSharedPreferences.contains(key);
    }

    @Override
    public boolean contains(final int key) {
        return mSharedPreferences.contains(getKeyName(key));
    }

    // Stores

    @Override
    public PersistentData store(final String key, final boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
        return this;
    }

    @Override
    public PersistentData store(final int key, final boolean value) {
        mSharedPreferences.edit().putBoolean(getKeyName(key), value).apply();
        return this;
    }

    @Override
    public PersistentData store(final String key, final String value) {
        mSharedPreferences.edit().putString(key, value).apply();
        return this;
    }

    @Override
    public PersistentData store(final int key, final String value) {
        mSharedPreferences.edit().putString(getKeyName(key), value).apply();
        return this;
    }

    @Override
    public PersistentData store(final String key, final int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
        return this;
    }

    @Override
    public PersistentData store(final int key, final int value) {
        mSharedPreferences.edit().putInt(getKeyName(key), value).apply();
        return this;
    }

    @Override
    public PersistentData store(final String key, final long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
        return this;
    }

    @Override
    public PersistentData store(final int key, final long value) {
        mSharedPreferences.edit().putLong(getKeyName(key), value).apply();
        return this;
    }

    @Override
    public PersistentData store(final String key, final float value) {
        mSharedPreferences.edit().putFloat(key, value).apply();
        return this;
    }

    @Override
    public PersistentData store(final int key, final float value) {
        mSharedPreferences.edit().putFloat(getKeyName(key), value).apply();
        return this;
    }

    // Restores

    @Override
    public boolean restore(final String key, final boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public boolean restore(final int key, final boolean defaultValue) {
        return mSharedPreferences.getBoolean(getKeyName(key), defaultValue);
    }

    @Override
    public String restore(final String key, final String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    @Override
    public String restore(final int key, final String defaultValue) {
        return mSharedPreferences.getString(getKeyName(key), defaultValue);
    }

    @Override
    public int restore(final String key, final int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public int restore(final int key, final int defaultValue) {
        return mSharedPreferences.getInt(getKeyName(key), defaultValue);
    }

    @Override
    public long restore(final String key, final long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public long restore(final int key, final long defaultValue) {
        return mSharedPreferences.getLong(getKeyName(key), defaultValue);
    }

    @Override
    public float restore(final String key, final float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public float restore(final int key, final float defaultValue) {
        return mSharedPreferences.getFloat(getKeyName(key), defaultValue);
    }

    // Removes

    @Override
    public PersistentData remove(final String key) {
        mSharedPreferences.edit().remove(key).apply();
        return this;
    }

    @Override
    public PersistentData remove(final int key) {
        mSharedPreferences.edit().remove(getKeyName(key)).apply();
        return this;
    }

    @Override
    public void registerOnPersistentDataChangeListener(final OnPersistentDataChangeListener listener) {
        mPersistentDataChangeListeners.add(listener);
    }

    @Override
    public void unregisterOnPersistentDataChangeListener(final OnPersistentDataChangeListener listener) {
        mPersistentDataChangeListeners.remove(listener);
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
        for (final OnPersistentDataChangeListener persistentDataChangeListener : mPersistentDataChangeListeners) {
            persistentDataChangeListener.onPersistentDataChanged(key);
        }
    }

    // Helper methods

    protected String getKeyName(final int key) {
        return mResources.getString(key);
    }
}