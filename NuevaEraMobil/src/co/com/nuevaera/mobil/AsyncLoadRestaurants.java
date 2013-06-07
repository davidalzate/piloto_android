package co.com.nuevaera.mobil;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;

public class AsyncLoadRestaurants extends AsyncTask<Void, Void, String> {

	private NEMobilMain activity;

	public AsyncLoadRestaurants(NEMobilMain activity) {
		this.activity = activity;
		// TODO Auto-generated constructor stub
	}

	protected String getASCIIContentFromEntity(HttpEntity entity)
			throws IllegalStateException, IOException {
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0) {
			byte[] b = new byte[4096];
			n = in.read(b);
			if (n > 0)
				out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	@Override
	protected String doInBackground(Void... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(
				"http://nuevaeramedellin.appspot.com/nuevaera/identityresource");
		String text = null;
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			text = getASCIIContentFromEntity(entity);
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
		return text;
	}

	protected void onPostExecute(String results) {
		if (results != null) {
			System.out.println("results="+results);
		/*	EditText et = (EditText) activity.findViewById(R.id.jsonText);
			et.setText(results);*/
		}
		/*Button b = (Button) activity.findViewById(R.id.button1);
		b.setClickable(true);*/
	}
    static void run(NEMobilMain tasksSample) {
        new AsyncLoadRestaurants(tasksSample).execute();
    }
}
