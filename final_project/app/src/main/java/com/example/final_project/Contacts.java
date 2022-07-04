package com.example.final_project;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Contacts extends Activity {
    private ArrayList<information> information_list = new ArrayList<>();

    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    Button btnShowContacts;

    CustomAdapter adapter = new CustomAdapter(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        btnShowContacts = (Button) findViewById(R.id.btnShowContact);

        btnShowContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContactPermission();
            }
        });
        ListView list = findViewById(R.id.listView);

        list.setAdapter(adapter);

        final EditText name = findViewById(R.id.name);
        final EditText num = findViewById(R.id.num);
        final EditText email = findViewById(R.id.email);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                information inf = new information();
                inf.setName(name.getText().toString());
                inf.setNum(num.getText().toString());
                inf.setEmail(email.getText().toString());
                information_list.add(inf);
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, inf.getName());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, inf.getNum());
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, inf.getEmail());
                startActivity(intent);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @SuppressLint("Range")
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String key = information_list.get(position).getKey();
                String ID = information_list.get(position).getId();
                String name = information_list.get(position).getName();
                String num = information_list.get(position).getNum();
                ContentResolver cr = getContentResolver();
                System.out.println("출력출력출력출력출력출력출력출력출력출력출력출력출력출력출력출력");
                System.out.println(key);
                System.out.println(ID);
                System.out.println(name);
                System.out.println(num);
                //String line1 = "";
                //line1 += "";
                //ContactsContract.Contacts.LOOKUP_KEY + "=? or " + ContactsContract.Contacts.LOOKUP_KEY + "=? or " + ContactsContract.Contacts.LOOKUP_KEY + "=?"
                //Cursor mCursor = cr.query(ContactsContract.Contacts.CONTENT_URI , null,
                //        ContactsContract.Contacts.LOOKUP_KEY + "=?",
                //        new String[] {key}, null);
                Cursor mCursor = cr.query(ContactsContract.Contacts.CONTENT_URI , null,
                        null,
                        null, null);

                //if(cur.getCount()>0){

                //}
                int lookupKeyIndex = mCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
                String currentLookupKey = mCursor.getString(lookupKeyIndex);
                // int lookupKeyIndex = mCursor.getColumnIndex(key);
                String line = "";
                line = String.format("%4d",lookupKeyIndex);
                System.out.println(currentLookupKey);
                return false;
            }
        });
    }

    @SuppressLint("Range")
    private void getContacts() {
        //TODO get contacts code here
        //Toast.makeText(this, "Get contacts ....", Toast.LENGTH_LONG).show();
        information_list = new ArrayList<information>();
        ContentResolver cr = getContentResolver();
        System.out.println(ContactsContract.Contacts.CONTENT_URI);
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI , null ,null, null, null);
        if(cur.getCount()>0){
            String line = "";
            while(cur.moveToNext()){
                information inf = new information();
                int id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String ID = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String key = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                //String key = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                System.out.println("AAAAAAAAAAAAAA");
                System.out.println(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                System.out.println(key);
                System.out.println("BBBBBBBBBBBBBBB");
                line = String.format("%4d",id);
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                line += " " + name;
                inf.setName(name);
                inf.setKey(key);
                inf.setId(ID);

                Cursor eCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[]{String.valueOf(id)}, null);
                String email = new String();
                while (eCur.moveToNext()){
                    email += eCur.getString(eCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }
                inf.setEmail(email);

                if(("1").equals(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{String.valueOf(id)}, null);
                    int i = 0;
                    int pCount = pCur.getCount();
                    String phoneNum = new String();
                    String[] phoneType = new String[pCount];

                    while (pCur.moveToNext()) {
                        phoneNum += pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        line += " " + phoneNum;
                        phoneType[i] = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        i++;
                    }
                    inf.setNum(phoneNum);
                    information_list.add(inf);
                    adapter.notifyDataSetChanged();

                }
//                numbook.add(line);
                Toast.makeText(this, line, Toast.LENGTH_LONG).show();
                line ="";
            }
        }


    }

    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Read Contacts permission");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please enable access to contacts.");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {android.Manifest.permission.READ_CONTACTS}
                                    , PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            } else {
                getContacts();
            }
        } else {
            getContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private class CustomAdapter extends BaseAdapter {
        Context context;

        public CustomAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return information_list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = View.inflate(context, R.layout.item, null);
            TextView nameItem = itemView.findViewById(R.id.nameItem);
            TextView numItem = itemView.findViewById(R.id.numItem);
            TextView emailItem = itemView.findViewById(R.id.emailItem);

            nameItem.setText(information_list.get(position).getName());
            numItem.setText(information_list.get(position).getNum());
            emailItem.setText(information_list.get(position).getEmail());
            // nameItem.setText(nameList.get(position));
            // numItem.setText(numList.get(position));

            /*
            ImageButton deleteBtn = itemView.findViewById(R.id.deleteBtn);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parentRow = (View) v.getParent();
                    ListView listView = (ListView) parentRow.getParent();

                    final int position = listView.getPositionForView(parentRow);
                    information_list.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });

             */
            return itemView;
        }
    }
}