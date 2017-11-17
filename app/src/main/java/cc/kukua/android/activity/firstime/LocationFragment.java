package cc.kukua.android.activity.firstime;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.activity.auth.SessionManager;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.eventbuses.TransactFragment;
import cc.kukua.android.interfaces.FragmentInterface;
import cc.kukua.android.utils.UiUtils;

import static android.app.Activity.RESULT_OK;

public class LocationFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {
    /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private GoogleMap mMap;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private String TAG = LocationFragment.class.getSimpleName();

    @BindView(R.id.location_subtitle)
    TextView subTitle;
    @BindView(R.id.btn_proceed)
    Button btnProceed;
    @BindView(R.id.edit_location)
    EditText edLocation;
    protected GoogleApiClient mGoogleApiClient;
    private double lat = 0, lng = 0;

    boolean isNew = true;

    FragmentInterface fragmentInterface;
    SessionManager session;
    private LocationRequest mLocationRequest;

    private MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, rootView);
        session = new SessionManager(getContext());

        this.isNew = getArguments().getBoolean("isNew");
        updateFragmentUI();
        if(this.isNew) {
            btnProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DummyDataProvider.userDetail.put("latLong", lat + "," + lng);
                    AppUsageFragment appUsageFragment = new AppUsageFragment();
                    EventBus.getDefault().post(new TransactFragment(appUsageFragment));
                }
            });
        }else{
            btnProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    session.saveLatitude(String.valueOf(lat));
                    session.saveLongitude(String.valueOf(lng));
                    session.saveLocation(String.valueOf(lat) +","+String.valueOf(lng));
                    UiUtils.showSnackbar("You location has been updated", v);
                }
            });
        }

        edLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                    final Snackbar snackbar = Snackbar.make(v,
                            "Google play services repair problem",
                            Snackbar.LENGTH_LONG);
                    snackbar.show();
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error. Prompt user to install play services
                    final Snackbar snackbar = Snackbar.make(v,
                            "Make sure that you have installed google place services",
                            Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        buildGoogleApiClient();
        return rootView;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MapsInitializer.initialize(getActivity());
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the    user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
            return;
        }
        mMap.setMyLocationEnabled(true);
        animateMarker();

    }

    private void animateMarker() {
        LatLng loc = new LatLng(lat, lng);
        if (mMap != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(loc).title("Your Current Location"));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    loc, 15);
            mMap.animateCamera(location);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create(); // Another way to write a new object
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10); // Always write in milliseconds


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    getActivity(), 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
            return;
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
            if (mMap != null) {
                animateMarker();
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                if (place != null) {

                    LatLng latLng = place.getLatLng();
                    lat = latLng.latitude;
                    lng = latLng.longitude;
                    DummyDataProvider.userDetail.put("latLong", lat + "," + lng);
                    animateMarker();
                    Log.i(TAG, "Place: " + place.getName());
                    Log.i(TAG, "Place: " + place.getLatLng());
                    edLocation.setText(place.getAddress());
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        animateMarker();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.btn_proceed:
                DummyDataProvider.userDetail.put("latLong", lat + "," + lng);
                AppUsageFragment appUsageFragment = new AppUsageFragment();
                EventBus.getDefault().post(new TransactFragment(appUsageFragment));
                break;
            case R.id.edit_location:
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                    final Snackbar snackbar = Snackbar.make(v,
                            "Google play services repair problem",
                            Snackbar.LENGTH_LONG);
                    snackbar.show();
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error. Prompt user to install play services
                    final Snackbar snackbar = Snackbar.make(v,
                            "Make sure that you have installed google place services",
                            Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                break;
       */ }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                } else {
                    // permission denied
                }
                break;
            }

            case MY_PERMISSION_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                } else {
                    // permission denied
                }
                break;
            }

        }

    }

    private void updateFragmentUI() {
        if (fragmentInterface != null) {
            fragmentInterface.setToolBarTitle(DummyDataProvider.PICK_LOCATION);

            if (this.isNew) {
                subTitle.setText(getResources().getString(R.string.subtitle_account_info_new));
                btnProceed.setText(getResources().getString(R.string.next));
            } else {
                subTitle.setText(getResources().getString(R.string.subtitle_account_info_edit));
                btnProceed.setText(getResources().getString(R.string.save));
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentInterface = (FragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Host activity must implement the Fragment Interface");
        }
    }
}