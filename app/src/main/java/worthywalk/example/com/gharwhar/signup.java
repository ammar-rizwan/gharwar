package worthywalk.example.com.gharwhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class signup extends AppCompatActivity implements TextWatcher {
Button btn;
EditText fname,lname,pass,phn,repass,email;
    private List<Signin> signinList=new ArrayList<>();
    private List<User> userList=new ArrayList<>();
    Gson gson;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btn=(Button) findViewById(R.id.register);
        final data check=new data(this);

        auth=FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.email) ;

        pass=(EditText)findViewById(R.id.password);
        repass=(EditText)findViewById(R.id.repassword);

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

        email.addTextChangedListener(this);
;
        repass.addTextChangedListener(this);
        pass.addTextChangedListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent;

                if(email.getText().toString().length()!=0 && pass.getText().toString().length()!=0 && pass.getText().toString().length()!=0) {

                    if(pass.getText().toString().equals(repass.getText().toString())) {


                        signup(email.getText().toString(), pass.getText().toString());

                        /* Create an Intent that will start the Menu-Activity. */
                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Passwords doesnt match",Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Enter email and password",Toast.LENGTH_LONG).show();
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



        if(email.getText().toString().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$"))
        if(pass.getText().length()<=6) pass.setError("Password should be greater than 6");

        if(repass.getText().length()<=6) repass.setError("Password should be greater than 6");

    }
    public void signup(String Email,String Password) {


        auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Intent mainIntent = new Intent(signup.this, profile.class);

                            signup.this.startActivity(mainIntent);
                            signup.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }

                });
    }
    }
