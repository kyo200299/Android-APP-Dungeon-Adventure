package assignment5.dungeon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DungeonView extends SurfaceView implements SurfaceHolder.Callback {
	private Board board;
	private DungeonThread dt;
	
	// Control Interface
	Bitmap dpad=BitmapFactory.decodeResource(getResources(), R.drawable.dpad);
	Bitmap dpadInvert=BitmapFactory.decodeResource(getResources(), R.drawable.dpadinvert);
	Bitmap buttonB=BitmapFactory.decodeResource(getResources(), R.drawable.buttonb);
	Bitmap buttonHelp=BitmapFactory.decodeResource(getResources(), R.drawable.buttonhelp);
	Bitmap guide=BitmapFactory.decodeResource(getResources(), R.drawable.guide);
	
	// Back Ground
	Bitmap grass=BitmapFactory.decodeResource(getResources(), R.drawable.grass);
	Bitmap floor=BitmapFactory.decodeResource(getResources(), R.drawable.floor);
	Bitmap mountain=BitmapFactory.decodeResource(getResources(), R.drawable.mountain);
	Bitmap rock=BitmapFactory.decodeResource(getResources(), R.drawable.rock);
	Bitmap gate=BitmapFactory.decodeResource(getResources(), R.drawable.gate);
	
	// Item
	Bitmap potion=BitmapFactory.decodeResource(getResources(), R.drawable.potion);
	Bitmap ruby=BitmapFactory.decodeResource(getResources(), R.drawable.ruby);
	
	// Link Stand
	Bitmap linkSU=BitmapFactory.decodeResource(getResources(), R.drawable.link_up_stand);
	Bitmap linkSD=BitmapFactory.decodeResource(getResources(), R.drawable.link_down_stand);
	Bitmap linkSL=BitmapFactory.decodeResource(getResources(), R.drawable.link_left_stand);
	Bitmap linkSR=BitmapFactory.decodeResource(getResources(), R.drawable.link_right_stand);
	// Link Walk
	Bitmap linkWU1=BitmapFactory.decodeResource(getResources(), R.drawable.link_up_walk1);
	Bitmap linkWU2=BitmapFactory.decodeResource(getResources(), R.drawable.link_up_walk2);
	Bitmap linkWD1=BitmapFactory.decodeResource(getResources(), R.drawable.link_down_walk1);
	Bitmap linkWD2=BitmapFactory.decodeResource(getResources(), R.drawable.link_down_walk2);
	Bitmap linkWL1=BitmapFactory.decodeResource(getResources(), R.drawable.link_left_walk1);
	Bitmap linkWL2=BitmapFactory.decodeResource(getResources(), R.drawable.link_left_walk2);
	Bitmap linkWR1=BitmapFactory.decodeResource(getResources(), R.drawable.link_right_walk1);
	Bitmap linkWR2=BitmapFactory.decodeResource(getResources(), R.drawable.link_right_walk2);
	// Link Attack
	Bitmap linkAU=BitmapFactory.decodeResource(getResources(), R.drawable.link_up_attack);
	Bitmap linkAD=BitmapFactory.decodeResource(getResources(), R.drawable.link_down_attack);
	Bitmap linkAL=BitmapFactory.decodeResource(getResources(), R.drawable.link_left_attack);
	Bitmap linkAR=BitmapFactory.decodeResource(getResources(), R.drawable.link_right_attack);

	// Boss Walk and Attack
	Bitmap bossW1=BitmapFactory.decodeResource(getResources(), R.drawable.boss_walk1);
	Bitmap bossW2=BitmapFactory.decodeResource(getResources(), R.drawable.boss_walk2);
	Bitmap bossA=BitmapFactory.decodeResource(getResources(), R.drawable.boss_attack);
	
	public DungeonView(Context context) {
		super(context);
		this.getHolder().addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		System.out.println("Surface Changed!");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		board = new Board(this);
		setOnTouchListener(board);	
		dt = new DungeonThread(this.getHolder(), board);
		dt.start();
		System.out.println("Dungeon Thread Started!");		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {		
		System.out.println("Surface Destroyed!");
		dt.stopGame();
		try {
			dt.join(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// Referred from example
			if (dt.isAlive()) {
				System.err.println("Error: thead did not stop in time");
			}
		}		
	}

}
