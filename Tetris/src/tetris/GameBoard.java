package tetris;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameBoard {

	private boolean[][] gameGrid;
	private String boardColor;
	private int points=0;
	private boolean lineDropHappened = false;
	private List<TetrisBlock_IF> blocks = new ArrayList<TetrisBlock_IF>();

	public GameBoard(int xLength, int yLength, String color){
		gameGrid = new boolean[xLength][yLength];
		clear();
		boardColor = color;
	}

	public void clear(){
		for(int i = 0; i < gameGrid.length; i++){
			for(int j = 0; j < gameGrid[i].length ; j++){
				gameGrid[i][j] = false;
			}
		}
		lineDropHappened = false;
		points = 0;
		blocks.clear();
	}

	public String getColor(){
		return boardColor;
	}

	public boolean[][] getGameBoard(){
		return gameGrid;
	}

	public boolean isCellOccupied(int x, int y){
		if(x<0 || y<0)
			return false;
		return gameGrid[x][y];
	}

	public void occupyCell(int x, int y){
		if(x<0 || x>gameGrid.length)
			return;
		else if(y<0 || y>gameGrid[0].length)
			return;
		gameGrid[x][y] = true;
	}

	public void occupyCells(TetrisBlock_IF block){
		blocks.add(block);
		int size = block.getShapes().size();
		for(int i = 0; i<size;i++){
			Block piece = block.getShapes().get(i);
			gameGrid[piece.getX()][piece.getY()] = true;
			System.out.println("Occupying : " + piece.getX() + " " + piece.getY());
			if(checkIfYLineFull(piece.getY())){
				size = block.getShapes().size();
				System.out.println("SIZE : " + size);
				i = -1;
			}
		}
	}

	private boolean checkIfYLineFull(int y){
		for(int i = 0; i<gameGrid.length;i++){
			System.out.println("Checking : " + i + " " + y);
			if(!gameGrid[i][y]){
				return false;
			}
		}
		points += gameGrid.length * 10;
		//call dropOccupiedDown
		dropOccupiedDown(y, 1);
		return true;
	}

	private void dropOccupiedDown(int y, int amount){
		List<Block> updatableBlocks = new ArrayList<Block>();
		int blocksSize = blocks.size();
		for(int i = 0; i<blocksSize;i++){
			TetrisBlock_IF block = blocks.get(i);
			int size = block.getShapes().size();
			for(int j = 0; j<size;j++){
				System.out.println("Size " + size);
				Block p = block.getShapes().get(j);
				if(p.getY()==y){
					System.out.println("Deleting " + p.getX() + " " + p.getY());
					gameGrid[p.getX()][p.getY()] =  false;
					block.detachBlock(j);
					size--;
					j--;
					continue;
				}else if(p.getY()<y){

					if((p.getY()+amount)<gameGrid[0].length){
						if(!updatableBlocks.contains(p)){
							System.out.println("adding : " + " x : " + p.getX() + " y : " + p.getY());
							updatableBlocks.add(p);
							gameGrid[p.getX()][p.getY()] =  false;
						}
						continue;
					}else{
						System.out.println("Deleting " + p.getX() + " " + p.getY());
						gameGrid[p.getX()][p.getY()] =  false;
						block.detachBlock(j);
						size--;
						j--;
						continue;
					}
				}
			}
			if(block.getShapes().size()==0){
				if(blocks.remove(block)){
					System.out.println("Removing stuff");
					blocksSize--;
					i=-1;
				}
			}
		}
		System.out.println("Listan koko : " + updatableBlocks.size());
		for(Block block : updatableBlocks){
			block.teleport(block.getX(), block.getY()+amount);
			System.out.println("updating : " + " x : " + block.getX() + " y : " + block.getY());
			gameGrid[block.getX()][block.getY()] =  true;
		}
		lineDropHappened = true;
	}

	public int getPoints() {
		return points;
	}

	public boolean lineDropHappened() {
		if(lineDropHappened){
			lineDropHappened = false;
			return true;
		}
		return false;
	}

	public List<TetrisBlock_IF> getTetrisBlocks(){
		return blocks;
	}

}
