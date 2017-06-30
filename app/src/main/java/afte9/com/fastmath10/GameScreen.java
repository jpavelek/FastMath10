package afte9.com.fastmath10;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameScreen extends AppCompatActivity {

    private int score;
    private int remaining_millis;
    private int level_score;
    private int level_target;
    private int level_move;
    private int level_score_increment;
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
        level_score = 0;
        level_move = 1;

        newMove();
    }

    public void firstChoiceMade(View view) {
        level_move++;
        if (this.result_choices[3] == 1) {
            updateScore();
        }
        timer.cancel();
        newMove();
    }

    public void secondChoiceMade(View view) {
        level_move++;
        if (this.result_choices[3] == 2) {
            updateScore();
        }
        timer.cancel();
        newMove();
    }

    public void thirdChoiceMade(View view) {
        level_move++;
        if (this.result_choices[3] == 3) {
            updateScore();
        }
        timer.cancel();
        newMove();
    }

    private void newMove() {
        if (level_move >= task_provider.ROUNDS) {
            //This was last move available, sum up and see how we did
        } else {
            //Start new move
            result = task_provider.getTaskResult();
            visual = task_provider.getTaskVisual();
            result_choices = task_provider.getResultChoices();
            level_score_increment = task_provider.getLevelScoreIncrement();
            level_target = task_provider.getLevelScoreTarget();

            ((Button) findViewById(R.id.buttonChoiceOne)).setText(String.valueOf(result_choices[0]));
            ((Button) findViewById(R.id.buttonChoiceTwo)).setText(String.valueOf(result_choices[1]));
            ((Button) findViewById(R.id.buttonChoiceThree)).setText(String.valueOf(result_choices[2]));

            timer = new CountDownTimer(task_provider.getTimeout(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
                    if (bar == null) return;
                    bar.setProgress((int) millisUntilFinished / 1000);
                    remaining_millis = (int) millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    //TODO - we ran out of time, move over
                    level_move++;
                }
            }.start();

        }

    }

    private void updateScore() {
        score = score + task_provider.getLevelScoreIncrement() + remaining_millis/1000;
        ((TextView) findViewById(R.id.textView_Score)).setText(String.format("Score :%04d", score));
    }
    //If Back arrow clicked, go back
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO - Should we just pause the level here and resume from MainScreen?
        finish();
        return true;
    }


}
