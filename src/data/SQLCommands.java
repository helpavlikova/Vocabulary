package data;

/**
 * Třída obsahující používané SQL příkazy.
 *
 * @author Helena Pavlikova
 */
public class SQLCommands {

    public static final String ADD_EMBEDDED = "INSERT INTO WORDS VALUES(DEFAULT, ?, ?, ?)";
    public static final String DELETE_EMBEDDED = "DELETE FROM WORDS WHERE ID = ?";
    public static final String GET_ALL_EMBEDDED = "SELECT WORDS.ID, WORDS.EXPR, WORDS.TRANSL, LANGUAGES.ID, LANGUAGES.NAME FROM WORDS JOIN LANGUAGES ON WORDS.LANGUAGEID = LANGUAGES.ID";
    public static final String GET_LANGUAGES_EMBEDDED = "SELECT LANGUAGES.ID, LANGUAGES.NAME FROM LANGUAGES";
    public static final String ADD_LANGUAGE_EMBEDDED = "INSERT INTO LANGUAGES VALUES(DEFAULT, ?)";
}
