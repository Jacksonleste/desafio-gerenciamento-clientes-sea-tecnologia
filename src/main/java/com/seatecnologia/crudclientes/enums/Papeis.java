package com.seatecnologia.crudclientes.enums;

public enum Papeis {
    ADMIN("Admin"),
    USER("Padrão");

    private String descricao;

    Papeis(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Papeis getEnumByDescricao(String descricao){
        for(Papeis e : Papeis.values()){
            if(e.descricao.equals(descricao)) return e;
        }
        return null;
    }

}
