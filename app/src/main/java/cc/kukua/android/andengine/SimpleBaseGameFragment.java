package cc.kukua.android.andengine;
import org.andengine.entity.scene.Scene;

import java.io.IOException;
/**
 * Created by mistaguy on 9/4/2017.
 */

public abstract class SimpleBaseGameFragment  extends BaseGameFragment {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    protected abstract void onCreateResources() throws IOException;
    protected abstract Scene onCreateScene();

    @Override
    public final void onCreateResources(final OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        this.onCreateResources();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public final void onCreateScene(final OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        final Scene scene = this.onCreateScene();

        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public final void onPopulateScene(final Scene pScene, final OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}