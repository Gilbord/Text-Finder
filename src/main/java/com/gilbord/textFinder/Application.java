package com.gilbord.textFinder;

import com.gilbord.textFinder.Graphics.PaintPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Application extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    private static final String TITLE = "Text Finder";

    private PaintPanel paintPanel;
    private JMenuBar menuBar;

    public Application() {
        this.paintPanel = new PaintPanel();
        this.menuBar = new JMenuBar();
        initMenu();
        initFrame();
    }

    public void initMenu() {
        JMenuItem item;
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        item = new JMenuItem("Find files");
        item.setMnemonic(KeyEvent.VK_D);
        item.addActionListener((ActionEvent) -> this.paintPanel.findFiles());
        menu.add(item);
        this.menuBar.add(menu);

        menu = new JMenu("Settings");
        menu.setMnemonic(KeyEvent.VK_F);
        item = new JMenuItem("Change Settings");
        item.setMnemonic(KeyEvent.VK_D);
        item.addActionListener((ActionEvent) -> this.paintPanel.changeSettings());
        menu.add(item);
        this.menuBar.add(menu);
    }

    public void initFrame() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setJMenuBar(this.menuBar);
        this.add(paintPanel);
    }

    public static void main(String[] argc) {
        SwingUtilities.invokeLater(() -> new Application());
    }

}
