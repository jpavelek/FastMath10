package com.afte9.fastmath10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private ScoreDbHelper mDbHelper;
    private String[] fromColumns = {ScoreDbHelper.COLUMN_NAME_SCORE, ScoreDbHelper.COLUMN_NAME_LEVEL, ScoreDbHelper.COLUMN_NAME_NAME};
    private int[] toViews = {R.id.textView_score, R.id.textView_level, R.id.textView_name};
    private SimpleCursorAdapter adapter;

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
                ScoreDbHelper.COLUMN_ID,
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

        adapter = new SimpleCursorAdapter(this, R.layout.scores_list_layout, cursor,fromColumns , toViews, 0);
        ListView listView = (ListView)findViewById(R.id.listView_scores);
        listView.setAdapter(adapter);

        //FIXME - I should close the cursor, but that crashes the app //cursor.close();
    }

    public void launchGame(View view) {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}
