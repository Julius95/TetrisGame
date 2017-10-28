package tetris;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

	private Stage mainStage;
	private TetrisVIEW_Controller controller;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		AnchorPane Layout=null;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("View.fxml"));
		try {
			Layout = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(Layout);
		//Set up callback functions for keypresses
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent keyEvent) {
	        	switch(keyEvent.getCode()){
		        	case UP://Up key pressed
		        		controller.rotateBlock();
		        		break;
		        	case DOWN:
		        		controller.moveBlock(0,1);//Move block down
		        		break;
		        	case LEFT:
		        		controller.moveBlock(-1,0);//Move block left
		        		break;
		        	case RIGHT:
		        		controller.moveBlock(1,0);//Move block right
		        		break;
				default:
					break;
	        	}
	        }
		}
		);
		controller.load();
		mainStage.setScene(scene);
		mainStage.setTitle("otsikko");
		mainStage.show();
	}

	@Override
	public void stop(){
	    System.out.println("Stage is closing");
	    controller.endIt();
	}
}
