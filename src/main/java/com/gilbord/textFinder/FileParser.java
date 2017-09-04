package com.gilbord.textFinder;

import javax.swing.tree.TreePath;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    public List<File> find(String directoryName, String text, String extension) throws IOException {
        List<File> currentFiles = getAllFilesFromDirectory(directoryName, extension);
        List<File> resultFiles = new ArrayList<>();
        for (File file : currentFiles) {
            if (isTextFound(file, text)) {
                resultFiles.add(file);
            }
        }
        return resultFiles;
    }

    public boolean isTextFound(File file, String text) throws IOException {
        /*FileInputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().map(s -> s.toLowerCase()).anyMatch(s -> s.equals(text));*/
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        Pattern word = Pattern.compile(text.toLowerCase());
        return br.lines().map(s -> s.toLowerCase()).anyMatch(s -> {
            Matcher match = word.matcher(s);
            return match.find();
        });
        /*String outstring = null;
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            outstring = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }*/
    }

    public List<File> getAllFilesFromDirectory(String directoryName, String extension) {
        File directory = new File(directoryName);
        List<File> resultFiles = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().endsWith(extension)) {
                resultFiles.add(file);
            } else if (file.isDirectory()) {
                resultFiles.addAll(getAllFilesFromDirectory(file.getPath(), extension));
            }
        }
        return resultFiles;
    }

    public String getText(TreePath treePath) throws IOException {
        String outString = null;
        BufferedReader br = new BufferedReader(new FileReader(getPathFromTreePath(treePath)));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            outString = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return outString;
    }

    public String getPathFromTreePath(TreePath treePath) {
        String resultPath = treePath.getPathComponent(0).toString();
        for (int i = 1; i < treePath.getPathCount() - 1; i++) {
            resultPath += treePath.getPathComponent(i).toString();
        }
        resultPath += treePath.getLastPathComponent().toString();
        return resultPath;
    }

    public void getFindingTextPositions(TreePath file, String findingText, ArrayList<Integer> positions) throws IOException {
        String text = getText(file).toLowerCase();
        Pattern word = Pattern.compile(findingText.toLowerCase());
        Matcher match = word.matcher(text);
        while (match.find()) {
            positions.add(match.start());
        }
    }


}
