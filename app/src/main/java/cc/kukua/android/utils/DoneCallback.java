package cc.kukua.android.utils;

/**
 * The DoneCallBack is a generic interface that is called when ever a long running operation
 * is done and result is needed to be sent back to the caller
 *
 * @author Ilo Calistus
 */

public interface DoneCallback<T> {
    /**
     * @param result The result to return
     * @param e      An exception that was thrown while the operation was running
     */
    void done(T result, Exception e);
}
