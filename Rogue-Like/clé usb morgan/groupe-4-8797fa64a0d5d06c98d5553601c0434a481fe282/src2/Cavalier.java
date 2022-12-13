import java.util.ArrayList;

public class Cavalier extends Piece {
    	
    public Cavalier (int x, int y, boolean player) {
        super(TypePiece.Cavalier, x, y, player, false);
        this.addMove(new Deplacement(-1, -2, true, true));
        this.addMove(new Deplacement(1, -2, true, true));
        this.addMove(new Deplacement(-2, -1, true, true));
        this.addMove(new Deplacement(2, -1, true, true));
        this.addMove(new Deplacement(-1, 2, true, true));
        this.addMove(new Deplacement(1, 2, true, true));
        this.addMove(new Deplacement(-2, 1, true, true));
        this.addMove(new Deplacement(2, 1, true, true));
    }


}