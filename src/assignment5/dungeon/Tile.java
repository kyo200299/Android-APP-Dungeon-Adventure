package assignment5.dungeon;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Tile {
	private final int dimension, col, row;
	private static int level;
	private Board board;
	private DrawableObject object;
	private Rect bgdst;
	
	public Tile(Board board, int level, int row, int col, int tileDimension) {
		this.board = board;
		Tile.level = level;
		this.row = row;
		this.col = col;
		this.dimension = tileDimension;
		bgdst = new Rect(currentX(), currentY(), currentX()+tileDimension, currentY()+tileDimension);
	}
	public void drawBackground(Canvas c, DungeonView dv) {
		// Drawing background for the level
		if (level == 1)
			c.drawBitmap(dv.grass, null, bgdst, null);
		else if (level == 2)
			c.drawBitmap(dv.floor, null, bgdst, null);
	}
	public void draw(Canvas c, DungeonView dv) {
		if (object != null)
			object.draw(c, dv);
	}
	
	public void move() {
		bgdst.offsetTo(currentX(), currentY());
		if (object != null)
			object.move();
	}
	
	public int checkCollision(int collisionType, int orientation, boolean linkAtk, boolean gateLocked) {
		if (object != null)
			collisionType = object.checkCollision(collisionType, orientation, linkAtk, gateLocked);
		
		return collisionType;		
	}
	
	public void setDrawable(DrawableObject obj) {
		this.object = obj;
	}
	
	public int getRelX() {
		return col * dimension;
	}
	
	public int getRelY() {
		return row * dimension;
	}
	
	public int currentX() {
		return getRelX() - board.getX();
	}
	
	public int currentY() {
		return getRelY() - board.getY();
	}
}
