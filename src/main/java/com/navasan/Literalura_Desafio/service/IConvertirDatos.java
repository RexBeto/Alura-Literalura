package com.navasan.Literalura_Desafio.service;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> classe);
}
