package com.afte9.fastmath10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    private ScoreDbHelper mDbHelper;
    private String[] FROM_COLUMNS = {ScoreDbHelper.COLUMN_NAME_SCORE, ScoreDbHelper.COLUMN_NAME_LEVEL, ScoreDbHelper.COLUMN_NAME_NAME};
    private int[] TO_VIEWS = {R.id.textView_score, R.id.textView_level, R.id.textView_name};
    private String[] PROJECTION = {
            ScoreDbHelper.COLUMN_ID,
            ScoreDbHelper.COLUMN_NAME_NAME,
            ScoreDbHelper.COLUMN_NAME_SCORE,
            ScoreDbHelper.COLUMN_NAME_LEVEL
    };
    private String SORT_ORDER = ScoreDbHelper.COLUMN_NAME_SCORE + " DESC";
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new ScoreDbHelper(getApplicationContext());

        populateUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCursor();
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged();
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
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(ScoreDbHelper.TABLE_NAME, null, null);
        updateCursor();
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged(); //This should update the ListView with new database
    }

    //Populate the cursor with latest data
    private void updateCursor() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        if (cursor != null) cursor.close();

        cursor = db.query(
                ScoreDbHelper.TABLE_NAME,
                PROJECTION,
                null, //WHERE
                null, //WHERE values
                null, //don't group rows
                null, //don't filter rows
                SORT_ORDER
        );
    }
    //Populate the main screen UI
    private void populateUi() {
        updateCursor();

        adapter = new SimpleCursorAdapter(this, R.layout.scores_list_layout, cursor, FROM_COLUMNS , TO_VIEWS, 0);
        ListView listView = (ListView)findViewById(R.id.listView_scores);
        listView.setEmptyView(findViewById(R.id.textViewScores));
        listView.setAdapter(adapter);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bmp);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        getWindow().getDecorView().setBackground(bitmapDrawable);
    }

    public void launchGame(View view) {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}
