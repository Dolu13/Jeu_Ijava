import java.util.ArrayList;

public class Reine extends Piece{
    public Reine(int x, int y, boolean player) {
		super(TypePiece.Reine, x, y, player, true);
        this.addMove(new Deplacement(-1, 1, true, true));
        this.addMove(new Deplacement(0, 1, true, true));
        this.addMove(new Deplacement(1, 1, true, true));
        this.addMove(new Deplacement(-1, 0, true, true));
        this.addMove(new Deplacement(1, 0, true, true));
        this.addMove(new Deplacement(-1, -1, true, true));
        this.addMove(new Deplacement(0, -1, true, true));
        this.addMove(new Deplacement(1, -1, true, true));
	}
	


}