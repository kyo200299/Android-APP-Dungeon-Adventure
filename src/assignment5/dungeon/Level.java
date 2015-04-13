package assignment5.dungeon;

public class Level {
	private int level;
	private final int MAX_TILE_X, MAX_TILE_Y;
	
	public Level(int level, int MAX_TILE_X, int MAX_TILE_Y) {
		this.level = level;
		this.MAX_TILE_X = MAX_TILE_X;
		this.MAX_TILE_Y = MAX_TILE_Y;
	}
	
	public void levelBuild(int[][] placement) {
		// 1=Mountain 2=Rock 3=Gate 4=Ruby 5=Potion  6=Boss
		switch(level) {
		case 1:
			for (int row = 0; row < MAX_TILE_Y; row++) {
				for (int col = 0; col < MAX_TILE_X; col++) {
					if (row < 4 && 
						!((row == 2 && col == 17) || (row == 2 && col == 18) || (row == 2 && col == 19) ||
						  (row == 3 && col == 17) || (row == 3 && col == 18) || (row == 3 && col == 19)))
						placement[row][col] = 1;	// Mountain
					if ((row == 2 && col == 17) || (row == 2 && col == 18) || (row == 2 && col == 19) ||
						(row == 3 && col == 17) || (row == 3 && col == 18) || (row == 3 && col == 19))
						placement[row][col] = 3;	// Gate
					if ((row == 12 && col == 6) || (row == 20 && col == 30) || (row == 26 && col == 17) ||
							(row == 30 && col == 26) || (row == 32 && col == 15))
						placement[row][col] = 4;	// Ruby
				}				
			}
			break;
		case 2:
			for (int row = 0; row < MAX_TILE_Y; row++) {
				for (int col = 0; col < MAX_TILE_X; col++) {
					if (row < 4 && 
						!((row == 2 && col == 17) || (row == 2 && col == 18) || (row == 2 && col == 19) ||
						  (row == 3 && col == 17) || (row == 3 && col == 18) || (row == 3 && col == 19)))
						placement[row][col] = 1;	// Mountain
					if ((row >= 18 && row <= 20 && col <= 14) ||
						(row >= 18 && row <= 20 && col >= 22))
						placement[row][col] = 2;	// Rock
					if ((row == 2 && col == 17) || (row == 2 && col == 18) || (row == 2 && col == 19) ||
						(row == 3 && col == 17) || (row == 3 && col == 18) || (row == 3 && col == 19))
						placement[row][col] = 3;	// Gate
					if ((row == 25 && col == 10) || (row == 25 && col == 26))
						placement[row][col] = 5;	// Potion
					if ((row == 10 && col == 18))
						placement[row][col] = 6;	// Boss
				}
			}
			break;
		}
	}
}
