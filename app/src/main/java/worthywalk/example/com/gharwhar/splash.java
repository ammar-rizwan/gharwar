package worthywalk.example.com.gharwhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                Intent mainIntent;

                    mainIntent = new Intent(splash.this,login.class);


                /* Create an Intent that will start the Menu-Activity. */

                splash.this.startActivity(mainIntent);
                splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
