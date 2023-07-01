package interprete;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    ELSE, LONG, RETURN, FOR, IF, WHILE,
    //Caracteres especiales
    OPCOMPARACION, PARABRE, PARCIERRA, CORABRE, CORCIERRA, LLAVEABRE, LLAVECIERRA, CADENA, IDENTIFICADOR, NUMERO,
    COMA, PUNTO, PUNTOYCOMA, DOSPUNTOS, OPARITMETICOS, IGUAL,

    //especiales
    CLASS, FUN, VAR, ID, VERDAD, FALSO, NULO, ESTE, SUPERR, PRINT, OR, AND, EPSILON,

    // Final de cadena
    EOF
}