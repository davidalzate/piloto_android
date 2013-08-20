package co.com.nuevaera.mobil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import co.com.nuevaera.mobil.model.AnuncioDto;
import co.com.nuevaera.mobil.model.CategoriaDto;
import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.model.db.NuevaEraDatabaseHandler;
import co.com.nuevaera.mobil.util.ImageLoader;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnShowListener;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/*public class RestaurantActivity extends ListActivity {
	 
	public final static String EXTRA_MESSAGE = "co.com.nuevaera.mobil.model.MESSAGE";
	public final static String EXTRA_MESSAGE2 = "co.com.nuevaera.mobil.model.MESSAGE2";
	 
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
		    intent.putExtra(EXTRA_MESSAGE2, categoriaDto.getNombre());

		    startActivity(intent);
	 
		}
	 
	}*/
public class RestaurantActivity extends Activity {
	 
	public final static String EXTRA_MESSAGE = "co.com.nuevaera.mobil.model.MESSAGE";
	public final static String EXTRA_MESSAGE2 = "co.com.nuevaera.mobil.model.MESSAGE2";
	private ImageLoader imageLoader;
	private ImageView banner; 
	private ImageView bigBanner; 
	private int addsLength;
	private int addsIndex;
	private long starttime;
	private ArrayList<AnuncioDto> adds;
	private Timer timer;
	private Dialog bannerDialog;
	private AnuncioDto anuncio;
	
	
	
@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	timer.cancel();
}
	
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.activity_restaurant);
			
			bannerDialog = createAddDialog();
			bannerDialog.setOnShowListener(new OnShowListener() {
				
				@Override
				public void onShow(DialogInterface dialog) {
					if(null!=anuncio){
						bigBanner = (ImageView)bannerDialog.findViewById(R.id.bigBanner);
						int stub_id2 = R.drawable.banner_image;
						imageLoader.DisplayImage(anuncio.getFotoSmall(), stub_id2, bigBanner);					
					}
				}
			});
			
			imageLoader=new ImageLoader(getApplicationContext());
			banner = (ImageView)findViewById(R.id.bannerView1);
			
			
			
			banner.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					bannerDialog.show();
				}
			});
			
			final ListView categoryListView = (ListView)findViewById(R.id.categoriesList);
			
			NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(getApplicationContext());
	 
			categoryListView.setAdapter(new CategoryArrayAdapter(this, databaseHandler.getCategories()));
			
			categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position,
						long arg3) {
	
			 				
					CategoriaDto categoriaDto =  ((CategoriaDto) categoryListView.getItemAtPosition(position));
					String categoryName = categoriaDto.getNombre();
					long  categoryId = categoriaDto.getIdCategoria();
					
					
					Intent intent = new Intent(RestaurantActivity.this, ElementsActivity.class);
				    intent.putExtra(EXTRA_MESSAGE, Long.toString(categoryId));
				    intent.putExtra(EXTRA_MESSAGE2, categoriaDto.getNombre());

				    startActivity(intent);
					
				}
				
				 
			});
			
			
			adds = databaseHandler.getAdds();
			
			if(null!=adds){
				starttime = System.currentTimeMillis();
				addsLength = adds.size();
				 timer = new Timer();
				timer.schedule(new ShowBannerTask(), 1000, 1000);
			}
			
			ImageButton myBackButton = (ImageButton) findViewById(R.id.imageButton1);
			  myBackButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			

	 
		}
		
		@Override
		protected void onRestart() {
			// TODO Auto-generated method stub
			super.onRestart();
			if(null!=adds){
				starttime = System.currentTimeMillis();
				addsLength = adds.size();
				 timer = new Timer();
				timer.schedule(new ShowBannerTask(), 1000, 1000);
			}
		}
		  class ShowBannerTask extends TimerTask {

		        @Override
		        public void run() {
		        	RestaurantActivity.this.runOnUiThread(new Runnable() {

		                @Override
		                public void run() {
		                	
		                	int stub_id = R.drawable.banner;
		                	int stub_id2 = R.drawable.banner_image;
		                	
		                	 anuncio = adds.get(addsIndex);
		                	imageLoader.DisplayImage(anuncio.getFotoSmall(), stub_id, banner);
		                	
		                	if(null!=bigBanner){
		                		if(!bannerDialog.isShowing()){
		                			imageLoader.DisplayImage(anuncio.getFotoSmall(), stub_id2, bigBanner);
		                		}
		                		
		                	}
		                	
		                	
		                   long millis = System.currentTimeMillis() - starttime;
		                   int seconds = (int) (millis / 1000);
		                   seconds     = seconds % 60;
		                   
		                   System.out.println("Anuncio = " + anuncio.getDuracion() + " vs " +seconds);
		                   
		                   if(anuncio.getDuracion()<seconds){
		                	   addsIndex++;
		                	   starttime = System.currentTimeMillis();
		                	   if(addsIndex>=addsLength){
		                		   addsIndex=0;
		                	   }
		                   }
		                }
		            });
		        }
		   }; 
		   
			private Dialog createAddDialog(){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				LayoutInflater inflater = getLayoutInflater();
				builder.setView(inflater.inflate(R.layout.banner_layout, null))
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
					
				});
				
				return builder.create();
			}
	 
	}