package assignment5.dungeon;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Link {
	private static final int velocity = 30;
	private int i = 0;
	static int xLink, yLink;
	static Rect linkdst;

	public Link(int tileDimension) {
		xLink = tileDimension*9 + tileDimension/2;
		yLink = tileDimension*5 + tileDimension/2;
		linkdst = new Rect();
	}
	
	public static void goLeft() {		
		xLink = xLink - velocity;
	}
	
	public static void goRight() {		
		xLink = xLink + velocity;		
	}

	public static void goUp() {		
		yLink = yLink - velocity;
	}
	
	public static void goDown() {		
		yLink = yLink + velocity;
	}
	
	public void draw(Canvas c, DungeonView dv, boolean move, boolean linkAtk, int orientation) {
		i++;	if (i == 100)	i = 0;	//Reset counter for animation		
		
		// Orientation: 1=up 2=down 3=left 4=right
		if (orientation == 3) {
			if (linkAtk) {
				linkdst.set(xLink-51, yLink, xLink+60, yLink+72);
				c.drawBitmap(dv.linkAL, null, linkdst, null);
			} else if (move) {
				linkdst.set(xLink, yLink, xLink+54, yLink+72);
				if (i%2 == 0)					
					c.drawBitmap(dv.linkWL1, null, linkdst, null);
				else if (i%2 == 1)
					c.drawBitmap(dv.linkWL2, null, linkdst, null);
			} else {
				linkdst.set(xLink, yLink, xLink+54, yLink+72);
				c.drawBitmap(dv.linkSL, null, linkdst, null);
			}
		} else if (orientation == 4) {
			if (linkAtk) {
				linkdst.set(xLink, yLink, xLink+111, yLink+72);
				c.drawBitmap(dv.linkAR, null, linkdst, null);
			} else if (move) {
				linkdst.set(xLink, yLink, xLink+54, yLink+72);
				if (i%2 == 0)
					c.drawBitmap(dv.linkWR1, null, linkdst, null);
				else if (i%2 == 1)					
					c.drawBitmap(dv.linkWR2, null, linkdst, null);
			} else {
				linkdst.set(xLink, yLink, xLink+54, yLink+72);
				c.drawBitmap(dv.linkSR, null, linkdst, null);
			}
		} else if (orientation == 1) {
			if (linkAtk) {
				linkdst.set(xLink, yLink-33, xLink+54, yLink+78);
				c.drawBitmap(dv.linkAU, null, linkdst, null);
			} else if (move) {
				linkdst.set(xLink, yLink, xLink+54, yLink+72);
				if (i%2 == 0)
					c.drawBitmap(dv.linkWU1, null, linkdst, null);
				else if (i%2 == 1)
					c.drawBitmap(dv.linkWU2, null, linkdst, null);
			} else {
				linkdst.set(xLink, yLink, xLink+54, yLink+72);
				c.drawBitmap(dv.linkSU, null, linkdst, null);
			}
		} else if (orientation == 2) {
			if (linkAtk) {
				linkdst.set(xLink, yLink, xLink+54, yLink+111);
				c.drawBitmap(dv.linkAD, null, linkdst, null);
			} else if (move) {
				linkdst.set(xLink, yLink, xLink+54, yLink+72);
				if (i%2 == 0)
					c.drawBitmap(dv.linkWD1, null, linkdst, null);
				else if (i%2 == 1)
					c.drawBitmap(dv.linkWD2, null, linkdst, null);
			} else {
				linkdst.set(xLink, yLink, xLink+54, yLink+72);
				c.drawBitmap(dv.linkSD, null, linkdst, null);
			}
		}			
	}

}
