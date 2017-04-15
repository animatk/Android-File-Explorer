package tk.anima.filexplorer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READWRITE_STORAGE = 10;

    private ArrayList<Item> list;
    ItemListAdapter adapter = null;
    GridView itemsGrid;
    String path;
    String title;
    String back;
    String absolute;
    TextView textView;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<Item>();
        backButton = (ImageButton) findViewById(R.id.backButton);
        textView = (TextView) findViewById(R.id.textView);
        absolute = Environment.getExternalStorageDirectory().getAbsolutePath();

        Bundle b = getIntent().getExtras();

        if(b != null )
        {
            path = b.getString("path");

            if(!path.equals( absolute ))
            {
                File file = new File( path );
                Item item = getItem(0, file);
                back = file.getParent();
                //
                title = item.getName();
                textView.setText(title);
                backButton.setVisibility(View.VISIBLE);
            }else
            {
                path = absolute;
                textView.setText("Carpeta Principal");
                backButton.setVisibility(View.GONE);
            }

        }else
        {
            path = absolute;
            textView.setText("Carpeta Principal");
            backButton.setVisibility(View.GONE);
        }

        permFiles(path);

        itemsGrid = (GridView)findViewById(R.id.itemsGrid);
        adapter = new ItemListAdapter(this, R.layout.item_layout, list);
        itemsGrid.setAdapter(adapter);

        itemsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Log.d("ID", String.valueOf(position));

                Item item = list.get(position);

                if(item.getType() == "Archivo")
                {
                    Log.d("ACTION", "Open File...");
                }else
                {
                    getNewPath(item.getPath());
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewPath( back );
            }
        });

    }

    public void  getNewPath(String path){
        //reset view in new directory
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("path", path);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();
    }

    public  void setBackButton(){
        getNewPath( back );
    }

    public void permFiles(String p) {
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED
                || permissionCheck2 != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READWRITE_STORAGE);
        }else
        {
            searchFiles(p);
        }
    }

    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults)
    {
        if (requestCode == REQUEST_READWRITE_STORAGE) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                searchFiles(path);
            }
        }
    }

    public void searchFiles(String path) {

        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            File file = files[i];
            Item item = getItem(i+1, file);
            list.add(item);
        }
    }

    public Item getItem(int ide, File file)
    {
        int id = ide;
        String name = file.getName();
        String size = String.valueOf( file.length() / 1024) + "Kb";
        String type = (file.isDirectory())? "Carpeta": "Archivo";
        String desc = type +" - "+ size;
        String filePath = file.getAbsolutePath().toString();

        return new Item(id,name,desc,filePath,size,type);
    }
}
