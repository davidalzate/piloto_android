package co.com.nuevaera.mobil;

import java.util.ArrayList;

import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.util.ImageLoader;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
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
		
		TextView categoryView = (TextView) rowView.findViewById(R.id.elementDescription);
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.elementImage);

		
		int stub_id = R.drawable.ic_launcher;
		
		imageLoader.DisplayImage(values.get(position).getFotoSmall(), stub_id, imageView);
		ElementoDto elementoDto = values.get(position);
		String summary = "<div><h1><H1>"+elementoDto.getNombre()+"</h1></div><div><font size='32'>"+elementoDto.getDescripcionCorta()+"</font></div><div><H1>"+elementoDto.getPrecio()+"</H1></div>";

		//Spanned strHtml= Html.fromHtml(summary);
		
		categoryView.setText(Html.fromHtml(summary));
		//<div>"+elementoDto.getDescripcionCorta()+"</div><div>"+elementoDto.getPrecio()+"</div>

		return rowView;
	}
}