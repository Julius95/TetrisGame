package testUnits;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tetris.Block;
import tetris.GameBoard;
import tetris.TetrisBlock;
import tetris.TetrisBlock_IF;

public class BlockDeletionTests {

	private static GameBoard board = new GameBoard(4,6,"green");
	//biggerBoard is used in the last two tests of this unit case
	private static GameBoard biggerBoard = new GameBoard(10,19,"green");

	private static void printBoard(){
		boolean[][] gameboard = board.getGameBoard();
		System.out.println();
		for(int i = 0; i<gameboard.length; i++){
			for(int j = 0; j < gameboard[0].length; j++){
				if(gameboard[i][j])
					System.out.print("*" + "(" + i + " " + j + ")");
				else
					System.out.print("-" + "(" + i + " " + j + ")");
			}
			System.out.println();
		}
	}

	private static void assertCoordinates(){
		boolean[][] grid = board.getGameBoard();
		boolean found = false;
		for(int i = 0; i < grid.length;i++){
			for(int j = 0; j<grid[0].length;j++){
				for(TetrisBlock_IF block : board.getTetrisBlocks()){
					for(Block piece : block.getShapes()){
						if(i == piece.getX()){
							if(j == piece.getY()){
								assertEquals(true, grid[i][j]);
								found = true;
								break;
							}
						}
					}
					if(found)
						break;
				}
				if(!found){
					System.out.println(i + " " +  j + " = FALSE");
					assertEquals(false, grid[i][j]);
				}
				found = false;
			}
		}
	}

	@Before
	public void clear(){
		board.clear();
		biggerBoard.clear();
	}

	@Test
	public void blockDeletion() {
		TetrisBlock_IF block = new TetrisBlock(new int[][]{{0,5}, {1,5}, {2,5}, {3,5}},"red",3);
		board.occupyCells(block);
		boolean[][] gameboard = board.getGameBoard();
		for(int i = 0; i<gameboard.length; i++){
			if(gameboard[i][5])
				fail("Game block x : " + i + " y : " + 5 + " should be false");
		}
		assertEquals(true, board.lineDropHappened());
	}

	@Test
	public void blockDeletions1() {
		TetrisBlock_IF block1 = new TetrisBlock(new int[][]{{0,5}, {1,5}},"red",0);
		TetrisBlock_IF block2 = new TetrisBlock(new int[][]{{2,5}, {3,5}},"red",0);

		//Occupy with the first block. Block should not be deleted
		board.occupyCells(block1);
		boolean[][] gameboard = board.getGameBoard();
		for(int i = 0; i<2; i++){
			if(!gameboard[i][5])
				fail("Game block x : " + i + " y : " + 5 + " should be true");
		}

		//Occupy with second block. Deletion should trigger
		board.occupyCells(block2);
		for(int i = 0; i<gameboard.length; i++){
			if(gameboard[i][5])
				fail("Game block x : " + i + " y : " + 5 + " should be false");
		}
		assertEquals(true, board.lineDropHappened());
	}

	@Test
	public void blockDeletions2() {
		TetrisBlock_IF block1 = new TetrisBlock(new int[][]{{0,5}, {1,5}, {2,5}},"red",0);
		TetrisBlock_IF block2 = new TetrisBlock(new int[][]{{3,5}, {3,4}, {2,4}, {2,3}},"red",0);

		//Occupy with the first block. Block should not be deleted
		board.occupyCells(block1);
		boolean[][] gameboard = board.getGameBoard();
		for(int i = 0; i<3; i++){
			if(!gameboard[i][5])
				fail("Game block x : " + i + " y : " + 5 + " should be true");
		}

		//Occupy with second block. Deletion should trigger
		board.occupyCells(block2);
		assertEquals(true, gameboard[3][5]);
		assertEquals(true, gameboard[2][5]);
		assertEquals(true, gameboard[2][4]);
		assertEquals(false, gameboard[1][5]);
		assertEquals(false, gameboard[0][5]);
		assertEquals(false, gameboard[3][4]);
		assertEquals(false, gameboard[2][3]);
		assertEquals(true, board.lineDropHappened());
	}

	@Test
	public void blockDeletions3() {
		TetrisBlock_IF block1 = new TetrisBlock(new int[][]{{0,5}, {1,5}, {2,5}},"red",0);
		TetrisBlock_IF block2 = new TetrisBlock(new int[][]{{1,4}, {2,4}, {3,4}, {0,4}},"red",0);

		//Occupy with the first block. Block should not be deleted
		board.occupyCells(block1);
		board.occupyCells(block2);
		boolean[][] gameboard = board.getGameBoard();
		for(int i = 0; i<3; i++){
			if(gameboard[i][4])
				fail("Game block x : " + i + " y : " + 4 + " should be false");
		}

		assertEquals(true, gameboard[0][5]);
		assertEquals(true, gameboard[1][5]);
		assertEquals(true, gameboard[2][5]);
		assertEquals(false, gameboard[3][5]);
		assertEquals(true, board.lineDropHappened());
	}

	@Test
	public void blockDeletions4() {
		TetrisBlock_IF block1 = new TetrisBlock(new int[][]{{0,5}, {2,5}},"red",0);
		TetrisBlock_IF block2 = new TetrisBlock(new int[][]{{2,4}, {3,4}},"red",0);
		TetrisBlock_IF block3 = new TetrisBlock(new int[][]{{0,3}, {1,3}, {2,3}},"red",0);
		TetrisBlock_IF block4 = new TetrisBlock(new int[][]{{3,3}},"red",0);

		//Occupy with the first block. Block should not be deleted
		board.occupyCells(block1);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block2);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block3);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block4);
		assertEquals(true, board.lineDropHappened());

		boolean[][] gameboard = board.getGameBoard();
		for(int i = 0; i<3; i++){
			if(gameboard[i][3])
				fail("Game block x : " + i + " y : " + 4 + " should be false");
		}

		assertEquals(true, gameboard[0][5]);
		assertEquals(false, gameboard[1][5]);
		assertEquals(true, gameboard[2][5]);
		assertEquals(false, gameboard[3][5]);
		assertEquals(false, gameboard[0][4]);
		assertEquals(false, gameboard[1][4]);
		assertEquals(true, gameboard[2][4]);
		assertEquals(true, gameboard[3][4]);
	}

	@Test
	public void blockDeletions5() {
		TetrisBlock_IF block1 = new TetrisBlock(new int[][]{{0,5}, {1,5}},"red",0);
		TetrisBlock_IF block2 = new TetrisBlock(new int[][]{{1,4}, {2,4}},"red",0);
		TetrisBlock_IF block3 = new TetrisBlock(new int[][]{{0,4}, {0,3}, {1,3}, {2,3}},"red",0);
		TetrisBlock_IF block4 = new TetrisBlock(new int[][]{{0,2}, {0,1}},"red",0);
		TetrisBlock_IF block5 = new TetrisBlock(new int[][]{{2,5}, {2,4}},"red",0);
		TetrisBlock_IF block6 = new TetrisBlock(new int[][]{{3,4}},"red",0);

		board.occupyCells(block1);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block2);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block3);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block4);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block5);
		assertEquals(false, board.lineDropHappened());

		boolean[][] gameboard = board.getGameBoard();


		int blockCount = board.getTetrisBlocks().size();
		assertEquals(5, blockCount);

		printBoard();

		//Trigger line deletion
		board.occupyCells(block6);
		assertEquals(true, board.lineDropHappened());

		blockCount = board.getTetrisBlocks().size();
		assertEquals(4, blockCount);
		for(TetrisBlock_IF b : board.getTetrisBlocks()){
			if(b.getShapes().size() == 0){
				fail("TetrisBlocks with zero block components should not exist");
			}
		}

		printBoard();

		assertEquals(true, gameboard[0][5]);
		assertEquals(true, gameboard[1][5]);
		assertEquals(true, gameboard[2][5]);
		assertEquals(false, gameboard[3][5]);
		assertEquals(false, gameboard[3][4]);
		assertEquals(true, gameboard[0][4]);
		assertEquals(true, gameboard[1][4]);
		assertEquals(true, gameboard[2][4]);
		assertEquals(true, gameboard[0][3]);
		assertEquals(true, gameboard[0][2]);
		assertEquals(false, gameboard[0][1]);
	}

	@Test
	public void blockDeletions6() {
		TetrisBlock_IF block1 = new TetrisBlock(new int[][]{{0,5}, {1,5}},"red",0);
		TetrisBlock_IF block2 = new TetrisBlock(new int[][]{{1,4}, {2,4}},"red",0);
		TetrisBlock_IF block3 = new TetrisBlock(new int[][]{{2,3}, {2,2}, {1,2}, {1,1}},"red",0);
		TetrisBlock_IF block4 = new TetrisBlock(new int[][]{{3,5}, {3,4}},"red",0);
		TetrisBlock_IF block5 = new TetrisBlock(new int[][]{{1,0}, {2,0},{3,0}},"red",0);
		TetrisBlock_IF block6 = new TetrisBlock(new int[][]{{0,2}, {0,3},{0,4}},"red",0);

		board.occupyCells(block1);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block2);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block3);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block4);
		assertEquals(false, board.lineDropHappened());
		board.occupyCells(block5);

		board.occupyCells(block6);
		assertEquals(true, board.lineDropHappened());

		assertCoordinates();

	}

	@Test
	public void blockDeletions7() {
		//Using bigger board
		for(int i = 0; i<6 ;i+=2){
			TetrisBlock_IF block1 = new TetrisBlock(new int[][]{{i,18}, {(i+1),18}, {i,17}, {(i+1),17}},"red",0);
			biggerBoard.occupyCells(block1);
			assertEquals(false, biggerBoard.lineDropHappened());
		}

		TetrisBlock_IF block = new TetrisBlock(new int[][]{{7,17}, {8,17}, {7,18}, {8,18}},"red",0);
		biggerBoard.occupyCells(block);
		block = new TetrisBlock(new int[][]{{8,15}, {8,16}, {9,15}, {9,16}},"red",0);
		biggerBoard.occupyCells(block);
		block = new TetrisBlock(new int[][]{{7,13}, {7,14}, {8,13}, {8,14}},"red",0);
		biggerBoard.occupyCells(block);
		block = new TetrisBlock(new int[][]{{8,11}, {8,12}, {9,11}, {9,12}},"red",0);
		biggerBoard.occupyCells(block);

		for(int i = 0; i<8 ;i+=2){
			TetrisBlock_IF block1 = new TetrisBlock(new int[][]{{i,15}, {(i+1),15}, {i,16}, {(i+1),16}},"red",0);
			biggerBoard.occupyCells(block1);
		}
		assertEquals(true, biggerBoard.lineDropHappened());
		printBoard();
		assertEquals(6, biggerBoard.getTetrisBlocks().size());

		assertCoordinates();

	}

	@Test
	public void blockDeletions8() {
		//Using bigger board
		//Start occupying gameboard slots without trigering block deletion

		for(int i = 0; i<8 ;i+=2){
			TetrisBlock_IF block1 = new TetrisBlock(new int[][]{{i,18}, {(i+1),18}, {i,17}, {(i+1),17}},"red",0);
			biggerBoard.occupyCells(block1);
			assertEquals(false, biggerBoard.lineDropHappened());
		}

		TetrisBlock_IF block = new TetrisBlock(new int[][]{{8,17}, {8,18}},"red",0);
		biggerBoard.occupyCells(block);
		for(int i = 0; i<6 ;i+=2){
			block = new TetrisBlock(new int[][]{{i,16}, {(i+1),16}},"red",0);
			biggerBoard.occupyCells(block);
			assertEquals(false, biggerBoard.lineDropHappened());
		}
		for(int i = 0; i<8 ;i+=2){
			block = new TetrisBlock(new int[][]{{i,15}, {(i+1),15}},"red",0);
			biggerBoard.occupyCells(block);
			assertEquals(false, biggerBoard.lineDropHappened());
		}
		block = new TetrisBlock(new int[][]{{8,15}, {8,15}},"red",0);
		biggerBoard.occupyCells(block);
		/////////////////////////////////

		//Trigger line deletions with this block. 3 of 4 rows should be deleted
		block = new TetrisBlock(new int[][]{{9,15}, {9,16}, {9,17}, {9,18}},"red",0);
		biggerBoard.occupyCells(block);
		//3 Rows should now be deleted
		assertEquals(true, biggerBoard.lineDropHappened());
		assertEquals(4, biggerBoard.getTetrisBlocks().size());
		assertCoordinates();

	}

}
