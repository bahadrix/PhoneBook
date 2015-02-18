package me.bahadir.bil425.dbsample;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import me.bahadir.bil425.dbsample.model.Contact;



public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mAdapter;
    private DBHelper dbHelper;
    private ListView lstContact;
    //private  contactService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*ContactEndpoint.Builder builder = new ContactEndpoint.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                null
        );
        builder.setRootUrl("")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        contactService = builder.build();*/

        AsyncTask<Void, Void, String> pingTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                return pingService();
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
        };
        pingTask.execute();


        lstContact = (ListView) findViewById(R.id.lstContacts);

        lstContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("yo","oı");
            }
        });

        dbHelper = new DBHelper(this);
        /**
        dbHelper.createDB(
                dbHelper.getWritableDatabase()
        );
        **/


        String[] listColumns = {Contact.COLUMN_NAME_NAME, Contact.COLUMN_NAME_EMAIL};
        int[] views = {android.R.id.text1, android.R.id.text2};
        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, null,
                listColumns, views, 0);
        //setListAdapter(mAdapter);




        lstContact.setAdapter(mAdapter);


        getLoaderManager().initLoader(0, null, this);

    }

    private String pingService() {
/*
        try {
            Response response = contactService.ping().execute();
            return response.getMesage();
        } catch (IOException e) {
            Log.e("error on load remote", e.getMessage());
            return e.getMessage();
        }
        */

        return "";


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addContact) {
            Intent intent = new Intent(this, SaveActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_detailed_list) {
            Intent intent = new Intent(this, DetailedListActivity.class);
            startActivity(intent);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onResume() {

        //mAdapter.notifyDataSetChanged();
        getLoaderManager().restartLoader(0, null, this);
        super.onResume();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this) {
            @Override
            public Cursor loadInBackground() {


                return loadContacts();

            }
        };
    }

    private Cursor loadContacts() {

        String[] projection = {
                Contact._ID,
                Contact.COLUMN_NAME_NAME,
                Contact.COLUMN_NAME_EMAIL
        };

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(
                Contact.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                Contact.COLUMN_NAME_NAME
        );



    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
