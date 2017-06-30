package afte9.com.fastmath10;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

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
        task_provider.reset();

        ((ProgressBar) findViewById(R.id.progressBar_levelProgress)).setMax(TaskProvider.ROUNDS);
        ((ProgressBar) findViewById(R.id.progressBar_levelScoreProgress)).setMax(task_provider.getLevelScoreTarget());

        newMove();
    }

    public void firstChoiceMade(View view) {
        level_move++;
        if (this.result_choices[3] == 1) {
            updateScore(true);
        }
        timer.cancel();
        newMove();
    }

    public void secondChoiceMade(View view) {
        level_move++;
        if (this.result_choices[3] == 2) {
            updateScore(true);
        }
        timer.cancel();
        newMove();
    }

    public void thirdChoiceMade(View view) {
        level_move++;
        if (this.result_choices[3] == 3) {
            updateScore(true);
        }
        timer.cancel();
        newMove();
    }

    private void newMove() {
        updateScore(false);
        if (level_move >= TaskProvider.ROUNDS) {
            //This was last move available at this level, sum up and see how we did
            if (level_score > level_target) {
                //TODO - show some dialog with summary and Continue button
                task_provider.increaseLevel();
                level_move = 1;
                level_score = 0;
                newMove();
            } else {
                //TODO - Not enough score, end game
                finish();
            }

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
            ((ProgressBar) findViewById(R.id.progressBar_time)).setMax(task_provider.getTimeout()/1000);
            ((ProgressBar) findViewById(R.id.progressBar_time)).setProgress(task_provider.getTimeout()/1000);
            ((TextView) findViewById(R.id.textView_test)).setText(task_provider.getTaskVisual());
            ((ProgressBar) findViewById(R.id.progressBar_levelProgress)).setProgress(level_move);
            ((ProgressBar) findViewById(R.id.progressBar_levelScoreProgress)).setProgress(level_score);

            timer = new CountDownTimer(task_provider.getTimeout(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    ((ProgressBar) findViewById(R.id.progressBar_time)).setProgress((int)millisUntilFinished / 1000 );
                    remaining_millis = (int) millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    // We ran out of time, move over
                    ((ProgressBar) findViewById(R.id.progressBar_time)).setProgress(0);
                    level_move++;
                    newMove();
                }
            }.start();
        }
    }

    private void updateScore(boolean hit) {
        if (hit) {
            int scoreIncrement = task_provider.getLevelScoreIncrement() + remaining_millis / 1000;
            score = score + scoreIncrement;
            level_score = level_score + scoreIncrement;
        }
        ((TextView) findViewById(R.id.textView_Score)).setText(String.format("Score :%4d", score));

    }
    //If Back arrow clicked, go back
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }


}
