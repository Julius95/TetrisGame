package testUnits;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import tetris.GameController;
import tetris.TetrisVIEW_Controller;

public class GameAndViewControllerTests {

	private static GridPane gameGrid = new GridPane();

	private static GridPane nextBlockGrid = new GridPane();

	private static Button startButton = new Button();

	private static Label scoreLabel = new Label();

	private static TetrisVIEW_Controller Vcon;

	private static GameController Gcon;

	@BeforeClass
	public static void init(){

		Vcon = new TetrisVIEW_Controller();
		Gcon = new GameController(Vcon);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
