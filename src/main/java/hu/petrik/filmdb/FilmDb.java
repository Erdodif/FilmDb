package hu.petrik.filmdb;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class FilmDb {
    Connection conn;

    public FilmDb() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/filmek","root","");
    }

    public List<Film> getFilmek() throws SQLException {
        List<Film> filmek = new ArrayList<Film>();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM filmek;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()){
            filmek.add(new Film(
                result.getInt("id"),
                result.getString("cim"),
                result.getString("kategoria"),
                result.getInt("hossz"),
                result.getInt("ertekeles")
            ));
        }
        return filmek;
    }
}
