package worthywalk.example.com.gharwhar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity implements TextWatcher {
    String fname, lname, phn, gend, days, months, years,image;
    CircleImageView profile_picture;
    float hei, wei;
    Gson gson;

    User user;
    EditText firstname;
    EditText lastname;
    EditText phone;
    Spinner gender;
    EditText day;
    Auth auth;

    EditText month;
    EditText year;
    TextView num;
    Auth auths;
    FirebaseAuth firebaseAuth;
    data check;
    FirebaseFirestore firebaseFirestore;
    FloatingActionButton go;
    boolean fn,ln,pn,gn,dy,mn,yr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseFirestore=FirebaseFirestore.getInstance();
        num=(TextView) findViewById(R.id.number);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        phone = (EditText) findViewById(R.id.phone);
        day = (EditText) findViewById(R.id.day);
        month = (EditText) findViewById(R.id.month);
        year = (EditText) findViewById(R.id.year);
        gender = (Spinner) findViewById(R.id.gender);
        go = (FloatingActionButton) findViewById(R.id.go);

        auths=new Auth(this);
        firstname.addTextChangedListener(this);
        lastname.addTextChangedListener(this);
        phone.addTextChangedListener(this);
        auths=new Auth(this);
        firebaseAuth=FirebaseAuth.getInstance();
check=new data(this);

if(check.isLoggedin()) num.setVisibility(View.GONE);
gson=new Gson();
//user=gson.fromJson(check.getuser(),User.class);
//firstname.setText(user.Firstname);
//lastname.setText(user.Lastname);
//phone.setText(user.contact);
//day.setText(String.valueOf(user.d));
//month.setText(String.valueOf(user.m));
//year.setText(String.valueOf(user.y));

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pn && dy && mn && yr) {

                    fname = firstname.getText().toString();
                    fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);
                    lname = lastname.getText().toString();
                    lname = lname.substring(0, 1).toUpperCase() + lname.substring(1);

                    phn = phone.getText().toString();
                    phn = "+92" + phn;

                    days = day.getText().toString();
                    months = month.getText().toString();
                    years = year.getText().toString();


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String dob = years + months + days;
                    Date d = null;
                    try {
                        d = sdf.parse(dob);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date currentTime = Calendar.getInstance().getTime();
                    int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(years);
//
//                    Intent mainIntent;
//
//                    mainIntent = new Intent(profile.this,home.class);
//
//
//                    /* Create an Intent that will start the Menu-Activity. */
//                    startActivity(mainIntent);
//                    finish();


                    User user=new User(fname,lname,phn,Integer.parseInt(days),Integer.parseInt(months),Integer.parseInt(years));


                    Adduserdetail(user);


                }
                else {
                    Toast.makeText(getApplicationContext(),"Form Validation failed , please provide the correct Information !",Toast.LENGTH_LONG).show();

                }
            }

        });
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        boolean boolphone=false;
        if(firstname.getText().toString().length()==0) {

            firstname.setError("This field can not be empty");
            fn=false;
        }else {
            fn=true;
        }


        boolphone=phone.getText().toString().matches("3[0-9]{9}");

        if(phone.getText().toString().length()<10) phone.setError("Enter 10 digit");
        else if(boolphone) pn=true ;
        else phone.setError("3XXXXXXXXX");

        if(day.getText().toString().length()==0){
            dy=false;
            day.setError("can not be empty");
        }

        else if(Integer.parseInt(day.getText().toString())>31){
            day.setError("Enter valid day");
            dy=false;
        }
        else{
            dy=true;
        }

        if(month.getText().toString().length()==0){
            mn=false;
            month.setError("can not be empty");
        }
        else if(Integer.parseInt(month.getText().toString())>12){
            mn=false;

            month.setError("Enter valid month");
        }else {
            mn=true;

        }

        if(year.getText().toString().length()==0){
            year.setError("can not be empty");
            yr=false;
        }
        else if(Integer.parseInt(year.getText().toString())<1940 || Integer.parseInt(year.getText().toString())>2019){
            year.setError("Not a valid year");
            yr=false;

        }
        else yr=true;








    }

    private void welcome() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(profile.this);
        alertDialogBuilder.setTitle("Welcome ");
        alertDialogBuilder.setMessage("We are glad to see you around . Happy Shopping !  ");
        alertDialogBuilder.setPositiveButton("Proceed",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        data chkuser=new data(profile.this);
                        chkuser.logout();

                        Intent mainIntentss;

                        mainIntentss = new Intent(profile.this,home.class);


                        /* Create an Intent that will start the Menu-Activity. */
                        startActivity(mainIntentss);

                        finish();

//                        sharedpreferences.edit().remove("User").commit();
//                        mAuth.signOut();
//
//                        loginManager.getInstance().logOut();
//
//                        Intent intent=new Intent(getActivity(), login.class);
//                        startActivity(intent);
//                        getActivity().finish();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();




    }
    public void Adduserdetail(User user){

        final Map<String,Object> doc=new HashMap<>();
        doc.put("Firstname",user.Firstname);
        doc.put("Lastname",user.Lastname);
        doc.put("Phone",user.contact);
//        doc.put("Gender",user.);
        doc.put("day",user.d);
        doc.put("month",user.m);
        doc.put("year",user.y);






        final DocumentReference documentReference=firebaseFirestore.collection("Users").document(auths.getuser());
        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                transaction.set(documentReference,doc);



                return null;

            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                welcome();

                Toast.makeText(getApplicationContext(),"User Added",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }





}
