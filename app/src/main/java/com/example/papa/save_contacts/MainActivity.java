package com.example.papa.save_contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    public LayoutInflater layoutInflater;
    public ListView myRoot;
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 1;
    private Button saveButton, mapButton;
    String latitude[]=new String[6],longitude[]=new String[6],name[]=new String[6], email[] = new String[6];
    int i=0;
    HandlerClass handlerClass;
    LinearLayout mRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        layoutInflater= (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        saveButton = (Button)findViewById(R.id.btnsavecnct);
        mapButton = (Button)findViewById(R.id.btnmap);

        handlerClass=new HandlerClass();
        mRoot=(LinearLayout)findViewById(R.id.linearLayoutContact);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        AccessContacts contacts = new AccessContacts();
        contacts.start();
//        mapButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
//                intent.putExtra("name", name[i]);
//                intent.putExtra("latitude", latitude[i]);
//                intent.putExtra("longitude", longitude[i]);
//                startActivity(intent);
//            }
//        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setClickable(false);
//                AccessContacts contacts = new AccessContacts();
//                contacts.start();
//                try {
//                    contacts.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                SaveContacts();
            }
        });
    }


    private class AccessContacts extends Thread {
        String str = "", str1 = "";
        int  i=0;
        @Override
        public void run() {

            try {
                URL url = new URL("http://www.cs.columbia.edu/~coms6998-8/assignments/homework2/contacts/contacts.txt");

                BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));

                while ((str = input.readLine()) != null) {
                    Log.d(TAG, "doInBackground: " + str);
                    String array[] = str.trim().split("\\s+");

                    Log.d(TAG, "doInBackground: Name:" + array[0]);
                    Log.d(TAG, "doInBackground: Email:" + array[1]);
                    Log.d(TAG, "doInBackground: Mobile:" + array[2]);
                    Log.d(TAG, "doInBackground: Phone:" + array[3]);

                    name[i] = array[0];
                    email[i] = array[1];
                    latitude[i] = array[2];
                    longitude[i] = array[3];
                    //public Contact(String n, String e, String m, String h)
                    Contact contact=new Contact(name[i],email[i],latitude[i],longitude[i]);
                    handlerClass.obtainMessage(1,contact).sendToTarget();
                    ContactList.contacts.add(contact);
                    i++;





                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void SaveContacts(){

        for (Contact contact:ContactList.contacts){
            ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
            arrayList.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());
            //------------------------------------------------------ Names
            if (contact.name != null) {
                arrayList.add(ContentProviderOperation.newInsert(
                        ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(
                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                contact.name).build());
            }

            //------------------------------------------------------ Mobile Number
            if (contact.mobile != null) {
                arrayList.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.mobile)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
            }

            //------------------------------------------------------ Home Numbers
            if (contact.home != null) {
                arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.home)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                        .build());
            }

            //------------------------------------------------------ Email
            if (contact.email != null) {
                arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, contact.email)
                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .build());
            }
            try {
                getContentResolver().applyBatch(ContactsContract.AUTHORITY, arrayList);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }
    class HandlerClass extends android.os.Handler {
        HandlerClass() {
        }

        public void handleMessage(Message msg) {
            Contact contact=(Contact ) msg.obj;
            switch (msg.what) {
                case 1:
                    View v = layoutInflater.inflate(R.layout.list_item, null);
                    TextView textViewName =(TextView)v.findViewById(R.id.name);
                    TextView textViewEmail =(TextView)v.findViewById(R.id.email);
                    TextView textViewMobile =(TextView)v.findViewById(R.id.mobile);
                    TextView textViewHome =(TextView)v.findViewById(R.id.home);

                    textViewName.setText(contact.name);
                    textViewEmail.setText(contact.email);
                    textViewMobile.setText(contact.mobile);
                    textViewHome.setText(contact.home);
                    mRoot.addView(v);
                    return;
                default:
                    return;
            }
        }
    }
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }



}