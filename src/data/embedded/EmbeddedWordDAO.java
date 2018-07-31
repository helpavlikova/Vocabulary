package data.embedded;

import data.WordDAO;
import data.SQLCommands;
import data.entities.Word;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DataAccessObject pro slova pro vnořenou databázi.
 *
 * @author Helena Pavlíková
 */
public class EmbeddedWordDAO extends WordDAO {

    public EmbeddedWordDAO(Connection connection) {
        try {
            addPs = connection.prepareStatement(SQLCommands.ADD_EMBEDDED);
            getAllPs = connection.prepareStatement(SQLCommands.GET_ALL_EMBEDDED);
            getLangPs = connection.prepareStatement(SQLCommands.GET_LANGUAGES_EMBEDDED);
            deletePs = connection.prepareStatement(SQLCommands.DELETE_EMBEDDED);
        } catch (SQLException ex) {
            Logger.getLogger(EmbeddedWordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addWord(Word e) {
        try {
            addPs.setString(1, e.getExpression());
            addPs.setString(2, e.getTranslation());
            addPs.setInt(3, e.getLanguage().getId());
            addPs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmbeddedWordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
