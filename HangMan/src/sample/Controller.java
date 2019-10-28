package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    public TextField letter;
    public ImageView hang;
    public TextField word;
    public TextArea guessed;
    public Label life;
    public Label chars;

    ArrayList<Character> letters = new ArrayList<>();
    ArrayList<Character> lettersUsed = new ArrayList<>();
    ArrayList<Character> correctLetters = new ArrayList<>();
    ArrayList<String> allWords = new ArrayList<>();
    ArrayList<String> correct = new ArrayList<>();
    String lets = "abcdefghijklmnopqrstuvwxyz";
    public String wordChosen;
    String dash = "";
    File fileStart = new File("./start.png");
    File fileHead = new File("./head.png");
    File fileBody = new File("./body.png");
    File fileLeftL = new File("./leftleg.png");
    File fileRightL = new File("./rightleg.png");
    File fileLeftA = new File("./leftarm.png");
    File fileRightA = new File("./rightarm.png");
    File fileLeftE = new File("./lefteye.png");
    File fileRightE= new File("./righteye.png");
    File fileMouth = new File("./mouth.png");
    File fileHair = new File("./hair.png");
    File fileCross = new File("./cross.png");
    int lives = 11;
    int len;

    public String getWordChosen() {
        return wordChosen;
    }

    public void addtoList() {
        for (int i = 0; i < lets.length(); i++) {
            letters.add(lets.charAt(i));
        }
    }

    public void generateWord() {
        Random rand = new Random();
        try {
            String line;
            FileReader file = new FileReader("words.txt");
            BufferedReader br = new BufferedReader(file);
            while ((line = br.readLine()) != null) {
                allWords.add(line);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        wordChosen = allWords.get(rand.nextInt(allWords.size()+1));
        for (int i = 0; i < wordChosen.length(); i++) {
            dash += "_";
        }
        word.setText(dash);
    }
    public void initialize() {
        addtoList();
        generateWord();
        genImage();
        correct.add(dash);
        len = wordChosen.length();
        chars.setText("Characters: " + len + " left");
    }

    public void genImage() {
        if (lives == 11) {
            Image start = new Image(fileStart.toURI().toString());
            hang.setImage(start);
        }
        if (lives == 10) {
            Image head = new Image(fileHead.toURI().toString());
            hang.setImage(head);
        }
        if (lives == 9) {
            Image body = new Image(fileBody.toURI().toString());
            hang.setImage(body);
        }
        if (lives == 8) {
            Image leftL = new Image(fileLeftL.toURI().toString());
            hang.setImage(leftL);
        }
        if (lives == 7) {
            Image rightL = new Image(fileRightL.toURI().toString());
            hang.setImage(rightL);
        }
        if (lives == 6) {
            Image leftA = new Image(fileLeftA.toURI().toString());
            hang.setImage(leftA);
        }
        if (lives == 5) {
            Image rightA = new Image(fileRightA.toURI().toString());
            hang.setImage(rightA);
        }
        if (lives == 4) {
            Image leftE = new Image(fileLeftE.toURI().toString());
            hang.setImage(leftE);
        }
        if (lives == 3) {
            Image rightE = new Image(fileRightE.toURI().toString());
            hang.setImage(rightE);
        }
        if (lives == 2) {
            Image mouth = new Image(fileMouth.toURI().toString());
            hang.setImage(mouth);
        }
        if (lives == 1) {
            Image hair = new Image(fileHair.toURI().toString());
            hang.setImage(hair);
        }
        if (lives == 0) {
            Image cross = new Image(fileCross.toURI().toString());
            hang.setImage(cross);
        }
        if (lives == -1) {
            try{
                FXMLLoader load = new FXMLLoader(getClass().getResource("./loseScreen.fxml"));
                Parent parent = load.load();
                loseController con = load.getController();
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Lost");
                stage.setScene(new Scene(parent));
                stage.show();

                con.setWord("The word was: " + wordChosen);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) letter.getScene().getWindow();
            stage.close();
        }
    }

    public void submit(ActionEvent actionEvent) {
        char[] temp = correct.get(correct.size()-1).toCharArray();
        boolean used = false;
        if (letter.getText().length() == 1) {
            Character c = letter.getText().toLowerCase().charAt(0);
            if(Character.isLetter(c)) {
                for (char s : lettersUsed) {
                    if (s == c) {
                        used = true;
                    }
                }
                for (char s: correctLetters) {
                    if (s == c) {
                        used = true;
                    }
                }
                if (!used) {
                    if (wordChosen.contains(Character.toString(c))) {
                        int dash = 0;
                        for (int i = 0; i < wordChosen.length(); i++) {
                            if (wordChosen.charAt(i) == c) {
                                temp[i] = c;
                            }
                        }
                        word.setText(String.valueOf(temp));
                        correct.add(String.valueOf(temp));
                        for (int i = 0; i < correct.get(correct.size() -1).length(); i++) {
                            if (correct.get(correct.size() -1).charAt(i) == '_') {
                                dash++;
                            }
                        }
                        len -= len - dash;
                        correctLetters.add(c);
                        chars.setText("Characters: " + len + " left");
                        if (correct.get(correct.size() - 1).equals(wordChosen)) {
                            try {
                                FXMLLoader load = new FXMLLoader(getClass().getResource("./winScreen.fxml"));
                                Parent parent = load.load();
                                winController con = load.getController();
                                Stage stage = new Stage(StageStyle.DECORATED);
                                stage.setTitle("Win");
                                stage.setScene(new Scene(parent));
                                stage.show();
                                con.setWord("The word was: " + wordChosen);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Stage stage = (Stage) letter.getScene().getWindow();
                            stage.close();
                        }
                    } else {
                        String hold = "";
                        lives--;
                        lettersUsed.add(c);
                        for (char d : lettersUsed) {
                            hold += d + ", ";
                        }
                        guessed.setText(hold);
                        life.setText("Lives: " + lives);
                    }
                }
                letter.clear();
                genImage();
            }
        }
    }
}
