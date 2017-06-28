package afte9.com.fastmath10;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class GameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        //Enable "BAck" arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //If Back arrow clicked, go back
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
