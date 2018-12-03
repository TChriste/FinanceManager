package heg.financemanager.business;

import java.util.Date;

public class Transaction {

    private long id;
    private Compte compte;
    private Categorie categorie;
    private double montant;
    private Date date;

    public Transaction(long id, Date date, Compte compte, Categorie categorie, double montant) {
        this.id = id;
        this.date = date;
        this.compte = compte;
        this.categorie = categorie;
        this.montant = montant;
    }

    public Transaction(Date date, Compte compte, Categorie categorie, double montant) {
        this.id = -1;
        this.date = date;
        this.compte = compte;
        this.categorie = categorie;
        this.montant = montant;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
