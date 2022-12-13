import java.util.ArrayList;

public class Tour extends Piece{
	
    public Tour(int x, int y, boolean player) {
		super(TypePiece.Tour, x, y, player, true);		
		this.addMove(new Deplacement(1, 0, true, true));
		this.addMove(new Deplacement(-1, 0, true, true));
		this.addMove(new Deplacement(0, 1, true, true));
		this.addMove(new Deplacement(0, -1, true, true));
	}
}
