package constants;

import java.awt.Dimension;

public final class Constants {

	private Constants() {

	}
	public static final Dimension WINDOW_SIZE = new Dimension(1600, 900);
	
	public static final Dimension TREE_SIZE = new Dimension(250, 900);
	
	public static final int TREE_VIEW_HEIGHT = 900;
	
	public static final double TREE_PANE_FACTOR = 0.65;
	
	public static final double FREE_DOC_FACTOR = 0.2;
	
	public static final Dimension INTERNAL_FRAME_SIZE = new Dimension(770, 770);
	
	public static final int PAGE_BOTTOM_MARGIN = 15;
	
	public static final int PAGE_TOP_MARGIN = 15;
	
	public static final int PAGE_WIDTH = 480;
	
	public static final int PAGE_HEIGHT = (int) Math.round(Math.sqrt(2) * PAGE_WIDTH);
	
	public static final Dimension PAGE_SIZE = new Dimension(PAGE_WIDTH,
			PAGE_TOP_MARGIN + PAGE_BOTTOM_MARGIN + PAGE_HEIGHT);
	
	public static final int CASCADE_OFFSET = 10;
}