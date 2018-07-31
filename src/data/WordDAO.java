package data;

import data.entities.Word;
import data.entities.Language;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstraktní třída pro DataAccessObjecty pro entitu slov.
 *
 * @author Helena Pavlikova
 */
public abstract class WordDAO {

    /**
     * PreparedStatementy - předpřipravené SQL příkazy
     */
    protected PreparedStatement addPs;
    protected PreparedStatement getAllPs;
    protected PreparedStatement getLangPs;
    protected PreparedStatement deletePs;

    /**
     * Metoda pro přidání slova do databáze.
     *
     * @param e slovo
     */
    public abstract void addWord(Word e);

    /**
     * Metoda pro získání všech slov z databáze.
     *
     * @return kolekce slov
     */
    public ArrayList<Word> getAll() {
        ArrayList<Word> ret = new ArrayList();
        try {
            ResultSet rs = getAllPs.executeQuery();
            while (rs.next()) {
                ret.add(new Word(rs.getInt(1), rs.getString(2), rs.getString(3), new Language(rs.getInt(4), rs.getString(5))));
            }
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(WordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public HashMap<String, ArrayList<Word>> getHashMap() {
        HashMap<String, ArrayList<Word>> wordMap = new HashMap();
        try {
            ResultSet rs1 = getLangPs.executeQuery();
            while (rs1.next()) {
                wordMap.put(rs1.getString(2), new ArrayList<Word>());
            }
            try {
                ResultSet rs = getAllPs.executeQuery();
                while (rs.next()) {
                    Word word = new Word(rs.getInt(1), rs.getString(2), rs.getString(3), new Language(rs.getString(5)));
                    wordMap.get(rs.getString(5)).add(word);
                }
            } catch (SQLException ex) {
                Logger.getLogger(WordDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(WordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wordMap;
    }

    /**
     * Metoda pro vymazání slova z databáze.
     *
     * @param id id slova
     */
    public void deleteWord(int id) {
        try {
            deletePs.setInt(1, id);
            deletePs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(WordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
