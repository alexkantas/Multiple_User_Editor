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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Alexandros Kantas
 */
public class Viewer extends JFrame implements Observer, ActionListener, DocumentListener {

    //Interface Elements
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private BorderLayout borderlayout = new BorderLayout();
    private JTextPane txtarea = new JTextPane();
    private JScrollPane scrollPane = new JScrollPane(txtarea);
    private JButton logbtn = new JButton("Log out");
    private JList list = new JList();
    private JScrollPane scrollPane2 = new JScrollPane(list);
    private Model model = Main.model;
    private String title;
    private StyleContext sc = StyleContext.getDefaultStyleContext();
    private AttributeSet aset;
    //

    public Viewer(String title) {
        this.title = title;
        initialize();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (logbtn.getText().equals("Log out")) {
            model.removeViewer(this);
            txtarea.getDocument().removeDocumentListener(this);
            logbtn.setText("Log in");
        } else {
            model.addViewer(this);
            txtarea.getDocument().addDocumentListener(this);
            logbtn.setText("Log out");
        }
    }
    
    @Override
    public void update(Observable o, Object o1) {
        txtarea.setText(model.getText());
    }
    
    @Override
    public void insertUpdate(DocumentEvent de) {
        model.deleteObserver(this);
        int len = txtarea.getDocument().getLength();
        txtarea.setCaretPosition(len);
        txtarea.setCharacterAttributes(aset, false);
        model.setText(txtarea.getText());
        model.addObserver(this);
    }
    
    @Override
    public void removeUpdate(DocumentEvent de) {
        model.deleteObserver(this);
        model.setText(txtarea.getText());
        model.addObserver(this);
    }
    
    @Override
    public void changedUpdate(DocumentEvent de) {
        
    }
    
    public void initialize() {
        //Add interface components
        txtarea.getDocument().addDocumentListener(this);
        panel1.add(txtarea);
        panel2.setLayout(borderlayout);
        logbtn.addActionListener(this);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        panel2.add(logbtn, BorderLayout.PAGE_START);
        panel2.add(list, BorderLayout.CENTER);
        add(panel1, BorderLayout.CENTER);
        add(panel2, BorderLayout.LINE_END);
        //

        if (title.equals("Viewer #1")) {
            aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
        } else {
            aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLUE);
        }
        
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                model.deleteObserver(Viewer.this);
                super.windowClosing(e);
            }
            
        });

        //Set JFrame properties
        setTitle(title);
        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        //
    }
    
}
