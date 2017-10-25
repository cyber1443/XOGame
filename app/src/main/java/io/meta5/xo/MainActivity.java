package io.meta5.xo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    ImageView turn;
    Player player1;
    Player player2;
    EditText et1,et2;
    int red=1;


    class Player{
        String ime;
        int znak;
        boolean[] polja = new boolean[9];
        int wins = 0;
        TextView publish;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turn = (ImageView)findViewById(R.id.ivTurn);
        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        player1 = new Player();
        player2 = new Player();
        player1.znak = getResources().getIdentifier("x","drawable",this.getPackageName());
        player1.ime = et1.getText().toString();
        player1.publish = (TextView)findViewById(R.id.tvWins1);
        player2.znak = R.drawable.o;
        player2.ime=et2.getText().toString();
        player2.publish = (TextView)findViewById(R.id.tvWins2);
    }

    public void change(View view){
        view = (ImageView)view;
        Player player = whoIsNext();
        ((ImageView) view).setImageResource(player.znak);
        view.animate().alpha(1f).setDuration(300);
        view.setClickable(false);
        String tag = (view.getResources().getResourceEntryName(view.getId()).substring(view.getResources().getResourceEntryName(view.getId()).length()-1));
        updatePolje(player,tag);
        if(red<5){

        }
        else if(checkWinner(player)){
            Toast.makeText(this,player.ime + " WINER!",Toast.LENGTH_LONG).show();
            player.wins++;
            player.publish.setText("Wins: "+player.wins);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    restart();
                }
            }, 500);
        }
        else if(red==10){
            Toast.makeText(this, "DRAW!", Toast.LENGTH_LONG).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    restart();
                }
            }, 500);
        }
    }

    public void updatePolje(Player player,String tag){
        for (int i=0;i<9;i++){
            if(i==Integer.parseInt(tag)-1) player.polja[i]=true;
        }
    }



    public boolean checkWinner(Player player){
            if(player.polja[0] && player.polja[1] && player.polja[2]) {
                return true;
            }
            if(player.polja[3] && player.polja[4] && player.polja[5]) {
                return true;
            }
            if(player.polja[6] && player.polja[7] && player.polja[8]) {
                return true;
            }
            if(player.polja[0] && player.polja[3] && player.polja[6]) {
                return true;
            }
            if(player.polja[1] && player.polja[4] && player.polja[7]) {
                return true;
            }
            if(player.polja[2] && player.polja[5] && player.polja[8]) {
                return true;
            }
            if(player.polja[0] && player.polja[4] && player.polja[8]) {
                return true;
            }
            if(player.polja[2] && player.polja[4] && player.polja[6]) {
                return true;
            }
        return false;
    }

    public Player whoIsNext(){
        if(red%2==1){
                turn.animate().translationXBy(210f).setDuration(20);
            red++;
            return player1;

        }
        else{
            turn.animate().translationXBy(-210f).setDuration(20);
            red++;
            return player2;
        }
    }
    public void restart(){


        for (int i=1;i<10;i++){
            int resId = this.getResources().getIdentifier("iv"+i,"id",this.getPackageName());
            ImageView iv = (ImageView)findViewById(resId);
            iv.animate().alpha(0f).setDuration(300);
            iv.setClickable(true);

            player1.polja[i-1] = false;
            player2.polja[i-1] = false;
        }
        if(red%2==0)turn.animate().translationXBy(-210f).setDuration(20);
        red = 1;

    }

}
