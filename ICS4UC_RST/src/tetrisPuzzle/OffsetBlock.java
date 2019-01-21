package tetrisPuzzle;

/**
 * @author Angela Zhou
 * Date: Jan 2019
 * Course: ICS4U
 * Teacher: Mrs. Spindler
 * OffsetBlock.java
 */
public abstract class OffsetBlock extends TetrisBlock {
	protected int offset;

	public OffsetBlock(int shift) {
		super();
		setOffset(shift);
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int shift) {
		this.offset = shift;
	}

}