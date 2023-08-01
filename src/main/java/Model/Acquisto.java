package Model;


public class Acquisto {
    private String titolo;
    private String utente;
    private String prezzo;
    private String quantita;
    private String tipo_ritiro;

    public Acquisto(String titolo, String utente, String prezzo, String quantita, String tipo_ritiro) {
        this.titolo = titolo;
        this.utente = utente;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.tipo_ritiro = tipo_ritiro;
    }

    public Acquisto() {
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

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
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
    
    
}
