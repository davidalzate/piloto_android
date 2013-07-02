package co.com.nuevaera.mobil;

import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.model.db.NuevaEraDatabaseHandler;
import co.com.nuevaera.mobil.util.ImageLoader;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.ImageView;

public class InfoElement extends Activity {
	private long idElement;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_element);
		
		Intent intent = getIntent();
		
		String message = intent.getStringExtra(ElementsActivity.EXTRA_MESSAGE1);
		
		this.idElement  = Long.parseLong(message);
		
		NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(getApplicationContext());
		ElementoDto elementoDto = databaseHandler.getElement(idElement);
		ImageView imageElement =  (ImageView)findViewById(R.id.imageElement);
		WebView infoElement =  (WebView)findViewById(R.id.infoElement);
		
		int stub_id = R.drawable.ic_launcher;
		
		ImageLoader imageLoader=new ImageLoader(this);
		imageLoader.DisplayImage(elementoDto.getFotoBig(), stub_id, imageElement);
		

		
		
		
		String summary = "<html><body><b><h1>"+elementoDto.getNombre()+"</h1></b><br><h2>"+elementoDto.getDescripcionLarga()+"</h2><br><b>"+elementoDto.getPrecio()+"</b></body></html>";
		
		infoElement.loadData(summary, "text/html", null);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info_element, menu);
		return true;
	}

}
