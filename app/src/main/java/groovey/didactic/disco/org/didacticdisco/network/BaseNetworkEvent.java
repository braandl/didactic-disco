package groovey.didactic.disco.org.didacticdisco.network;

import android.support.annotation.Nullable;


public abstract class BaseNetworkEvent<T> {

    private final boolean mSuccessful;

    private Throwable mThrowable;

    private T mContent;

    BaseNetworkEvent(final T content) {
        this(true, null);
        mContent = content;
    }

    BaseNetworkEvent(final Throwable throwable) {
        this(false, throwable);
    }

    private BaseNetworkEvent(final boolean successful, @Nullable final Throwable throwable) {
        mSuccessful = successful;
        mThrowable = throwable;
    }

    public boolean isSuccessful() {
        return mSuccessful;
    }

    @Nullable
    public Throwable getThrowable() {
        return mThrowable;
    }

    @Nullable
    public T getContent() {
        return mContent;
    }
}
