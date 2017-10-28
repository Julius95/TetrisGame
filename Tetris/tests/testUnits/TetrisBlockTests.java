package testUnits;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import tetris.BlockPiece;
import tetris.Block;
import tetris.TetrisBlock;
import tetris.TetrisBlock_IF;

public class TetrisBlockTests{

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

	/*
	 * Testing main tetris block movement on the left side of the board.
	 * Testing includes moving the block and verifying the block actually moved,
	 * created block should not be able to move to the left.
	 */
	@Test
	public void mainTetrisBlockMovementTest_1() {
		TetrisBlock_IF block =
		new TetrisBlock(
				new BlockPiece[]{new BlockPiece(0,-1), new BlockPiece(1,-1)},
				"red",
				1
		);

		assertEquals(0, block.getDrawables().length);
		assertEquals(false, block.canMoveHorizontally(-1, gameGrid));

		block.move(0, 1);

		assertEquals(2, block.getDrawables().length);
		assertEquals(false, block.canMoveHorizontally(-1, gameGrid));
	}

	@Test
	public void mainTetrisBlockMovementTest_2() {
		TetrisBlock_IF block =
		new TetrisBlock(
				new BlockPiece[]{new BlockPiece(8,-1), new BlockPiece(9,-1)},
				"red",
				1
		);

		assertEquals(0, block.getDrawables().length);
		assertEquals(false, block.canMoveHorizontally(1, gameGrid));
		assertEquals(true, block.canMoveVertically(1, gameGrid));

		block.move(0, 1);

		assertEquals(2, block.getDrawables().length);
		assertEquals(false, block.canMoveHorizontally(1, gameGrid));
		assertEquals(true, block.canMoveVertically(1, gameGrid));
	}

	@Test
	public void mainTetrisBlockMovementTest_3() {
		TetrisBlock_IF block =
		new TetrisBlock(
				new BlockPiece[]{new BlockPiece(4,-1), new BlockPiece(5,-1)},
				"red",
				1
		);

		block.move(0, 19);

		assertEquals(2, block.getDrawables().length);
		assertEquals(false, block.canMoveVertically(1, gameGrid));
		assertEquals(true, block.canMoveHorizontally(1, gameGrid));
	}

	/**
	 * Test contains 4 90-degree rotations and after every rotation we check if the coordinates are right
	 */
	@Test
	public void mainTetrisBlockRotationTest_L_Block() {
		TetrisBlock_IF block =
		new TetrisBlock(
				new BlockPiece[]{new BlockPiece(4,-1), new BlockPiece(5,-1), new BlockPiece(6,-1), new BlockPiece(4,-2)},
				"red",
				1
		);

		block.move(0, 5);

		//First Rotation
		block.tryRotate(gameGrid);
		int[][] expected_coords = {{5,3},{5,4},{5,5},{6,3}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}

		//Second rotation
		block.tryRotate(gameGrid);
		expected_coords = new int[][]{{6,4},{5,4},{4,4},{6,5}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}

		//Third rotation
		block.tryRotate(gameGrid);
		expected_coords = new int[][]{{5,5},{5,4},{5,3},{4,5}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}

		//Fourth Rotation
		block.tryRotate(gameGrid);
		expected_coords = new int[][]{{4,4},{5,4},{6,4},{4,3}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}

	}

	@Test
	public void mainTetrisBlockRotationTest_2(){
		TetrisBlock_IF block =
		new TetrisBlock(
				new BlockPiece[]{new BlockPiece(4,5), new BlockPiece(5,5), new BlockPiece(6,5), new BlockPiece(4,4)},
				"red",
				3
		);
		block.tryRotate(gameGrid);
		int [][]expected_coords = new int[][]{{3,4},{3,5},{3,6},{4,4}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}

		block.tryRotate(gameGrid);
		expected_coords = new int[][]{{4,3},{3,3},{2,3},{4,4}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}

		block.move(1, 2);

		block.tryRotate(gameGrid);
		expected_coords = new int[][]{{6,6},{6,5},{6,4},{5,6}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}
	}


	@Test
	public void mainTetrisBlockRotationTest_GameBoardBoundary(){
		TetrisBlock_IF block =
		new TetrisBlock(
				new BlockPiece[]{new BlockPiece(1,5), new BlockPiece(2,5), new BlockPiece(3,5), new BlockPiece(1,4)},
				"red",
				3
		);
		block.tryRotate(gameGrid);
		int [][]expected_coords = new int[][]{{1,4},{1,5},{1,6},{2,4}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}

		block = new TetrisBlock(new int[][]{{10,8}, {10,7}, {10,6}, {9,8}},"red",3);

		block.tryRotate(gameGrid);

		expected_coords = new int[][]{{7,9},{8,9},{9,9},{7,8}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}

		block = new TetrisBlock(new int[][]{{9,8}, {9,7}, {9,6}, {8,8}},"red",3);
		block.tryRotate(gameGrid);
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}
	}

	@Test
	public void mainTetrisBlockRotationTest_RotatingNextToOtherPieces(){
		TetrisBlock_IF block = new TetrisBlock(new int[][]{{0,8}, {0,7}, {0,6}, {1,8}},"red",3);
		for(int i = 0; i<gameGrid[0].length;i++){
			gameGrid[2][i] = true;
		}
		assertEquals(false,block.tryRotate(gameGrid));
		int [][]expected_coords = new int[][]{{0,8},{0,7},{0,6},{1,8}};
		for(int i = 0; i<block.getDrawables().length;i++){
			assertEquals(expected_coords[i][0], block.getDrawables()[i].getX());
			assertEquals(expected_coords[i][1], block.getDrawables()[i].getY());
		}

		gameGrid = new boolean[10][19];
		for(int i = 0; i < gameGrid.length; i++){
			for(int j = 0; j < gameGrid[i].length ; j++){
				gameGrid[i][j] = false;
			}
		}
	}

	@Test
	public void getAndSetColor(){
		TetrisBlock_IF block = new TetrisBlock(new int[][]{{0,8}, {0,7}, {0,6}, {1,8}},"red",3);
		assertEquals("red", block.getColor());
		block.setColor("blue");
		assertEquals("blue", block.getColor());
	}

	@Test
	public void detachPiece(){
		TetrisBlock_IF block = new TetrisBlock(new int[][]{{0,8}, {0,7}, {0,6}, {1,8}},"red",3);
		assertEquals(4, block.getShapes().size());
		block.detachBlock(1);//Delete blockpiece with coordinates (0,7)
		assertEquals(3, block.getShapes().size());
		for(Block piece : block.getShapes()){
			if(piece.getX() == 0 && piece.getY()==7)
				fail("BlockPiece with coordinates 0 and 7 should not exist!");
		}
	}

}
