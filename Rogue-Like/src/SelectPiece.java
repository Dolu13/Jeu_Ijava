import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SelectPiece extends JPanel {

    public static Point firstCase;
    public static Point lastCase;
    public static int height;
    public static int width;

    public SelectPiece () {}

    public static void fixPosition() {
    	Scanner sc = new Scanner(System.in); 
        Console terminal = System.console();
        System.out.println(String.join("\n", Display.loadImage("../data/images/clickRatio.ascii")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        System.out.println(String.join("\n", Display.loadImage("../data/images/clickRatio1.ascii")));
        firstCase = getPointer();
        System.out.println(String.join("\n", Display.loadImage("../data/images/clickRatio2.ascii")));
        lastCase = getPointer();
        height = (lastCase.y - firstCase.y) * 180 / 179;
        width = (lastCase.x - firstCase.x) * 36 / 35;
        firstCase.x -= width / 360;
        firstCase.y -= height / 72;
        lastCase.x += width / 360;
        lastCase.y += height / 72;
    }

    public static Position getCase() {
        return onGrid(getPointer());
    }

    private static Position onGrid(Point mouse) {
        Position p;
        for (int x = 0; x < 60; x++) {
            for (int y = 0; y < 12; y++) {
                if (mouse.x > (firstCase.x + ((x * width) / 60)) && mouse.x < (firstCase.x + (((x+1) * width) / 60)) && mouse.y > (firstCase.y + ((y * height) / 12)) && mouse.y < (firstCase.y + (((y+1) * height) / 12))) {
                    p = new Position(x,y);
                    return p;
                }
            }
        }
        return null;
    }

    public static Point getPointer() {
        Console jeanjak = System.console();
        jeanjak.readLine();
        Point p = MouseInfo.getPointerInfo().getLocation();
        return p;
    }
}
