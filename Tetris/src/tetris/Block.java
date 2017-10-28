package tetris;

public interface Block extends TetrisBlockMaster_IF{
	public int getX();
	public int getY();
	public boolean canBeDrawn();
	public void teleport(int x, int y);
}
