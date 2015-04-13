package assignment5.dungeon;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Block extends DrawableObject {
	private final int tileDimension, type;
	private final Tile tile;
	private int xTileIni, yTileIni, xTileFin, yTileFin;
	private Rect blockdst;
	
	public Block(Tile tile, int placement, int tileDimension) {
		this.tile = tile;
		this.type = placement;
		this.tileDimension = tileDimension;
		blockdst = new Rect(getRelX(), getRelY(),
				getRelX() + tileDimension, getRelY() + tileDimension);		
	}

	@Override
	public int getRelX() {
		return tile.getRelX();
	}

	@Override
	public int getRelY() {
		return tile.getRelY();
	}

	@Override
	public void draw(Canvas c, DungeonView dv) {		
		if (type == 1)
			c.drawBitmap(dv.mountain, null, blockdst, null);
		if (type == 2)
			c.drawBitmap(dv.rock, null, blockdst, null);
	}

	@Override
	public void move() {
		blockdst.offsetTo(tile.currentX(), tile.currentY());
	}

	@Override
	public int checkCollision(int collisionType, int orientation, boolean linkAtk, boolean gateLocked) {
		xTileIni = tile.currentX();				// Initial X Position
		yTileIni = tile.currentY();				// Initial Y Position 
		xTileFin = xTileIni + tileDimension;	// Final X Position
		yTileFin = yTileIni + tileDimension;	// Final Y Position

		// Collision Type of Blocks: 1=Top 2=Bottom 3=Left 4=Right
		if ((Link.yLink+tileDimension >= yTileIni) && (Link.yLink+tileDimension < yTileFin) &&
			(((Link.xLink >= xTileIni) && (Link.xLink <= xTileFin)) ||
			((Link.xLink+tileDimension >= xTileIni) && (Link.xLink+tileDimension <= xTileFin))))
			collisionType = 1; // Link touches top of it
		else if ((Link.yLink <= yTileFin-tileDimension/2) && (Link.yLink > yTileIni) &&
				(((Link.xLink >= xTileIni) && (Link.xLink <= xTileFin)) ||
				((Link.xLink+tileDimension >= xTileIni) && (Link.xLink+tileDimension <= xTileFin))))
			collisionType = 2; // Link touches bottom of it
		else if ((Link.xLink+tileDimension >= xTileIni) && (Link.xLink+tileDimension < xTileFin) &&
				(((Link.yLink >= yTileIni) && (Link.yLink <= yTileFin)) ||
				((Link.yLink+tileDimension >= yTileIni) && (Link.yLink+tileDimension <= yTileFin))))
			collisionType = 3; // Link touches left of it
		else if ((Link.xLink <= xTileFin) && (Link.xLink > xTileIni) &&
				(((Link.yLink >= yTileIni) && (Link.yLink <= yTileFin)) ||
				((Link.yLink+tileDimension >= yTileIni) && (Link.yLink+tileDimension <= yTileFin))))
			collisionType = 4; // Link touches right of it
		else
			collisionType = 0;

		return collisionType;
	}
}
