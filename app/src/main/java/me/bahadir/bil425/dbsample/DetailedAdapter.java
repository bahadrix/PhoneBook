package me.bahadir.bil425.dbsample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.bahadir.bil425.dbsample.model.Contact;

/**
 * This adapter is built from BaseAdapter for showing more implementation
 * details of Adapter.
 * Created by bahadir on 09/02/15.
 */
public class DetailedAdapter extends BaseAdapter {

    private final DBHelper dbHelper;
    private final Context context;
    private final LayoutInflater inflater;

    private List<ContactItem> items;

    public DetailedAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
        dbHelper = new DBHelper(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//e

    }

    @Override
    public void notifyDataSetChanged() {
        load();
        super.notifyDataSetChanged();
    }

    public void load() {

        items.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                Contact._ID,
                Contact.COLUMN_NAME_NAME,
                Contact.COLUMN_NAME_EMAIL,
                Contact.COLUMN_NAME_CITY
        };


        Cursor cursor = db.query(
                Contact.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                Contact.COLUMN_NAME_NAME
        );

        if(cursor.getCount() == 0)
            return;

        int // Column Indexes
                ciID = cursor.getColumnIndex(Contact.COLUMN_NAME_CONTACT_ID),
                ciName = cursor.getColumnIndex(Contact.COLUMN_NAME_NAME),
                ciMail = cursor.getColumnIndex(Contact.COLUMN_NAME_EMAIL),
                ciCity = cursor.getColumnIndex(Contact.COLUMN_NAME_CITY);


        while(cursor.moveToNext()) {

            ContactItem item = new ContactItem();
            item.setId(cursor.getLong(ciID));
            item.setName(cursor.getString(ciName));
            item.setEmail(cursor.getString(ciMail));
            item.setCity(cursor.getString(ciCity));

            items.add(item);

        }

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * Feature investigation of the getView calls shows that it is executed 3 times for each
         * visible item on the list before it will be displayed for the first time.
         * User maybe will not see much different on the screen but could notice that our app
         * "eat the battery" very fast switching between views.
         * This happen because of the layout logic in the android. The measure, layout, and measure
         * are this three passes.
         * @see "http://www.codeproject.com/Articles/316690/Custom-list-item-layout"
         */

        try{
            ViewHolder holder;

            if (convertView == null)  {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.row_custom, null);

                holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
                holder.txtMail = (TextView) convertView.findViewById(R.id.txtMail);
                holder.txtCity = (TextView) convertView.findViewById(R.id.txtCity);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ContactItem item = items.get(position);
            holder.txtName.setText(item.getName());
            holder.txtMail.setText(item.getEmail());
            holder.txtCity.setText(item.getCity());

        } catch (Exception e) {
            Log.e("Item view generation error. ", e.getMessage());
        }

        return convertView;

    }


    private static class ViewHolder {

        TextView txtName;
        TextView txtMail;
        TextView txtCity;

    }
}
