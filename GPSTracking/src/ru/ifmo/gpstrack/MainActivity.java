package ru.ifmo.gpstrack;

import java.util.ArrayList;
import java.util.List;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
	ArrayList<Data> dataArray;
	DataSource datasource;
	private GoogleMap map;
	LocationManager locManager;
	Marker me;
	Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		datasource = new DataSource(this);
		datasource.open();
		dataArray = datasource.getAllData();
		datasource.close();
		initDB();

		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		location = getLastBestLocation();
		updateCurLocation();
		LatLng curLocation = new LatLng(location.getLatitude(), location.getLongitude());
		setContentView(R.layout.main);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

		for (int i = 1; i < dataArray.size(); i++) {
			Data d = dataArray.get(i);
			map.addMarker(new MarkerOptions().position(new LatLng(d.latitude, d.longitude)).title(d.name)
					.snippet(d.text));
		}
		me = map.addMarker(new MarkerOptions()
				.position(new LatLng(dataArray.get(0).latitude, dataArray.get(0).longitude))
				.title(dataArray.get(0).name).snippet(dataArray.get(0).text)
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 12));

	}

	private void initDB() {
		if (!dataArray.isEmpty()) {
			return;
		}
		Data d1 = new Data("User1", "working", 59.9558, 30.2994);
		Data d2 = new Data("User2", "eating", 59.9558, 30.2994);
		Data d3 = new Data("User3", "sleeping", 59.9568, 30.31917);

		dataArray = new ArrayList<Data>();
		datasource.open();
		d1 = datasource.createData(d1);
		d2 = datasource.createData(d2);
		d3 = datasource.createData(d3);
		datasource.close();
		dataArray.add(d1);
		dataArray.add(d2);
		dataArray.add(d3);

	}

	private void updateCurLocation() {
		Data d = dataArray.get(0);
		d.latitude = location.getLatitude();
		d.longitude = location.getLongitude();
		datasource.open();
		datasource.updateData(d);
		datasource.close();
	}

	private void updateCurStatus(String status) {
		Data d = dataArray.get(0);
		d.text = status;
		datasource.open();
		datasource.updateData(d);
		datasource.close();
		me.setSnippet(d.text);
	}

	public Location getLastBestLocation() {
		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestTime = Long.MIN_VALUE;

		List<String> matchingProviders = locManager.getAllProviders();
		for (String provider : matchingProviders) {
			Location location = locManager.getLastKnownLocation(provider);
			if (location != null) {
				float accuracy = location.getAccuracy();
				long time = location.getTime();

				if (accuracy < bestAccuracy) {
					bestResult = location;
					bestAccuracy = accuracy;
					bestTime = time;
				} else if (bestAccuracy == Float.MAX_VALUE && time > bestTime) {
					bestResult = location;
					bestTime = time;
				}
			}
		}
		return bestResult;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			Intent intent = new Intent(this, EditActivity.class);
			intent.putExtra("status", dataArray.get(0).text);
			startActivityForResult(intent, 1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		String status = data.getStringExtra("status");
		updateCurStatus(status);

	}

}