package co.com.nuevaera.mobil;

import android.provider.BaseColumns;

public class RestaurantContract {
	
	public RestaurantContract() {}
	
	public static abstract class RestaurantEntry implements BaseColumns {
        public static final String TABLE_NAME = "restaurant";
        public static final String COLUMN_NAME_ENTRY_ID = "restaurandid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_BANNER = "banner";

    }

}
