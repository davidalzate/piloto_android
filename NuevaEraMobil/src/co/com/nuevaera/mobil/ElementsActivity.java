package co.com.nuevaera.mobil;

import co.com.nuevaera.mobil.model.db.NuevaEraDatabaseHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

public class ElementsActivity extends Activity {
	
	private long  idCategory;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(RestaurantActivity.EXTRA_MESSAGE);
		
		this.idCategory  = Long.parseLong(message);
		
		setContentView(R.layout.activity_elements);
		ListView elementListView = (ListView)findViewById(R.id.elementList);
		NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(getApplicationContext());
		elementListView.setAdapter(new ElementArrayAdapter(this, databaseHandler.getElements(this.idCategory)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elements, menu);
		return true;
	}

}
