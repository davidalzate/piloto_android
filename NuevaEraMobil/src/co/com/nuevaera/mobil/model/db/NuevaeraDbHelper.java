package co.com.nuevaera.mobil.model.db;

import co.com.nuevaera.mobil.model.db.RestaurantContract.Anuncio;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Category;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Element;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Restaurant;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NuevaeraDbHelper extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "NuevaEra.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String INT_TYPE = " INTEGER";
	private static final String REAL_TYPE = " REAL";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_RESTAURANTS = 
			"CREATE TABLE "
			+ Restaurant.TABLE_NAME + " (" + Restaurant._ID
			+ " INTEGER PRIMARY KEY," + Restaurant.COLUMN_NAME_RESTAURANT_ID
			+ INT_TYPE + COMMA_SEP + Restaurant.COLUMN_NAME_NAME
			+ TEXT_TYPE + COMMA_SEP + Restaurant.COLUMN_NAME_BANNER
			+ TEXT_TYPE + COMMA_SEP + Restaurant.COLUMN_NAME_FONDO
			+ TEXT_TYPE + " ); ";

	private static final String SQL_CREATE_CATEGORIES =
			"CREATE TABLE "
			+ Category.TABLE_NAME + " (" + Category._ID
			+ " INTEGER PRIMARY KEY," + Category.COLUMN_NAME_CATEGORY_ID
			+ INT_TYPE + COMMA_SEP + Category.COLUMN_NAME_NAME
			+ TEXT_TYPE + COMMA_SEP + Category.COLUMN_NAME_RESTAURANT_ID
			+ TEXT_TYPE + COMMA_SEP + Category.COLUMN_NAME_FOTO
			+ TEXT_TYPE + " ); " +
			"";
	
	private static final String SQL_CREATE_ELEMENTS =
			"CREATE TABLE "
			+ Element.TABLE_NAME + " (" + Element._ID
			+ " INTEGER PRIMARY KEY," + Element.COLUMN_NAME_ELEMENT_ID
			+ INT_TYPE + COMMA_SEP + Element.COLUMN_NAME_CATEGORY_ID
			+ INT_TYPE + COMMA_SEP + Element.COLUMN_NAME_NAME
			+ TEXT_TYPE + COMMA_SEP + Element.COLUMN_NAME_DESLARGA
			+ TEXT_TYPE + COMMA_SEP + Element.COLUMN_NAME_DESCORTA
			+ INT_TYPE + COMMA_SEP + Element.COLUMN_NAME_DISPONIBLE
			+ TEXT_TYPE + COMMA_SEP + Element.COLUMN_NAME_FOTOBIG
			+ TEXT_TYPE + COMMA_SEP + Element.COLUMN_NAME_FOTOSMALL
			+ REAL_TYPE + COMMA_SEP + Element.COLUMN_NAME_PRECIO
			+ TEXT_TYPE + " ); " +
			"";
	
	private static final String SQL_CREATE_ANUNCIOS =
			"CREATE TABLE "
			+ Anuncio.TABLE_NAME + " (" + Anuncio._ID
			+ " INTEGER PRIMARY KEY," + Anuncio.COLUMN_NAME_ANUNCIO_ID
			+ INT_TYPE + COMMA_SEP + Anuncio.COLUMN_NAME_EMPRESA_ID
			+ INT_TYPE + COMMA_SEP + Anuncio.COLUMN_NAME_FOTOBIG
			+ TEXT_TYPE + COMMA_SEP + Anuncio.COLUMN_NAME_FOTOSMALL
			+ TEXT_TYPE + COMMA_SEP + Anuncio.COLUMN_NAME_DURACION
			+ INT_TYPE + " ); " +
			"";

	private static final String SQL_DELETE_RESTAURANTS = "DROP TABLE IF EXISTS "
			+ Restaurant.TABLE_NAME;
	
	private static final String SQL_DELETE_CATEGORIES = "DROP TABLE IF EXISTS "
			+ Category.TABLE_NAME;

	private static final String SQL_DELETE_ELEMENTS = "DROP TABLE IF EXISTS "
			+ Element.TABLE_NAME;
	
	private static final String SQL_DELETE_ADDS = "DROP TABLE IF EXISTS "
			+ Anuncio.TABLE_NAME;
	
	public NuevaeraDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_RESTAURANTS);
		db.execSQL(SQL_CREATE_CATEGORIES);
		db.execSQL(SQL_CREATE_ELEMENTS);
		db.execSQL(SQL_CREATE_ANUNCIOS);
		Log.v("db", "NuevaEra database was created");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_RESTAURANTS);
		db.execSQL(SQL_DELETE_CATEGORIES);
		db.execSQL(SQL_DELETE_ELEMENTS);
		db.execSQL(SQL_DELETE_ADDS);
		
		
		Log.v("db", "NuevaEra database wass removed");
		onCreate(db);
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}
