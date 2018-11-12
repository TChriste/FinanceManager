package heg.financemanager.business;

public class Transaction {

    private long id;
    private Compte compte;
    private Categorie categorie;
    private float montant;

    public Transaction(long id, Compte compte, Categorie categorie, float montant) {
        this.id = id;
        this.compte = compte;
        this.categorie = categorie;
        this.montant = montant;
    }

    public long getId() {
        return id;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }
}
