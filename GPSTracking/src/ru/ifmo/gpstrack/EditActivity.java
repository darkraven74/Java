package ru.ifmo.gpstrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		Intent intent = getIntent();
		String status = intent.getStringExtra("status");
		EditText t = (EditText) findViewById(R.id.editText1);
		t.setText(status);

	}

	public void clickSave(View view) {
		Intent intent = new Intent();
		EditText t = (EditText) findViewById(R.id.editText1);
		intent.putExtra("status", t.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}

}