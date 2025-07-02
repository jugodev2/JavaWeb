package com.alonso.webapp.proyecto.models.enums;

public enum Estatus {
    DISPONIBLE,
    NO_DISPONIBLE;


    public String getEstatusEnum() {
        switch (this){
            case DISPONIBLE -> {return "Disponible";}
            case NO_DISPONIBLE -> {return "No Disponible";}
            default -> { return "Error";}
        }
    }
}
