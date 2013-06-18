package co.com.nuevaera.mobil;

import co.com.nuevaera.mobil.model.CategoriaDto;
import co.com.nuevaera.mobil.model.db.NuevaEraDatabaseHandler;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class RestaurantActivity extends ListActivity {
	 
		static final String[] MOBILE_OS = 
	               new String[] { "Android", "iOS", "WindowsMobile", "Blackberry"};
	 
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(getApplicationContext());
			
	 
			setListAdapter(new MobileArrayAdapter(this, databaseHandler.getCategories()));
	 
		}
	 
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
	 
			//get selected items
			String selectedValue = ((CategoriaDto) getListAdapter().getItem(position)).getNombre();
			Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
	 
		}
	 
	}