package worthywalk.example.com.gharwhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity implements TextWatcher {
Button signin,signup;
EditText email,password;
List<Signin> signinList=new ArrayList<>();
List<User> userList=new ArrayList<>();
int index;
Gson gson;
TextView tv1;
ProgressBar pbloading;
String emails,passwords;
private RequestQueue mrequest;
FirebaseAuth mAuth;
FirebaseFirestore firebaseFirestore;
    data check;


private StringRequest srequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin=(Button) findViewById(R.id.signinButton);
        signup=(Button) findViewById(R.id.signup);
        email=(EditText)findViewById(R.id.usernameText);
        password=(EditText) findViewById(R.id.passwordText);
         email.addTextChangedListener(this);
        password.addTextChangedListener(this);
        mrequest= Volley.newRequestQueue(this);
        pbloading=(ProgressBar) findViewById(R.id.pbLoading);

        tv1=(TextView) findViewById(R.id.forget);
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        signinList.add(new Signin("moazzamm2@gmail.com","123456"));
        signinList.add(new Signin("ahmed@gmail.com","123456"));
        signinList.add(new Signin("mustafa@gmail.com","123456"));
        signinList.add(new Signin("ammar@gmail.com","123456"));
        signinList.add(new Signin("raza@gmail.com","123456"));
        userList.add(new User("Moazzam","Maqsood","03248262087",6,1996,11));
        userList.add(new User("Ahmed","Gul","03248262087",6,1996,11));
        userList.add(new User("Mustafa","Irfan","03248262087",6,1996,11));
        userList.add(new User("Ammar","Rizwan","03248262087",6,1996,11));
        userList.add(new User("Raza","vasnano","03248262087",6,1996,11));

        gson=new Gson();
         check=new data(this);
        if(check.isLoggedin()){
            Intent intent= new Intent(login.this,home.class);
            startActivity(intent);
            finish();

        }

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              Intent intent=new Intent(login.this,forgetpass.class);
              startActivity(intent);

            }
        });



        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pbloading.setVisibility(View.VISIBLE);

                        //pult your code here
                        if(email.getText().toString().length()>0 && password.getText().toString().length()>0)
                    loginfb(email.getText().toString(),password.getText().toString());




            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent;

                mainIntent = new Intent(login.this,signup.class);


                /* Create an Intent that will start the Menu-Activity. */

                login.this.startActivity(mainIntent);

            }
        });
    }
    private  void loginfb(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pbloading.setVisibility(View.GONE);
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Succesfully Signed In",Toast.LENGTH_LONG).show();
                            Intent mainIntent;
                            mainIntent = new Intent(login.this,home.class);
                            Toast.makeText(login.this, "Succesfully Signed in",Toast.LENGTH_SHORT).show();
                            mAuth.getUid();
                            firebaseFirestore.collection("User").document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    User user=(User) documentSnapshot.toObject(User.class);
                                    String userjson=gson.toJson(user);
                                    check.saveuser(userjson);
                                    Intent mainIntent;
                                    mainIntent = new Intent(login.this,home.class);
                                    login.this.startActivity(mainIntent);
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    pbloading.setVisibility(View.GONE);

                                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                }
                            });



                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void login(){
        pbloading.setVisibility(View.GONE);
        Log.d("idpass",email.getText().toString());
        String emails=email.getText().toString();
        String pass=password.getText().toString();
        boolean valid=false;
        if(email.getText().toString().length()!=0 || password.getText().toString().length()!=0 ){

            Signin checkuser=new Signin(email.getText().toString(),password.getText().toString());
            int i=signinList.indexOf(checkuser);

            for (Signin p:signinList
                 ) {
                if(p.Email.equals(emails)){
                    if(p.Password.equals(pass)) valid=true;
                }
            }
            if(valid){


                User user=userList.get(index);
                String userjson=gson.toJson(user);

                check.saveuser(userjson);
                check.login();
                Intent mainIntent;
                mainIntent = new Intent(login.this,home.class);
                Toast.makeText(login.this, "Succesfully Signed in",Toast.LENGTH_SHORT).show();


                login.this.startActivity(mainIntent);
                finish();



            }else {
                Toast.makeText(getApplicationContext(),"Id or Password is incorrect",Toast.LENGTH_LONG).show();
            }




        }else {
            Toast.makeText(getApplicationContext(),"Please Enter Email and password",Toast.LENGTH_LONG).show();

        }



        /* Create an Intent that will start the Menu-Activity. */



    }
    private void senddata(){

      String url=Constants.LOGIN_URL;

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
        if(email.getText().length()==0) email.setError("username can not be empty");

        if(password.getText().length()<6) password.setError("Password's length can not be less than 6");



    }
    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.forgetpass);
        final EditText et = (EditText) dialog.findViewById(R.id.email);


        Button dialogButton = (Button) dialog.findViewById(R.id.button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText().toString().length() > 0) {
                    mAuth.sendPasswordResetEmail(et.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            }
                dialog.show();

            }


    });
    }
    private void sendemail2(){
        LayoutInflater factory = LayoutInflater.from(login.this);
        final View deleteDialogView = factory.inflate(R.layout.forgetpass, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();

        deleteDialog.setView(deleteDialogView);

        final EditText et=(EditText) deleteDialog.findViewById(R.id.email);
        Button btn=(Button) deleteDialog.findViewById(R.id.button);

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (et.getText().toString().length() > 0) {
            mAuth.sendPasswordResetEmail(et.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_LONG).show();
                    deleteDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }
});
        deleteDialog.show();
    }
    public void sendemail(){
        final Dialog dialog = new Dialog(login.this);
        dialog.setContentView(R.layout.forgetpass);
//        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        final EditText text = (EditText) dialog.findViewById(R.id.email);

        Button dialogButton = (Button) dialog.findViewById(R.id.button);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().toString().length() > 0) {
                    mAuth.sendPasswordResetEmail(text.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }


        dialog.show();
    }
});
        }
    }


