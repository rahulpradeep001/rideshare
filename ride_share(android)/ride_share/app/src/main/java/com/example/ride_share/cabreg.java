package com.example.ride_share;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class cabreg extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    SharedPreferences sh;
    Button b1,b2;
    RadioButton r1,r2;
    String fname,lname,gender,place,post,pin,phone,email,username,password,url,photo;

    String res;
    String fileName = "", path = "";
    private static final int FILE_SELECT_CODE = 0;


    String  ip, lid,title,url1;
    String PathHolder="";
    byte[] filedt=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabreg);
        e1 = findViewById(R.id.editTextTextPersonName7);
        e2 = findViewById(R.id.editTextTextPersonName9);
        e3 = findViewById(R.id.editTextTextPersonName10);
        e4 = findViewById(R.id.editTextTextPersonName11);
        e5 = findViewById(R.id.editTextTextPersonName12);
        e6 = findViewById(R.id.editTextTextPersonName14);
        e7 = findViewById(R.id.editTextTextPersonName15);
        e8 = findViewById(R.id.editTextTextPersonName16);
        e9 = findViewById(R.id.editTextTextPersonName17);
        e10 = findViewById(R.id.editTextTextPersonName18);

        b1 = findViewById(R.id.button26);
        b2 = findViewById(R.id.button27);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        r1 = findViewById(R.id.radioButton);
        r2 = findViewById(R.id.radioButton2);
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        url = "http://" + sh.getString("ip", "") + ":5000/cabreg";
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
//            intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 7);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = e1.getText().toString();
                lname = e2.getText().toString();
                place = e3.getText().toString();
                post = e4.getText().toString();
                pin = e5.getText().toString();
                photo = e8.getText().toString();
                phone = e6.getText().toString();
                email = e7.getText().toString();
                username = e9.getText().toString();
                password = e10.getText().toString();
                if (r1.isChecked()) {
                    gender = r1.getText().toString();

                } else
                {
                    gender = r2.getText().toString();
                }




                if (fname.equalsIgnoreCase("")) {
                    e1.setError("enter your first name");
//                } else if (fname.matches(("^[a-zA-Z]*$"))) {
//                    e1.setError(" characters  allowed");
                }

                else if (lname.equalsIgnoreCase("")) {
                    e2.setError("enter your last name");
//                } else if (lname.matches(("^[a-zA-Z]*$"))) {
//                    e2.setError(" characters  allowed");
                }
                else if (place.equalsIgnoreCase("")) {
                    e3.setError("enter your place");
//                } else if (place.matches(("^[a-z]*$"))) {
//                    e3.setError(" characters  allowed");
                }
                else  if (post.equalsIgnoreCase("")) {
                    e4.setError("enter your  post");
//                } else if (post.matches(("^[a-z]*$"))) {
//                    e4.setError(" characters  allowed");
                }
                else if (pin.equalsIgnoreCase("")) {
                    e5.setError("enter your pin");
                } else if (pin.length() != 6) {
                    e5.setError(" numbers  allowed");
                }




                else if (phone.equalsIgnoreCase("")) {
                    e6.setError("enter your phone");
                } else if (phone.length() != 10) {
                    e6.setError(" numbers  allowed");
                }

                else if(email.equalsIgnoreCase(""))
                {
                    e4.setError("Enter Your Email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    e4.setError("Enter Valid Email");
                    e4.requestFocus();
                }
                else if (username.equalsIgnoreCase("")) {
                    e9.setError("enter your  username");
//                } else if (username.matches(("^[a-z]*$"))) {
//                    e9.setError(" characters  allowed");
                }

                else  if (password.equalsIgnoreCase("")) {
                    e10.setError("enter your  password");
                }
//                else if (username.matches(("^[a-z]*$"))) {
//                    e10.setError(" characters  allowed");
//                }


                else
                {


                    uploadBitmap(title);
                }}





        });


    }
    ProgressDialog pd;
    private void uploadBitmap(final String title) {
        pd=new ProgressDialog(cabreg.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response1) {
                        pd.dismiss();
                        String x=new String(response1.data);
                        try {
                            JSONObject obj = new JSONObject(new String(response1.data));
//                        Toast.makeText(Upload_agreement.this, "Report Sent Successfully", Toast.LENGTH_LONG).show();
                            if (obj.getString("task").equalsIgnoreCase("success")) {

                                Toast.makeText(cabreg.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),cabreg.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "====+++++++"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();





                params.put("lid",sh.getString("lid",""));
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("place", place);
                params.put("post", post);
                params.put("pin", pin);
                params.put("gender", gender);

                params.put("phone", phone);
                params.put("email", email);
                params.put("username", username);
                params.put("password", password);


//                params.put("file", image);

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(PathHolder , filedt ));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.d("File Uri", "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        PathHolder = FileUtils.getPathFromURI(this, uri);
//                        PathHolder = data.getData().getPath();
//                        Toast.makeText(this, PathHolder, Toast.LENGTH_SHORT).show();

                        filedt = getbyteData(PathHolder);
                        Log.d("filedataaa", filedt + "");
//                        Toast.makeText(this, filedt+"", Toast.LENGTH_SHORT).show();
                        e8.setText(PathHolder);
                        Toast.makeText(this, "eeeee ==>  filedataaa==++"+filedt, Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e){
                        Toast.makeText(this, "eeeee ==>  "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private byte[] getbyteData(String pathHolder) {
        Log.d("path", pathHolder);
        File fil = new File(pathHolder);
        int fln = (int) fil.length();
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(fil);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[fln];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }
            byteArray = bos.toByteArray();
            inputStream.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"error++"+e,Toast.LENGTH_LONG).show();
        }
        return byteArray;


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(getApplicationContext(), registration.class);
        startActivity(ii);

    }
}