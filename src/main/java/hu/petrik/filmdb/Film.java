package hu.petrik.filmdb;

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
}
