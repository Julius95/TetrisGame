package tetris;

public class BlockPiece implements Block{
	private int x;
	private int y;
	private boolean isMoving;

	public BlockPiece(int x, int y){
		this.x = x;
		this.y = y;
		isMoving = false;
	}

	public boolean canMoveHorizontally(int numberOfXToMove, boolean[][] gameGrid){
		if(numberOfXToMove + x >= gameGrid.length)
			return false;
		else if(numberOfXToMove + x < 0)
			return false;
		else if(y<0 || gameGrid[x+numberOfXToMove][y])
			return false;
		return true;
	}

	public boolean canMoveVertically(int numberOfYToMove, boolean[][] gameGrid){
		if(numberOfYToMove + y <= 0)
			return true;
		if(numberOfYToMove + y >= gameGrid[0].length || gameGrid[x][y + numberOfYToMove])
			return false;
		return true;
	}

	public void dropdown(){
		if(y!=0 && y%18==0)
			y=0;
		else
			y++;
	}

	public boolean canBeDrawn(){
		if(y<0)
			return false;
		return true;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
		//System.out.println("Moving to x : " + this.x );
	}

	@Override
	public void teleport(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
