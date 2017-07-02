package afte9.com.fastmath10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ScoreDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new ScoreDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        if (mDbHelper.checkDb(db)) {
            String[] projection = {
                    ScoreDbHelper.COLUMN_ID,
                    ScoreDbHelper.COLUMN_NAME_NAME,
                    ScoreDbHelper.COLUMN_NAME_SCORE,
                    ScoreDbHelper.COLUMN_NAME_LEVEL
            };
            String sortOrder = ScoreDbHelper.COLUMN_NAME_SCORE + " DESC";
            Cursor cursor = db.query(
                    ScoreDbHelper.TABLE_NAME,
                    null, //WHERE
                    null, //WHERE values
                    projection,
                    null, //don't group rows
                    null, //don't filter rows
                    sortOrder
            );
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ScoreDbHelper.COLUMN_NAME_NAME));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreDbHelper.COLUMN_NAME_SCORE));
                int level = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreDbHelper.COLUMN_NAME_LEVEL));
                System.out.println("Got name:" + name + " with score " + score + "at level " + level);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_reset_scores:
                //TODO - reset scores
                System.out.println("Reset scores ... not implemented");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void launchGame(View view) {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}
