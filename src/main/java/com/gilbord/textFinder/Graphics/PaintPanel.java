package com.gilbord.textFinder.Graphics;

import com.gilbord.textFinder.FileParser;
import com.gilbord.textFinder.TreeBuilder;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class PaintPanel extends JPanel {

    private JTabbedPane tabs;
    private JScrollPane pane;
    private int maxWindow;
    private static final String DEFAULT_EXTENSION = ".txt";
    private String extension;
    private String findingText;

    public PaintPanel() {
        this.extension = DEFAULT_EXTENSION;
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
    }

    public void findFiles() {
        new Thread(() -> {
            java.util.List<File> files = null;
            String directory = getDirectory();
            this.findingText = getFindingText();
            try {
                files = new FileParser().find(directory,
                        this.findingText,
                        getExtension());
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (File file : files) {
                System.out.println(file);
            }
            if (this.pane != null) {
                this.remove(this.pane);
            }
            if (this.tabs != null) {
                this.remove(this.tabs);
            }
            this.tabs = new JTabbedPane();
            this.pane = new JScrollPane(getTree(files));
            this.pane.setMaximumSize(new Dimension(this.getWidth() * 10 / 4, Integer.MAX_VALUE));
            this.add(this.pane);
            this.add(this.tabs);
            this.revalidate();
        }).start();
    }

    public String getDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        File file = null;
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(true);
        int ret = fileChooser.showDialog(null, "Open file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        return file.getPath();
    }

    public String getFindingText() {
        return (String) JOptionPane.showInputDialog(
                this,
                "Find :",
                "Find Text",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
    }

    public String getExtension() {
        return this.extension;
    }

    public JTree getTree(List<File> files) {
        JTree tree = null;
        try {
            tree = new TreeBuilder().build(files);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tree.getSelectionModel().addTreeSelectionListener(e -> {
            if (Paths.get(new FileParser().getPathFromTreePath(e.getPath())).toFile().isFile()) {
                newWindow(e.getPath());
            }
        });
        tree.setShowsRootHandles(true);
        return tree;
    }

    public void newWindow(TreePath file) {
        new Thread(() -> {
            this.tabs.add(((Integer) maxWindow++).toString(), new TextPanel(file, this.findingText));
            this.repaint();
        }).start();
    }

    public void deleteWindow() {
        try {
            this.tabs.removeTabAt(this.tabs.getSelectedIndex());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void changeSettings() {
        this.extension = (String) JOptionPane.showInputDialog(
                this,
                "extension of finding files :",
                "Settings",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                DEFAULT_EXTENSION);
        if (this.extension == null) {
            this.extension = DEFAULT_EXTENSION;
        }
    }

}
