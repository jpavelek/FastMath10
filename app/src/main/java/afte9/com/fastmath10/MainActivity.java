package afte9.com.fastmath10;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ScoreDbHelper mDbHelper = new ScoreDbHelper(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

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
