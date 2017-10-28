package tetris;

import java.util.List;
/**
 *An Object that implements this interface consist of one or more Blocks.
 *Blocks can be managed as a group trough this interface e.g moving entire
 *TetrisBlock to the left by one or rotate the entire TetrisBlock by 90 degrees.
 */
public interface TetrisBlock_IF extends TetrisBlockMaster_IF{
	public List<Block> getShapes();
	public Block[] getDrawables();
	public String getColor();
	public void setColor(String colorName);
	/**
	 * Tries to rotate itself on the given gameGrid.
	 * If rotation is possible occupies the gamegrid cells after rotation.
	 * @param gameGrid
	 * @return
	 */
	public boolean tryRotate(boolean[][] gameGrid);
	/**
	 * Detach a block piece from the whole tetris block
	 * @param index
	 */
	public void detachBlock(int index);
}
