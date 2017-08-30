package cc.kukua.android.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.LayoutGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;

public class CharacterCustomizationActivity extends LayoutGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 720;
    private static final int CAMERA_HEIGHT = 480;

    // ===========================================================
    // Fields
    // ===========================================================

    private ITexture mPlayerTexture;
    private TiledTextureRegion mPlayerTextureRegion;
    private ITexture mEnemyTexture;
    private TiledTextureRegion mEnemyTextureRegion;

    private ITexture mParallaxLayerBackTexture;
    private ITexture mParallaxLayerMidTexture;
    private ITexture mParallaxLayerFrontTexture;

    private ITextureRegion mParallaxLayerBackTextureRegion;
    private ITextureRegion mParallaxLayerMidTextureRegion;
    private ITextureRegion mParallaxLayerFrontTextureRegion;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        this.mPlayerTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/player.png", TextureOptions.BILINEAR);
        this.mPlayerTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mPlayerTexture, 3, 4);
        this.mPlayerTexture.load();

        this.mEnemyTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/enemy.png", TextureOptions.BILINEAR);
        this.mEnemyTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mEnemyTexture, 3, 4);
        this.mEnemyTexture.load();

        this.mParallaxLayerBackTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/parallax_background_layer_back.png");
        this.mParallaxLayerBackTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerBackTexture);
        this.mParallaxLayerBackTexture.load();

        this.mParallaxLayerMidTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/parallax_background_layer_mid.png");
        this.mParallaxLayerMidTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerMidTexture);
        this.mParallaxLayerMidTexture.load();

        this.mParallaxLayerFrontTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/parallax_background_layer_front.png");
        this.mParallaxLayerFrontTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerFrontTexture);
        this.mParallaxLayerFrontTexture.load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();

    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();

        final Scene scene = new Scene();
        final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
        scene.setBackground(autoParallaxBackground);

        final Sprite parallaxLayerBackSprite = new Sprite(0, 0, this.mParallaxLayerBackTextureRegion, vertexBufferObjectManager);
        parallaxLayerBackSprite.setOffsetCenter(0, 0);
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.0f, parallaxLayerBackSprite));

        final Sprite parallaxLayerMidSprite = new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerMidTextureRegion.getHeight() - 80, this.mParallaxLayerMidTextureRegion, vertexBufferObjectManager);
        parallaxLayerMidSprite.setOffsetCenter(0, 0);
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-5.0f, parallaxLayerMidSprite));

        final Sprite parallaxLayerFrontSprite = new Sprite(0, 0, this.mParallaxLayerFrontTextureRegion, vertexBufferObjectManager);
        parallaxLayerFrontSprite.setOffsetCenter(0, 0);
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-10.0f, parallaxLayerFrontSprite));

        final float playerX = CAMERA_WIDTH * 0.5f;
        final float playerY = 5;

		/* Create two sprits and add it to the scene. */
        final AnimatedSprite player = new AnimatedSprite(playerX, playerY, this.mPlayerTextureRegion, vertexBufferObjectManager);
        player.setScaleCenterY(0);
        player.setOffsetCenterY(0);
        player.setScale(2);
        player.animate(new long[]{200, 200, 200}, 3, 5, true);

        final AnimatedSprite enemy = new AnimatedSprite(playerX - 80, playerY, this.mEnemyTextureRegion, vertexBufferObjectManager);
        enemy.setScaleCenterY(0);
        enemy.setOffsetCenterY(0);
        enemy.setScale(2);
        enemy.animate(new long[]{200, 200, 200}, 3, 5, true);

        scene.attachChild(player);
        scene.attachChild(enemy);

        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
    @Override
    protected int getLayoutID() {
        return R.layout.activity_character_customization;
    }

    @Override
    protected int getRenderSurfaceViewID() {
        return R.id.xml_rendersurfaceview;

    }
}