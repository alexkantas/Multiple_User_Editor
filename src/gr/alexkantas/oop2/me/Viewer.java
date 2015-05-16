package gr.alexkantas.oop2.me;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
 * @author Alex
 */
public class Viewer extends JFrame implements Observer, ActionListener, DocumentListener {

//Interface Elements
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    BorderLayout borderlayout = new BorderLayout();
    JTextPane txtarea = new JTextPane();
    JScrollPane scrollPane = new JScrollPane(txtarea);
    JButton logbtn = new JButton("Log out");
    JList list = new JList();
    JScrollPane scrollPane2 = new JScrollPane(list);
    Model model;
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset ;
    //

    public Viewer(String title, Model model) {
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
        
        if (title.equals("Viewer #1"))
        aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
        else
            aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLUE);

        this.model = model;

        //Set JFrame properties
        setTitle(title);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        //

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

}
