package co.com.nuevaera.mobil.model.db;

import java.util.ArrayList;
import java.util.Iterator;

import co.com.nuevaera.mobil.model.CategoriaDto;
import co.com.nuevaera.mobil.model.RestauranteDto;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Category;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Restaurant;
import android.content.ContentValues;
import android.content.Context;
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
			Log.v("db", "Ocurrio un error agregando el restaurante " + e.getMessage());
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
	
	public void createDb(){
		
	}
	
	public void upgradeDb(){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

	}
}
