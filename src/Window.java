import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    private int WIDTHWINDOW;
    private int HEIGHTWINDOW;

    public Window(String title, Game game){
        //create frame
        JFrame frame = new JFrame(title);

        //get the screensize
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.WIDTHWINDOW = screenSize.width;
        this.HEIGHTWINDOW = screenSize.height;

        frame.setPreferredSize(new Dimension(WIDTHWINDOW, HEIGHTWINDOW));//set the size of the dimension of the frame
        frame.setMaximumSize(new Dimension(WIDTHWINDOW, HEIGHTWINDOW));
        frame.setMinimumSize(new Dimension(WIDTHWINDOW, HEIGHTWINDOW));

        //make the game fullscreen:
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//makes the exit button int the top right corner work
        frame.setResizable(false);//makes sure you cannot resize the window which could generate a lot of problems
        frame.setLocationRelativeTo(null);//the window is locked to the middle of the screen now (null does that)
        frame.add(game);//add the Game class (the main class u just created) to the frame
        frame.setVisible(true);//makes the frame visible
        game.start();//runs the start method you created in your Game class
    }

    public int getWIDTHWINDOW() {
        return WIDTHWINDOW;
    }

    public int getHEIGHTWINDOW() {
        return HEIGHTWINDOW;
    }
}
