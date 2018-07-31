package controller;

import data.entities.Word;
import data.AbstractData;
import data.LanguageDAO;
import data.WordDAO;
import data.embedded.EmbeddedDerbyData;
import data.entities.Language;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Helena Pavlíková
 */
public class FXMLDocumentController implements Initializable {

    /**
     * Metoda pro získání datové vrstvy pro vnořenou databázi.
     *
     * @return datová vrstva pro vnořenou databázi
     */
    private static AbstractData getEmbeddedDerbyData() {
        String url = ".";
        String username = "testovaci";
        String pass = "heslo";
        return new EmbeddedDerbyData(url, username, pass);
    }
    /**
     * Datová vrstva
     */
    private final AbstractData data = getEmbeddedDerbyData();

    /**
     * DAO k tabulce slov
     */
    WordDAO wordDAO = data.getWordDAO();
    /**
     * DAO k tabulce jazyku
     */
    LanguageDAO languageDAO = data.getLanguageDAO();
    /**
     * kolekce slov
     */
    public ArrayList<Word> words = new ArrayList();
    private HashMap<String, ArrayList<Word>> wordMap = new HashMap();

    @FXML
    private Label testLabel;

    @FXML
    public ComboBox<Language> langComboBox;

    @FXML
    private TextField exprTextField;
    @FXML
    private TextField translTextField;

    @FXML
    private TableView<Word> wordTable;
    @FXML
    private TableColumn<Word, String> foreignWord;
    @FXML
    private TableColumn<Word, String> nativeWord;

    @FXML
    private void excerciseButtonAction(ActionEvent event) throws IOException {
        Stage stage = showExcercises(langComboBox.getValue());
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(testLabel.getScene().getWindow());

        stage.showAndWait();
    }
    
    @FXML
    private void newLanguageButtonAction(ActionEvent event) {
        LanguageDialog dialog = new LanguageDialog(testLabel.getScene().getWindow());
        dialog.showAndWait();
        
        String newLang = dialog.getName();
        if (newLang != null)
        {
                languageDAO.add(newLang);
                Language actualLang = langComboBox.getValue();
                ObservableList<Language> langList = FXCollections.observableArrayList(languageDAO.getAll().toArray(new Language[0]));
                langComboBox.setItems(langList);
                langComboBox.setValue(actualLang);
                
        }
    }

    @FXML
    private void changeLanguageAction(ActionEvent event) {
        parseWordList();
        Refresh(wordTable, words);
    }

    @FXML
    private void addNewButtonAction(ActionEvent event) {
        testLabel.setText("Nové slovo vloženo.");
        Word insertion = getWord();
        wordDAO.addWord(insertion);
        parseWordList();
        Refresh(wordTable, words);
        exprTextField.setText(null);
        translTextField.setText(null);
    }

    @FXML
    private void deleteButtonAction(ActionEvent event) {
        Word word = wordTable.getSelectionModel().getSelectedItem();
        if (word == null){return;}
        wordDAO.deleteWord(word.getId());
        parseWordList();
        Refresh(wordTable, words);
        testLabel.setText("Slovo bylo smazáno.");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        langComboBox.getItems().addAll(languageDAO.getAll().toArray(new Language[0]));
        langComboBox.setValue(new Language(1, "English"));

        foreignWord.setCellValueFactory(new PropertyValueFactory<Word, String>("expression"));
        nativeWord.setCellValueFactory(new PropertyValueFactory<Word, String>("translation"));
        wordTable.getItems().setAll(parseWordList());

    }

    private List<Word> parseWordList() {
        //words = wordDAO.getAll();
        wordMap = wordDAO.getHashMap();
        String language = langComboBox.getValue().getName();
        words = wordMap.get(language);

        return words;
    }

    public Word getWord() {
        return new Word(exprTextField.getText(), translTextField.getText(), langComboBox.getValue());
    }

    public static <Word> void Refresh(final TableView<Word> table, final List<Word> tableList) {
        table.setItems(null);
        table.layout();
        table.setItems(FXCollections.observableList(tableList));
    }

    public Stage showExcercises(Language lang) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/view/Excercise.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        
        stage.setTitle("Cvičení slovíček");

        ExcerciseController controller = loader.<ExcerciseController>getController();
        controller.initData(words);

        return stage;
    }

}
