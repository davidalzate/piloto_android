package co.com.nuevaera.mobil;

import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.model.db.NuevaEraDatabaseHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ElementsActivity extends Activity {
	
	private long  idCategory;
	public final static String EXTRA_MESSAGE1 = "co.com.nuevaera.mobil.model.MESSAGE1";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(RestaurantActivity.EXTRA_MESSAGE);
		String message2 = intent.getStringExtra(RestaurantActivity.EXTRA_MESSAGE2);
		
		this.setTitle(message2);

		
		this.idCategory  = Long.parseLong(message);
		
		setContentView(R.layout.activity_elements);
		final ListView elementListView = (ListView)findViewById(R.id.elementList);
		
		NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(getApplicationContext());
		elementListView.setAdapter(new ElementArrayAdapter(this, databaseHandler.getElements(this.idCategory)));
		

		
		elementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				ElementoDto listItem = (ElementoDto)elementListView.getItemAtPosition(position);
				System.out.println(listItem);
				Toast.makeText(ElementsActivity.this, "elementid -"+ listItem.toString(), Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(ElementsActivity.this, InfoElement.class);
			    intent.putExtra(EXTRA_MESSAGE1, Long.toString(listItem.getIdElemento()));

			    startActivity(intent);
		 				
				
			}
			
			 
		});
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elements, menu);
		return true;
	}

	
	
}
