package gr.alexkantas.oop2.me;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alexandros Kantas
 */
public class Main extends JFrame implements Observer {

    //Interface Elements
    GridLayout grid = new GridLayout(2, 1);
    JPanel panel1 = new JPanel(grid);
    JLabel label = new JLabel("<html>Connected users: 0</html>");
    JButton button = new JButton("+");
    Model model = new Model();
    //

    public Main() {
        //Add interface components
        panel1.add(label);
        panel1.add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                model.addViewer();
            }
        });
        add(panel1);
        model.addObserver(this);
        //

        //Set JFrame properties
        setTitle("Main");
        setSize(240, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        //
    }

    @Override
    public void update(Observable o, Object o1) {
        label.setText("Connected users: " + model.getListsize());
        System.out.println(model.getText());
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
