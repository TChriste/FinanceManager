package heg.financemanager.business;

public class Categorie {

    private long id;
    private String libelle;

    public long getId() {
        return id;
    }

    public Categorie(String libelle) {
        this.id = -1;
        this.libelle = libelle;
    }
    public Categorie(long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setId(long id) {
        this.id = id;
    }
}
