package com.afte9.fastmath10;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {

    private int total_score;
    private int remaining_millis;
    private int level_score;
    private int level_move;
    private TaskProvider task_provider = TaskProvider.getInstance();
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);


        //Background
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bmp);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        getWindow().getDecorView().setBackground(bitmapDrawable);

        //Reset game from start
        total_score = 0;
        level_score = 0;
        level_move = 1;
        task_provider.reset();
        dimLevelColors();
        String newTitle = String.format(getString(R.string.level_title_progress_format), getString(R.string.app_name), task_provider.getTaskLevel());
        getSupportActionBar().setTitle(newTitle);
        newMove();
    }

    public void choiceButtonsClicked(View view) {
        //One of the three choice buttons were clicked. Check if that was the right one and respond
        timer.cancel();
        ((Button)findViewById(R.id.buttonChoiceOne)).setClickable(false);
        ((Button)findViewById(R.id.buttonChoiceTwo)).setClickable(false);
        ((Button)findViewById(R.id.buttonChoiceThree)).setClickable(false);
        level_move++;
        if ((Integer.parseInt(((Button) view).getText().toString())) == task_provider.getTaskResult()) {
            updateScore(true);
        } else {
            System.out.println("*** Wrong reply to " + task_provider.getTaskVisual() + ". Replied " + (((Button) view).getText().toString()) + " but correct is " + task_provider.getTaskResult());
            blinkScreen();
        }
        newMove();
    }

    private void blinkScreen() {
        ((View) findViewById(R.id.layout_top)).setAlpha((float)0.2);

        CountDownTimer t = new CountDownTimer(300, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                ((View) findViewById(R.id.layout_top)).setAlpha((float)1.0);
            }
        }.start();
    }

    private void newMove() {
        updateScore(false);
        task_provider.getNextTask();
        if (level_move >= TaskProvider.ROUNDS) {
            //This was last move available at this level
            if (level_score > task_provider.getLevelScoreTarget()) {
                //Passed the level, moving up
                upLevel();
            } else {
                //That was not enough to pass the level, game ends
                showEndgameScreen();
            }
        } else {
            //Still have moves left on this level, start new test
            prepNewMove();
        }
        ((Button)findViewById(R.id.buttonChoiceOne)).setClickable(true);
        ((Button)findViewById(R.id.buttonChoiceTwo)).setClickable(true);
        ((Button)findViewById(R.id.buttonChoiceThree)).setClickable(true);
    }

    private void upLevel() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        if (task_provider.getTaskLevel() == (TaskProvider.TaskLevels.NINE.ordinal() + 1)) {
            //We are already at the last level, end the game
            builder.setTitle(getString(R.string.dialog_gameover_title))
                    .setMessage(String.format(getString(R.string.dialog_message_format), level_score, total_score))
                    .setPositiveButton(getString(R.string.button_continue), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            showEndgameScreen();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            //Still levels to go, show resume and go to next level
            builder.setTitle(getString(R.string.dialog_title))
                    .setMessage(String.format(getString(R.string.dialog_message_format), level_score, total_score))
                    .setPositiveButton(getString(R.string.button_continue), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            updateLevelColors();
                            task_provider.increaseLevel();
                            String newTitle = String.format(getString(R.string.level_title_progress_format), getString(R.string.app_name), task_provider.getTaskLevel());
                            getSupportActionBar().setTitle(newTitle);
                            level_move = 1;
                            level_score = 0;
                            newMove();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void showEndgameScreen () {
        //We did not make the level, game ends. Log your name for the score and go back to main
        Intent intent = new Intent(getApplicationContext(), EndGameScreen.class);
        intent.putExtra(ScoreDbHelper.COLUMN_NAME_SCORE, total_score);
        intent.putExtra(ScoreDbHelper.COLUMN_NAME_LEVEL, task_provider.getTaskLevel());
        startActivity(intent);
        finish();
    }

    private void prepNewMove() {
        ((Button) findViewById(R.id.buttonChoiceOne)).setText(String.valueOf(task_provider.getResultChoices()[0]));
        ((Button) findViewById(R.id.buttonChoiceTwo)).setText(String.valueOf(task_provider.getResultChoices()[1]));
        ((Button) findViewById(R.id.buttonChoiceThree)).setText(String.valueOf(task_provider.getResultChoices()[2]));
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

    private void updateScore(boolean hit) {
        if (hit) {
            int scoreIncrement = task_provider.getLevelScoreIncrement() + remaining_millis / 1000;
            total_score = total_score + scoreIncrement;
            level_score = level_score + scoreIncrement;
        }
        ((TextView) findViewById(R.id.textView_Score)).setText(String.format(getString(R.string.score_formatted), total_score));

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
            default:
                break;
        }
    }
}