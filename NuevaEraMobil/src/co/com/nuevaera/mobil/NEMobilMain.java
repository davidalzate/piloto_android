package co.com.nuevaera.mobil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import co.com.nuevaera.mobil.model.CategoriaDto;
import co.com.nuevaera.mobil.model.ElementoDto;
import co.com.nuevaera.mobil.model.RestauranteDto;
import co.com.nuevaera.mobil.model.db.NuevaEraDatabaseHandler;
import co.com.nuevaera.mobil.model.db.RestaurantContract.Restaurant;
import co.com.nuevaera.mobil.util.FileCache;
import co.com.nuevaera.mobil.util.NuevaEraJSonParser;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.tasks.TasksScopes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NEMobilMain extends Activity {
	/**
	 * Logging level for HTTP requests/responses.
	 * 
	 * <p>
	 * To turn on, set to {@link Level#CONFIG} or {@link Level#ALL} and run this
	 * from command line:
	 * </p>
	 * 
	 * <pre>
	 *      adb shell setprop log.tag.HttpTransport DEBUG
	 * </pre>
	 */
	private static final Level LOGGING_LEVEL = Level.ALL;

	private static final String PREF_ACCOUNT_NAME = "accountName";

	static final String TAG = "NEMobilMain";

	static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;

	static final int REQUEST_AUTHORIZATION = 1;

	static final int REQUEST_ACCOUNT_PICKER = 2;

	final HttpTransport transport = AndroidHttp.newCompatibleTransport();

	final JsonFactory jsonFactory = new GsonFactory();

	GoogleAccountCredential credential;

	List<String> tasksList;

	ArrayAdapter<String> adapter;

	com.google.api.services.tasks.Tasks service;

	int numAsyncTasks;

	private ListView listView;

	private String LOG_TAG = "GAEAuthMainActivity";
	private static final int USER_PERMISSION = 989;
	boolean expireToken = false;
	private AccountManager accountManager;
	private DefaultHttpClient httpclient = new DefaultHttpClient();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// enable logging
		Logger.getLogger("com.google.api.client").setLevel(LOGGING_LEVEL);
		// view and menu
		setContentView(R.layout.activity_nemobil_main);
		listView = (ListView) findViewById(R.id.list);

		// Google Accounts
		credential = GoogleAccountCredential.usingOAuth2(this,
				Collections.singleton(TasksScopes.TASKS));
		SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
		credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME,
				null));
		// Tasks client
		service = new com.google.api.services.tasks.Tasks.Builder(transport,
				jsonFactory, credential).setApplicationName("nuevaeramedellin")
				.build();

	}

	void showGooglePlayServicesAvailabilityErrorDialog(
			final int connectionStatusCode) {
		runOnUiThread(new Runnable() {
			public void run() {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
						connectionStatusCode, NEMobilMain.this,
						REQUEST_GOOGLE_PLAY_SERVICES);
				dialog.show();
			}
		});
	}

	void refreshView() {
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, tasksList);
		listView.setAdapter(adapter);
	}
	
	public void bclick(View v){
	    final int id = v.getId();
	    switch (id) {
	    case R.id.button1:
			FileCache fileCache = new FileCache(this.getBaseContext());
			fileCache.clear();
			NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(
					getBaseContext());
			databaseHandler.deleteAllRecords();
	        break;
	    // even more buttons here
	    }

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (checkGooglePlayServicesAvailable()) {
			haveGooglePlayServices();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_GOOGLE_PLAY_SERVICES:
			if (resultCode == Activity.RESULT_OK) {
				haveGooglePlayServices();
			} else {
				checkGooglePlayServicesAvailable();
			}
			break;
		case REQUEST_AUTHORIZATION:
			if (resultCode == Activity.RESULT_OK) {
				AsyncLoadTasks.run(this);
			} else {
				chooseAccount();
			}
			break;
		case REQUEST_ACCOUNT_PICKER:
			if (resultCode == Activity.RESULT_OK && data != null
					&& data.getExtras() != null) {
				String accountName = data.getExtras().getString(
						AccountManager.KEY_ACCOUNT_NAME);
				if (accountName != null) {
					credential.setSelectedAccountName(accountName);
					SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString(PREF_ACCOUNT_NAME, accountName);
					editor.commit();
					// setIntent(data);
					AsyncLoadTasks.run(this);
				}
			}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.nemobil_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			AsyncLoadTasks.run(this);
			/*
			 * Intent data = getIntent(); AccountManager accountManager =
			 * AccountManager.get(getApplicationContext()); Account account =
			 * (Account)data.getExtras().get("account");
			 * accountManager.getAuthToken(account, "ah", false, new
			 * GetAuthTokenCallback(this), null);
			 */
			Account account = credential.getSelectedAccount();
			AccountManager accountManager = AccountManager
					.get(getApplicationContext());
			// accountManager.getAuthToken(account, "ah", false, new
			// GetAuthTokenCallback(this), null);
			expireToken = false;
			accountManager.getAuthToken(account, "ah", null, false,
					new OnTokenAcquired(), null);

			// et.setText("credential="+credential.getToken());

			break;
		case R.id.menu_restaurants:
			Intent intent = new Intent(NEMobilMain.this, RestaurantActivity.class);

		    startActivity(intent);
			break;
		case R.id.menu_accounts:
			chooseAccount();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Check that Google Play services APK is installed and up to date. */
	private boolean checkGooglePlayServicesAvailable() {
		final int connectionStatusCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
			showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
			return false;
		}
		return true;
	}

	private void haveGooglePlayServices() {
		// check if there is already an account selected
		if (credential.getSelectedAccountName() == null) {
			// ask user to choose account
			chooseAccount();
		} else {
			// load calendars
			AsyncLoadTasks.run(this);
		}
	}

	private void chooseAccount() {
		startActivityForResult(credential.newChooseAccountIntent(),
				REQUEST_ACCOUNT_PICKER);
	}

	/*
	 * private class GetAuthTokenCallback implements
	 * AccountManagerCallback<Bundle> { private NEMobilMain neMobilMain; public
	 * GetAuthTokenCallback(NEMobilMain neMobilMain) { this.neMobilMain =
	 * neMobilMain; // TODO Auto-generated constructor stub } public void
	 * run(AccountManagerFuture<Bundle> result) { Bundle bundle; try { bundle =
	 * result.getResult(); Intent intent =
	 * (Intent)bundle.get(AccountManager.KEY_INTENT); if(intent != null) { //
	 * User input required System.out.println("User input required");
	 * startActivity(intent); } else { onGetAuthToken(bundle); } } catch
	 * (OperationCanceledException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (AuthenticatorException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } } protected
	 * void onGetAuthToken(Bundle bundle) { String auth_token =
	 * bundle.getString(AccountManager.KEY_AUTHTOKEN); new
	 * GetCookieTask(neMobilMain).execute(auth_token); } };
	 */

	private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

		public void run(AccountManagerFuture<Bundle> result) {

			Bundle bundle;

			try {
				bundle = (Bundle) result.getResult();
				if (bundle.containsKey(AccountManager.KEY_INTENT)) {
					Intent intent = bundle
							.getParcelable(AccountManager.KEY_INTENT);
					intent.setFlags(intent.getFlags()
							& ~Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivityForResult(intent, USER_PERMISSION);
				} else {
					setAuthToken(bundle);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	// using the auth token and ask for a auth cookie
	protected void setAuthToken(Bundle bundle) {
		String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
		if (expireToken) {
			accountManager.invalidateAuthToken("ah", authToken);
		} else {
			new GetCookie().execute(authToken);
		}
	}

	// using the token to get an auth cookie
	private class GetCookie extends AsyncTask<String, Void, Boolean> {

		HttpParams params = httpclient.getParams();
		private HttpResponse response;

		protected Boolean doInBackground(String... tokens) {

			try {

				// Don't follow redirects
				params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);

				HttpGet httpGet = new HttpGet(
						"http://nuevaeramedellin.appspot.com/_ah/login?continue=http://localhost/&auth="
								+ tokens[0]);
				response = httpclient.execute(httpGet);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();

				if (response.getStatusLine().getStatusCode() != 302) {
					// Response should be a redirect
					Log.v(LOG_TAG, "No cookie");
					return false;
				}

				// check if we received the ACSID or the SACSID cookie, depends
				// on http or https request
				for (Cookie cookie : httpclient.getCookieStore().getCookies()) {
					Log.v(LOG_TAG, cookie.getName());
					if (cookie.getName().equals("ACSID")
							|| cookie.getName().equals("SACSID")) {
						Log.v(LOG_TAG, "Got cookie");
						return true;
					}
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				cancel(true);
			} catch (IOException e) {
				e.printStackTrace();
				cancel(true);
			} catch (Exception e) {
				e.printStackTrace();
				cancel(true);
			} finally {
				params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
			}
			return false;
		}

		protected void onPostExecute(Boolean result) {
			Log.v(LOG_TAG, "Done cookie");
			new RestaurantSyncRequest()
					.execute("http://nuevaeramedellin.appspot.com/nuevaera/identityresource");
		}
	}

	// make your authenticated request here using the same httpclient that
	// received the cookie
	private class RestaurantSyncRequest extends
			AsyncTask<String, Void, Boolean> {

		private HttpResponse response;
		private String content = null;

		protected Boolean doInBackground(String... urls) {

			try {

				HttpGet httpGet = new HttpGet(urls[0]);
				response = httpclient.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				Log.v(LOG_TAG, statusLine.getReasonPhrase());
				for (Cookie cookie : httpclient.getCookieStore().getCookies()) {
					Log.v(LOG_TAG, cookie.getName());
				}
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					content = out.toString();
					return true;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				cancel(true);
			} catch (IOException e) {
				e.printStackTrace();
				cancel(true);
			} catch (Exception e) {
				e.printStackTrace();
				cancel(true);
			}
			return false;
		}

		// display the response from the request above
		protected void onPostExecute(Boolean result) {
			Log.v(LOG_TAG, content);

			CategorySyncRequest categorySyncRequest = new CategorySyncRequest();
			categorySyncRequest
					.execute("http://nuevaeramedellin.appspot.com/nuevaera/categoryresource");

			ArrayList<RestauranteDto> restaurants = NuevaEraJSonParser
					.getRestaurantsFromJson(content);

			NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(
					getBaseContext());
			databaseHandler.addRestaurantList(restaurants);

		}
	}

	private class CategorySyncRequest extends AsyncTask<String, Void, String> {

		private HttpResponse response;
		private String content = null;

		protected String doInBackground(String... urls) {
			String ret = "";
			try {

				HttpGet httpGet = new HttpGet(urls[0]);
				response = httpclient.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				Log.v(LOG_TAG, statusLine.getReasonPhrase());
				for (Cookie cookie : httpclient.getCookieStore().getCookies()) {
					Log.v(LOG_TAG, cookie.getName());
				}
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					ret = out.toString();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				cancel(true);
			} catch (IOException e) {
				e.printStackTrace();
				cancel(true);
			} catch (Exception e) {
				e.printStackTrace();
				cancel(true);
			}
			return ret;
		}

		// display the response from the request above
		protected void onPostExecute(String result) {
			Log.v(LOG_TAG, result);
	

			ElementSyncRequest elementSyncRequest = new ElementSyncRequest();
			elementSyncRequest
					.execute("http://nuevaeramedellin.appspot.com/nuevaera/elementresource");
			
			ArrayList<CategoriaDto> categories = NuevaEraJSonParser
					.getCategoriesFromJson(result);

			NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(
					getBaseContext());
			databaseHandler.addCategoriesList(categories);

		}
	}
	
	private class ElementSyncRequest extends AsyncTask<String, Void, String> {

		private HttpResponse response;
		private String content = null;

		protected String doInBackground(String... urls) {
			String ret = "";
			try {

				HttpGet httpGet = new HttpGet(urls[0]);
				response = httpclient.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				Log.v(LOG_TAG, statusLine.getReasonPhrase());
				for (Cookie cookie : httpclient.getCookieStore().getCookies()) {
					Log.v(LOG_TAG, cookie.getName());
				}
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					ret = out.toString();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				cancel(true);
			} catch (IOException e) {
				e.printStackTrace();
				cancel(true);
			} catch (Exception e) {
				e.printStackTrace();
				cancel(true);
			}
			return ret;
		}

		// display the response from the request above
		protected void onPostExecute(String result) {
			Log.v(LOG_TAG, result);

			ArrayList<ElementoDto> elements = NuevaEraJSonParser
					.getElementsFromJson(result);

			NuevaEraDatabaseHandler databaseHandler = new NuevaEraDatabaseHandler(
					getBaseContext());
			databaseHandler.addElementsList(elements);
			
			Intent intent = new Intent(NEMobilMain.this, RestaurantActivity.class);

		    startActivity(intent);
		}
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(LOG_TAG, "DSTR");
	}
}