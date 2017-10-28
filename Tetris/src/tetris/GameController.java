package tetris;

import java.util.concurrent.ExecutionException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameController implements Runnable{

	//FXML controller
	private final TetrisVIEW_Controller controller;

	private volatile boolean playing;

	private TetrisBlock_IF block;

	private TetrisBlock_IF nextBlock;

	private GameBoard gameBoard;

	public GameController(TetrisVIEW_Controller controller){
		playing = false;
		this.controller = controller;
		block = null;
	}

	public synchronized void createGrid(int xLength, int yLength){
		if(gameBoard==null)
			gameBoard = new GameBoard(xLength, yLength, "green");
	}

	@Override
	public void run() {
		playing = true;
		//block = createBlock(false);
		//nextBlock = createBlock(true);
		//game loop
		while(playing){
			if(block == null){
				block = nextBlock;
				nextBlock = createBlock(true);
			}
			this.moveBlock(0, 1);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Gamethread has ended");
	}

	/**
	 * Simple factory method that randomly chooses what kind of block to return
	 * @param nextBlock
	 * @return TetrisBlock_IF
	 */
	private synchronized TetrisBlock_IF createBlock(boolean nextBlock){
		if(!playing)
			return null;
		System.out.println("creating a block");
		TetrisBlock block = null;
		double random = Math.random();
		int[][] coords = null;
		int[][] coords2 = null;
		if(random<0.3){
			coords = new int[][]{{5,-1}, {6,-1}, {7,-1}, {5,-2}};
			coords2 = new int[][]{{2,2}, {3,2}, {4,2}, {2,1}};
			block = new TetrisBlock(coords,"red",1);
		}else if(random<0.6){
			coords = new int[][]{{5,-1}, {6,-1}, {5,-2}, {6,-2}};
			coords2 = new int[][]{{2,2}, {3,2}, {2,1}, {3,1}};
			block = new TetrisBlock(coords,"blue",-1);
		}else{
			coords = new int[][]{{5,-1}, {6,-1}, {7,-1}, {8,-1}};
			coords2 = new int[][]{{1,2}, {2,2}, {3,2}, {4,2}};
			block = new TetrisBlock(coords,"gold",1);
		}
		//if(nextBlock){
			controller.clearNextBlockGrid();
			controller.setNextBlockGridColor(coords2, block.getColor());
		//}
		return block;
	}


	public synchronized void moveBlock(int x, int y){
		if(block == null || !playing)
			return;
		//Check if block can move right or left
		if(x!=0 && !block.canMoveHorizontally(x, gameBoard.getGameBoard())){
			return;
		//Check if block can be moved up or down
		}else if(y!=0 && !block.canMoveVertically(y, gameBoard.getGameBoard())){
			//cannot move further down so set the current gamegrid cells permanently occupied
			//Check if the whole block is inside the gamegrid. If not end the game
			if(block.getDrawables().length != block.getShapes().size()){
				this.endGame();
			}else{
				gameBoard.occupyCells(block);
				if(gameBoard.lineDropHappened()){
					controller.clearGameGrid();
					for(TetrisBlock_IF tb : gameBoard.getTetrisBlocks()){
						for(Block block : tb.getShapes()){
							controller.updateGridNodePane(block.getX(), block.getY(), tb.getColor());
						}
					}
					controller.setScore(gameBoard.getPoints());
				}
				block = null;
			}
			return;
		}

		Block[] coords = block.getDrawables();
		for(int i = 0; i<coords.length;i++){
			controller.updateGridNodePane(coords[i].getX(), coords[i].getY(), gameBoard.getColor());
		}
		block.move(x, y);
		coords = block.getDrawables();
		for(int i = 0; i<coords.length;i++){
			controller.updateGridNodePane(coords[i].getX(), coords[i].getY(), block.getColor());
		}
	}

	public synchronized void rotate(){
		if(block == null || !playing)
			return;
		System.out.println("rotating");
		Block[] coords = block.getDrawables();
		for(int i = 0; i<coords.length;i++){
			controller.updateGridNodePane(coords[i].getX(), coords[i].getY(), gameBoard.getColor());
		}
		System.out.println(block.tryRotate(gameBoard.getGameBoard()));
		coords = block.getDrawables();
		for(int i = 0; i<coords.length;i++){
			controller.updateGridNodePane(coords[i].getX(), coords[i].getY(), block.getColor());
		}
	}

	public boolean isPlaying(){
		return playing;
	}

	public void notPlaying(){
		playing = false;
	}

	public synchronized void endGame(){
		playing = false;
		gameBoard.clear();
		controller.showMessage("GAMEOVER",
				"Game is over since blocks reached the top!"
				, "Press Start button to begin a new game!");
		controller.resetView();
	}


}
