import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SelectPiece extends JPanel {
    static ArrayList<Map> listMap = new ArrayList<>();

    public static void main(String[] arg) {
        // listMap.add(new Map(Map.loadMap("map1.lvl")));
        // System.out.println(listMap.get(0).showMap());
        // Point p = new Point();
        // p.getLocation();
        // System.out.println(p.x);
        // System.out.println(p.y);
        boolean active = true;
        while (active) {
            Scanner terminal = new Scanner(System.in); 
            Point p = MouseInfo.getPointerInfo().getLocation();
            System.out.println(p.x);
            System.out.println(p.y);
            terminal.nextLine();
            if (p.x < 100)
                active = false;
        }
        
    }
}
