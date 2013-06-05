package co.com.nuevaera.mobil;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import android.os.AsyncTask;
import android.view.View;

import java.io.IOException;
/**
 * Created by nelsonmanuelmorenorestrepo on 3/06/13.
 */
abstract class CommonAsyncTask extends AsyncTask<Void, Void, Boolean> {

    final NEMobilMain activity;
    final com.google.api.services.tasks.Tasks client;
    private final View progressBar;

    CommonAsyncTask(NEMobilMain activity) {
        this.activity = activity;
        client = activity.service;
        progressBar = activity.findViewById(R.id.title_refresh_progress);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.numAsyncTasks++;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected final Boolean doInBackground(Void... ignored) {
        try {
            doInBackground();
            return true;
        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            activity.showGooglePlayServicesAvailabilityErrorDialog(
                    availabilityException.getConnectionStatusCode());
        } catch (UserRecoverableAuthIOException userRecoverableException) {
            activity.startActivityForResult(
                    userRecoverableException.getIntent(), NEMobilMain.REQUEST_AUTHORIZATION);
        } catch (IOException e) {
            Utils.logAndShow(activity, NEMobilMain.TAG, e);
        }
        return false;
    }

    @Override
    protected final void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (0 == --activity.numAsyncTasks) {
            progressBar.setVisibility(View.GONE);
        }
        if (success) {
            activity.refreshView();
        }
    }

    abstract protected void doInBackground() throws IOException;
}
