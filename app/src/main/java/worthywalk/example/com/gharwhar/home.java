package worthywalk.example.com.gharwhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.material.navigation.NavigationView.*;

public class home extends AppCompatActivity {
    private DrawerLayout dl;
    SeekBar seekBar;
    Spinner room , baths;
    RequestQueue requestQueue;
    RecyclerView rev;
    List<cardInfo> list=new ArrayList<>();
    ImageButton menu;
    TextView homet,shopt,propt,flatt;
    private ActionBarDrawerToggle t;
    List<Property> lists=new ArrayList<>();
    List<Property> flatlist=new ArrayList<>();

    int searchrooms=0,searchbath=0,searchrange=50000;
    LinearLayout homelt,proplt,shoplt,flatlt;
    storeadapter adapter;
    private NavigationView nv;
    TextView founddata;
    Auth auth;


    private static home mInstance;
    int type=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().collapseActionView(this);
        super.onCreate(savedInstanceState);
        auth=new Auth(this);
        setContentView(R.layout.activity_home);
        rev=(RecyclerView) findViewById(R.id.rev);
        rev.setHasFixedSize(true);
        rev.setLayoutManager(new LinearLayoutManager(this));
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        menu=(ImageButton) findViewById(R.id.menu) ;
        requestQueue= Volley.newRequestQueue(this);
        dl.addDrawerListener(t);
        t.syncState();
        mInstance=this;
        seekBar=(SeekBar)findViewById(R.id.seekBar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homelt   =(LinearLayout)findViewById(R.id.homelt);
        shoplt      =(LinearLayout)findViewById(R.id.shoplt);
        flatlt        =(LinearLayout)findViewById(R.id.flatlt);
        proplt        =(LinearLayout)findViewById(R.id.proplt);
        homet=(TextView)findViewById(R.id.hometext);
        shopt=(TextView)findViewById(R.id.shoptext);
        flatt=(TextView)findViewById(R.id.flattext);
        propt=(TextView)findViewById(R.id.properttext);
        founddata=(TextView) findViewById(R.id.datanotfound);
        room=(Spinner)findViewById(R.id.room);
        baths=(Spinner)findViewById(R.id.bath);


        senddata();

        lists.add(new Property("Ahmed",5,3,"87/A Bahadurabad",125000,"https://upload.wikimedia.org/wikipedia/commons/thumb/c/cf/Nathan_Sanderson_I_House%2C_Waltham_MA.jpg/1200px-Nathan_Sanderson_I_House%2C_Waltham_MA.jpg",false));
        lists.add(new Property("Ahmed akbar",2,3,"87/A pib",50000,"http://www.atharassociates.co/wp-content/uploads/2015/05/most-luxrious.jpg",false));
        lists.add(new Property("Ali ",3,3,"87/D clifton",212000,"https://i.ytimg.com/vi/RIJUfkiuOwE/hqdefault.jpg",false));
        lists.add(new Property("Nouman",2,3,"87/A Gulistan johar",252000,"http://cdn.house-crazy.com/wp-content/uploads/2013/01/Pink-House-Cape-May-New-Jersey.jpg",false));
        lists.add(new Property("Mujtaba",5,3,"87/A PECHS",200000,"https://i.dailymail.co.uk/i/pix/2014/06/20/article-2663533-1EF4334B00000578-606_964x510.jpg",false));
        lists.add(new Property("Ammar",5,3,"87/A Gulshan",175000,"https://www.fjtown.com/wp-content/uploads/2012/01/Model-House-3.jpg",false));
        lists.add(new Property("Moazzam",3,3,"87/A pib",252000,"http://2.bp.blogspot.com/-LNVHXs0duLM/U3q6ItA44gI/AAAAAAAAMM0/sU5XrzcS_dQ/s1600/2+Kanal+House+Bahria+Town.jpg",false));
        lists.add(new Property("Yasir",5,3,"87/A Saddar",25000,"https://i.pinimg.com/736x/c0/f2/dc/c0f2dc43c726c4b839da46b1878f8925--pakistan-home-amazing-architecture.jpg",false) );
        lists.add(new Property("Saqib",5,3,"87/A Malir",15000,"http://4.bp.blogspot.com/-cqka7kor720/U3yeJ1kT98I/AAAAAAAARxg/vYpP9lxBrcA/s1600/bahria+town.jpg",false));
        lists.add(new Property("Hanzala",2,3,"88/B Malir",15000,"https://www.wired.com/wp-content/uploads/archive/news/images/full/1671_f.jpg",true));
        lists.add(new Property("Amin",3,3,"87/A PECHS",500000,"http://2.bp.blogspot.com/-pdt1ZEQkXZE/UE7FfJ2CS1I/AAAAAAAACss/0fqtyeWIWdg/s1600/houses-street-american-village-erbil-iraq-thumb.jpg",true));
        lists.add(new Property("Mustafa",2,3,"87/A Gulshan",25000,"http://www.propertyguide.com.pk/wp-content/uploads/2016/11/10-Marla-House-For-Sale-Phase-6-DHA-Lahore.jpg",true));
        lists.add(new Property("Irfan",5,3,"17/A Gulshan",125000,"https://i.ytimg.com/vi/Z5pycgNbpEQ/hqdefault.jpg",true));
        lists.add(new Property("Raza",5,3,"87/A PECHS",25000,"http://photonshouse.com/photo/41/41f92d5581ac34fe020f3bc190f2833d.jpg",true));
        lists.add(new Property("Tahir",3,3,"87/A Saddar",25000,"http://a.abcnews.com/images/US/HT_erbil_dream_house_2_jt_141104_4x3_992.jpg",true));
        lists.add(new Property("Mehdi",4,3,"87/A Saddar",25000,"https://www.aarz.pk/uploads/properties/2017/7/5-marla-house-for-sale-in-green-park-housing-scheme-lahore-81975-image-1-actual.jpg",true));
        lists.add(new Property("Fahad",4,2,"81/A Saddar",25000,"https://i.ytimg.com/vi/Z5pycgNbpEQ/hqdefault.jpg",true));
        lists.add(new Property("Omar",5,3,"8/A Gulshan",25000,"https://i.ytimg.com/vi/LOW8cOkgq2c/hqdefault.jpg",true));


        flatlist.add(new Property("Ahmed",5,3,"87/A Bahadurabad",12000,"http://www.thegreenage.co.uk/wp-content/uploads/2013/11/Block-of-flats.jpg",false) );
        flatlist.add(new Property("Ahmed akbar",2,3,"87/A pib",50000,"https://d1bvpoagx8hqbg.cloudfront.net/originals/beautiful-bright-2-bedroom-flat-center-f6c6f41c603054a76e34c62086935ac5.jpg",false));
        flatlist.add(new Property("Tahir",3,3,"87/A Saddar",10000,"http://www.munsterjoinery.co.uk/files/c_20140630062343E_Clarendon.jpg",true) );
        flatlist.add(new Property("Mehdi",4,3,"87/A Saddar",25000,"http://homeworlddesign.com/wp-content/uploads/2017/07/High-Rise-Apartment-3.jpg",true) );
        flatlist.add(new Property("Fahad",4,2,"81/A Saddar",75000,"https://i.ytimg.com/vi/Z5pycgNbpEQ/hqdefault.jpg",true) );
        flatlist.add(new Property("Omar",5,3,"8/A Gulshan",25000,"http://homeworlddesign.com/wp-content/uploads/2017/07/High-Rise-Apartment-3.jpg",false) );

        adapter=new storeadapter(lists,list,this);

        rev.setAdapter(adapter);

homelayout();

        room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String searchroom= (String) room.getItemAtPosition(i);

                if(!searchroom.equals("-")){
                    searchrooms=Integer.parseInt(searchroom);
                    setlayout();

                }else {
                    searchrooms=0;
                    setlayout();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        baths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String searchroom= (String) baths.getItemAtPosition(i);

                if(!searchroom.equals("-")){
                    searchbath=Integer.parseInt(searchroom);
                    setlayout();

                }else {
                    searchbath=0;
                    setlayout();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dl.isDrawerOpen(Gravity.LEFT))
                                dl.closeDrawer(Gravity.LEFT);
                else dl.openDrawer(Gravity.LEFT);

            }
        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seekBar.incrementProgressBy(50000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               searchrange=i*100000+50000;

                setlayout();
                Toast.makeText(getApplicationContext(),"seekbar progress: "+i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Intent mainIntents;

                        mainIntents = new Intent(home.this,profile.class);


                        /* Create an Intent that will start the Menu-Activity. */
                        startActivity(mainIntents);
                        break;
                    case R.id.addproperty:
                        Intent mainIntent;

                        mainIntent = new Intent(home.this,MainActivity.class);


                        /* Create an Intent that will start the Menu-Activity. */
                        startActivity(mainIntent);

                        break;
                    case R.id.settings:
                        Toast.makeText(home.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    case R.id.mycart:
                       auth.logout();
                       logoutfunc();
                        break;


                    default:
                        return true;
                }


                return true;

            }
        });


    }
    private void clrview(){
        homet.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        shopt.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        flatt.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        propt.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));

    }
    private  void homelayout(){
        homelt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clrview();
                homet.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                type=1;
                setlayout();

            }
        });
        shoplt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clrview();

                shopt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                type=3;
                setlayout();

            }
        });
        flatlt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clrview();

                flatt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                type=2;
                setlayout();



            }
        });
        proplt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clrview();

                propt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                type=4;
                setlayout();


            }
        });
    }

    private void setlayout(){

        List<Property> checklist=new ArrayList<>();
        List<Property> newlist=new ArrayList<>();
        if(type==1){
            checklist=lists;
            founddata.setVisibility(GONE);
            rev.setVisibility(VISIBLE);
        }else if(type==2){
            checklist=flatlist;
            founddata.setVisibility(GONE);
            rev.setVisibility(VISIBLE);
        }else if(type==3){
            rev.setVisibility(GONE);
            founddata.setVisibility(VISIBLE);
        }else if(type==4){
            rev.setVisibility(GONE);
            founddata.setVisibility(VISIBLE);
        }

        for (Property p:checklist
             ) {

            if(searchbath!=0 && searchrooms!=0 && searchrange<50000 ){
                if(p.baths<=searchbath && p.rooms<=searchrooms && p.price<=searchrange){
                    newlist.add(p);
                }
            }else if (searchrooms!=0 && searchrange<50000){
                if(p.rooms<=searchrooms && p.price<=searchrange){
                    newlist.add(p);
                }
            }else if(searchbath!=0 && searchrooms!=0 ){
                if(p.rooms<=searchrooms &&p.baths<=searchbath){
                    newlist.add(p);
                }
            }else if(searchbath!=0 && searchrange<50000  ){
                if(p.price<=searchrange &&p.baths<=searchbath){
                    newlist.add(p);
                }
            }else if(searchrooms!=0){
                if(p.rooms<=searchrooms){
                    newlist.add(p);
                }
            }else if(searchbath!=0){
                if(p.baths<=searchbath){
                    newlist.add(p);
                }
            }else if(searchrange<50000 ){
                if(p.price<=searchrange){
                    newlist.add(p);
                }

            }else {
                newlist.add(p);
            }



            storeadapter adapter=new storeadapter(newlist,list,this);

            rev.setAdapter(adapter);

        }

    }
    private void senddata(){



        String url=Constants.GET_PROPERTIES;

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray jsonArray=response.getJSONArray("Property");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject property=jsonArray.getJSONObject(i);
                                lists.add(new Property(property.getString("Addedby"),property.getInt("Rooms"),property.getInt("Baths"),property.getString("Location"),property.getInt("Price"),property.getString("Image"),false));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });


        mInstance.addToRequestQueue(request,"getproperties");
    }
    public void addToRequestQueue(Request request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);

    }
    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());

        return requestQueue;
    }
    private void logoutfunc() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(home.this);
        alertDialogBuilder.setTitle("Logout");
        alertDialogBuilder.setMessage("Are you sure you want to Logout ? ");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        data chkuser=new data(home.this);
                        chkuser.logout();

                        Intent mainIntentss;

                        mainIntentss = new Intent(home.this,login.class);


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

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();



    }
    private void welcome() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(home.this);
        alertDialogBuilder.setTitle("Welcome ");
        alertDialogBuilder.setMessage("We are glad to see you around . Happy Shopping !  ");
        alertDialogBuilder.setPositiveButton("Proceed",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        data chkuser=new data(home.this);
                        chkuser.logout();

                        Intent mainIntentss;

                        mainIntentss = new Intent(home.this,login.class);


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


}
