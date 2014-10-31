package com.example.databasepractise;

import com.example.databasepractise.data.DatabaseAdapter;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends ActionBarActivity {
	
	private static EditText username;
	private static EditText password;
	private static EditText deleteUsername;
	private static EditText updateOldUsername;
	private static EditText updateNewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
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

    	DatabaseAdapter dbAdapter;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
           
            return rootView;
        }

		@Override
		public void onStart() {
			super.onStart();
			username = (EditText) getActivity().findViewById(R.id.username);
	        password = (EditText) getActivity().findViewById(R.id.password);
	        deleteUsername = (EditText) getActivity().findViewById(R.id.delete);
	        updateOldUsername = (EditText) getActivity().findViewById(R.id.update_old_username);
	        updateNewUsername = (EditText) getActivity().findViewById(R.id.update_new_username);
	        dbAdapter = new DatabaseAdapter(getActivity());
			Button saveButton = (Button) getActivity().findViewById(R.id.saveButton);
			saveButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					addUser();
					
				}
			});
			Button viewDetailsButton = (Button) getActivity().findViewById(R.id.ViewDetails);
			viewDetailsButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					viewDetails();
					
				}
			});
			Button deleteButton = (Button) getActivity().findViewById(R.id.delete_button);
			deleteButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					deleteUser();
					
				}
			});
			Button updateUserButton = (Button) getActivity().findViewById(R.id.update_user_button);
		    updateUserButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					updateUser();
					
				}
			});
		}
		public void addUser(){
	    	
			String user = username.getText().toString();
			String pass = password.getText().toString();
			
			
			long id =dbAdapter.insertData(user, pass);
			if(id<0){
				Message.message(getActivity(), "Persiatance Unsuccessful");
			}
			else{
				Message.message(getActivity(), "Persistance Successful");
			}
			
	    }
		public void viewDetails(){
			Message.message(getActivity(),dbAdapter.getAllData());
		}
		public void deleteUser(){
			
			String user = deleteUsername.getText().toString();
			Message.message(getActivity(),Integer.toString(dbAdapter.deleteUser(user)));
		}
        public void updateUser(){
			
			String oldUser = updateOldUsername.getText().toString();
			String newUser = updateNewUsername.getText().toString();
			Message.message(getActivity(),Integer.toString(dbAdapter.updateUser(oldUser,newUser	)));
		}
        
    }
}
