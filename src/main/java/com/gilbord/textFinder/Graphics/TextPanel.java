package com.gilbord.textFinder.Graphics;

import com.gilbord.textFinder.FileParser;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TextPanel extends JPanel {

    private ArrayList<Integer> positions;
    private JTextArea area;
    private ButtonPanel buttonPanel;
    private String findingText;
    private int currentPosition;

    public TextPanel(TreePath file, String findingText) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.findingText = findingText;
        this.buttonPanel = new ButtonPanel(this);
        this.positions = new ArrayList<>();
        this.currentPosition = 0;
        SwingUtilities.invokeLater(() -> {
            try {
                new FileParser().getFindingTextPositions(file, findingText, positions);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        JScrollPane scrollPane;
        try {
            this.area.read(
                    new BufferedReader(
                            new InputStreamReader
                                    (new FileInputStream
                                            (new FileParser().getPathFromTreePath(file)))), file.getPath().toString());
            this.area.setText(new FileParser().getText(file));
            this.area.setCaretPosition(this.positions.get(this.currentPosition));
            highLightWord();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.area.setEditable(false);
        scrollPane = new JScrollPane(area);
        this.add(scrollPane);
        this.add(this.buttonPanel);
    }

    public int getCurrentPosition() {
        return this.positions.get(this.currentPosition);
    }

    public void highLightWord() {
        Highlighter highlighter = this.area.getHighlighter();
        Highlighter.HighlightPainter painter =
                new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
        int p0 = getCurrentPosition();
        int p1 = p0 + this.findingText.length();
        try {
            highlighter.removeAllHighlights();
            highlighter.addHighlight(p0, p1, painter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void nextPosition() {
        this.area.setCaretPosition(getNextPosition());
    }

    public void prevPosition() {
        this.area.setCaretPosition(getPrevPosition());
    }

    public int getNextPosition() {
        if (this.currentPosition == this.positions.size() - 1) {
            this.currentPosition = 0;
        } else {
            this.currentPosition++;
        }

        highLightWord();
        return this.positions.get(this.currentPosition);
    }

    public int getPrevPosition() {
        if (this.currentPosition == 0) {
            this.currentPosition = this.positions.size() - 1;
        } else {
            this.currentPosition--;
        }
        highLightWord();
        return this.positions.get(this.currentPosition);
    }

}
