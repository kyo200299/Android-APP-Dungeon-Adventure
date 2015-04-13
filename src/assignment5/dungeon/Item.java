package assignment5.dungeon;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Item extends DrawableObject {
	private final int type;
	private final Tile tile;
	private boolean used;
	private Rect itemdst;
	
	// Item type: 3=Gate 4=Ruby 5=Potion
	public Item(Tile tile, int placement, int tileDimension) {
		this.tile = tile;
		this.type = placement;
		used = false;
		itemdst = new Rect(getRelX(), getRelY(),
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
		if (type == 3)
			c.drawBitmap(dv.gate, null, itemdst, null);
		if (type == 4 && !used)
			c.drawBitmap(dv.ruby, null, itemdst, null);
		if (type == 5 && !used)
			c.drawBitmap(dv.potion, null, itemdst, null);		
	}

	@Override
	public void move() {
		itemdst.offsetTo(tile.currentX(), tile.currentY());		
	}

	@Override
	public int checkCollision(int collisionType, int orientation, boolean linkAtk, boolean gateLocked) {
		// Collision Type of Items: 5=Gate 6=Ruby 7=Potion
		if (Rect.intersects(Link.linkdst, itemdst) && !used) {
			if (type == 3 && !gateLocked)
				collisionType = 5;
			if (type == 4)
				collisionType = 6;
			if (type == 5)
				collisionType = 7;
			used = true;
		} else
			collisionType = 0;
		
		return collisionType;
	}

}
