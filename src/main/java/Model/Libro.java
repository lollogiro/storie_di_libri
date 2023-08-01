package Model;

public class Libro {
    private String ID_Libro;
    private String titolo;
    private String autore;
    private String genere;
    private String prezzo;
    private String quantita;
    private String quantitaCarrello;
    private String tipoAcquisto;

    public Libro(String titolo, String autore, String genere, String prezzo, String quantita, String quantitaCarrello, String tipoAcquisto) {
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.quantitaCarrello = quantitaCarrello;
        this.tipoAcquisto = tipoAcquisto;
    }

    public Libro() {
        
    }
    
    public String getID_Libro() {
        return ID_Libro;
    }

    public void setID_Libro(String ID_Libro) {
        this.ID_Libro = ID_Libro;
    }
    
    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
    
    public String getQuantita() {
        return quantita;
    }

    public void setQuantita(String quantita) {
        this.quantita = quantita;
    }
    
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getQuantitaCarrello() {
        return quantitaCarrello;
    }

    public void setQuantitaCarrello(String quantitaCarrello) {
        this.quantitaCarrello = quantitaCarrello;
    }

    public String getTipoAcquisto() {
        return tipoAcquisto;
    }

    public void setTipoAcquisto(String tipoAcquisto) {
        this.tipoAcquisto = tipoAcquisto;
    }

    
    
    
    
}