package tetris;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TetrisVIEW_Controller{

	/**
	 * GameGrid is the tetris game screen. It consist of Panes
	 */
	@FXML
	private GridPane gameGrid;
	/**
	 * GridPane that shows next block to come.
	 */
	@FXML
	private GridPane nextBlockGrid;
	@FXML
	private Button startButton;
	@FXML
	private Label scoreLabel;

	/**
	 * A list that holds all Pane objects.
	 * Pane objects are used to draw tetris blocks on screen.
	 */
	private ObservableList<Node> childrens;

	private GameController gameLogicController;

	private Thread gameThread;

	public TetrisVIEW_Controller(){
		gameLogicController = new GameController(this);
	}

	public TetrisVIEW_Controller(GridPane gameGrid, GridPane nextBlockGrid, Button startButton, Label scoreLabel) {
		this.gameGrid = gameGrid;
		this.nextBlockGrid = nextBlockGrid;
		this.startButton = startButton;
		this.scoreLabel = scoreLabel;
	}

	/**
	 *
	 */
	public synchronized void load(){
		System.out.println(gameGrid.getColumnConstraints().size() +
				" " + gameGrid.getRowConstraints().size());

		gameLogicController.createGrid(gameGrid.getColumnConstraints().size(),
				gameGrid.getRowConstraints().size());

		int xlength = gameGrid.getColumnConstraints().size();
		int ylength = gameGrid.getRowConstraints().size();
		for(int i = 0; i<xlength; i++){
			for(int j = 0; j<ylength;j++){
				Pane pane = new Pane();
				pane.setStyle("-fx-background-color:green");
				gameGrid.add(pane, i, j);
			}
		}
		childrens = gameGrid.getChildren();

		for(int i = 0; i<5; i++){
			for(int j = 0; j<5;j++){
				Pane pane = new Pane();
				pane.setStyle("-fx-background-color:cyan");
				nextBlockGrid.add(pane, i, j);
			}
		}
		//int x =
		//gameGrid.getRowConstraints().get(3) setStyle("-fx-border-color:red");
		//System.out.println(startButton.toString());
	}

	public void startGame(){
		startButton.setDisable(true);
		gameThread = new Thread(gameLogicController);
		gameThread.start();
	}

	public synchronized void updateGridNodePane(int newx, int newy, String color){
		//int calculateIndex = (oldx-1)*19+oldy;
		//childrens.get(calculateIndex).setStyle("-fx-background-color:green");
		//System.out.println("drawn");
		//if(!(newx > 0))
			//newx = 1;
		//else
		int calculatedIndex = (newx)*19+newy; //(newx-1)*19+newy
		childrens.get(calculatedIndex).setStyle("-fx-background-color:" + color);
	}

	public void moveBlock(int moveX, int moveY){
		if(gameLogicController.isPlaying())
			gameLogicController.moveBlock(moveX, moveY);
	}

	public void rotateBlock(){
		if(gameLogicController.isPlaying())
			gameLogicController.rotate();
	}

	public void endIt(){
		gameLogicController.notPlaying();
	}

	public void resetView(){
		startButton.setDisable(false);
		clearGameGrid();
	}

	public void clearGameGrid(){
		for(Node n : childrens){
			n.setStyle("-fx-background-color:green");
		}
	}

	public void clearNextBlockGrid(){
		for(Node n : nextBlockGrid.getChildren()){
			n.setStyle("-fx-background-color:cyan");
		}
	}

	public void setNextBlockGridColor(int [][] coords, String color){
		System.out.println(nextBlockGrid.getChildren().size());
		for(int [] co : coords){
			int calculatedIndex = (co[0]-1)*5+co[1];
			nextBlockGrid.getChildren().get(calculatedIndex).setStyle("-fx-background-color:" + color);
		}
	}

	public void showMessage(String Title, String Header, String msg){
		Platform.runLater(new Runnable() {
		      @Override public void run() {
		    	  Alert info = new Alert(AlertType.INFORMATION);
		          info.setTitle(Title);
		          info.setHeaderText(Header);
		          info.setContentText(msg);
		          info.showAndWait();
		      }
		    });
	}

	public void setScore(int points){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				scoreLabel.setText("" + points);
			}
		});
	}
}
