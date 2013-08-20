package co.com.nuevaera.mobil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import co.com.nuevaera.mobil.RestaurantActivity.ShowBannerTask;
import co.com.nuevaera.mobil.model.AnuncioDto;
import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.model.db.NuevaEraDatabaseHandler;
import co.com.nuevaera.mobil.util.ImageLoader;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnShowListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ElementsActivity extends Activity {
	
	private long  idCategory;
	public final static String EXTRA_MESSAGE1 = "co.com.nuevaera.mobil.model.MESSAGE1";

	private ImageLoader imageLoader;
	private ImageView banner; 
	private ImageView bigBanner; 
	private int addsLength;
	private int addsIndex;
	private long starttime;
	private ArrayList<AnuncioDto> adds;
	private Timer timer;
	private Dialog bannerDialog;
	private String message2;
	private AnuncioDto anuncio;

	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		timer.cancel();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		Intent intent = getIntent();
		String message = intent.getStringExtra(RestaurantActivity.EXTRA_MESSAGE);
		 message2 = intent.getStringExtra(RestaurantActivity.EXTRA_MESSAGE2);
		
		this.setTitle(message2);


		
		this.idCategory  = Long.parseLong(message);
		
		setContentView(R.layout.activity_elements);
		
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
		
		final ListView elementListView = (ListView)findViewById(R.id.elementList);
		
		NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(getApplicationContext());
		elementListView.setAdapter(new ElementArrayAdapter(this, databaseHandler.getElements(this.idCategory)));
		

		
		elementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				ElementoDto listItem = (ElementoDto)elementListView.getItemAtPosition(position);
				System.out.println(listItem);
				Toast.makeText(ElementsActivity.this, "elementid -"+ listItem.toString(), Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(ElementsActivity.this, InfoElement.class);
			    intent.putExtra(EXTRA_MESSAGE1, Long.toString(listItem.getIdElemento()));

			    startActivity(intent);
		 				
				
			}
			
			 
		});
		
		
		ImageButton myBackButton = (ImageButton) findViewById(R.id.imageButton1);
		  myBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
			adds = databaseHandler.getAdds();
			
			if(null!=adds){
				starttime = System.currentTimeMillis();
				addsLength = adds.size();
				 timer = new Timer();
				timer.schedule(new ShowBannerTask(), 1000, 1000);
			}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elements, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		TextView title = (TextView)findViewById(R.id.textView2);
		title.setText(message2);
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
	        	ElementsActivity.this.runOnUiThread(new Runnable() {

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
