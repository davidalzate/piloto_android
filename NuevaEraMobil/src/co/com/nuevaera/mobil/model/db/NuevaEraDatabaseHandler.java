package co.com.nuevaera.mobil.model.db;

import java.util.ArrayList;
import java.util.Iterator;

import co.com.nuevaera.mobil.model.AnuncioDto;
import co.com.nuevaera.mobil.model.CategoriaDto;
import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.model.RestauranteDto;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Anuncio;
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
	
	public void addAnuncio(AnuncioDto anuncio){
		try {
			/// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put(Anuncio.COLUMN_NAME_ANUNCIO_ID, anuncio.getIdAnuncio());
			values.put(Anuncio.COLUMN_NAME_EMPRESA_ID, anuncio.getIdEmpresa());
			values.put(Anuncio.COLUMN_NAME_FOTOBIG, anuncio.getFotoBig());
			values.put(Anuncio.COLUMN_NAME_FOTOSMALL, anuncio.getFotoSmall());
			values.put(Anuncio.COLUMN_NAME_DURACION, anuncio.getDuracion());

			// Insert the new row, returning the primary key value of the new row
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			long newRowId;
			newRowId = db.insert(
					Anuncio.TABLE_NAME,
					null,
			         values);
			Log.v("db", "anuncioId===="+newRowId+"----");
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
	
	public void addAnunciosList(ArrayList<AnuncioDto> adds){
		for (AnuncioDto anuncio : adds) {
			addAnuncio(anuncio);
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
	
	public ArrayList<ElementoDto> getElements(long idCategory){
		ArrayList<ElementoDto> elementList = new ArrayList<ElementoDto>();
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
		    Element._ID,
		    Element.COLUMN_NAME_ELEMENT_ID,
		    Element.COLUMN_NAME_CATEGORY_ID,
		    Element.COLUMN_NAME_DESCORTA,
		    Element.COLUMN_NAME_DESLARGA,
		    Element.COLUMN_NAME_FOTOBIG,
		    Element.COLUMN_NAME_FOTOSMALL,
		    Element.COLUMN_NAME_NAME,
		    Element.COLUMN_NAME_PRECIO
		    };
		String selection = Element.COLUMN_NAME_CATEGORY_ID + " = ?";
		// Specify arguments in placeholder order.
		String[] selectionArgs = { Long.toString(idCategory) };


		Cursor cursor = db.query(
				Element.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    selection,                                // The columns for the WHERE clause
		    selectionArgs,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    null                                 // The sort order
		    );		
		
		if (cursor.moveToFirst()) {
	        do {
	        	ElementoDto elemento = new ElementoDto();
	        	elemento.setIdElemento(cursor.getLong(1));
	        	elemento.setIdCategoria(cursor.getLong(2));
	        	elemento.setDescripcionCorta(cursor.getString(3));
	        	elemento.setDescripcionLarga(cursor.getString(4));
	        	elemento.setFotoBig(cursor.getString(5));
	        	elemento.setFotoSmall(cursor.getString(6));
	        	elemento.setNombre(cursor.getString(7));
	        	elemento.setPrecio(cursor.getString(8));
	            elementList.add(elemento);
	        } while (cursor.moveToNext());
	    }
		
		cursor.close();
		db.close();
		
		return elementList;
	}
	
	public ArrayList<AnuncioDto> getAdds(){
		ArrayList<AnuncioDto> addsList = new ArrayList<AnuncioDto>();
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				Anuncio._ID,
				Anuncio.COLUMN_NAME_ANUNCIO_ID,
				Anuncio.COLUMN_NAME_EMPRESA_ID,
				Anuncio.COLUMN_NAME_FOTOBIG,
				Anuncio.COLUMN_NAME_FOTOSMALL,
				Anuncio.COLUMN_NAME_DURACION
		    };
		


		Cursor cursor = db.query(
				Anuncio.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    null,                                // The columns for the WHERE clause
		    null,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    null                                 // The sort order
		    );		
		
		if (cursor.moveToFirst()) {
	        do {
	            AnuncioDto anuncio = new AnuncioDto();
	            anuncio.setIdAnuncio(cursor.getLong(1));
	            anuncio.setIdEmpresa(cursor.getInt(2));
	            anuncio.setFotoBig(cursor.getString(3));
	            anuncio.setFotoSmall(cursor.getString(4));
	            anuncio.setDuracion(cursor.getInt(5));
	            addsList.add(anuncio);
	        } while (cursor.moveToNext());
	    }
		
		cursor.close();
		db.close();
		
		return addsList;
	}
	
	
	public int deleteRestaurant(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		// Issue SQL statement.
		int count = db.delete(Restaurant.TABLE_NAME, null, null);
		db.close();
		return count;
	}
	
	public int deleteCategories(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		// Issue SQL statement.
		int count = db.delete(Category.TABLE_NAME, null, null);
		db.close();
		return count;
	}
	
	public int deleteElements(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		// Define 'where' part of query.
		// Specify arguments in placeholder order.
		// Issue SQL statement.
		int count = db.delete(Element.TABLE_NAME, null, null);
		db.close();
		return count;
	}
	
	
	public int deleteAdds(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		// Define 'where' part of query.
		// Specify arguments in placeholder order.
		// Issue SQL statement.
		int count = db.delete(Anuncio.TABLE_NAME, null, null);
		db.close();
		return count;
	}
	
	public void deleteAllRecords(){
		deleteRestaurant();
		deleteCategories();
		deleteElements();
		deleteAdds();
	}
	
	public void createDb(){
		
	}
	
	public void upgradeDb(){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

	}
	
	public ElementoDto getElement(long idElement){
		ElementoDto elemento = null;
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
		    Element._ID,
		    Element.COLUMN_NAME_ELEMENT_ID,
		    Element.COLUMN_NAME_CATEGORY_ID,
		    Element.COLUMN_NAME_DESCORTA,
		    Element.COLUMN_NAME_DESLARGA,
		    Element.COLUMN_NAME_FOTOBIG,
		    Element.COLUMN_NAME_FOTOSMALL,
		    Element.COLUMN_NAME_NAME,
		    Element.COLUMN_NAME_PRECIO
		    };
		String selection = Element.COLUMN_NAME_ELEMENT_ID + " = ?";
		// Specify arguments in placeholder order.
		String[] selectionArgs = { Long.toString(idElement) };


		Cursor cursor = db.query(
				Element.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    selection,                                // The columns for the WHERE clause
		    selectionArgs,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    null                                 // The sort order
		    );		
		
		if (cursor.moveToFirst()) {
	       
	        	elemento = new ElementoDto();
	        	elemento.setIdElemento(cursor.getLong(1));
	        	elemento.setIdCategoria(cursor.getLong(2));
	        	elemento.setDescripcionCorta(cursor.getString(3));
	        	elemento.setDescripcionLarga(cursor.getString(4));
	        	elemento.setFotoBig(cursor.getString(5));
	        	elemento.setFotoSmall(cursor.getString(6));
	        	elemento.setNombre(cursor.getString(7));
	        	elemento.setPrecio(cursor.getString(8));

	    }
		
		cursor.close();
		db.close();
		
		return elemento;
	}
}
