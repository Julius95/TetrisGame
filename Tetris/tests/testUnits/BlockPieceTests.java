package testUnits;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import tetris.BlockPiece;
import tetris.Block;
import tetris.TetrisBlock;
import tetris.TetrisBlock_IF;

public class BlockPieceTests {

	//10*19 gameboard
	private static boolean[][] gameGrid;

	@BeforeClass
	public static void testienAloitus() {
		gameGrid = new boolean[10][19];
		for(int i = 0; i < gameGrid.length; i++){
			for(int j = 0; j < gameGrid[i].length ; j++){
				gameGrid[i][j] = false;
			}
		}
	}

	@Test
	public void moveToLeftBorder() {
		Block t = new BlockPiece(5,-1);
		t.move(-1, 1);
		t.move(-1, 1);
		t.move(-1, 1);
		t.move(-1, 1);
		t.move(-1, 1);

		assertEquals(0, t.getX());
		assertEquals(4, t.getY());
		assertEquals(false, t.canMoveHorizontally(-1, gameGrid));
		assertEquals(true, t.canMoveVertically(1, gameGrid));
	}

	@Test
	public void moveToRightBorder() {
		Block t = new BlockPiece(5,-1);
		t.move(1, 1);
		t.move(1, 1);

		assertEquals(7, t.getX());
		assertEquals(1, t.getY());
		assertEquals(true, t.canMoveHorizontally(1, gameGrid));
		assertEquals(true, t.canMoveVertically(1, gameGrid));

		t.move(1, 1);
		t.move(1, 1);

		assertEquals(9, t.getX());
		assertEquals(3, t.getY());
		assertEquals(false, t.canMoveHorizontally(1, gameGrid));
		assertEquals(true, t.canMoveVertically(1, gameGrid));
	}

	@Test
	public void shouldBeDrawable(){
		Block b = new BlockPiece(5,-1);
		assertEquals(false, b.canBeDrawn());
		b.move(1, 1);
		assertEquals(true, b.canBeDrawn());
	}

	@Test
	public void allTheWayDown(){
		Block b = new BlockPiece(8,0);
		b.move(0, 18);
		assertEquals(true, b.canMoveHorizontally(1, gameGrid));
		assertEquals(false, b.canMoveVertically(1, gameGrid));
	}



}
