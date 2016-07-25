/*
 * Copyright 2016 Alexandros Kantas .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gr.alexkantas.multipleusereditor;

import java.awt.GridLayout;
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
    static Model model = new Model();
    //

    
    public Main() {
        //Add interface components
        panel1.add(label);
        panel1.add(button);
        button.addActionListener(e -> model.addViewer());
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
