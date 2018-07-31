package data.embedded;

import data.AbstractData;
import data.WordDAO;
import data.LanguageDAO;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 * Hlavní třída datové vrstvy pro vnořenou databázi.
 *
 * @author Helena Pavlikova
 */
public class EmbeddedDerbyData extends AbstractData {

    /**
     * DataAccessObject pro jazyky
     */
    protected EmbeddedLanguageDAO languageDAO;

    public EmbeddedDerbyData(String url, String username, String pass) {
        super();
        try {
            EmbeddedDataSource eds = new EmbeddedDataSource();
            eds.setDatabaseName(url + "/embeddeddb");
            eds.setCreateDatabase("create");
            eds.setUser(username);
            eds.setPassword(pass);
            connection = eds.getConnection();
            languageDAO = null;
            init();
        } catch (SQLException ex) {
            Logger.getLogger(EmbeddedDerbyData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metoda, která vytvoří tabulky, pokud neexistují.
     */
    private void init() {
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "WORDS", null);
            if (!rs.next()) {
                Statement s = connection.createStatement();
                s.executeUpdate("CREATE TABLE WORDS"
                        + "(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,"
                        + "EXPR VARCHAR(50),"
                        + "TRANSL VARCHAR(50),"
                        + "LANGUAGEID INT,"
                        + "PRIMARY KEY (ID))");
            }
            rs = md.getTables(null, null, "LANGUAGES", null);
            if (!rs.next()) {
                Statement s = connection.createStatement();
                s.executeUpdate("CREATE TABLE LANGUAGES"
                        + "(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,"
                        + "NAME VARCHAR(50),"
                        + "PRIMARY KEY (ID))");
                addLanguages();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmbeddedDerbyData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metoda pro přidání vzorových dat jazyků.
     */
    private void addLanguages() {
        if (languageDAO == null) {
            languageDAO = new EmbeddedLanguageDAO(connection);
        }
        languageDAO.add("English");
        languageDAO.add("French");
    }

    /**
     * Metoda, která vrací DataAccessObject pro slova.
     *
     * @return Data Access Object pro slova
     */
    @Override
    public WordDAO getWordDAO() {
        if (wordDAO == null) {
            wordDAO = new EmbeddedWordDAO(connection);
        }
        return wordDAO;
    }

    /**
     * Metoda, která vrací DataAccessObject pro jazyky.
     *
     * @return Data Access Object pro jazyky
     */
    @Override
    public LanguageDAO getLanguageDAO() {
        if (languageDAO == null) {
            languageDAO = new EmbeddedLanguageDAO(connection);
        }
        return languageDAO;
    }
}
