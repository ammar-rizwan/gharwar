package worthywalk.example.com.gharwhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static worthywalk.example.com.gharwhar.App.CHANNEL_ID;

public class MainActivity extends AppCompatActivity {
    ImageView upload;
    Button button;
    TextView sell,rent;
    Spinner type,room,baths;
    int rooms=0,bath=0,types;

    Gson gson;
    Auth auth;
    data check;
    EditText Area,Value,PostalCode,city,imagename,location ;



    View rentv,sellv;

    // Creating button.
    Button ChooseButton, UploadButton;

// Creating EditText.

    // Creating ImageView.
    ImageView SelectImage;

    // Creating URI.
    Uri FilePathUri;


    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    FirebaseFirestore databaseReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upload=(ImageView) findViewById(R.id.upload);
        button=(Button) findViewById(R.id.add);
        rentv=(View)findViewById(R.id.rentview);
        sellv=(View)findViewById(R.id.sellview);
        sell=(TextView) findViewById(R.id.sell);
        rent=(TextView)findViewById(R.id.rent);
        room=(Spinner)findViewById(R.id.room) ;
        baths=(Spinner) findViewById(R.id.bath);
        Area=(EditText) findViewById(R.id.area);
        location=(EditText)findViewById(R.id.address);
        Value=(EditText) findViewById(R.id.price);
        PostalCode=(EditText) findViewById(R.id.postalcode);
        city=(EditText) findViewById(R.id.city);
        imagename=(EditText) findViewById(R.id.imagename);
        auth=new Auth(this);

        gson=new Gson();

        check=new data(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference=FirebaseFirestore.getInstance();

        SelectImage =(ImageView) findViewById(R.id.upload);

           room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

           //     String searchroom= (String) room.getItemAtPosition(i);
                if(room.getSelectedItem().toString().equals("-")) rooms=Integer.parseInt(room.getSelectedItem().toString());
                else rooms=0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //     String searchroom= (String) room.getItemAtPosition(i);
//                if(baths.getSelectedItem().toString().equals("-")) bath=Integer.parseInt(baths.getSelectedItem().toString());
//                else bath=0;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });




        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellv.setVisibility(View.VISIBLE);
                rentv.setVisibility(View.INVISIBLE);

            }
        });
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rentv.setVisibility(View.VISIBLE);
                sellv.setVisibility(View.INVISIBLE);


            }
        });










        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("Welcome ");
                    alertDialogBuilder.setMessage("We are glad to see you around . Happy Shopping !  ");
                    alertDialogBuilder.setPositiveButton("Proceed",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    UploadImageFileToFirebaseStorage();

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






                showDialog(MainActivity.this);
createNotification();
            }
        });

    }

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.addsuccess);


        Button dialogButton = (Button) dialog.findViewById(R.id.btn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    public void createNotification() {
        Intent intent = new Intent( this , MainActivity. class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this,CHANNEL_ID);
        mNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("SUCCESSFULL")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("You have Succesfully added your property for sale ! You will soon get response from buyers"
                        ))
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
//                ChooseButton.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    public void UploadImageFileToFirebaseStorage() {

        if(Value.getText().length()>0 && location.getText().toString().length()>0&&PostalCode.getText().length()>0&&city.getText().length()>0 && imagename.getText().length()>0 ) {
            // Checking whether FilePathUri Is empty or not.
            if (FilePathUri != null) {

                // Setting progressDialog Title.
//                progressDialog.setTitle("Image is Uploading...");

                // Showing progressDialog.
//                progressDialog.show();

                // Creating second StorageReference.
                StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));


                // Adding addOnSuccessListener to second StorageReference.
                storageReference2nd.putFile(FilePathUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                // Getting image name from EditText and store into string variable.
                                String TempImageName = imagename.getText().toString().trim();

                                // Hiding the progressDialog after done uploading.
//                                progressDialog.dismiss();

                                // Showing toast message after done uploading.
                                Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();


                                User user=gson.fromJson((String) check.getuser(),User.class);


                                @SuppressWarnings("VisibleForTests")
                                Property imageUploadInfo = new Property("Ahmed",rooms,bath,location.getText().toString(),Integer.parseInt(Value.getText().toString()),  taskSnapshot.getStorage().getDownloadUrl()+TempImageName.toString(),false);

                                Map<String,Object> maps=new HashMap<>();
                                maps.put("Firstname","Ahmed");
                                maps.put("Rooms",rooms);
                                maps.put("Bath",bath);
                                maps.put("Location",location.getText().toString());
                                maps.put("Value",Integer.parseInt(Value.getText().toString()));
                                maps.put("Url",imageUploadInfo.Url);
                                maps.put("Buy",false);
                                // Getting image upload ID.
                                DocumentReference ImageUploadId = databaseReference.collection("Property").document();


                                // Adding image upload id s child element into databaseReference.
                                databaseReference.collection("Property").document().set(maps).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        data chkuser=new data(MainActivity.this);


                                        Intent mainIntentss;

                                        mainIntentss = new Intent(MainActivity.this,home.class);


                                        /* Create an Intent that will start the Menu-Activity. */
                                        startActivity(mainIntentss);

                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        })
                        // If something goes wrong .
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                                // Hiding the progressDialog.


                                // Showing exception erro message.
                                Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


            } else {

                Toast.makeText(MainActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

            }
        }else {

            Toast.makeText(MainActivity.this, "Please fill the form correctly ", Toast.LENGTH_LONG).show();

        }
    }
}
