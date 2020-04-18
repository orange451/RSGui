package scripts.rsgui.backend;

public interface RSGuiMouseListener {
	public boolean onMousePress( int x, int y );
	public void onMouseDown( int x, int y );
	public void onMouseUpdate( int x, int y );
}