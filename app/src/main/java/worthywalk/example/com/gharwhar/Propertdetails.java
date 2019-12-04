package worthywalk.example.com.gharwhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class Propertdetails extends AppCompatActivity {

    Gson gson;
    ImageView iv;
    Property prop;
    TextView name,price,rooms,baths,location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertdetails);

        iv=(ImageView) findViewById(R.id.imgview);
        name=(TextView)findViewById(R.id.owner);
        price=(TextView) findViewById(R.id.price);
        rooms=(TextView) findViewById(R.id.rooms);
        baths=(TextView) findViewById(R.id.baths);
        location=(TextView) findViewById(R.id.location);
        Intent intent=getIntent();

        gson=new Gson();
        String users=intent.getStringExtra("prop");
        prop=gson.fromJson(users,Property.class);

        Glide.with(this).load(prop.Url).into(iv);
        name.setText(prop.Property);
        price.setText(String.valueOf(prop.price));
        rooms.setText(String.valueOf(prop.rooms));
        baths.setText(String.valueOf(prop.baths));
        location.setText(prop.location);


    }
}
