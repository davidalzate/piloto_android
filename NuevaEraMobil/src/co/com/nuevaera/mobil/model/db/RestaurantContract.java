package co.com.nuevaera.mobil.model.db;

import android.provider.BaseColumns;

public class RestaurantContract {
	
	public RestaurantContract() {}
	
	public static abstract class Restaurant implements BaseColumns {
        public static final String TABLE_NAME = "restaurant";
        public static final String COLUMN_NAME_RESTAURANT_ID = "restaurandid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_BANNER = "banner";
        public static final String COLUMN_NAME_FONDO = "fondo";
    }

	public static abstract class Category implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_CATEGORY_ID  = "categoryid";
        public static final String COLUMN_NAME_RESTAURANT_ID = "restaurandid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_FOTO = "foto";
    }
	
	public static abstract class Element implements BaseColumns {
        public static final String TABLE_NAME = "element";
        public static final String COLUMN_NAME_CATEGORY_ID  = "categoryid";
        public static final String COLUMN_NAME_ELEMENT_ID = "elementid";
        public static final String COLUMN_NAME_DESLARGA = "descripcionLarga";
        public static final String COLUMN_NAME_DESCORTA = "descripcionCorta";
        public static final String COLUMN_NAME_DISPONIBLE = "disponible";
        public static final String COLUMN_NAME_FOTOBIG = "fotoBig";
        public static final String COLUMN_NAME_FOTOSMALL = "fotoSmall";
        public static final String COLUMN_NAME_NAME = "nombre";
        public static final String COLUMN_NAME_PRECIO = "precio";
    }
	
	public static abstract class Anuncio implements BaseColumns {
        public static final String TABLE_NAME = "anuncio";
        public static final String COLUMN_NAME_ANUNCIO_ID  = "anuncioid";
        public static final String COLUMN_NAME_EMPRESA_ID  = "empresaid";
        public static final String COLUMN_NAME_FOTOBIG = "fotoBig";
        public static final String COLUMN_NAME_FOTOSMALL = "fotoSmall";
        public static final String COLUMN_NAME_DURACION = "duracion";
    }
}
