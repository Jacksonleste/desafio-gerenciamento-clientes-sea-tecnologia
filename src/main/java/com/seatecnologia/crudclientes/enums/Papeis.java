package com.seatecnologia.crudclientes.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Papeis {
    ADMIN("Admin"),
    DEFAULT("Padrão");

    private String descricao;

    Papeis(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static String getEnumByDescricao(String descricao){
        for(Papeis e : Papeis.values()){
            if(e.descricao.equals(descricao)) return e.getDescricao();
        }
        return null;
    }

}
