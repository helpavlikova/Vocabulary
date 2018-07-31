package controller;


import data.entities.Word;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Helena Pavlíková
 */
public class ExcerciseController implements Initializable {
    
    private final Random randomGenerator = new Random();
    
    public ArrayList<Word> tableList;
    
    private int index;
    
    private final String[] choices = {"Do češtiny", "Z češtiny"};

    @FXML
    private Button nextButton;
    
    @FXML
    private Label translationLabel;
    
    @FXML
    private Label expressionLabel;
    
    @FXML
    private Label resultLabel;
    
    @FXML
    private TextField inputTextField;
    
    @FXML
    private ComboBox<String> typeChoiceBox;
    
    @FXML
    private void resultButtonAction(ActionEvent event) { 
        if((expressionLabel.getText().equals("Slovo"))){
            return;
        }
        
        if(typeChoiceBox.getValue().equals("Do češtiny")){
                    evaluate(tableList.get(index).getTranslation(),inputTextField.getText(), translationLabel);
        }else{
                    evaluate(tableList.get(index).getExpression(),inputTextField.getText(), expressionLabel);
        }
        
    }
    
    @FXML
    private void nextButtonAction(ActionEvent event) {
        if(nextButton.getText().equals("Start")){
            nextButton.setText("Další");
        }
        
        inputTextField.setText(null);
        resultLabel.setText(null);
        randomIndex();
        
        if(typeChoiceBox.getValue().equals("Do češtiny")){
            nextRound(translationLabel, expressionLabel, tableList.get(index).getExpression());
        }else{
            nextRound (expressionLabel, translationLabel, tableList.get(index).getTranslation());
        }
        
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       typeChoiceBox.getItems().addAll(choices);
       typeChoiceBox.setValue(choices[0]);
    }   
    
    private void randomIndex(){
        index = randomGenerator.nextInt(tableList.size());
    }

    public void initData( ArrayList<Word> words) {
        tableList = words;
    }
    
    private void evaluate(String template, String input, Label where) {
        where.setText(template);
        if (inputTextField.getText()!= null){
            if( template.toLowerCase().contains(input.toLowerCase()) ){
                resultLabel.setText("Správně!");
            }else{
                resultLabel.setText("Špatně!");
            }
    }
    }
    
    private void nextRound(Label empty, Label notEmpty, String word){
        notEmpty.setText(word);
        empty.setText("");
    
    }
    

}
