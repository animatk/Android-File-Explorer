package tk.anima.filexplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by animatk on 4/14/17.
 */

public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Item> itemsList;

    public ItemListAdapter(Context context, int layout, ArrayList<Item> itemsList) {
        this.context = context;
        this.layout = layout;
        this.itemsList = itemsList;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textName, textDesc;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textName = (TextView) row.findViewById(R.id.textName);
            holder.textDesc = (TextView) row.findViewById(R.id.textDesc);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Item item = itemsList.get(position);

        holder.textName.setText(item.getName());
        holder.textDesc.setText(item.getDesc());

        String itemImage = item.getType();
        Bitmap bitmap;

        if(itemImage == "Carpeta"){
             bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.path_icon);
        }else
        {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.file_icon);
        }

        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
