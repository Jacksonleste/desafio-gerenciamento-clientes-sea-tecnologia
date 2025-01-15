package com.seatecnologia.crudclientes.enums;

public enum TipoTelefone {
    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial"),
    CELULAR("Celular");

    private String descricao;

    TipoTelefone(String descricao){this.descricao = descricao;}

    public String getDescricao() {
        return descricao;
    }


    public static TipoTelefone getEnumByDescricao(String descricao){
        for(TipoTelefone e : TipoTelefone.values()){
            if(e.descricao.equals(descricao)) return e;
        }
        return null;
    }
}
