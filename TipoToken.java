public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    ELSE, LONG, SWITCH, BREAK, ENUM, REGISTER, TYPEDEF, CASE, EXTERN, RETURN, UNION, CHAR, FLOAT, SHORT, UNSIGNED,
    CONST, FOR, SIGNED, VOID, CONTINUE, GOTO, SIZEOF, VOLATILE, DEFAULT, IF, STATIC, WHILE, DO, INT, STRUCT, _PACKED,
    DOUBLE,
    //Caracteres especiales
    OPCOMPARACION, PARABRE, PARCIERRA, CORABRE, CORCIERRA, LLAVEABRE, LLAVECIERAA, CADENA, IDENTIFICADOR, NUMERO,
    COMA, PUNTO, PUNTOYCOMA, DOSPUNTOS, OPARITMETICOS, IGUAL,

    // Final de cadena
    EOF
}
