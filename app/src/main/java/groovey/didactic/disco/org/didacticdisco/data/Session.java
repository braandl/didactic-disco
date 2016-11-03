package groovey.didactic.disco.org.didacticdisco.data;


public interface Session {

    /**
     * Check if given key is available in cache or persistent store
     *
     * @param key the search key
     * @return true, if any value exists
     */
    boolean contains(int key);

    /**
     * Stores given pair into cache and persistent store
     *
     * @param key   the key
     * @param value the value
     */
    void store(int key, boolean value);

    /**
     * Stores given pair into cache and persistent store
     *
     * @param key   the key
     * @param value the value
     */
    void store(int key, String value);

    /**
     * Stores given pair into cache and persistent store
     *
     * @param key   the key
     * @param value the value
     */
    void store(int key, int value);

    /**
     * Stores given pair into cache and persistent store
     *
     * @param key   the key
     * @param value the value
     */
    void store(int key, long value);

    /**
     * Stores given pair into cache and persistent store
     *
     * @param key   the key
     * @param value the value
     */
    void store(int key, float value);

    /**
     * Holds given pair into volatile cache
     *
     * @param key   the key
     * @param value the value
     */
    void set(int key, boolean value);

    /**
     * Holds given pair into volatile cache
     *
     * @param key   the key
     * @param value the value
     */
    void set(int key, String value);

    /**
     * Holds given pair into volatile cache
     *
     * @param key   the key
     * @param value the value
     */
    void set(int key, int value);

    /**
     * Holds given pair into volatile cache
     *
     * @param key   the key
     * @param value the value
     */
    void set(int key, long value);

    /**
     * Holds given pair into volatile cache
     *
     * @param key   the key
     * @param value the value
     */
    void set(int key, float value);

    /**
     * Returns the boolean value for given key. If not exists it returns the given default value
     *
     * @param key          the search key
     * @param defaultValue the default value
     * @return the value or the default value
     */
    boolean get(int key, boolean defaultValue);

    /**
     * Returns the String value for given key. If not exists it returns the given default value
     *
     * @param key          the search key
     * @param defaultValue the default value
     * @return the value or the default value
     */
    String get(int key, String defaultValue);

    /**
     * Returns the integer value for given key. If not exists it returns the given default value
     *
     * @param key          the search key
     * @param defaultValue the default value
     * @return the value or the default value
     */
    int get(int key, int defaultValue);

    /**
     * Returns the long value for given key. If not exists it returns the given default value
     *
     * @param key          the search key
     * @param defaultValue the default value
     * @return the value or the default value
     */
    long get(int key, long defaultValue);

    /**
     * Returns the float value for given key. If not exists it returns the given default value
     *
     * @param key          the search key
     * @param defaultValue the default value
     * @return the value or the default value
     */
    float get(int key, float defaultValue);

    /**
     * Removes a setting
     *
     * @param key key of setting
     */
    void removeSetting(int key);
}