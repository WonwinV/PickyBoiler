package pickyboiler.pickyboiler;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng tripleX = new LatLng(40.4227077, -86.90541109999998);
        LatLng puccinis = new LatLng(40.4222415,-86.9021022);
        LatLng pappys = new LatLng(40.424702,-86.911036);
        LatLng maruSushi = new LatLng(40.424270,-86.906759);
        LatLng madMushroom = new LatLng(40.424098,-86.908994);
        LatLng pandaExpress = new LatLng(40.424694,-86.907793);

        mMap.addMarker(new MarkerOptions().position(tripleX).title("Triple X"));
        mMap.addMarker(new MarkerOptions().position(puccinis).title("Puccini's"));
        mMap.addMarker(new MarkerOptions().position(pappys).title("Pappy's Sweet Shop"));
        mMap.addMarker(new MarkerOptions().position(maruSushi).title("Maru Sushi"));
        mMap.addMarker(new MarkerOptions().position(madMushroom).title("Mad Mushroom Pizza"));
        mMap.addMarker(new MarkerOptions().position(pandaExpress).title("Panda Express"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(tripleX));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tripleX, 15.0f));
    }
}
