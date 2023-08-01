package Model;


public class Carrello {
    private String ID_Libro;
    private String Titolo;
    private String Email;
    private String quantita;
    private String tipoAcquisto;
    private String tipoRitiro;
    private String tipoConsegna;

    public Carrello(String ID_Libro, String Titolo, String Email, String quantita, String tipoAcquisto, String tipoRitiro, String tipoConsegna) {
        this.ID_Libro = ID_Libro;
        this.Titolo = Titolo;
        this.Email = Email;
        this.quantita = quantita;
        this.tipoAcquisto = tipoAcquisto;
        this.tipoRitiro = tipoRitiro;
        this.tipoConsegna = tipoConsegna;
    }

    public Carrello() {
    }

    public String getID_Libro() {
        return ID_Libro;
    }

    public void setID_Libro(String ID_Libro) {
        this.ID_Libro = ID_Libro;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getQuantita() {
        return quantita;
    }

    public void setQuantita(String quantita) {
        this.quantita = quantita;
    }

    public String getTipoAcquisto() {
        return tipoAcquisto;
    }

    public void setTipoAcquisto(String tipoAcquisto) {
        this.tipoAcquisto = tipoAcquisto;
    }

    public String getTipoRitiro() {
        return tipoRitiro;
    }

    public void setTipoRitiro(String tipoRitiro) {
        this.tipoRitiro = tipoRitiro;
    }

    public String getTipoConsegna() {
        return tipoConsegna;
    }

    public void setTipoConsegna(String tipoConsegna) {
        this.tipoConsegna = tipoConsegna;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String Titolo) {
        this.Titolo = Titolo;
    }
    
    
}
