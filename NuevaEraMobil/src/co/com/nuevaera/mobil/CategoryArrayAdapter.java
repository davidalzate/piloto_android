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
import android.widget.TextView;

public class CategoryArrayAdapter extends ArrayAdapter<CategoriaDto> {
	private final Context context;
	private final ArrayList<CategoriaDto> values;
	public ImageLoader imageLoader; 
 
	public CategoryArrayAdapter(Context context, ArrayList<CategoriaDto> values) {
		super(context, R.layout.category_list_layout, values);
		this.context = context;
		this.values = values;
		imageLoader=new ImageLoader(context);
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		
		View rowView = inflater.inflate(R.layout.category_list_layout, parent, false);
		
		TextView categoryView = (TextView) rowView.findViewById(R.id.category);
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.categoryImage);

		
		int stub_id = R.drawable.ic_launcher;
		
		imageLoader.DisplayImage(values.get(position).getFoto(), stub_id, imageView);
		
		categoryView.setText(values.get(position).getNombre());
 
		// Change icon based on name
		//String s = values[position];
		
		String foto = values.get(position).getFoto();
 
		System.out.println(foto);
		
		//imageView.setImageResource(resId)(R.drawable.banner);
 
	
 
		return rowView;
	}
}


