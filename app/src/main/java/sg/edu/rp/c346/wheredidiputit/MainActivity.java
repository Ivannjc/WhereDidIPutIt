package sg.edu.rp.c346.wheredidiputit;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etItem;
    EditText etPlace;
    Button btnAdd;
    ListView lvItem;
    ListView lvPlace;


    ArrayList<String>  alItem = new ArrayList<String>();
    ArrayAdapter aaItem;
    String item = "";
    ArrayList<String>  alPlace = new ArrayList<String>();
    ArrayAdapter aaPlace;
    String place = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etItem = (EditText) findViewById(R.id.editTextItem);
        etPlace = (EditText) findViewById(R.id.editTextPlace);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        lvItem = (ListView) findViewById(R.id.listViewItem);
        lvPlace = (ListView) findViewById(R.id.listViewPlace);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strItem = etItem.getText().toString();
                String strPlace = etPlace.getText().toString();

                DBHelper db = new DBHelper(MainActivity.this);

                db.insertItem(strItem);
                db.insertPlace(strPlace);
                db.close();

                DBHelper dbRetrieve = new DBHelper(MainActivity.this);
                alItem = dbRetrieve.getItem();
                dbRetrieve.close();

                 aaItem = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, alItem);

                lvItem.setAdapter(aaItem);
                aaItem.notifyDataSetChanged();

                DBHelper dbRetrievePlace = new DBHelper(MainActivity.this);
                alPlace = dbRetrievePlace.getPlace();
                dbRetrievePlace.close();

                aaPlace = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, alPlace);

                lvPlace.setAdapter(aaPlace);
                aaPlace.notifyDataSetChanged();

                strItem = "";
                strPlace = "";

            }
        });
        lvItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Inflate the input.xml layout file
//                LayoutInflater inflater = (LayoutInflater).getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setIcon(android.R.drawable.ic_dialog_alert);


                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
//                        lvItem.remove(position);
                        Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                        aaItem.notifyDataSetChanged();

                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                b.show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbRetrieve = new DBHelper(MainActivity.this);
        alItem = dbRetrieve.getItem();
       alPlace = dbRetrieve.getPlace();
        dbRetrieve.close();
    }
}
