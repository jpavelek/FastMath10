package afte9.com.fastmath10;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import afte9.com.fastmath10.R;

public class EndGameScreen extends AppCompatActivity {
    private ScoreDbHelper mDbHelper;
    private int bScore, bLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_screen);

        Bundle b = getIntent().getExtras();
        mDbHelper = new ScoreDbHelper(getApplicationContext());
        bScore = b.getInt("score");
        bLevel = b.getInt("level");

        ((TextView) findViewById(R.id.textView_endgame_summary)).setText(String.format(getString(R.string.end_game_summary_format), bScore, bLevel));
    }

    public void saveClicked (View view) {
        //TODO - database call to save the name, score and achieved level
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String name = ((TextView)findViewById(R.id.editText_name)).getText().toString();
        if (!name.isEmpty()) {
            values.put(ScoreDbHelper.COLUMN_NAME_NAME, name);
            values.put(ScoreDbHelper.COLUMN_NAME_SCORE, bScore);
            values.put(ScoreDbHelper.COLUMN_NAME_LEVEL, bLevel);

            long newRowId = db.insert(ScoreDbHelper.TABLE_NAME, null, values);
        }
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void cancelClicked (View view) {
        //Done here, back to main screen
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
