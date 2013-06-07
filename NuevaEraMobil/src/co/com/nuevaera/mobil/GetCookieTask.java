package co.com.nuevaera.mobil;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;


public class GetCookieTask extends AsyncTask<String, Void, Boolean> {
	
	private NEMobilMain activity;

	
	
	public GetCookieTask(NEMobilMain activit) {
		this.activity = activity;
		// TODO Auto-generated constructor stub
	}
	@Override
    protected Boolean doInBackground(String... tokens) {
    	DefaultHttpClient http_client = new DefaultHttpClient();
            try {
                    // Don't follow redirects
                    http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);
                    
                    HttpGet http_get = new HttpGet("https://nuevaeramedellin.appspot.com/_ah/login?continue=http://localhost/&auth=" + tokens[0]);
                    HttpResponse response;
                    response = http_client.execute(http_get);
                    System.out.println("http_get="+"https://nuevaeramedellin.appspot.com/_ah/login?continue=http://localhost/&auth=" + tokens[0]+"  "+response.getStatusLine().getStatusCode());
                    if(response.getStatusLine().getStatusCode() != 302)
                            // Response should be a redirect
                            return false;
                    
                    for(Cookie cookie : http_client.getCookieStore().getCookies()) {
                        Log.v("GETING COOKIE" , cookie.getName());
                        if(cookie.getName().equals("ACSID") || cookie.getName().equals("SACSID")){
                         Log.v(", ", "Got cookie");
                         return true;
                        } 
                       }
                    
            } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } finally {
                    http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
            }
            return false;
    }
	
	
	@Override
    protected void onPostExecute(Boolean result) {
            AsyncLoadRestaurants.run(activity);
    }
	
	static void run( String token, final NEMobilMain activit){
		new GetCookieTask(activit).execute(token);
	}



}