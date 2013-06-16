package co.com.nuevaera.mobil;

import co.com.nuevaera.mobil.RestaurantContract.RestaurantEntry;
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

	// Contacts table name
	private static final String TABLE_RESTAURANTS = "restaurants";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ RestaurantEntry.TABLE_NAME + " (" + RestaurantEntry._ID
			+ " INTEGER PRIMARY KEY," + RestaurantEntry.COLUMN_NAME_ENTRY_ID
			+ TEXT_TYPE + COMMA_SEP + RestaurantEntry.COLUMN_NAME_NAME
			+ TEXT_TYPE + COMMA_SEP + RestaurantEntry.COLUMN_NAME_BANNER
			+  " )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ RestaurantEntry.TABLE_NAME;

	public NuevaeraDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.v("db", " Constructor Nueva Era database created");
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
		Log.v("db", "YA!! Nueva Era database created");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		Log.v("db", "Nueva Era database removed");
		onCreate(db);
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}
