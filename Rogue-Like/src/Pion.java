import java.util.ArrayList;

public class Pion extends Piece {
	
	public Pion(int x, int y, boolean player) {
		super(TypePiece.Pion, x, y, player, false);
        this.addMove(new Deplacement(-1, 1, false, true));
        this.addMove(new Deplacement(0, 1, true, false));
        this.addMove(new Deplacement(1, 1, false, true));
        this.addMove(new Deplacement(-1, 0, true, false));
        this.addMove(new Deplacement(1, 0, true, false));
        this.addMove(new Deplacement(-1, -1, false, true));
        this.addMove(new Deplacement(0, -1, true, false));
        this.addMove(new Deplacement(1, -1, false, true));
	}
	

}
