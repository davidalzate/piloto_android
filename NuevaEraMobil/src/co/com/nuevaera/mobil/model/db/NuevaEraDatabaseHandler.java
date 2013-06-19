package co.com.nuevaera.mobil.model.db;

import java.util.ArrayList;
import java.util.Iterator;

import co.com.nuevaera.mobil.model.CategoriaDto;
import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.model.RestauranteDto;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Category;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Element;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Restaurant;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class NuevaEraDatabaseHandler {
	private Context context;
	private NuevaeraDbHelper mDbHelper;

	public NuevaEraDatabaseHandler(Context context) {
		this.context = context;
		mDbHelper = new NuevaeraDbHelper(getContext());
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public void addRestaurant(RestauranteDto restaurant){
		try {
			/// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put(Restaurant.COLUMN_NAME_RESTAURANT_ID, restaurant.getIdRestaurante());
			values.put(Restaurant.COLUMN_NAME_NAME, restaurant.getNombre());
			values.put(Restaurant.COLUMN_NAME_BANNER,restaurant.getBanner());
			values.put(Restaurant.COLUMN_NAME_FONDO, restaurant.getFondo());

			// Insert the new row, returning the primary key value of the new row
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			long newRowId;
			newRowId = db.insert(
					Restaurant.TABLE_NAME,
					null,
			         values);
			Log.v("db", "restaurantId===="+newRowId+"----");
			db.close();
			
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			Log.v("db", "Ocurrio un error agregando el restaurante " + e.getMessage());
		}
	}
	
	public void addCategory(CategoriaDto categoria){
		try {
			/// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put(Category.COLUMN_NAME_CATEGORY_ID, categoria.getIdCategoria());
			values.put(Category.COLUMN_NAME_NAME, categoria.getNombre());
			values.put(Category.COLUMN_NAME_RESTAURANT_ID, categoria.getIdRestaurante());
			values.put(Category.COLUMN_NAME_FOTO, categoria.getFoto());

			// Insert the new row, returning the primary key value of the new row
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			long newRowId;
			newRowId = db.insert(
					Category.TABLE_NAME,
					null,
			         values);
			Log.v("db", "categoriaId===="+newRowId+"----");
			db.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.v("db", "Ocurrio un error agregando la categoria " + e.getMessage());
		}
	}
	
	public void addElement(ElementoDto elemento){
		try {
			/// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put(Element.COLUMN_NAME_ELEMENT_ID, elemento.getIdElemento());
			values.put(Element.COLUMN_NAME_CATEGORY_ID, elemento.getIdCategoria());
			values.put(Element.COLUMN_NAME_NAME, elemento.getNombre());
			values.put(Element.COLUMN_NAME_DESCORTA, elemento.getDescripcionCorta());
			values.put(Element.COLUMN_NAME_DESLARGA, elemento.getDescripcionLarga());
			values.put(Element.COLUMN_NAME_DISPONIBLE, (elemento.isDisponible()) ? 1 : 0);
			values.put(Element.COLUMN_NAME_FOTOBIG, elemento.getFotoBig());
			values.put(Element.COLUMN_NAME_FOTOSMALL, elemento.getFotoSmall());
			values.put(Element.COLUMN_NAME_PRECIO, elemento.getPrecio());

			// Insert the new row, returning the primary key value of the new row
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			long newRowId;
			newRowId = db.insert(
					Element.TABLE_NAME,
					null,
			         values);
			Log.v("db", "elementId===="+newRowId+"----");
			db.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.v("db", "Ocurrio un error agregando el elemento " + e.getMessage());
		}
	}
	
	public void addRestaurantList(ArrayList<RestauranteDto> restaurants){
		for (RestauranteDto restaurant : restaurants) {
			addRestaurant(restaurant);
		}
	}
	
	
	public void addCategoriesList(ArrayList<CategoriaDto> categories){
		for (CategoriaDto category : categories) {
			addCategory(category);
		}
	}
	
	public void addElementsList(ArrayList<ElementoDto> elements){
		for (ElementoDto elemento : elements) {
			addElement(elemento);
		}
	}
	
	public ArrayList<CategoriaDto> getCategories(){
		ArrayList<CategoriaDto> categoriasList = new ArrayList<CategoriaDto>();
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
		    Category._ID,
		    Category.COLUMN_NAME_CATEGORY_ID,
		    Category.COLUMN_NAME_NAME,
		    Category.COLUMN_NAME_FOTO,
		    Category.COLUMN_NAME_RESTAURANT_ID,
		    };
		


		Cursor cursor = db.query(
				Category.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    null,                                // The columns for the WHERE clause
		    null,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    null                                 // The sort order
		    );		
		
		if (cursor.moveToFirst()) {
	        do {
	            CategoriaDto categoria = new CategoriaDto();
	            categoria.setIdCategoria(cursor.getLong(1));
	            categoria.setNombre(cursor.getString(2));
	            categoria.setFoto(cursor.getString(3));
	            categoria.setIdRestaurante(cursor.getLong(4));
	            categoriasList.add(categoria);
	        } while (cursor.moveToNext());
	    }
		
		cursor.close();
		db.close();
		
		return categoriasList;
	}
	
	public void createDb(){
		
	}
	
	public void upgradeDb(){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

	}
}
