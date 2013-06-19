package co.com.nuevaera.mobil;



import java.util.ArrayList;

import co.com.nuevaera.mobil.model.CategoriaDto;
import co.com.nuevaera.mobil.util.ImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MobileArrayAdapter extends ArrayAdapter<CategoriaDto> {
	private final Context context;
	private final ArrayList<CategoriaDto> values;
	public ImageLoader imageLoader; 
 
	public MobileArrayAdapter(Context context, ArrayList<CategoriaDto> values) {
		super(context, R.layout.activity_restaurant, values);
		this.context = context;
		this.values = values;
		imageLoader=new ImageLoader(context);
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.activity_restaurant, parent, false);
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.banner);
		
		int stub_id = R.drawable.ic_launcher;
		
		imageLoader.DisplayImage(values.get(position).getFoto(), stub_id, imageView);
 
		// Change icon based on name
		//String s = values[position];
		
		String foto = values.get(position).getFoto();
 
		System.out.println(foto);
		
		//imageView.setImageResource(resId)(R.drawable.banner);
 
	
 
		return rowView;
	}
}


