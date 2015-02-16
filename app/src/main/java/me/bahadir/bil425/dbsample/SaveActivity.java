package me.bahadir.bil425.dbsample;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.bahadir.bil425.dbsample.model.Contact;


public class SaveActivity extends ActionBarActivity {

    TextView txtName, txtMail, txtPhone, txtStreet, txtCity;
    Button btnSave;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        dbHelper = new DBHelper(this);

        txtName = (TextView) findViewById(R.id.txtName);
        txtMail = (TextView) findViewById(R.id.txtMail);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtStreet = (TextView) findViewById(R.id.txtStreet);
        txtCity = (TextView) findViewById(R.id.txtCity);

        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();

            }
        });
    }

    private void save() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contact.COLUMN_NAME_NAME,    txtName.getText().toString());
        values.put(Contact.COLUMN_NAME_EMAIL,   txtMail.getText().toString());
        values.put(Contact.COLUMN_NAME_PHONE,   txtPhone.getText().toString());
        values.put(Contact.COLUMN_NAME_STREET,  txtStreet.getText().toString());
        values.put(Contact.COLUMN_NAME_CITY,    txtCity.getText().toString());

        db.insert(Contact.TABLE_NAME, null, values);


        finish();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
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
}
