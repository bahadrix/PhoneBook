package me.bahadir.bil425.dbsample;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;

import me.bahadir.bil425.dbsample.model.Contact;


public class DetailedListActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    BaseAdapter adapter;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_list);

        dbHelper = new DBHelper(this);
        //adapter = new DetailedAdapter(this);
        adapter = new DetailedCursorAdapter(this, R.layout.row_custom, null, true);

        ListView lstDetailed = (ListView) findViewById(R.id.lstDetailed);
        lstDetailed.setAdapter(adapter);
        getLoaderManager().initLoader(0,null, this);
    }

    @Override
    protected void onResume() {

        //mAdapter.notifyDataSetChanged();
        getLoaderManager().restartLoader(0, null, this);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Cursor loadCursor() {
        Log.i("db","Loading contacts");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                Contact._ID,
                Contact.COLUMN_NAME_NAME,
                Contact.COLUMN_NAME_EMAIL,
                Contact.COLUMN_NAME_CITY
        };


        return  db.query(
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        return new CursorLoader(this) {
            @Override
            public Cursor loadInBackground() {
                return loadCursor();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(adapter instanceof ResourceCursorAdapter) {
            ((ResourceCursorAdapter)adapter).swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(adapter instanceof ResourceCursorAdapter) {
            ((ResourceCursorAdapter)adapter).swapCursor(null);
        }
    }
}
