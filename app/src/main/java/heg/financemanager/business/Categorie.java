package heg.financemanager.business;

public class Categorie {

    private long id;
    private String libelle;

    public long getId() {
        return id;
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
}
