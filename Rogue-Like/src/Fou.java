import java.util.ArrayList;

public class Fou extends Piece{
    public Fou(int x, int y, boolean player) {
		super(TypePiece.Fou, x, y, player, true);
        this.addMove(new Deplacement(-1, 1, true, true));
        this.addMove(new Deplacement(1, 1, true, true));
        this.addMove(new Deplacement(-1, -1, true, true));
        this.addMove(new Deplacement(1, -1, true, true));
	}
	

}