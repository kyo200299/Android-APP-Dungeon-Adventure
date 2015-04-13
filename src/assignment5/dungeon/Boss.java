package assignment5.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Boss extends DrawableObject {
	private static final int vx = 20;
	private final int tileDimension;
	private final Tile tile;
	private int xBoss, yBoss, hp, attkCount, delayCount, i;
	private boolean LR, dead, bossAtk, delay;
	private int xTileIni, yTileIni;
	
	
	private Paint textPaint = new Paint();
	private Rect bossdst;
	
	public Boss(Tile tile, int tileDimension) {
		this.tile = tile;
		this.tileDimension = tileDimension;
		xBoss = 0;
		yBoss = 0;
		LR = false;
		dead = false;
		bossAtk = false;
		delay = false;
		attkCount = 0;
		delayCount = 0;
		hp = 1000;
		this.bossdst = new Rect(getRelX(), getRelY(), getRelX() + 240, getRelY() + 185);
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
		i++;	if (i == 100)	i = 0;	//Reset counter for animation
		xTileIni = tile.currentX();				// Initial X Position
		yTileIni = tile.currentY();				// Initial Y Position 
		
		if(!dead) {			
			if (bossAtk) {
				bossdst.set(xTileIni+xBoss, yTileIni+yBoss,
						xTileIni+xBoss+240, yTileIni+yBoss+240);
				c.drawBitmap(dv.bossA, null, bossdst, null);
			} else if (i%2 == 0) {
				bossdst.set(xTileIni+xBoss, yTileIni+yBoss-5,
						xTileIni+xBoss+240, yTileIni+yBoss+185);
				c.drawBitmap(dv.bossW1, null, bossdst, null);
			} else if (i%2 == 1) {
				bossdst.set(xTileIni+xBoss, yTileIni+yBoss-8,
						xTileIni+xBoss+200, yTileIni+yBoss+192);
				c.drawBitmap(dv.bossW2, null, bossdst, null);
			}
			textPaint.setColor(Color.MAGENTA);		
			textPaint.setTextSize(60);
			c.drawText("BOSS HP { " + hp + " }", tileDimension*7, tileDimension*2, textPaint);
		}		
	}

	@Override
	public void move() {
		if (!dead) {
			if (LR) {
				xBoss = xBoss + vx;
				if (xBoss >= 8*tileDimension)
					LR = false;
			} else if (!LR){
				xBoss = xBoss - vx;
				if (xBoss <= -8*tileDimension)
					LR = true;
			}
			 
			// Duration of attack
			attkCount++; 
			if (attkCount%50 == 40)
				bossAtk = true;
			if (attkCount%50 == 45)
				bossAtk = false;
			if (attkCount%80 == 40 && hp < 1000)
				hp += 5;		// Boss slightly recovers his own HP
			if (attkCount == 100)
				attkCount = 0;
		}		
	}

	@Override
	public int checkCollision(int collisionType, int orientation, boolean linkAtk, boolean gateLocked) {
		// Collision Type of Boss: 8=Boss Attack 9=Boss Died
		if (Rect.intersects(Link.linkdst, bossdst) && !delay && !dead) {
			if (!bossAtk && linkAtk) {
				hp -= 120;
				if (hp <= 0) {
					dead = true;
					collisionType = 9;
				} else
					collisionType = 0;
			} else
				collisionType = 8;
			delay = true;
		} else
			collisionType = 0;
		
		// Delay to move without collision for a period of time
		if (delay) {
			delayCount++;
			if (delayCount == 15) {
				delay = false;
				delayCount = 0;
			}
		}
		
		return collisionType;
	}

}
