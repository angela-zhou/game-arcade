package tetrisPuzzle;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * TetrisPuzzleGame.java 
 */
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TetrisPuzzleGame extends Application{
	
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	 
	enum BlockType { 
	    O_BLOCK, I_BLOCK, Z_BLOCK, S_BLOCK, L_BLOCK, T_BLOCK, J_BLOCK; 
	} 
	
	enum BlockClass {
		SQUARE_BLOCK, LINE_BLOCK, TWO_AND_TWO_BLOCK, THREE_AND_ONE_BLOCK;
	}
	
	BlockType[] btArray = {BlockType.O_BLOCK, BlockType.I_BLOCK, BlockType.Z_BLOCK, BlockType.S_BLOCK, 
							BlockType.L_BLOCK, BlockType.T_BLOCK, BlockType.J_BLOCK};
	
	BlockClass[] bcArray = {BlockClass.SQUARE_BLOCK, BlockClass.LINE_BLOCK, BlockClass.TWO_AND_TWO_BLOCK, 
							BlockClass.THREE_AND_ONE_BLOCK};

	Group root;
	TetrisBlock block;
	Text upcoming;

	@Override
	public void start(Stage myStage) throws Exception {

		root = new Group();
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

		block = getBlock();

		upcoming = new Text(5, 15, block.toString());
		root.getChildren().add(upcoming);

		scene.setOnMouseClicked(event -> placeBlock(event));

		myStage.setTitle("Tetris");
		myStage.setScene(scene);
		myStage.show();

	}

	private void placeBlock(MouseEvent event) {

		//snap block to grid
		int X = ((int) event.getX() / TetrisBlock.SIZE) * TetrisBlock.SIZE;
		int Y = ((int) event.getY() / TetrisBlock.SIZE) * TetrisBlock.SIZE;

		//set the location to X and Y and draw the block
		block.setLocation(X, Y);
		block.draw();

		// get a new block and display its description in the Text object
		block = getBlock();
		upcoming.setText(block.toString());
	}

	private TetrisBlock getBlock() {
		TetrisBlock block;
		
		// creating a block at random, the seven BlockTypes have equal probability of showing up 
		switch (randomBlock()) {
		case O_BLOCK:
			block = new SquareBlock();
			break;
		case I_BLOCK:
			block = new LineBlock(randomAngle(BlockClass.LINE_BLOCK));
			break;
		case Z_BLOCK:
			block = new TwoAndTwoBlock(-1, randomAngle(BlockClass.TWO_AND_TWO_BLOCK));
			break;
		case S_BLOCK:
			block = new TwoAndTwoBlock(1, randomAngle(BlockClass.TWO_AND_TWO_BLOCK));
			break;
		case L_BLOCK:
			block = new ThreeAndOneBlock(1, randomAngle(BlockClass.THREE_AND_ONE_BLOCK));
			break;
		case T_BLOCK:
			block = new ThreeAndOneBlock(2, randomAngle(BlockClass.THREE_AND_ONE_BLOCK));
			break;
		case J_BLOCK:
			block = new ThreeAndOneBlock(3, randomAngle(BlockClass.THREE_AND_ONE_BLOCK));
			break;
		default:
			block = new SquareBlock();
			break;
		}
		
		// adding block to the scene graph
		root.getChildren().add(block);
		
		return block;
	}
	
	private BlockType randomBlock() {
		// method to assist getBlock() with creating random TetrisBlocks
		// returns random int from 0-6
	    int index = (int) (Math.random() * btArray.length);
	    return btArray[index];
	}
	
	private int randomAngle(BlockClass bc) {
		int angle;
		
		// randomly generate angle for specific case
		switch (bc) {
		case LINE_BLOCK:
			angle = (Math.random() < 0.5) ? 0 : 90; // randomly generate 0 or 90
			break;
		case TWO_AND_TWO_BLOCK:
			angle = (Math.random() < 0.5) ? 0 : 90; // randomly generate 0 or 90
			break;
		case THREE_AND_ONE_BLOCK:
			angle = (int) (Math.random() * 4) * 90; // randomly generate 0, 90, 180, or 270
			break;
		default:
			angle = 0; // all blocks that implement Orientable can have angle of 0
			break;
		}
		return angle;
	}

	public static void main(String[] args) {
		launch(args);
	}


}
