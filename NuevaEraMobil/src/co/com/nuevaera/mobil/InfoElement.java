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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoElement extends Activity {
	private long idElement;
	
	
	private ImageLoader imageLoader;
	private ImageView banner; 
	private ImageView bigBanner; 
	private int addsLength;
	private int addsIndex;
	private long starttime;
	private ArrayList<AnuncioDto> adds;
	private Timer timer;
	private Dialog bannerDialog;


	private String title;
	
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
		 
		  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_info_element);
		
		
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
		
		Intent intent = getIntent();
		
		String message = intent.getStringExtra(ElementsActivity.EXTRA_MESSAGE1);
		
		this.idElement  = Long.parseLong(message);
		
		NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(getApplicationContext());
		ElementoDto elementoDto = databaseHandler.getElement(idElement);
		title = elementoDto.getNombre();
		ImageView imageElement =  (ImageView)findViewById(R.id.imageElement);
		WebView infoElement =  (WebView)findViewById(R.id.infoElement);
		

		//WebView imgElement =  (WebView)findViewById(R.id.infoElement);
		
		int stub_id = R.drawable.ic_launcher;
		
		//ImageLoader imageLoader=new ImageLoader(this);
		System.out.println("djdjdjdjdjdj = " + imageLoader.getFilePath(elementoDto.getFotoBig()+"=s380"));
		
		imageLoader.DisplayImage(elementoDto.getFotoBig()+"=s600", stub_id, imageElement);
		
		//imgElement.loadUrl(elementoDto.getFotoBig());
		
		String summary = "<html><body><div><b><h1>"+elementoDto.getNombre()+"</h1></b></div><br>" +
				"<div style=\"color:#383636;font-size: 22pt;font-family: \"Helvetica LT Std Cond Light\";\">"+elementoDto.getDescripcionLarga()+"</div><br>" +
						"<H1><div><b>"+elementoDto.getPrecio()+"</b></div></H1></body></html>";
		
		infoElement.loadData(summary, "text/html", null);
		
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
		getMenuInflater().inflate(R.menu.info_element, menu);
		return true;
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
	        	InfoElement.this.runOnUiThread(new Runnable() {

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
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	TextView title = (TextView)findViewById(R.id.textView2);
	title.setText(this.title);
}
}
