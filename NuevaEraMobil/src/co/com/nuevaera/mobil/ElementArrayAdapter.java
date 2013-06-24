package co.com.nuevaera.mobil;

import java.util.ArrayList;

import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.util.ImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ElementArrayAdapter extends ArrayAdapter<ElementoDto> {
	private final Context context;
	private final ArrayList<ElementoDto> values;
	public ImageLoader imageLoader; 
 
	public ElementArrayAdapter(Context context, ArrayList<ElementoDto> values) {
		super(context, R.layout.activity_restaurant, values);
		this.context = context;
		this.values = values;
		imageLoader=new ImageLoader(context);
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		
		View rowView = inflater.inflate(R.layout.element_list_layout, parent, false);
		
		WebView categoryView = (WebView) rowView.findViewById(R.id.elementDescription);
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.elementImage);

		
		int stub_id = R.drawable.ic_launcher;
		
		imageLoader.DisplayImage(values.get(position).getFotoBig(), stub_id, imageView);
		ElementoDto elementoDto = values.get(position);
		String summary = "<html><body><br>"+elementoDto.getNombre()+"<b><h1><br>"+elementoDto.getDescripcionCorta()+"</br></h1>"+elementoDto.getPrecio()+"</p></body></html>";
		categoryView.loadData(summary, "text/html", null);
		

		return rowView;
	}
}