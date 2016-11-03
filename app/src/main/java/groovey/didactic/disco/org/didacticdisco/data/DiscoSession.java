package groovey.didactic.disco.org.didacticdisco.data;

import android.content.Context;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import groovey.didactic.disco.org.didacticdisco.R;


public final class DiscoSession implements Session {

    private final Map<Integer, Object> mCache;

    private final PersistentData mPersistentData;

    public DiscoSession(final Context context, final PersistentData persistentData) {
        mPersistentData = persistentData;
        mCache = new HashMap<>();
        initialize();
    }

    @Override
    public boolean contains(final int key) {
        boolean contains = mCache.containsKey(key);
        return contains || mPersistentData.contains(key);
    }

    @Override
    public void set(final int key, final boolean value) {
        mCache.put(key, value);
    }

    @Override
    public void set(final int key, final String value) {
        mCache.put(key, value);
    }

    @Override
    public void set(final int key, final int value) {
        mCache.put(key, value);
    }

    @Override
    public void set(final int key, final long value) {
        mCache.put(key, value);
    }

    @Override
    public void set(final int key, final float value) {
        mCache.put(key, value);
    }

    @Override
    public void store(final int key, final boolean value) {
        set(key, value);
        mPersistentData.store(key, value);
    }

    @Override
    public void store(final int key, final String value) {
        set(key, value);
        mPersistentData.store(key, value);
    }

    @Override
    public void store(final int key, final int value) {
        set(key, value);
        mPersistentData.store(key, value);
    }

    @Override
    public void store(final int key, final long value) {
        set(key, value);
        mPersistentData.store(key, value);
    }

    @Override
    public void store(final int key, final float value) {
        set(key, value);
        mPersistentData.store(key, value);
    }

    @Override
    public boolean get(final int key, final boolean defaultValue) {
        Object value = mCache.get(key);
        if (value == null) {
            value = mPersistentData.restore(key, defaultValue);
            mCache.put(key, value);
            return (boolean) value;
        } else {
            return (boolean) value;
        }
    }

    @Override
    public String get(final int key, final String defaultValue) {
        Object value = mCache.get(key);
        if (value == null) {
            value = mPersistentData.restore(key, defaultValue);
            if (value != null) {
                mCache.put(key, value);
                return (String) value;
            }
        } else {
            return (String) value;
        }

        return defaultValue;
    }

    @Override
    public int get(final int key, final int defaultValue) {
        Object value = mCache.get(key);
        if (value == null) {
            value = mPersistentData.restore(key, defaultValue);
            mCache.put(key, value);
            return (int) value;
        } else {
            return (int) value;
        }
    }

    @Override
    public long get(final int key, final long defaultValue) {
        Object value = mCache.get(key);
        if (value == null) {
            value = mPersistentData.restore(key, defaultValue);
            mCache.put(key, value);
            return (long) value;
        } else {
            return (long) value;
        }
    }

    @Override
    public float get(final int key, final float defaultValue) {
        Object value = mCache.get(key);
        if (value == null) {
            value = mPersistentData.restore(key, defaultValue);
            mCache.put(key, value);
            return (float) value;
        } else {
            return (float) value;
        }
    }

    /**
     * Initialize all temp cache properties from device and <code>environment.xml</code>
     * version name and version code
     */
    private void initialize() {
        String uuid = get(R.string.key_uuid, "");
        if (uuid.equals("")) {
            store(R.string.key_uuid, UUID.randomUUID().toString());
        }
    }


    @Override
    public void removeSetting(int key) {
        mPersistentData.remove(key);
        if (mCache.containsKey(key)) {
            mCache.remove(key);
        }
    }
}