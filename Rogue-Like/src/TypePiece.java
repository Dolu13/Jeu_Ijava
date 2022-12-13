import java.util.Random;

public enum TypePiece {
	Pion('P', 100),Cavalier('H', 40),Fou('F',30),Tour('T',20),Roi('K', 50),Reine('Q', 0);

	private char carac;
	private int drop;
	TypePiece(char carac, int drop){
		this.carac = carac;
		this.drop = drop;
	}

	public char getCarac(){
		return this.carac;
	}

	public boolean randDrop() {
		Random r = new Random();
		int low = 1;
		int high = 100;
		int result = r.nextInt(high-low) + low;
		if (result <= this.drop) {
			return true;
		} else {
			return false;
		}
	}
}
