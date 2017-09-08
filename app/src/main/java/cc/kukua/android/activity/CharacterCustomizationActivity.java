package cc.kukua.android.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.LayoutGameActivity;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.adapters.CustomizeListAdapter;
import cc.kukua.android.constants.DummyDataProvider;
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
    HashMap<String, List<Integer>> expandableListDetail;
    private int lastExpandedPosition = -1;
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 640; //480

    // ===========================================================
    // Fields
    // ===========================================================
    private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
    private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas2;

    private ITexture mParallaxLayerBackTexture;
    private ITexture mParallaxLayerMidTexture;

    private ITextureRegion mParallaxLayerBackTextureRegion;
    private ITextureRegion mParallaxLayerMidTextureRegion;
    private ITextureRegion mSVGTestTextureRegion;

    private ITextureRegion mFace1Region;
    private ITextureRegion mFace2Region;

    private ITextureRegion hatSprite1Region;
    private ITextureRegion hatSprite2Region;

    private ITextureRegion armRegionLeft1;
    private ITextureRegion handRegionLeft1;
    private ITextureRegion armRegionRight1;
    private ITextureRegion handRegionRight1;

    private ITextureRegion armRegionLeft2;
    private ITextureRegion handRegionLeft2;
    private ITextureRegion armRegionRight2;
    private ITextureRegion handRegionRight2;

    private ITextureRegion legRegionLeft1;
    private ITextureRegion shoesRegionLeft1;
    private ITextureRegion legRegionRight1;
    private ITextureRegion shoesRegionRight1;

    private ITextureRegion legRegionLeft2;
    private ITextureRegion shoesRegionLeft2;
    private ITextureRegion legRegionRight2;
    private ITextureRegion shoesRegionRight2;

    private ITextureRegion shirtRegion1;
    private ITextureRegion shirtRegion2;

    Integer characterOption = 2;
    Scene scene;

    Sprite faceSprite1;
    Sprite faceSprite2;

    Sprite hatSprite1;
    Sprite hatSprite2;

    Sprite armSpriteLeft1;
    Sprite handSpriteLeft1;
    Sprite armSpriteRight1;
    Sprite handSpriteRight1;

    Sprite armSpriteLeft2;
    Sprite handSpriteLeft2;
    Sprite armSpriteRight2;
    Sprite handSpriteRight2;

    Sprite legSpriteLeft1;
    Sprite shoesSpriteLeft1;
    Sprite legSpriteRight1;
    Sprite shoesSpriteRight1;

    Sprite legSpriteLeft2;
    Sprite shoesSpriteLeft2;
    Sprite legSpriteRight2;
    Sprite shoesSpriteRight2;

    Sprite shirtSprite1;
    Sprite shirtSprite2;


    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {

        this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 768, TextureOptions.NEAREST);
        SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mSVGTestTextureRegion = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "cloud.svg", 150, 150);
        this.mFace1Region = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "item_face1.svg", 150, 150);
        this.mFace2Region = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "item_face2.svg", 150, 150);
        this.hatSprite1Region = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "hat1.svg", 180, 180);
        this.hatSprite2Region = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "hat2.svg", 150, 150);

        armRegionLeft1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "arm_left1.svg", 80, 140);
        handRegionLeft1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "hand_left1.svg",200, 130);
        armRegionRight1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "arm_right1.svg", 150, 100);
        handRegionRight1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "hand_right1.svg", 200, 120);

        armRegionLeft2 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "arm_left2.svg", 80, 140);
        handRegionLeft2 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "hand_left2.svg", 200, 130);
        armRegionRight2 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "arm_right2.svg", 150, 100);
        handRegionRight2 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "hand_right2.svg", 200, 120);

        legRegionLeft1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "leg_left1.svg", 150, 120);
        shoesRegionLeft1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "shoes_left1.svg", 150, 100);
        legRegionRight1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "leg_right1.svg", 150, 120);
        shoesRegionRight1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "shoes_right1.svg", 150, 100);

        legRegionLeft2 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "leg_left2.svg", 150, 120);
        shoesRegionLeft2 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "shoes_left2.svg", 150, 100);
        legRegionRight2 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "leg_right2.svg", 150, 120);
        shoesRegionRight2 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "shoes_right2.svg", 150, 100);

        shirtRegion1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "shirt1.svg", 220, 170);
        shirtRegion2 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "shirt2.svg", 220, 170);


        try {
            this.mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.mBuildableBitmapTextureAtlas.load();
        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }


        this.mParallaxLayerBackTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/background.png");
        this.mParallaxLayerBackTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerBackTexture);
        this.mParallaxLayerBackTexture.load();

        this.mParallaxLayerMidTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/parallax_background_layer_mid.png");
        this.mParallaxLayerMidTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerMidTexture);
        this.mParallaxLayerMidTexture.load();

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


                    }
                });

                expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                    @Override
                    public void onGroupCollapse(int groupPosition) {


                    }
                });

                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        String purpose = expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition);

                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                        parent.setItemChecked(index, true);

                        Integer drawableId = expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition);

                        String purposeTitle = expandableListTitle.get(groupPosition);

                        if (purposeTitle.equals("FACE")) {
                            try {
                                if (drawableId == R.drawable.item_face1) {
                                    faceSprite2.setVisible(false);
                                    faceSprite1.setVisible(true);
                                } else {
                                    faceSprite2.setVisible(true);
                                    faceSprite1.setVisible(false);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (purposeTitle.equals("HAT")) {
                            try {
                                if (drawableId == R.drawable.item_hat1) {
                                    hatSprite2.setVisible(false);
                                    hatSprite1.setVisible(true);
                                } else {
                                    hatSprite2.setVisible(true);
                                    hatSprite1.setVisible(false);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (purposeTitle.equals("SHIRT")) {
                            try {
                                if (drawableId == R.drawable.item_torso1) {
                                    shirtSprite2.setVisible(false);
                                    shirtSprite1.setVisible(true);
                                } else {
                                    shirtSprite2.setVisible(true);
                                    shirtSprite1.setVisible(false);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        if (purposeTitle.equals("SHOES")) {
                            try {
                                if (drawableId == R.drawable.item_lower_leg1) {
                                    shoesSpriteLeft2.setVisible(false);
                                    shoesSpriteLeft1.setVisible(true);
                                    shoesSpriteRight2.setVisible(false);
                                    shoesSpriteRight1.setVisible(true);
                                } else {
                                    shoesSpriteLeft2.setVisible(true);
                                    shoesSpriteLeft1.setVisible(false);
                                    shoesSpriteRight2.setVisible(true);
                                    shoesSpriteRight1.setVisible(false);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        if (purposeTitle.equals("PANTS")) {
                            try {
                                if (drawableId == R.drawable.item_upper_leg1) {
                                    legSpriteLeft2.setVisible(false);
                                    legSpriteLeft1.setVisible(true);
                                    legSpriteRight2.setVisible(false);
                                    legSpriteRight1.setVisible(true);
                                } else {
                                    legSpriteLeft2.setVisible(true);
                                    legSpriteLeft1.setVisible(false);
                                    legSpriteRight2.setVisible(true);
                                    legSpriteRight1.setVisible(false);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

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

        scene = new Scene();
        final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
        scene.setBackground(autoParallaxBackground);

        final Sprite parallaxLayerBackSprite = new Sprite(0, 0, this.mParallaxLayerBackTextureRegion, vertexBufferObjectManager);
        parallaxLayerBackSprite.setOffsetCenter(0, 0);
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.0f, parallaxLayerBackSprite));

        final Sprite parallaxLayerMidSprite = new Sprite(0, CAMERA_HEIGHT - this.mSVGTestTextureRegion.getHeight() - 2, this.mSVGTestTextureRegion, vertexBufferObjectManager);
        parallaxLayerMidSprite.setOffsetCenter(0, 0);
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-5.0f, parallaxLayerMidSprite));

        faceSprite1 = new Sprite(
                CAMERA_WIDTH - (
                        CAMERA_WIDTH / 2),
                CAMERA_HEIGHT - this.mFace1Region.getHeight() - 50,
                this.mFace1Region, vertexBufferObjectManager);

        faceSprite2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2),
                CAMERA_HEIGHT - this.mFace2Region.getHeight() - 50,
                this.mFace2Region, vertexBufferObjectManager);

        hatSprite1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2),
                CAMERA_HEIGHT - 110,
                this.hatSprite1Region, vertexBufferObjectManager);
        hatSprite2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)+20,
                CAMERA_HEIGHT - 125,
                this.hatSprite2Region, vertexBufferObjectManager);

        armSpriteLeft1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2) + this.armRegionLeft1.getWidth()+10,
                CAMERA_HEIGHT - this.armRegionLeft1.getHeight() - 130,
                this.armRegionLeft1, vertexBufferObjectManager);

        armSpriteLeft2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)+ this.armRegionLeft2.getWidth()+10,
                CAMERA_HEIGHT - this.armRegionLeft2.getHeight() - 130,
                this.armRegionLeft2, vertexBufferObjectManager);

        handSpriteLeft1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)+ this.armRegionLeft1.getWidth()+ 9,
                CAMERA_HEIGHT - this.handRegionLeft1.getHeight() - 65,
                this.handRegionLeft1, vertexBufferObjectManager);

        handSpriteLeft2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)+ this.armRegionLeft2.getWidth()+ 20,
                CAMERA_HEIGHT - this.handRegionLeft2.getHeight() - 70,
                this.handRegionLeft2, vertexBufferObjectManager);

        armSpriteRight1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)-100,
                CAMERA_HEIGHT - this.armRegionRight1.getHeight() - 218,
                this.armRegionRight1, vertexBufferObjectManager);

        armSpriteRight2 = new Sprite(
                (CAMERA_WIDTH / 2)-100,
                CAMERA_HEIGHT - this.armRegionRight2.getHeight() - 218,
                this.armRegionRight2, vertexBufferObjectManager);

        handSpriteRight1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)-114,
                CAMERA_HEIGHT - this.handRegionRight1.getHeight() - 287,
                this.handRegionRight1, vertexBufferObjectManager);

        handSpriteRight2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)-105,
                CAMERA_HEIGHT - this.handRegionRight2.getHeight() - 287,
                this.handRegionRight2, vertexBufferObjectManager);

        legSpriteLeft1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2) + 45,
                CAMERA_HEIGHT - this.legRegionLeft1.getHeight() - 371,
                this.legRegionLeft1, vertexBufferObjectManager);

        legSpriteLeft2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)+45,
                CAMERA_HEIGHT - this.legRegionLeft2.getHeight() - 371,
                this.legRegionLeft2, vertexBufferObjectManager);

        shoesSpriteLeft1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)+ 85,
                CAMERA_HEIGHT - this.shoesRegionLeft1.getHeight() - 500,
                this.shoesRegionLeft1, vertexBufferObjectManager);

        shoesSpriteLeft2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)+78,
                CAMERA_HEIGHT - this.shoesRegionLeft2.getHeight() - 495,
                this.shoesRegionLeft2, vertexBufferObjectManager);


        legSpriteRight1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)-45,
                CAMERA_HEIGHT - this.legRegionRight1.getHeight() - 371,
                this.legRegionRight1, vertexBufferObjectManager);

        legSpriteRight2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)-45,
                CAMERA_HEIGHT - this.legRegionRight2.getHeight() - 371,
                this.legRegionRight2, vertexBufferObjectManager);

        shoesSpriteRight1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2)-85,
                CAMERA_HEIGHT - this.shoesRegionRight1.getHeight() - 500,
                this.shoesRegionRight1, vertexBufferObjectManager);

        shoesSpriteRight2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH /2)-74,
                CAMERA_HEIGHT - this.shoesRegionRight2.getHeight() - 495,
                this.shoesRegionRight2, vertexBufferObjectManager);

        shirtSprite1 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2),
                CAMERA_HEIGHT - this.shirtRegion1.getHeight() - this.faceSprite1.getHeight() - 30,
                this.shirtRegion1, vertexBufferObjectManager);

        shirtSprite2 = new Sprite(
                CAMERA_WIDTH - (CAMERA_WIDTH / 2),
                CAMERA_HEIGHT - this.shirtRegion2.getHeight() - this.faceSprite2.getHeight() - 30,
                this.shirtRegion2, vertexBufferObjectManager);


        scene.attachChild(faceSprite1);
        scene.attachChild(faceSprite2);

        scene.attachChild(hatSprite1);
        scene.attachChild(hatSprite2);

        scene.attachChild(shirtSprite1);
        scene.attachChild(shirtSprite2);

        scene.attachChild(armSpriteLeft1);
        scene.attachChild(armSpriteLeft2);

        scene.attachChild(handSpriteLeft1);
        scene.attachChild(handSpriteLeft2);

        scene.attachChild(armSpriteRight1);
        scene.attachChild(armSpriteRight2);

        scene.attachChild(handSpriteRight1);
        scene.attachChild(handSpriteRight2);

        scene.attachChild(legSpriteLeft1);
        scene.attachChild(legSpriteLeft2);

        scene.attachChild(shoesSpriteLeft1);
        scene.attachChild(shoesSpriteLeft2);

        scene.attachChild(legSpriteRight1);
        scene.attachChild(legSpriteRight2);

        scene.attachChild(shoesSpriteRight1);
        scene.attachChild(shoesSpriteRight2);


        if (DummyDataProvider.userDetail.get("character") == "0") {
            faceSprite1.setVisible(true);
            faceSprite2.setVisible(false);
            hatSprite1.setVisible(true);
            hatSprite2.setVisible(false);
            shirtSprite1.setVisible(true);
            shirtSprite2.setVisible(false);
            armSpriteLeft1.setVisible(true);
            armSpriteLeft2.setVisible(false);
            handSpriteLeft1.setVisible(true);
            handSpriteLeft2.setVisible(false);
            armSpriteRight1.setVisible(true);
            armSpriteRight2.setVisible(false);
            handSpriteRight1.setVisible(true);
            handSpriteRight2.setVisible(false);
            legSpriteLeft1.setVisible(true);
            legSpriteLeft2.setVisible(false);
            shoesSpriteLeft1.setVisible(true);
            shoesSpriteLeft2.setVisible(false);
            legSpriteRight1.setVisible(true);
            legSpriteRight2.setVisible(false);
            shoesSpriteRight1.setVisible(true);
            shoesSpriteRight2.setVisible(false);

        } else {
            faceSprite1.setVisible(false);
            faceSprite2.setVisible(true);
            hatSprite1.setVisible(false);
            hatSprite2.setVisible(true);
            shirtSprite1.setVisible(false);
            shirtSprite2.setVisible(true);
            armSpriteLeft1.setVisible(false);
            armSpriteLeft2.setVisible(true);
            handSpriteLeft1.setVisible(false);
            handSpriteLeft2.setVisible(true);
            armSpriteRight1.setVisible(false);
            armSpriteRight2.setVisible(true);
            handSpriteRight1.setVisible(false);
            handSpriteRight2.setVisible(true);
            legSpriteLeft1.setVisible(false);
            legSpriteLeft2.setVisible(true);
            shoesSpriteLeft1.setVisible(false);
            shoesSpriteLeft2.setVisible(true);
            legSpriteRight1.setVisible(false);
            legSpriteRight2.setVisible(true);
            shoesSpriteRight1.setVisible(false);
            shoesSpriteRight2.setVisible(true);
        }


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