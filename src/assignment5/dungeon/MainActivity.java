package assignment5.dungeon;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private static boolean flag;
	static int level;
	Button level1, level2, play;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		goToMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == play.getId())
			level = 1;
		else if (v.getId() == level1.getId())
			level = 1;
		else if (v.getId() == level2.getId())
			level = 2;

		DungeonView dv = new DungeonView(getBaseContext());
		setContentView(dv);
		flag = false;
	}
	
	@Override
	public void onBackPressed() {
		if (flag)
			this.finish();		
		goToMenu();
	}	
	
	// Going back to main menu
	public void goToMenu() {
		setContentView(R.layout.activity_main);
		play = (Button)findViewById(R.id.play);
		play.setOnClickListener(this);
		level1 = (Button)findViewById(R.id.level1);
		level1.setOnClickListener(this);
		level2 = (Button)findViewById(R.id.level2);
		level2.setOnClickListener(this);
		flag = true;
	}
}
