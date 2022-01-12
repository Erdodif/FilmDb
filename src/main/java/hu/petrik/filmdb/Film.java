package hu.petrik.filmdb;

import java.util.Objects;

public class Film {
    private int id;
    private String cim;
    private String kategoria;
    private int hossz;
    private int ertekeles;

    public Film(int id, String cim, String kategoria, int hossz, int ertekeles) {
        this.id = id;
        this.cim = cim;
        this.kategoria = kategoria;
        this.hossz = hossz;
        this.ertekeles = ertekeles;
    }

    public int getId() {
        return id;
    }

    public String getCim() {
        return cim;
    }

    public String getKategoria() {
        return kategoria;
    }

    public int getHossz() {
        return hossz;
    }

    public int getErtekeles() {
        return ertekeles;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public void setHossz(int hossz) {
        this.hossz = hossz;
    }

    public void setErtekeles(int ertekeles) {
        this.ertekeles = ertekeles;
    }

    @Override
    public String toString() {
        return "" +id + cim + kategoria + hossz + ertekeles ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && hossz == film.hossz && ertekeles == film.ertekeles && cim.equals(film.cim) && kategoria.equals(film.kategoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cim, kategoria, hossz, ertekeles);
    }
}
