package com.gilbord.textFinder.Graphics;

import javax.swing.*;

public class ButtonPanel extends JPanel {

    private TextPanel textPanel;
    private JButton next;
    private JButton prev;

    public ButtonPanel(TextPanel textPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.textPanel = textPanel;
        this.next = new JButton("Next");
        this.prev = new JButton("Prev");
        this.next.addActionListener((e) -> this.textPanel.nextPosition());
        this.prev.addActionListener((e) -> this.textPanel.prevPosition());
        this.add(this.prev);
        this.add(this.next);
    }
}
