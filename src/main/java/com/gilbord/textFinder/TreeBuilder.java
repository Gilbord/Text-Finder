package com.gilbord.textFinder;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class TreeBuilder {


    public JTree build(List<File> files) throws FileNotFoundException {
        TreePath[] treePaths = new TreePath[files.size()];
        for (int i = 0; i < treePaths.length; i++) {
            treePaths[i] = new TreePath(getSplitedPath(files.get(i)));
        }
        TreeModel model = new TreePathsTreeModel(files.get(0).toPath().getRoot().toString(), treePaths);
        JTree tree = new JTree(model);
        return tree;
    }

    public String[] getSplitedPath(File file) {
        String[] splitedPath = new String[file.toPath().getNameCount() + 1];
        splitedPath[0] = file.toPath().getRoot().toString();
        for (int i = 0; i < splitedPath.length - 1; i++) {
            splitedPath[i + 1] = file.toPath().getName(i).toString() + "\\";
        }
        return splitedPath;
    }

}
