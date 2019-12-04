package worthywalk.example.com.gharwhar;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class Auth {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firbaseFirestore;
    private Context context;



    public Auth(Context context){
        mAuth=  FirebaseAuth.getInstance();
        firbaseFirestore=FirebaseFirestore.getInstance();

        this.context=context;
    }

    public void logout(){
        mAuth.signOut();

    }

    public String  getuser(){
       return mAuth.getUid();
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






        final DocumentReference documentReference=firbaseFirestore.collection("Users").document(getuser());
        firbaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                transaction.set(documentReference,doc);



                return null;

            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"User Added",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }


}

