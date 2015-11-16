// import java.util.IOException;

public class Board {
	private int[][] tiles = new int[3][3];
	private int winner = -1;
	private int player = 0;
	private static int MAX_SIZE = 3;
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
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				tiles[x][y] = -1;
			}
		}

	}

	private int decideWinner(int playerID) {
		//if (00 == 01 == 02) or (10 == 11 == 12) or (20 == 21 == 22)
		// or (00 == 10 == 20) or (01 == 11 == 21) or (02 == 12 == 22)
		// or (00 == 11 == 22) or (02 == 11 == 20)
		if  (tiles[0][0] == -1 && tiles[2][2] == -1 && tiles[0][2] == -1 && tiles[2][0] == -1 && tiles[1][1] == -1) {
			return -1;
		}
		if ((tiles[0][0] == tiles [0][1] && tiles[0][0] == tiles[0][2]) || (tiles[1][0] == tiles[1][1] && tiles[1][0] == tiles[1][2]) || (tiles[2][0] == tiles [2][1] && tiles[2][0] == tiles[2][2])
		|| (tiles[0][0] == tiles [1][0]  && tiles[0][0] == tiles[2][0]) || (tiles[1][0] == tiles[1][1] && tiles[1][0] == tiles[2][1]) || (tiles[0][2] == tiles [1][2] && tiles[0][2] == tiles[2][2])
		|| (tiles[0][0] == tiles [1][1]  && tiles[0][0] == tiles[2][2]) || (tiles[0][2] == tiles[1][1] && tiles[0][2] == tiles[2][0])) {
			return playerID;
		} else {
			if (turns == 0 ){
				return -2;
			} else {
				return -1;
			}
		}
	}

	private boolean canSelect(int x, int y) {
		return x >= 0 && x < 3 && y >= 0 && y <= 3 && tiles[x][y] == -1;
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
					b.switchTurn();
					// System.out.println((int)x);
					// System.out.println((int)y);
				}
			}
			b.winner = b.decideWinner(b.player);
		}
		System.out.println("player " + b.winner  + " wins");
	}

	
}