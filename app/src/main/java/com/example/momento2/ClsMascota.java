package com.example.momento2;

public class ClsMascota {

    private String cod_mascota;
    private String nom_mascota;
    private String edad_mascota;
    private String nom_propietario;

    public ClsMascota() {
    }

    public String getCod_mascota() {
        return cod_mascota;
    }

    public void setCod_mascota(String cod_mascota) {
        this.cod_mascota = cod_mascota;
    }

    public String getNom_mascota() {
        return nom_mascota;
    }

    public void setNom_mascota(String nom_mascota) {
        this.nom_mascota = nom_mascota;
    }

    public String getEdad_mascota() {
        return edad_mascota;
    }

    public void setEdad_mascota(String edad_mascota) {
        this.edad_mascota = edad_mascota;
    }

    public String getNom_propietario() {
        return nom_propietario;
    }

    public void setNom_propietario(String nom_propietario) {
        this.nom_propietario = nom_propietario;
    }
}
