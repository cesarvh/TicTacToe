// import java.util.IOException;

public class Board {
	private static int MAX_SIZE = 3;
	private int[][] tiles = new int[MAX_SIZE][MAX_SIZE];
	private int winner = -1;
	private int player = 0;
	private int turns = MAX_SIZE * MAX_SIZE;
	public Board() {
		drawTiles(MAX_SIZE, MAX_SIZE);
		launchTiles();
	}

	private void switchTurn() {
		if (player == 0) {
			player = 1;
		} else {
			player = 0;
		}
	}

	private void drawTiles(int height, int width) {
		if (height != width) throw new IllegalArgumentException("The height and width must be equal.");
		StdDrawPlus.setXscale(0, width);
        StdDrawPlus.setYscale(0, height);
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				if ((h + w) % 2 == 0) {
					StdDrawPlus.setPenColor(StdDrawPlus.BOOK_BLUE);
				} else {
					StdDrawPlus.setPenColor(StdDrawPlus.BOOK_LIGHT_BLUE);
				}
				StdDrawPlus.filledSquare(h + .5, w + .5, .5);
			}
		}
	}

	private void launchTiles() {
		for (int x = 0; x < MAX_SIZE; x++) {
			for (int y = 0; y < MAX_SIZE; y++) {
				tiles[x][y] = -1;
			}
		}
	}

	private int decideWinner(int xPos, int yPos, int playerID) {
		// check row
		
// [0] --> [0 0] [0 1] [0 2]
// [1] --> [1 0] [1 1] [1 2]
// [2] --> [2 0] [2 1] [2 2]

		int n = 3;
		for (int i = 0; i < n; i++) {
			if (tiles[xPos][i] != playerID) {
				break;
			} 
			if (tiles[xPos][i] == tiles[xPos][n - 1]) {
				return playerID;
			}
		}
		// check column
		for (int i = 0; i < n; i++) {
			if (tiles[i][yPos] != playerID) {
				break;
			} 
			if (tiles[i][yPos] == tiles[n - 1][yPos]) {
				return playerID;
			}
		}
		if (xPos == yPos) {
			// check diag
			for (int i = 0; i < n; i ++) {
				if (tiles[i][i] != playerID) {
					break;
				} 
				if (i == n - 1) {
					return playerID;
				}
			}
			for (int i = 0; i < n; i++) {
				if (tiles[i][(n = 1)-i] != playerID) {
					break;
				} 
				if (i == n - 1) {
					return playerID;
				}
			}
		}
		return -1;
	}

	private boolean canSelect(int x, int y) {
		return x >= 0 && x < MAX_SIZE && y >= 0 && y <= MAX_SIZE && tiles[x][y] == -1;
	}

	private void setImage(int x, int y, int playerID) {
		tiles[x][y] = playerID;
		if (playerID == 1) {
			StdDrawPlus.picture(x + .5, y + .5, "img/x.png", 1, 1);
		} else {
			StdDrawPlus.picture(x + .5, y + .5, "img/o.png", 1, 1);
		}
	}

	public static void main(String[] args) {
		Board b = new Board();
		StdDrawPlus.setXscale(0, MAX_SIZE);
        StdDrawPlus.setYscale(0, MAX_SIZE);

		while (b.winner == -1) {
			if (StdDrawPlus.mousePressed()) {
				double x = StdDrawPlus.mouseX();
				double y = StdDrawPlus.mouseY();
				if (b.canSelect((int) x, (int) y)) {
					b.setImage((int) x, (int) y, b.player);
					b.winner = b.decideWinner((int) x, (int) y,b.player);

					b.switchTurn();
					// System.out.println((int)x);
					// System.out.println((int)y);
				}
			}
		}
		System.out.println("player " + b.winner  + " wins");
	}

	
}