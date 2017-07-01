package afte9.com.fastmath10;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import afte9.com.fastmath10.R;

public class EndGameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_screen);

        Bundle b = getIntent().getExtras();

        int s, l, r;
        s = b.getInt("score");
        l = b.getInt("level");
        r = b.getInt("rank");
        ((TextView) findViewById(R.id.textView_endgame_summary)).setText(String.format(getString(R.string.end_game_summary_format), s, l, r));
    }

    public void saveClicked (View view) {
        //TODO - database call to save the name, score and achieved level
        finish();
    }
    public void cancelClicked (View view) {
        //Done here, back to main screen
        finish();
    }
}
