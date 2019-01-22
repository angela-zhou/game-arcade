package tetrisPuzzle;

import javafx.scene.layout.Region;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * PlaceHolder.java 
 */
public class PlaceHolder extends Region {

    final private int row;
    final private int column;

    private TetrisBlock block;

    public PlaceHolder(int row, int col, Board board) {
        this.row = row;
        this.column = col;

        // start out with a blank tile
        placeBlock(block);

    }

    public void placeBlock(TetrisBlock newBlock) {
        // remove the blank tile already there
        getChildren().clear();
        // update reference to tile in this place
        this.block = newBlock;
        // ensure block is displayed in the region
        getChildren().add(block);
    }

    public TetrisBlock getBlock() {
        return block;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
