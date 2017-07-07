package com.afte9.fastmath10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private ScoreDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateUi();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset_scores:
                resetScores();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Reset scores database
    private void resetScores() {
        mDbHelper = new ScoreDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(ScoreDbHelper.TABLE_NAME, null, null);
        ((TextView) findViewById(R.id.textViewScores)).setText(getText(R.string.no_scores));
    }

    //Populate the main screen UI
    private void populateUi() {
        mDbHelper = new ScoreDbHelper(getApplicationContext());

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ScoreDbHelper.COLUMN_NAME_NAME,
                ScoreDbHelper.COLUMN_NAME_SCORE,
                ScoreDbHelper.COLUMN_NAME_LEVEL
        };
        String sortOrder = ScoreDbHelper.COLUMN_NAME_SCORE + " DESC";
        Cursor cursor = db.query(
                ScoreDbHelper.TABLE_NAME,
                projection,
                null, //WHERE
                null, //WHERE values
                null, //don't group rows
                null, //don't filter rows
                sortOrder
        );
        String tableString = new String();
        boolean gotScores = false;
        while (cursor.moveToNext()) {
            gotScores = true;
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ScoreDbHelper.COLUMN_NAME_NAME));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreDbHelper.COLUMN_NAME_SCORE));
            int level = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreDbHelper.COLUMN_NAME_LEVEL));
            tableString = tableString.concat(String.format(getString(R.string.table_row_format), score, level, name));
        }
        if (gotScores) {
            ((TextView) findViewById(R.id.textViewScores)).setText(tableString);
        }
        cursor.close();
    }

    public void launchGame(View view) {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}
