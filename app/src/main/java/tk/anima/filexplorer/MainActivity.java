package tk.anima.filexplorer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READWRITE_STORAGE = 10;

    private ArrayList<Item> list;
    ItemListAdapter adapter = null;
    GridView itemsGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<Item>();

        permFiles();

        itemsGrid = (GridView)findViewById(R.id.itemsGrid);
        adapter = new ItemListAdapter(this, R.layout.item_layout, list);
        itemsGrid.setAdapter(adapter);

    }

    public void permFiles() {
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            Log.d("Permisions", " If ");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READWRITE_STORAGE);
        }else{
            Log.d("Permisions", " Else ");
            searchFiles();
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {

        Log.d("ReqCode", "is " + requestCode );

        if (requestCode == REQUEST_READWRITE_STORAGE) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                searchFiles();
            }
        }
    }

    public void searchFiles() {
        String path =  Environment.getExternalStorageDirectory().getAbsolutePath();
        //    String path = "/Download/Videos3m";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);

        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            File file = files[i];
            int id = i+1;
            String name = file.getName();
            String size = String.valueOf( file.length() / 1024) + "Kb";
            String type = (file.isDirectory())? "Carpeta": "Archivo";
            String desc = type +" "+ size;
            String filePath = file.getAbsolutePath().toString();
/*
            Log.d("NAME", name);
            Log.d("SIZE", size);
            Log.d("DESC", desc);
            Log.d("PATH", filePath);
            Log.d("AGAIN", "----------");
            */


            list.add(new Item(id,name,desc,filePath,size,type));
        }
    }
}
