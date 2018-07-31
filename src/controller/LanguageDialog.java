package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author Helena Pavlikova
 */
public class LanguageDialog extends Stage {

    public LanguageDialog(Window okno) {
        setTitle("Vložení nového jazyka");
        setWidth(350);
        setHeight(250);

        initStyle(StageStyle.UTILITY);
        initModality(Modality.WINDOW_MODAL);
        initOwner(okno);
        setScene(CreateScene());
    }

    private String name;

    public String getName() {
        return name;
    }

    private Scene CreateScene() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);

        final TextField languageTextField = new TextField();
        languageTextField.setMaxWidth(150);
        Label label = new Label();
        label.setText("Zadejte název nového jazyka:");

        Button okButton = new Button("OK");
        
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                name = languageTextField.getText();
                hide();
            }
        });

        box.getChildren().addAll(label,languageTextField, okButton);
        return new Scene(box);

    }
}
