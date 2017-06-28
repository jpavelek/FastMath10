package afte9.com.fastmath10;

import android.app.ActionBar;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class GameScreen extends AppCompatActivity {

    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        //Enable "Back" arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Reset game from start
        score = 0;


        //Start timer and game
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                ProgressBar bar = (ProgressBar)findViewById(R.id.progressBar);
                if (bar == null) return;
                bar.setProgress((int) millisUntilFinished / 1000);
            }
            public void onFinish() {
                //TODO - End game
            }
        }.start();


    }

    //If Back arrow clicked, go back
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }


}
