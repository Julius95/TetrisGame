package tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TetrisBlock implements TetrisBlock_IF{

	private List<Block> blocks = new ArrayList<Block>();
	private String color;
	private boolean allBlocksDrawable;
	private int[][] rotationMatrix = {{0,-1},{1,0}};
	private int keskiIndeksi;

	/**
	 * Constructor for a tetrisblock
	 * @param pieces
	 * @param color
	 * @param keskiIndeksi
	 */
	public TetrisBlock(int coords[][], String color, int keskiIndeksi){
		allBlocksDrawable = false;
		this.color = color;
		this.keskiIndeksi = keskiIndeksi;
		for(int i = 0; i <coords.length;i++){
			Block piece = new BlockPiece(coords[i][0], coords[i][1]);
			blocks.add(piece);
		}
	}

	/**
	 * Constructor for a tetrisblock
	 * @param pieces
	 * @param color
	 * @param keskiIndeksi
	 */
	public TetrisBlock(Block[] pieces, String color, int keskiIndeksi){
		allBlocksDrawable = false;
		this.color = color;
		for(Block piece : pieces){
			blocks.add(piece);
		}
		this.keskiIndeksi = keskiIndeksi;
	}

	@Override
	public void move(int x, int y) {
		int laskuri = 0;
		for(Block t : blocks){
			t.move(x, y);
			if(t.canBeDrawn())
				laskuri++;
		}
		if(!allBlocksDrawable && laskuri == blocks.size())
			allBlocksDrawable = true;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public void setColor(String colorName) {
		color = colorName;
	}

	@Override
	public List<Block> getShapes() {
		return blocks;
	}

	@Override
	public boolean canMoveHorizontally(int numberOfXToMove, boolean[][] gameGrid) {
		for(Block t : blocks){
			if(!t.canMoveHorizontally(numberOfXToMove, gameGrid))
				return false;
		}
		return true;
	}

	@Override
	public boolean canMoveVertically(int numberOfYToMove, boolean[][] gameGrid) {
		for(Block t : blocks){
			if(!t.canMoveVertically(numberOfYToMove,  gameGrid))
				return false;
		}
		return true;
	}

	/**
	 * Function that tries to rotate a Tetrisblock on a given gamegrid
	 * @return boolean True if rotation was succesfull False otherwise
	 */
	public boolean tryRotate(boolean[][] gameGrid){
		if(keskiIndeksi<0)
			return false;
		int[] pisteet = new int[blocks.size()*2];
		int index = 0;
		int xCorrection = 0;
		for(int i = 0; i<blocks.size();i++){
			int newX = blocks.get(i).getX() - blocks.get(keskiIndeksi).getX();
			int newY = blocks.get(i).getY() - blocks.get(keskiIndeksi).getY();
			int storeX = newX;
			System.out.println("Before rotation matrix " + newX + " " + newY);
			storeX = rotationMatrix[0][0] * newX + rotationMatrix[0][1] * newY;
			newY = rotationMatrix[1][0] * newX + rotationMatrix[1][1] * newY;
			newX = storeX;
			System.out.println("=== " + newX + " " + newY);
			newX += blocks.get(keskiIndeksi).getX();
			newY += blocks.get(keskiIndeksi).getY();
			System.out.println(newX + " " + newY + " real coords : "
			+ blocks.get(i).getX() + " " + blocks.get(i).getY());
			if(newX <= 0 || newY <= 0){
				if(newX<=0){
					if(newX<=xCorrection)
						xCorrection = Math.abs(newX) + 1;
				}else{
					return false;
				}
				//if(newX<=0)
					//newX = 1;
			}
			else if(newX>=gameGrid.length || newY>=gameGrid[0].length){
				System.out.println("here with " + newX + " length " + gameGrid.length);
				if(newX>=gameGrid.length){
					if(gameGrid.length - newX<=xCorrection)
						xCorrection = (gameGrid.length - newX) -1;
				}else
					return false;
			}else if(gameGrid[newX][newY])
				return false;

			pisteet[index] = newX;
			pisteet[(index+1)] = newY;
			index+=2;
			System.out.println("index : " + index);
		}

		//Final validation if the block can be moved if xCorrections was changed
		if(xCorrection!=0){
			for(int i = 0;i<pisteet.length;i+=2){
				if(gameGrid [pisteet[i]+xCorrection] [pisteet[(i+1)]])
					return false;
			}
		}

		for(int i = 0; i<pisteet.length;i++){
			System.out.println(pisteet[i]);
		}

		System.out.println("XCorrection" + xCorrection);
		index = 0;
		for(int i = 0; i < blocks.size(); i++){
			System.out.println(pisteet[index] + xCorrection);
			int x = pisteet[index] + xCorrection;
			blocks.get(i).teleport(x, pisteet[(index+1)]);
			index+=2;
			System.out.println(blocks.get(i).getX() + " " + blocks.get(i).getY());
		}
		return true;
	}

	@Override
	public Block[] getDrawables() {
		if(allBlocksDrawable)
			return (Block[])blocks.toArray(new Block[blocks.size()]);
		ArrayList<Block> res = new ArrayList<Block>();
		for(Block b : blocks){
			if(b.canBeDrawn()){
				res.add(b);
			}
		}
		return (Block[]) res.toArray(new Block[res.size()]);
	}

	@Override
	public void detachBlock(int index) {
		blocks.remove(index);
	}




}
