package tetris;

public interface TetrisBlockMaster_IF {
	public void move(int x, int y);
	public boolean canMoveHorizontally(int numberOfXToMove, boolean[][] gameGrid);
	public boolean canMoveVertically(int numberOfYToMove, boolean[][] gameGrid);
}
