package assignment5.dungeon;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DungeonThread extends Thread {
	final Board board;
	final SurfaceHolder sh;
	boolean gamestillrunning = true;
	
	public DungeonThread(SurfaceHolder sh, Board board) {
		this.board = board;
		this.sh = sh;
	}
	
	@Override
	public void run() {			
		while(gamestillrunning) {
			
    		board.updateWorld();

			Canvas c=sh.lockCanvas();
			
			if (c!=null) {
				board.draw(c);
				sh.unlockCanvasAndPost(c);
			} else {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}		
	}
	
	// Terminating the game
	public synchronized void stopGame() {
		gamestillrunning = false;
	}
}
