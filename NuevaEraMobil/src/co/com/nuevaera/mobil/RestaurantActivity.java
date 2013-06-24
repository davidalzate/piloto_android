package co.com.nuevaera.mobil;

import co.com.nuevaera.mobil.model.CategoriaDto;
import co.com.nuevaera.mobil.model.db.NuevaEraDatabaseHandler;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class RestaurantActivity extends ListActivity {
	 
	public final static String EXTRA_MESSAGE = "co.com.nuevaera.mobil.model.MESSAGE";
	 
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(getApplicationContext());
	 
			setListAdapter(new CategoryArrayAdapter(this, databaseHandler.getCategories()));
	 
		}
	 
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
	 
			//get selected items
			CategoriaDto categoriaDto =  ((CategoriaDto) getListAdapter().getItem(position));
			String categoryName = categoriaDto.getNombre();
			long  categoryId = categoriaDto.getIdCategoria();
			Toast.makeText(this, "categoryId -"+ categoryName, Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(RestaurantActivity.this, ElementsActivity.class);
		    intent.putExtra(EXTRA_MESSAGE, Long.toString(categoryId));

		    startActivity(intent);
	 
		}
	 
	}