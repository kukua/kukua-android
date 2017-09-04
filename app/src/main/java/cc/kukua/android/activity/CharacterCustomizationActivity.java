package cc.kukua.android.activity;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

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
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.LayoutGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.adapters.AppuseListAdapter;
import cc.kukua.android.adapters.CustomizeListAdapter;
import cc.kukua.android.model.AppuseList;
import cc.kukua.android.model.CustomizeList;

public class CharacterCustomizationActivity extends LayoutGameActivity {
    @BindView(R.id.toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_next3)
    Button btnNextButton;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private int lastExpandedPosition = -1;
    private int lastExpandedItemPosition = -1;
    private static String purpose;
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 640; //480

    // ===========================================================
    // Fields
    // ===========================================================
    private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;

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
    private ITextureRegion mSVGTestTextureRegion;
    private ITextureRegion mSVGPreview1;

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

        this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(),  1024, 1024, TextureOptions.NEAREST);
        SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mSVGTestTextureRegion = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "cloud.svg", 200, 200);
        this.mSVGPreview1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "category_preview1.svg", 294, 521);

        try {
            this.mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.mBuildableBitmapTextureAtlas.load();
        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
        this.mPlayerTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/player.png", TextureOptions.BILINEAR);
        this.mPlayerTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mPlayerTexture, 3, 4);
        this.mPlayerTexture.load();

        this.mEnemyTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/enemy.png", TextureOptions.BILINEAR);
        this.mEnemyTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mEnemyTexture, 3, 4);
        this.mEnemyTexture.load();

        this.mParallaxLayerBackTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/parallax_background_layer_back.jpg");
        this.mParallaxLayerBackTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerBackTexture);
        this.mParallaxLayerBackTexture.load();

        this.mParallaxLayerMidTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/parallax_background_layer_mid.png");
        this.mParallaxLayerMidTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerMidTexture);
        this.mParallaxLayerMidTexture.load();

        this.mParallaxLayerFrontTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/parallax_background_layer_front.png");
        this.mParallaxLayerFrontTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerFrontTexture);
        this.mParallaxLayerFrontTexture.load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();

        ButterKnife.bind(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvToolbarTitle.setText(getString(R.string.customize_your_character));

                expandableListDetail = CustomizeList.getData();
                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                expandableListAdapter = new CustomizeListAdapter(getApplicationContext(), expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (lastExpandedPosition != -1
                                && groupPosition != lastExpandedPosition) {
                            expandableListView.collapseGroup(lastExpandedPosition);
                        }
                        lastExpandedPosition = groupPosition;

                        Toast.makeText( getApplicationContext(),
                                expandableListTitle.get(groupPosition) + " List Expanded.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

                expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                    @Override
                    public void onGroupCollapse(int groupPosition) {
                        Toast.makeText( getApplicationContext(),
                                expandableListTitle.get(groupPosition) + " List Collapsed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        purpose = expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition);

                        Toast.makeText(
                                getApplicationContext(), purpose, Toast.LENGTH_SHORT
                        ).show();

                        if (lastExpandedItemPosition != -1
                                && childPosition != lastExpandedItemPosition) {
                            int index = parent.getFlatListPosition(
                                    ExpandableListView.getPackedPositionForChild(lastExpandedPosition, lastExpandedItemPosition)
                            );
                            parent.setItemChecked(index, false);
                        }
                        lastExpandedItemPosition = childPosition;

                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                        parent.setItemChecked(index, true);

                        return true;
                    }
                });
            }
        });


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

//        final Sprite parallaxLayerMidSprite = new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerMidTextureRegion.getHeight() - 80, this.mParallaxLayerMidTextureRegion, vertexBufferObjectManager);
//        parallaxLayerMidSprite.setOffsetCenter(0, 0);
//        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-5.0f, parallaxLayerMidSprite));

        final Sprite parallaxLayerMidSprite = new Sprite(0, CAMERA_HEIGHT - this.mSVGTestTextureRegion.getHeight() - 2, this.mSVGTestTextureRegion, vertexBufferObjectManager);
        parallaxLayerMidSprite.setOffsetCenter(0, 0);
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-5.0f, parallaxLayerMidSprite));
//
//        final Sprite parallaxLayerFrontSprite = new Sprite(0, 0, this.mParallaxLayerFrontTextureRegion, vertexBufferObjectManager);
//        parallaxLayerFrontSprite.setOffsetCenter(0, 0);
//        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-10.0f, parallaxLayerFrontSprite));
//
//        final float playerX = CAMERA_WIDTH * 0.5f;
//        final float playerY = 5;
//
//		/* Create two sprits and add it to the scene. */
//        final AnimatedSprite player = new AnimatedSprite(playerX, playerY, this.mPlayerTextureRegion, vertexBufferObjectManager);
//        player.setScaleCenterY(0);
//        player.setOffsetCenterY(0);
//        player.setScale(2);
//        player.animate(new long[]{200, 200, 200}, 3, 5, true);
//
//        final AnimatedSprite enemy = new AnimatedSprite(playerX - 80, playerY, this.mEnemyTextureRegion, vertexBufferObjectManager);
//        enemy.setScaleCenterY(0);
//        enemy.setOffsetCenterY(0);
//        enemy.setScale(2);
//        enemy.animate(new long[]{200, 200, 200}, 3, 5, true);
//
//        scene.attachChild(player);
//        scene.attachChild(enemy);

        final Sprite previewOneSprite = new Sprite(0, CAMERA_HEIGHT - this.mSVGPreview1.getHeight() - 100, this.mSVGPreview1, vertexBufferObjectManager);
        //previewOneSprite.setOffsetCenter(0, 0);
        scene.attachChild(previewOneSprite);
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