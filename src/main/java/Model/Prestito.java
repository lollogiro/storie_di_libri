/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author LUCA
 */
public class Prestito {
    private String titolo;
    private String utente;
    private String data_inizio;
    private String data_fine;
    private String quantita;
    private String tipo_ritiro;
    private String tipo_consegna;

    public Prestito(String titolo, String utente, String data_inizio, String data_fine, String quantita, String tipo_ritiro, String tipo_consegna) {
        this.titolo = titolo;
        this.utente = utente;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.quantita = quantita;
        this.tipo_ritiro = tipo_ritiro;
        this.tipo_consegna = tipo_consegna;
    }

    public Prestito() {
    }
    
    

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(String data_inizio) {
        this.data_inizio = data_inizio;
    }

    public String getData_fine() {
        return data_fine;
    }

    public void setData_fine(String data_fine) {
        this.data_fine = data_fine;
    }

    public String getQuantita() {
        return quantita;
    }

    public void setQuantita(String quantita) {
        this.quantita = quantita;
    }

    public String getTipo_ritiro() {
        return tipo_ritiro;
    }

    public void setTipo_ritiro(String tipo_ritiro) {
        this.tipo_ritiro = tipo_ritiro;
    }

    public String getTipo_consegna() {
        return tipo_consegna;
    }

    public void setTipo_consegna(String tipo_consegna) {
        this.tipo_consegna = tipo_consegna;
    }
    
    
}

