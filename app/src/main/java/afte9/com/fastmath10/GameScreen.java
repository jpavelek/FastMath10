package afte9.com.fastmath10;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class GameScreen extends AppCompatActivity {

    private int score;
    private int remaining_millis;
    private TaskProvider task_provider = TaskProvider.getInstance();
    private String visual;
    private int result;
    private int[] result_choices;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        //Enable "Back" arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Reset game from start
        score = 0;
        result_choices = null;

        result = task_provider.getTaskResult();
        visual = task_provider.getTaskVisual();
        result_choices = task_provider.getResultChoices();

        ((Button)findViewById(R.id.buttonChoiceOne)).setText(String.valueOf(result_choices[0]));
        ((Button)findViewById(R.id.buttonChoiceTwo)).setText(String.valueOf(result_choices[1]));
        ((Button)findViewById(R.id.buttonChoiceThree)).setText(String.valueOf(result_choices[2]));

        timer = new CountDownTimer(task_provider.getTimeout(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ProgressBar bar = (ProgressBar)findViewById(R.id.progressBar);
                if (bar == null) return;
                bar.setProgress((int) millisUntilFinished / 1000);
                remaining_millis = (int)millisUntilFinished;
            }

            @Override
            public void onFinish() {
                //TODO - we ran out of time, game over
            }
        }.start();
    }

    public void firstChoiceMade(View view) {
        if (this.result_choices[3] == 1) {
            updateScore();
        }
        timer.cancel();

    }

    public void secondChoiceMade(View view) {
        if (this.result_choices[3] == 2) {
            System.out.println("MATCH!");
        } else {
            System.out.println("FAIL!");
        }
    }

    public void thirdChoiceMade(View view) {
        if (this.result_choices[3] == 3) {
            System.out.println("MATCH!");
        } else {
            System.out.println("FAIL!");
        }
    }


    private void updateScore() {
        score = score + remaining_millis;
    }
    //If Back arrow clicked, go back
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO - Should we just pause the level here and resume from MainScreen?
        finish();
        return true;
    }


}
