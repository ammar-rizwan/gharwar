package worthywalk.example.com.gharwhar;

import android.content.Context;
import android.content.SharedPreferences;

public class data {

    SharedPreferences sharedpreferences ;
    public static final String MyPREFERENCES = "MyPrefs";

    public data(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);





    }

    public  void adddata(){
        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();



    }

    public boolean isLoggedin(){
        boolean loggedin=sharedpreferences.getBoolean("Login",false);
        return loggedin;

    }
    public void login(){

        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();

        prefsEditor.putBoolean("Login",true);
        prefsEditor.commit();
    }
    public void logout(){

        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();

        prefsEditor.putBoolean("Login",false);
        prefsEditor.commit();
    }
    public void savedata(String sign){

        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();

        prefsEditor.putString("signups",sign);
        prefsEditor.commit();
    }


    public void saveuser(String userjson) {
        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();

        prefsEditor.putString("User",userjson);
        prefsEditor.commit();


    }
    public String  getuser(){
        return  sharedpreferences.getString("User","a");
    }
}
