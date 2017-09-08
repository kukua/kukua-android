package cc.kukua.android.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.andengine.entity.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.activity.auth.SessionManager;
import cc.kukua.android.adapters.CustomizeListAdapter;
import cc.kukua.android.model.CustomizeList;

public class CharacterCustomizationCharacterActivity extends BaseCharacterActivity {
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

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {

        super.onCreateResources(pOnCreateResourcesCallback);

        ButterKnife.bind(this);
        session = new SessionManager(getApplicationContext());

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
                                    session.saveHead(R.drawable.item_face1);
                                } else {
                                    faceSprite2.setVisible(true);
                                    faceSprite1.setVisible(false);
                                    session.saveHead(R.drawable.item_face2);
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
                                    session.saveHat(R.drawable.item_hat1);
                                } else {
                                    hatSprite2.setVisible(true);
                                    hatSprite1.setVisible(false);
                                    session.saveHat(R.drawable.item_hat2);
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
                                    session.saveShirt(R.drawable.item_torso1);
                                } else {
                                    shirtSprite2.setVisible(true);
                                    shirtSprite1.setVisible(false);
                                    session.saveShirt(R.drawable.item_torso2);

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
                                    session.saveShoes(R.drawable.item_lower_leg1);

                                } else {
                                    shoesSpriteLeft2.setVisible(true);
                                    shoesSpriteLeft1.setVisible(false);
                                    shoesSpriteRight2.setVisible(true);
                                    shoesSpriteRight1.setVisible(false);
                                    session.saveShoes(R.drawable.item_lower_leg2);

                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        if (purposeTitle.equals("GlOVES")) {
                            try {
                                if (drawableId == R.drawable.item_lower_arm1) {
                                    handSpriteRight2.setVisible(false);
                                    handSpriteRight1.setVisible(true);
                                    handSpriteLeft2.setVisible(false);
                                    handSpriteLeft1.setVisible(true);
                                    session.saveHand(R.drawable.item_lower_arm1);

                                } else {
                                    handSpriteRight2.setVisible(true);
                                    handSpriteRight1.setVisible(false);
                                    handSpriteLeft2.setVisible(true);
                                    handSpriteLeft1.setVisible(false);
                                    session.saveHand(R.drawable.item_lower_arm2);

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