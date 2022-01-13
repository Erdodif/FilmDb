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

    public boolean addFilm(String cim, String kategoria, int hossz, int ertekeles) throws  SQLException{
        String sql = "INSERT INTO `filmek` (cim, kategoria, hossz, ertekeles) VALUES(?,?,?,?)";
        PreparedStatement prpstm = this.conn.prepareCall(sql);
        prpstm.setString(1,cim);
        prpstm.setString(2,kategoria);
        prpstm.setInt(3,hossz);
        prpstm.setInt(4,ertekeles);
        return prpstm.executeUpdate() == 1;
    }

    public boolean deleteFilm(int id) throws SQLException{
        String sql = "DELETE FROM `filmek` WHERE id = ?";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        prpstmt.setInt(1,id);
        return prpstmt.executeUpdate() == 1;
    }

    public boolean editFilm(Film film) throws SQLException{
        String sql = "UPDATE `filmek` SET cim = ?, kategoria = ?, hossz = ?, ertekeles = ? WHERE id = ?;";
        PreparedStatement prpstm = this.conn.prepareCall(sql);
        prpstm.setString(1,film.getCim());
        prpstm.setString(2,film.getKategoria());
        prpstm.setInt(3,film.getHossz());
        prpstm.setInt(4,film.getErtekeles());
        prpstm.setInt(5,film.getId());
        return prpstm.executeUpdate() == 1;
    }
}
