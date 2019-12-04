package worthywalk.example.com.gharwhar;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

public class storeadapter extends RecyclerView.Adapter{
    private OnItemClickListener mlistener;

    private static final long DEFAULT_QUALIFICATION_SPAN = 700;

    private long doubleClickQualificationSpanInMillis;

    int clickcount;
    private long timestampLastClick=0;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }
    List<Property> data=new ArrayList<>();
    List<cardInfo> data2=new ArrayList<>();

    Context context;
    public storeadapter(List<Property> a,List<cardInfo> b, Context cont){
        data=a;
        data2=b;
        context=cont;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater minflator=LayoutInflater.from(parent.getContext());
        View view=minflator.inflate(R.layout.homecard,parent,false);

        return new listViewHolder(view,mlistener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((listViewHolder)holder).bindView(position);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    private class listViewHolder extends ViewHolder implements View.OnClickListener{

        TextView name;
        ImageView likeview;
        ImageView banner;
        TextView value;
        int clickcount=0;


        public listViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            banner=(ImageView) itemView.findViewById(R.id.imgview);
            name=(TextView) itemView.findViewById(R.id.name);

            likeview=(ImageView) itemView.findViewById(R.id.like);
            value=(TextView) itemView.findViewById(R.id.value);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();


                    Property a=data.get(position);
//                    cardInfo b =data2.get(position);

                    if(timestampLastClick==0){
                       timestampLastClick= SystemClock.elapsedRealtime();
                        clickcount++;
//                        Toast.makeText(context,String.valueOf(1),Toast.LENGTH_LONG).show();


                    }
                    else if(SystemClock.elapsedRealtime() - timestampLastClick < 700){
                        shakeItBaby();

                                            Toast.makeText(context,"Liked",Toast.LENGTH_LONG).show();

                        if(a.buy){
                            a.buy=false;
                            likeview.setBackgroundResource(R.drawable.ic_heart);

                        }else {
                            a.buy=true;
                            likeview.setBackgroundResource(R.drawable.ic_like);

                        }
//                        Toast.makeText(context,String.valueOf(SystemClock.elapsedRealtime() - timestampLastClick),Toast.LENGTH_LONG).show();
                        timestampLastClick=0;
                        clickcount=0;
                    }else{
//                        Toast.makeText(context,String.valueOf(SystemClock.elapsedRealtime() - timestampLastClick),Toast.LENGTH_LONG).show();

//                    Toast.makeText(context,String.valueOf(3),Toast.LENGTH_LONG).show();


                    clickcount=0;
                            timestampLastClick=0;


                    }



                }
            });
//
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.d("clickedsss","clickers");

                    Gson gson=new Gson();
                    int position=getAdapterPosition();

                    Property a=data.get(position);
                    String prop=gson.toJson(a,Property.class);

                        Log.d("clickedsss",String.valueOf(position));
                    Intent intent=new Intent(context,Propertdetails.class);
                    intent.putExtra("prop",prop);
                    context.startActivity(intent);
//                    Toast.makeText(context,"longpress",Toast.LENGTH_LONG).show();
                    return false;



                }
            });

        }


        public void bindView (int position){
       //     final cardInfo a =data2.get(position);
            final Property b =data.get(position);



            likeview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shakeItBaby();

                    if(b.buy){
                        b.buy=false;
                        likeview.setBackgroundResource(R.drawable.ic_heart);
                    }else {
                        b.buy=true;
                        likeview.setBackgroundResource(R.drawable.ic_like);

                    }
                }
            });
            if(b.buy){
                likeview.setBackgroundResource(R.drawable.ic_like);

            }else if(!b.buy){
                likeview.setBackgroundResource(R.drawable.ic_heart);

            }
            Glide.with(context).load(b.Url).into(banner);
            name.setText(b.Property);
            value.setText(String.valueOf(b.price));
        }
        @Override
        public void onClick(View view) {                        Log.d("clickedsss","Clicker");

        }
    }

    private void shakeItBaby() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }
}
