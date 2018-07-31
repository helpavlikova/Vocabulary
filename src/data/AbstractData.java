package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstraktní třída pro datové vrstvy.
 *
 * @author Helena Pavlikova
 */
public abstract class AbstractData {

    /**
     * DataAccessObject pro slova
     */
    protected WordDAO wordDAO;
    /**
     * DataAccessObject pro jazyky
     */
    protected LanguageDAO languageDAO;
    /**
     * Spojení k databázi
     */
    protected Connection connection;

    public abstract WordDAO getWordDAO();

    public abstract LanguageDAO getLanguageDAO();

    public AbstractData() {
        wordDAO = null;
        languageDAO = null;
        connection = null;
    }

    /**
     * Metoda pro uzavření spojení s databází.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(AbstractData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
