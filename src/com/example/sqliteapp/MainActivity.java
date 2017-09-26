package com.example.sqliteapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	DatabaseHelper myDb;
	EditText editName,editLastName, editMarks;
	Button btnAddData;
	Button btnviewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        editName=(EditText)findViewById(R.id.editText_name);
        editLastName=(EditText)findViewById(R.id.editText_lastname);
        editMarks=(EditText)findViewById(R.id.editText_Marks);
        btnAddData=(Button)findViewById(R.id.button_add);
        btnviewAll=(Button)findViewById(R.id.button_viewAll);
        AddData();
        viewAll();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    
    public void AddData(){
    	btnAddData.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean isInserted=myDb.insertData(editName.getText().toString(),
						editLastName.getText().toString(), 
						editMarks.getText().toString());
				if(isInserted=true){
					System.out.println("ddd");
					Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
				}else
					Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();

			}
		});
    }

    public void viewAll(){
    	btnviewAll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cursor res=myDb.getAllData();
				if(res.getCount()==0){
					showMessage("Title", "Nothing found");
					return ;
				}
				StringBuffer buffer=new StringBuffer();
				while(res.moveToNext()){
					buffer.append("Id :"+res.getString(0)+"\n");
					buffer.append("Name :"+res.getString(1)+"\n");
					buffer.append("LastName :"+res.getString(2)+"\n");
					buffer.append("Marks :"+res.getString(3)+"\n\n");
				}
				showMessage("Data", buffer.toString());
				
			}
		});
    }
    
    public void showMessage(String title, String message){
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
