import java.util.ArrayList;

public class Roi extends Piece{
    
    public Roi(int x, int y, boolean player) {
        super(TypePiece.Roi, x, y, player, false);
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
