package assignment5.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Board implements OnTouchListener {
	private DungeonView dv;
	private Link link;
	private Level lv;
	private final int MAX_X, MAX_Y, MAX_TILE_X, MAX_TILE_Y;
	private final static int SCREEN_TILE_X = 12, SCREEN_TILE_Y = 12, multiplier = 3, velocity = 30;
	private int xBoard, yBoard, level, score, hp, lifeCount, rubyCount, orientation, tileDimension, xScreen, yScreen, buttonDimension;
	private int collisionType = 0, touchx = -1, touchy = -1;
	private boolean gateLocked, help, move, goLeft, goRight, goUp, goDown, linkAtk, dead;
	private int placement[][];
	private Tile tile[][];
	private Paint textPaint = new Paint();
	private Paint gameover = new Paint();
	private Rect dst = new Rect();			// General use
	
	public Board(DungeonView dungeonView) {
		this.dv = dungeonView;
		this.level = MainActivity.level;
		tileDimension = dv.getHeight() / SCREEN_TILE_Y;
		MAX_TILE_X = SCREEN_TILE_X * multiplier;
		MAX_TILE_Y = SCREEN_TILE_Y * multiplier;
		MAX_X = tileDimension * MAX_TILE_X;
		MAX_Y = tileDimension * MAX_TILE_Y;
		xScreen = dv.getWidth();
		yScreen = dv.getHeight();
		buttonDimension = yScreen/8;  // Button Dimension
		//Display values for debugging
		System.out.println("Screen Width   = " + dv.getWidth());
		System.out.println("Screen Height  = " + dv.getHeight());
		System.out.println("Max Width   = " + MAX_X);
		System.out.println("Max Height  = " + MAX_Y);
		System.out.println("Tile Dimension = " + tileDimension);
		//
		
		lifeCount = 5;
		hp = 300;
		score = 0;
		help = false;
		dead = false;
		rubyCount = 0;
		
		levelInitialize();
		System.out.println("Game Board Built!");
	}
	
	// Initialization of the level
	public void levelInitialize() {
		placement = new int[MAX_TILE_Y][MAX_TILE_X];
		lv = new Level(level, MAX_TILE_X, MAX_TILE_Y);
		lv.levelBuild(placement);
		tile = new Tile[MAX_TILE_Y][MAX_TILE_X];		
		// Create new tiles for the new game
		for (int row = 0; row < MAX_TILE_Y; row++) {
			for (int col = 0; col < MAX_TILE_X; col++) {
				tile[row][col] = new Tile(this, level, row, col, tileDimension);
				// System output for checking placement in each position
				//System.out.println("Position("+row+", "+col+"): " + placement[row][col]);
			}
		}
		
		xBoard = 8*tileDimension;
		yBoard = 24*tileDimension;				
		gateLocked = true;
		move = false;
		goLeft = true;
		goRight = true;
		goUp = true;
		goDown = true;
		linkAtk = false;		
		orientation = 1;		// Facing up at beginning
		link = new Link(tileDimension);
		
		// Converting from 2D array of integers to storing "DrawableObject" in Tile
		for (int row = 0; row < MAX_TILE_Y; row++) {
			for (int col = 0; col < MAX_TILE_X; col++) {
				// 1=Mountain 2=Rock 3=Gate 4=Ruby 5=Potion  6=Boss
				switch(placement[row][col]) {
				case 1:
					DrawableObject mountain = new Block(tile[row][col], placement[row][col], tileDimension);
					tile[row][col].setDrawable(mountain);
					break;
				case 2:
					DrawableObject rock = new Block(tile[row][col], placement[row][col], tileDimension);
					tile[row][col].setDrawable(rock);
					break;
				case 3:
					DrawableObject gate = new Item(tile[row][col], placement[row][col], tileDimension);
					tile[row][col].setDrawable(gate);
					break;
				case 4:
					DrawableObject ruby = new Item(tile[row][col], placement[row][col], tileDimension);
					tile[row][col].setDrawable(ruby);
					break;
				case 5:
					DrawableObject potion = new Item(tile[row][col], placement[row][col], tileDimension);
					tile[row][col].setDrawable(potion);
					break;
				case 6:					
					DrawableObject boss = new Boss(tile[row][col], tileDimension);
					tile[row][col].setDrawable(boss);
					break;
				}			
			}
		}		

		System.out.println("Game Initialized!");
	}
	
	public void draw(Canvas c) {
		c.drawColor(Color.WHITE);
		// Drawing background, has to separate from objects to avoid overlapping.
		for (int row = 0; row < MAX_TILE_Y; row++) {
			for (int col = 0; col < MAX_TILE_X; col++) {
				if (isOnScreen(tile[row][col]))
					tile[row][col].drawBackground(c, dv);
			}
		}
		// Drawing objects on screen
		for (int row = 0; row < MAX_TILE_Y; row++) {
			for (int col = 0; col < MAX_TILE_X; col++) {
				if (isOnScreen(tile[row][col]))
					tile[row][col].draw(c, dv);
			}
		}
		
		// Draw Link
		link.draw(c, dv, move, linkAtk, orientation);		
		// Game Over message
		gameover.setColor(Color.RED);
		gameover.setTextSize(100);
		if (lifeCount == 0 && dead)
			c.drawText("GAME OVER !!", 6*tileDimension, 6*tileDimension, gameover);
		
		// User Interfaces
		textPaint.setColor(Color.LTGRAY);		
		textPaint.setTextSize(50);
		c.drawText("Life x"+ lifeCount, tileDimension*4, tileDimension*2/3, textPaint);
		c.drawText("Score : "+ score, tileDimension*8, tileDimension*2/3, textPaint);
		c.drawText("Level "+ level, tileDimension*15, tileDimension*2/3, textPaint);
		textPaint.setTextSize(40);
		textPaint.setColor(Color.RED);
		c.drawText("Link HP { " + hp + " }", buttonDimension*5, buttonDimension*7+25, textPaint);
		textPaint.setColor(Color.BLUE);
		c.drawText("Ruby      {   " + rubyCount + "   }", buttonDimension*5, buttonDimension*8-20, textPaint);
		dst.set(0, buttonDimension*5, buttonDimension*3, buttonDimension*8);
		if (level == 1)
			c.drawBitmap(dv.dpad, null, dst, null);
		else if (level == 2)
			c.drawBitmap(dv.dpadInvert, null, dst, null);
		dst.set(buttonDimension*11, buttonDimension*6, buttonDimension*13, buttonDimension*8);
		c.drawBitmap(dv.buttonB, null, dst, null);
		if (help) {
		dst.set(0, 0, xScreen, yScreen);
		c.drawBitmap(dv.guide, null, dst, null);
		}
		dst.set(0, 0, buttonDimension, buttonDimension);
		c.drawBitmap(dv.buttonHelp, null, dst, null);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {			    		    	
	    case MotionEvent.ACTION_MOVE:
	    case MotionEvent.ACTION_DOWN:
	    	synchronized (this) {
	    		touchx = (int)event.getX();
	    		touchy = (int)event.getY();
	    		
	    		if (!dead && (touchx >= 0) && (touchy >= buttonDimension*5) &&
	    				(touchx <= buttonDimension*3) && (touchy <= buttonDimension*8))
	    			move = true;	    		
	    		if ((touchx >= 0) && (touchy >= 0) &&
	    				(touchx <= buttonDimension) && (touchy <= buttonDimension)) {
	    			if (!help)
	    				help = true;
	    			else if (help)
	    				help = false;
	    		}	    		
	    		if (!dead && (touchx >= buttonDimension*11) && (touchy >= buttonDimension*6) &&
	    				(touchx <= buttonDimension*13) && (touchy <= buttonDimension*8))
	    			linkAtk = true;	    			
	    	}	    	
	    	return true;
	    case MotionEvent.ACTION_UP:
	    	move = false;
	    	linkAtk = false;
	    	return true;		    	
		}		
		return false;
	}
	
	public void updateWorld() {
		// Check life and other information due to collision
		synchronized (this) {
			if (!dead) {
				checkLife();
				collisionType = 0;
			}
		}
		// Movement of Link
		// Orientation: 1=up 2=down 3=left 4=right
		if (move && !dead && !help) {
			if ((touchx >= buttonDimension) && (touchy >= buttonDimension*5) &&
    			(touchx <= buttonDimension*2) && (touchy <= buttonDimension*6)) {				
				orientation = 1; // Up
				if (goUp) {
					if(Link.yLink > yScreen*5/12) {	// When Link is free to move within range
						Link.goUp();
					} else if (yBoard > 0)			// When board is not on map edge
						yBoard = yBoard - velocity;
					else if (Link.yLink > 0)		// When board is on map edge
						Link.goUp();
				}
			}
		
			if ((touchx >= buttonDimension) && (touchy >= buttonDimension*7) &&
				(touchx <= buttonDimension*2) && (touchy <= buttonDimension*8)) {
				orientation = 2; // Down
				if (goDown) {
					if(Link.yLink < yScreen*7/12) {
						Link.goDown();
					} else if (yBoard < (MAX_Y - yScreen - velocity))
						yBoard = yBoard + velocity;
					else if (Link.yLink < (yScreen - velocity))
						Link.goDown();
				}
			}
			
			if ((touchx >= 0) && (touchy >= buttonDimension*6) &&
				(touchx <= buttonDimension) && (touchy <= buttonDimension*7)) {
				orientation = 3; // Left
				if (goLeft) {
					if(Link.xLink > xScreen*7/16) {
						Link.goLeft();
					} else if (xBoard > 0)
						xBoard = xBoard - velocity;
					else if (Link.xLink > 0)
						Link.goLeft();
				}
			}
			
			if ((touchx >= buttonDimension*2) && (touchy >= buttonDimension*6) &&
				(touchx <= buttonDimension*3) && (touchy <= buttonDimension*7)) {
				orientation = 4; // Right
				if (goRight) {
					if(Link.xLink < xScreen*9/16) {
						Link.goRight();
					} else if (xBoard < (MAX_X - xScreen - velocity))
						xBoard = xBoard + velocity;
					else if (Link.xLink < (xScreen - velocity))
						Link.goRight();
				}
			}
		}
		
		if (!help) {
			// Move objects on tiles
			for (int row = 0; row < MAX_TILE_Y; row++) {
				for (int col = 0; col < MAX_TILE_X; col++) {
					tile[row][col].move();
				}
			}
			// Check if Link collide with any object in tiles, return collision type
			for (int row = 0; row < MAX_TILE_Y; row++) {
				for (int col = 0; col < MAX_TILE_X; col++) {
					if (collisionType == 0)
						collisionType = tile[row][col].checkCollision(collisionType, orientation, linkAtk, gateLocked);
				}
			}
			
			// Debug System Output
			//System.out.println("Collision Type	: " + collisionType);			
			//System.out.println("Flag 'goUP'		: " + goUP);
			//System.out.println("Flag 'goDown'		: " + goDown);		
			//System.out.println("Flag 'goLeft'		: "	+ goLeft);
			//System.out.println("Flag 'goRight'	: " + goRight);
			//System.out.println("Flag 'gateLocked'	: " + gateLocked);
		}
	}
	// Referred from discussion example, get position of game board
	public int getX() {
		return xBoard;
	}	
	public int getY() {
		return yBoard;
	}
	
	// Referred from discussion example, detecting if the object is on the screen
	public boolean isOnScreen(Tile tile) {
		if (tile.currentX() + tileDimension <= 0)
			return false;
		if (tile.currentX() > dv.getWidth())
			return false;
		if (tile.currentY() + tileDimension <= 0)
			return false;
		if (tile.currentY() > dv.getHeight())
			return false;
		return true;
	}
	
	// Check life and other information due to collision
	private void checkLife() {
		// Collision Type of Blocks	: 1=Top		2=Bottom	3=Left		4=Right
		// Collision Type of Items	: 5=Gate	6=Ruby		7=Potion
		// Collision Type of Boss	: 8=Boss Attack			9=Boss Died
		switch(collisionType) {
		case 0:
			goLeft = true;
			goRight = true;
			goUp = true;
			goDown = true;
			break;
		case 1:
			goLeft = true;
			goRight = true;
			goUp = true;
			goDown = false;
			break;
		case 2:
			goLeft = true;
			goRight = true;
			goUp = false;
			goDown = true;
			break;
		case 3:
			goLeft = true;
			goRight = false;
			goUp = true;
			goDown = true;
			break;
		case 4:
			goLeft = false;
			goRight = true;
			goUp = true;
			goDown = true;
			break;
		case 5:
			score += 600;
			if (level == 1)
				level = 2;
			else if (level == 2)
				level = 1;
			levelInitialize();
			break;
		case 6:
			rubyCount++;
			score += 1250;
			if (rubyCount%5 == 0)
				gateLocked = false;
			break;
		case 7:
			hp += 80;
			if (hp > 300)
				hp = 300;			
			score += 345;
			break;
		case 8:
			hp -= 72;
			if (hp <= 0) {
				if (lifeCount > 1) {
					lifeCount --;
					link = new Link(tileDimension);
					hp = 300;							
					orientation = 1;
					xBoard = 8*tileDimension;
					yBoard = 24*tileDimension;
				} else if (lifeCount == 1) {
					lifeCount --;
					hp = 0;
					dead = true;
				}					
			}				
			break;
		case 9:
			score += 900720;
			gateLocked = false;
			break;
		}	
	}	
}
