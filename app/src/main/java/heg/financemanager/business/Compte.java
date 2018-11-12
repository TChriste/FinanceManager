package heg.financemanager.business;

public class Compte {
    private long id;
    private String libelle;
    private float solde;

    public Compte(String libelle) {
        this.id = -1;
        this.libelle = libelle;
        this.solde = 0;
    }

    public Compte(long id, String libelle, float solde) {
        this.id = id;
        this.libelle = libelle;
        this.solde = solde;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public float getSolde() {
        return solde;
    }

    public void addToSolde(float montant) {
        this.solde += montant;
    }

    public void removeToSolde(float montant) {
        this.solde -= montant;
    }
}
