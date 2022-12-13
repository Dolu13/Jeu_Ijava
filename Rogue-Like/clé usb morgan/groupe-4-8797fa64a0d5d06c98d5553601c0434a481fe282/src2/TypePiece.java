
public enum TypePiece {
	Pion('♙'),Cavalier('♘'),Fou('♗'),Tour('♖'),Roi('♔'),Reine('♕');

	private char carac;

	TypePiece(char carac){
		this.carac = carac;
	}

	char getCarac(){
		return this.carac;
	}
}
