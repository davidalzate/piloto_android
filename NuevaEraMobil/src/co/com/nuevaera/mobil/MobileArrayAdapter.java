package co.com.nuevaera.mobil;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MobileArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
 
	public MobileArrayAdapter(Context context, String[] values) {
		super(context, R.layout.activity_restaurant, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.activity_restaurant, parent, false);
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.banner);
		
 
		// Change icon based on name
		String s = values[position];
 
		System.out.println(s);
		
		imageView.setImageResource(R.drawable.banner);
 
	
 
		return rowView;
	}
}


