package data;

import data.entities.Language;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstraktní třída pro DataAccessObjecty pro entitu jazyků.
 *
 * @author Helena Pavlikova
 */
public abstract class LanguageDAO {

    /**
     * PreparedStatementy - předpřipravené SQL příkazy
     */
    protected PreparedStatement getAllPs;
    
    
    /**
     * Metoda pro přidání jazyka do databáze.
     *
     * @param name název
     */
    public abstract void add(String name);

    /**
     * Metoda pro získání všech jazyků z databáze.
     *
     * @return kolekce jazyku
     */
    public Collection<Language> getAll() {
        List<Language> ret = new ArrayList<>();
        try {
            ResultSet rs = getAllPs.executeQuery();
            while (rs.next()) {
                ret.add(new Language(rs.getInt(1), rs.getString(2)));
            }
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(LanguageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
