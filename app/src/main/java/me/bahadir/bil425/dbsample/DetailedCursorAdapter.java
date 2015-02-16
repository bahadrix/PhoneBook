package me.bahadir.bil425.dbsample;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import me.bahadir.bil425.dbsample.model.Contact;

/**
 * Created by bahadir on 09/02/15.
 */
public class DetailedCursorAdapter extends ResourceCursorAdapter {
    public DetailedCursorAdapter(Context context, int layout, Cursor c, boolean autoRequery) {
        super(context, layout, c, autoRequery);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        int ciID = cursor.getColumnIndex(Contact.COLUMN_NAME_CONTACT_ID),
                ciName = cursor.getColumnIndex(Contact.COLUMN_NAME_NAME),
                ciMail = cursor.getColumnIndex(Contact.COLUMN_NAME_EMAIL),
                ciCity = cursor.getColumnIndex(Contact.COLUMN_NAME_CITY);

        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtMail = (TextView) view.findViewById(R.id.txtMail);
        TextView txtCity = (TextView) view.findViewById(R.id.txtCity);

        txtName.setText(cursor.getString(ciName));
        txtMail.setText(cursor.getString(ciMail));
        txtCity.setText(cursor.getString(ciCity));

    }
}
