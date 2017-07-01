package afte9.com.fastmath10;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        dimLevelColors();
        ((TextView) findViewById(R.id.textView_level)).setText(String.format("Level :%2d",task_provider.getTaskLevel()));
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
                //TODO - show some dialog with summary and Continue button first
                updateLevelColors();
                task_provider.increaseLevel();
                ((TextView) findViewById(R.id.textView_level)).setText(String.format("Level :%2d",task_provider.getTaskLevel()));
                level_move = 1;
                level_score = 0;
                newMove();
            } else {
                //We did not make the level, game ends. Log your name for the score and go back to main
                Intent intent = new Intent(this, EndGameScreen.class);
                intent.putExtra("score", score);
                intent.putExtra("level", task_provider.getTaskLevel());
                intent.putExtra("rank", 11); //TODO - fix this and provide real rank for this score!!!
                startActivity(intent);
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

    private void dimLevelColors() {
        findViewById(R.id.imageView_l01).setAlpha((float)0.15);
        findViewById(R.id.imageView_l02).setAlpha((float)0.15);
        findViewById(R.id.imageView_l03).setAlpha((float)0.15);
        findViewById(R.id.imageView_l04).setAlpha((float)0.15);
        findViewById(R.id.imageView_l05).setAlpha((float)0.15);
        findViewById(R.id.imageView_l06).setAlpha((float)0.15);
        findViewById(R.id.imageView_l07).setAlpha((float)0.15);
        findViewById(R.id.imageView_l08).setAlpha((float)0.15);
        findViewById(R.id.imageView_l09).setAlpha((float)0.15);
        findViewById(R.id.imageView_l10).setAlpha((float)0.15);
        findViewById(R.id.imageView_l11).setAlpha((float)0.15);
        findViewById(R.id.imageView_l12).setAlpha((float)0.15);
        findViewById(R.id.imageView_l13).setAlpha((float)0.15);
        findViewById(R.id.imageView_l14).setAlpha((float)0.15);
        findViewById(R.id.imageView_l15).setAlpha((float)0.15);
        findViewById(R.id.imageView_l16).setAlpha((float)0.15);
        findViewById(R.id.imageView_l17).setAlpha((float)0.15);
        findViewById(R.id.imageView_l18).setAlpha((float)0.15);
        findViewById(R.id.imageView_l19).setAlpha((float)0.15);
        findViewById(R.id.imageView_l20).setAlpha((float)0.15);
        findViewById(R.id.imageView_l21).setAlpha((float)0.15);
    }

    private void updateLevelColors() {
        switch (task_provider.getTaskLevel()) {
            case 1:
                findViewById(R.id.imageView_l01).setAlpha((float)1.0);
                break;
            case 2:
                findViewById(R.id.imageView_l02).setAlpha((float)1.0);
                break;
            case 3:
                findViewById(R.id.imageView_l03).setAlpha((float)1.0);
                break;
            case 4:
                findViewById(R.id.imageView_l04).setAlpha((float)1.0);
                break;
            case 5:
                findViewById(R.id.imageView_l05).setAlpha((float)1.0);
                break;
            case 6:
                findViewById(R.id.imageView_l06).setAlpha((float)1.0);
                break;
            case 7:
                findViewById(R.id.imageView_l07).setAlpha((float)1.0);
                break;
            case 8:
                findViewById(R.id.imageView_l08).setAlpha((float)1.0);
                break;
            case 9:
                findViewById(R.id.imageView_l09).setAlpha((float)1.0);
                break;
            case 10:
                findViewById(R.id.imageView_l10).setAlpha((float)1.0);
                break;
            case 11:
                findViewById(R.id.imageView_l11).setAlpha((float)1.0);
                break;
            case 12:
                findViewById(R.id.imageView_l12).setAlpha((float)1.0);
                break;
            case 13:
                findViewById(R.id.imageView_l13).setAlpha((float)1.0);
                break;
            case 14:
                findViewById(R.id.imageView_l14).setAlpha((float)1.0);
                break;
            case 15:
                findViewById(R.id.imageView_l15).setAlpha((float)1.0);
                break;
            case 16:
                findViewById(R.id.imageView_l16).setAlpha((float)1.0);
                break;
            case 17:
                findViewById(R.id.imageView_l17).setAlpha((float)1.0);
                break;
            case 18:
                findViewById(R.id.imageView_l18).setAlpha((float)1.0);
                break;
            case 19:
                findViewById(R.id.imageView_l19).setAlpha((float)1.0);
                break;
            case 20:
                findViewById(R.id.imageView_l20).setAlpha((float)1.0);
                break;
            case 21:
                findViewById(R.id.imageView_l21).setAlpha((float)1.0);
                break;
        }
    }


    //If Back arrow clicked, go back
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }


}
