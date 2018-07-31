package data.embedded;

import data.LanguageDAO;
import data.SQLCommands;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DataAccessObject pro jazyky pro vnořenou databázi.
 *
 * @author Helena Pavlikova
 */
public class EmbeddedLanguageDAO extends LanguageDAO {

    /**
     * PreparedStatementy - předpřipravené SQL příkazy
     */
    protected PreparedStatement addPs;

    public EmbeddedLanguageDAO(Connection connection) {
        try {
            getAllPs = connection.prepareStatement(SQLCommands.GET_LANGUAGES_EMBEDDED);
            addPs = connection.prepareStatement(SQLCommands.ADD_LANGUAGE_EMBEDDED);
        } catch (SQLException ex) {
            Logger.getLogger(EmbeddedLanguageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metoda pro přidání jazyka do databáze.
     *
     * @param name nazev jazyka
     */
    @Override
    public void add(String name) {
        try {
            addPs.setString(1, name);
            addPs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmbeddedLanguageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
