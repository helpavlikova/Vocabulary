package data.entities;

import javafx.beans.property.SimpleStringProperty;

/**
 * Entita pro slovicka.
 *
 * @author Helena Pavlikova
 */
public class Word {

    private int id;
    private SimpleStringProperty expression;
    private SimpleStringProperty translation;
    private final Language language;

    public Word(int id, String expression, String translation, Language language) {
        this.id = id;
        setExpression(expression);
        setTranslation(translation);
        this.language = language;
    }

    public Word(String expression, String translation, Language language) {
        setExpression(expression);
        setTranslation(translation);
        this.language = language;
    }
    
    public SimpleStringProperty expressionProperty() { 
         if (expression == null){ expression = new SimpleStringProperty(this, "expression");}
         return expression; 
     }
    
    public SimpleStringProperty translationProperty() { 
         if (translation == null) {translation = new SimpleStringProperty(this, "translation");}
         return translation; 
     }
    
    public void setExpression(String value) { 
        expressionProperty().set(value);
    }
    
    public void setTranslation(String value) { 
        translationProperty().set(value);
    }

    public int getId() {
        return id;
    }

    public String getExpression() {
        return expressionProperty().get();
    }

    public String getTranslation() {
        return translationProperty().get();
    }

    public Language getLanguage() {
        return language;
    }
}
