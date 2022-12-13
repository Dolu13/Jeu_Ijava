import java.util.ArrayList;

public class Tour extends Piece{
	
    public Tour(int x, int y, boolean player) {
		super(TypePiece.Tour, x, y, player, true);		
		this.addMove(new Deplacement(1, 0, true, true));
		this.addMove(new Deplacement(-1, 0, true, true));
		this.addMove(new Deplacement(0, 1, true, true));
		this.addMove(new Deplacement(0, -1, true, true));
	}
	


	/*public ArrayList<Position> getPossibleMoves(Map map){
		ArrayList<Position> autorise = new ArrayList<Position>();
        int i = 1;
        boolean valide = true;
        while(valide){
            if(map.map.get(this.getX()-i).charAt(this.getY()) == '.'){
			    autorise.add(new Position(this.getX()-i, this.getY()));
		    }else{
                valide = false;
            }
            i++;
        }
        valide = true;
        while(valide){
            if(map.map.get(this.getX()+i).charAt(this.getY()) == '.'){
			    autorise.add(new Position(this.getX()+i, this.getY()));
		    }else{
                valide = false;
            }
            i++;
        }
        
        valide = true;
        while(valide){
            if(map.map.get(this.getX()).charAt(this.getY()+i) == '.'){
			    autorise.add(new Position(this.getX(), this.getY()+i));
		    }else{
                valide = false;
            }
            i++;
        }
        valide = true;
        while(valide){
            if(map.map.get(this.getX()).charAt(this.getY()-i) == '.'){
			    autorise.add(new Position(this.getX()-i, this.getY()));
		    }else{
                valide = false;
            }
            i++;
        }
        return autorise;
    }*/
}
