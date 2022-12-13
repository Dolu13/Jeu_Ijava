import java.io.Console;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {

	public static int cols = 0;
	public static int rows = 0;
	public static Display display = new Display(rows, cols);
	public static int offsetX = 1;
	public static int offsetY = 0;

	public static void main(String[] args) {

		/*
		int titleClicked = 0;
		Console terminal = System.console();

		if (args.length > 1) {
			cols = Integer.parseInt(args[0]);
			rows = Integer.parseInt(args[1]);
		} else {
			//System.exit(0);
			System.out.println("Utilise launch.sh au lieu de java Scenario please");
		}
		if (cols < 180 || rows < 40) {
			System.out.println("Ecran trop petit");
			System.exit(84);
		}
		System.out.println(String.join("\n", (display.cropImage(display.loadImage("../data/images/titleblink.ascii"), (cols - 189) / 2, (cols - 189) / 2, rows - 42, 0,' '))));
		Game.wait(2000);
		System.out.println(String.join("\n", (display.cropImage(display.loadImage("../data/images/titlescreen.ascii"), (cols - 190) / 2, (cols - 190) / 2, rows - 42, 0,' '))));
		terminal.readLine();
		*/

		boolean end = false;
		boolean finished = false;

		int lvl = 1;
		Map map = new Map("map"+lvl, lvl);

		HashMap<Integer, Integer> ennemiesPerLevel = new HashMap<Integer, Integer>();
		ennemiesPerLevel.put(1, 3);
		ennemiesPerLevel.put(2, 3);
		ennemiesPerLevel.put(3, 3);
		ennemiesPerLevel.put(4, 3);
		ennemiesPerLevel.put(5, 3);
		ennemiesPerLevel.put(6, 3);
		boolean ennemiesSpawned = false;
		
		boolean changeLevel = false;
		
		// SelectPiece.fixPosition();

		Game.init(map);

		while (!end && !finished) {
			if (changeLevel) {
				System.out.println("Bravo ! Vous etes passe au niveau "+lvl+" !");
				Game.wait(5000);
				ArrayList<Piece> allies = map.getAllies();
				map = new Map("map"+lvl, lvl);
				ArrayList<Position> possiblePlaces = map.getPossiblePlaces();
				Collections.sort(possiblePlaces);
				int idx = 0;
				while (allies.size() > 0 && idx < possiblePlaces.size()) {
					allies.get(0).move(possiblePlaces.get(idx).getX(), possiblePlaces.get(idx).getY());
					idx++;
					map.addPiece(allies.get(0));
					allies.remove(0);
				}

				changeLevel = false;
			}

			if (!ennemiesSpawned) {
				// Ennemis pas encore apparus
				for (int i=0; i<ennemiesPerLevel.get(lvl); i++) {
					map.addEnnemisAlea();
				}
				ennemiesSpawned = true;
			}
			Game.show(map);
			System.out.println("Quel pion souhaitez vous choisir ?");
			Piece toMove = Game.getToMove(map);
			
			ArrayList<Position> possibleMoves = toMove.getPossibleMoves(map);
			ArrayList<Position> possibleEats = toMove.getPossibleEats(map);

			map.setPossibleMoves(possibleMoves);
			map.setPossibleEats(possibleEats);

			Game.show(map);

			Position newPos = null;
			Collections.sort(possibleMoves);
			Collections.sort(possibleEats);

			do {
				if (newPos != null) {
					System.out.println("Ce deplacement n'est pas permis.");
					newPos = null;
				}
				System.out.println("Ou voulez-vous deplacer votre pion ?");
				Game.show(map);
				if (possibleEats.size() > 0) {
					System.out.println("Vous pouvez manger :");
					Game.showMoves(possibleEats);
				}
				newPos = Game.getPos(map);
			} while (!(Game.isInPos(possibleMoves, newPos) || Game.isInPos(possibleEats, newPos)));
			
			if (map.getPiece(newPos.getX(), newPos.getY()) != null) {
				// Il y a une pi�ce : On la mange
				Piece toAdd = null;
				if (map.getPiece(newPos.getX(), newPos.getY()).getTypePiece().randDrop()) {
					// On drop la piece
					toAdd = map.getPiece(newPos.getX(), newPos.getY());
					toAdd.setPlayer(true);
				}
				map.eatPiece(toMove, newPos.getX(), newPos.getY());
				if (toAdd != null) {
					map.addPiece(toAdd);
				}
				if (map.getEnnemies().size() == 0) {
					lvl++;
					if (lvl == 7) {
						finished = true;
					}
					changeLevel = true;
					ennemiesSpawned = false;
				}
			} else if (map.isExit(newPos.getX(), newPos.getY())) {
				// Sortie prise
				lvl++;
				if (lvl == 7) {
					finished = true;
				}
				changeLevel = true;
				ennemiesSpawned = false;
			} else {
				toMove.move(newPos.getX(), newPos.getY());
			}
			if (!changeLevel) {
				map.setPossibleMoves(new ArrayList<Position>());
				map.setPossibleEats(new ArrayList<Position>());
				
				// Le joueur a jou� - Au tour des ennemis
				if (map.getEnnemies().size() > 0) {
					
					Piece ennemyWhoWillEat = map.randomEater(map.getAllies(), map.getEnnemies());
					
					if (ennemyWhoWillEat != null) {
						map.setPossibleMoves(ennemyWhoWillEat.getPossibleMoves(map));
						map.setPossibleEats(ennemyWhoWillEat.getPossibleEats(map));
						Game.show(map);
						map.setPossibleMoves(new ArrayList<Position>());
						map.setPossibleEats(new ArrayList<Position>());
					} else {
						Game.show(map);
					}
		
					Game.wait(2000);
					
					// Voir si un ennemi ne peut pas manger un pion
					if (!map.randomEat(map.getAllies(), map.getEnnemies())) {
						// Aucune possibilit� de mangeage - D�placement
						Random rdm = new Random();
						map.getEnnemies().get(rdm.nextInt(map.getEnnemies().size())).moveAlea(map);
					} else {
						if (map.getAllies().size() == 0) {
							end = true;
						}
					}
				}
	
				Game.show(map);
			}
		}
		if (end) {
			System.out.println("Vous avez perdu !");
		} else if (finished) {
			System.out.println("Felicitations, vous avez gagne !");
		}
	}

	public static void init(Map map) {
		map.addPiece(new Tour(2, 1, true));
	}
	
	public static void wait(int ms) {
		try {
			Thread.sleep(ms);
		}
		catch(InterruptedException e){}
	}

	public static void show(Map map) {
		System.out.println(String.join("\n", Textured.getMap(map)));
	}

	public static Position getPos(Map map) {
		Scanner sc = new Scanner(System.in);
		Integer x = null;
		Integer y = null;

		do {
			if (x != null && y != null) {
				if (map.isOutOfBounds(x, y)) {
					System.out.println("Hors de la carte, veuillez reessayer.");
				} else if (map.isWall(x, y)) {
					System.out.println("C'est un mur!");
				} else if (map.isObstacle(x, y)) {
					System.out.println("C'est un arbre !");
				}
				x = null;
				y = null;
			}

			while (x == null) {
				try {
					System.out.print("Position x : ");
					x = Integer.valueOf(sc.next());
				} catch (NumberFormatException e) {
					System.out.println("Ce n'est pas une position correcte, veuillez reessayer.");
				}
			}
			while (y == null) {
				try {
					System.out.print("Position y : ");
					y = Integer.valueOf(sc.next());
				} catch (NumberFormatException e) {
					System.out.println("Ce n'est pas une position correcte, veuillez reessayer.");
				}
			}
			x = x+Game.offsetX;
			y = y+Game.offsetY;
			/*Position p = SelectPiece.getCase();
			x = p.getX();
			y = p.getY();
			System.out.println(x+" "+y);*/
		} while (map.isOutOfBounds(x, y) || map.isWall(x, y) || map.isObstacle(x, y));
		return new Position(x, y);

	}

	public static Piece getToMove(Map map) {
		Scanner sc = new Scanner(System.in);
		Position p = null;

		do {
			if (p != null) {
				if (map.getPiece(p.getX(), p.getY()) == null) {
					System.out.println("Il n'y a pas de pion ici !");
				} else if (!map.getPiece(p.getX(), p.getY()).isPlayer()) {
					System.out.println("C'est un ennemi");
				}
			}
			p = Game.getPos(map);
			//p = SelectPiece.getCase();
		} while (map.getPiece(p.getX(), p.getY()) == null || !map.getPiece(p.getX(), p.getY()).isPlayer());
		return map.getPiece(p.getX(), p.getY());
	}

	public static void showMoves(ArrayList<Position> moves) {
		for (Position move: moves ) {
			System.out.print(move.getX()-1+" "+move.getY()+", ");
		}
		System.out.println("\n");
	}
	
	public static boolean isInPos(ArrayList<Position> positions, Position p) {
		boolean result = false;
		int idx = 0;
		while (!result && idx<positions.size()) {
			result = positions.get(idx).equals(p);
			idx++;
		}
		return result;
	}

}