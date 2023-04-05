import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("long", TipoToken.LONG);
        palabrasReservadas.put("switch", TipoToken.SWITCH);
        palabrasReservadas.put("break", TipoToken.BREAK);
        palabrasReservadas.put("enum", TipoToken.ENUM);
        palabrasReservadas.put("register", TipoToken.REGISTER);
        palabrasReservadas.put("typedef", TipoToken.TYPEDEF);
        palabrasReservadas.put("case", TipoToken.CASE);
        palabrasReservadas.put("extern", TipoToken.EXTERN);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("union", TipoToken.UNION);
        palabrasReservadas.put("char", TipoToken.CHAR);
        palabrasReservadas.put("float", TipoToken.FLOAT);
        palabrasReservadas.put("short", TipoToken.SHORT);
        palabrasReservadas.put("unsigned", TipoToken.UNSIGNED);
        palabrasReservadas.put("const", TipoToken.CONST);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("signed", TipoToken.SIGNED);
        palabrasReservadas.put("void", TipoToken.VOID);
        palabrasReservadas.put("continue", TipoToken.CONTINUE);
        palabrasReservadas.put("goto", TipoToken.GOTO);
        palabrasReservadas.put("sizeof", TipoToken.SIZEOF);
        palabrasReservadas.put("volatile", TipoToken.VOLATILE);
        palabrasReservadas.put("default", TipoToken.DEFAULT);
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("static", TipoToken.STATIC);
        palabrasReservadas.put("while", TipoToken.WHILE);
        palabrasReservadas.put("do", TipoToken.DO);
        palabrasReservadas.put("int", TipoToken.INT);
        palabrasReservadas.put("struct", TipoToken.STRUCT);
        palabrasReservadas.put("_Packed", TipoToken._PACKED);
        palabrasReservadas.put("double", TipoToken.DOUBLE);
    }

    public Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        // Hay que leer todo el archivo
        int i = 0;
        String lexema = "";
        int estado = 0;
        int linea = 1;
        int lugarApuntador = 0; // Variable paar saber la posición en donde nos encontramos y no reiniciar el
                                // analisis desde el principio
        boolean valido = true;
        int lineaC = 0;
        /*
         * estado 0 para palabras reservadas
         * estado 1 para identificadores
         * estado 2 para numeros
         * estado 3 para cadenas
         * estado 4 para comentarios largos
         * estado 5 para comentarios cortos //
         * estado 6 para caracteres reservados por el lenguaje
         */
        while (i < source.length()) {
            char c = source.charAt(i);
            if (c == '\12')
                linea++;
            switch (estado) {
                case 0:
                    if (Character.isLetter(c)) {
                        lexema = lexema + c;
                        i++;
                        estado = 0;
                    } else {
                        TipoToken a = palabrasReservadas.get(lexema);
                        if (a == null) {
                            i = lugarApuntador;
                            estado = 1;
                            lexema = "";
                        } else {
                            lugarApuntador = i;
                            tokens.add(new Token(a, lexema, null, linea));
                            lexema = "";
                            estado = 1;
                        }
                    }
                    break;
                case 1:
                    if (i == lugarApuntador) {
                        if (Character.isLetter(c) || c == '_') {
                            estado = 1;
                            lexema = lexema + c;
                            i++;
                        } else {
                            estado = 2;
                            i = lugarApuntador;
                            lexema = "";
                        }
                    } else {
                        if (Character.isLetterOrDigit(c) || c == '_') {
                            estado = 1;
                            lexema = lexema + c;
                            estado = 1;
                            i++;
                        } else {
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, lexema, null, linea));
                            lexema = "";
                            lugarApuntador = i;
                            estado = 1;
                        }
                    }
                    break;
                case 2:
                    int estado2 = 0;
                    switch (estado2) {
                        case 0:
                            if (c == '+' || c == '-') {
                                estado2 = 1;
                                i++;
                                lexema = lexema + c;
                            } else if (Character.isDigit(c)) {
                                estado2 = 2;
                                i++;
                                lexema = lexema + c;
                            } else {
                                estado = 3;
                                lexema = "";
                            }
                            break;
                        case 1:
                            if (Character.isDigit(c)) {
                                estado2 = 2;
                                i++;
                                lexema = lexema + c;
                            } else {
                                estado = 3;
                                lexema = "";
                                i = lugarApuntador;
                            }
                            break;
                        case 2:
                            if (Character.isDigit(c)) {
                                estado2 = 2;
                                i++;
                                lexema += c;
                            } else if (c == '.') {
                                estado2 = 3;
                                i++;
                                lexema += c;
                            } else if (c == 'e' || c == 'E') {
                                estado2 = 5;
                                i++;
                                lexema += c;
                            } else {
                                tokens.add(new Token(TipoToken.NUMERO, lexema, null, linea));
                                lexema = "";
                                lugarApuntador = i;
                                estado = 1;
                            }
                            break;
                        case 3:
                            if (Character.isDigit(c)) {
                                estado2 = 4;
                                i++;
                                lexema += c;
                            } else {
                                Interprete.error(linea, "Número no valido");
                                lexema = "";
                                lugarApuntador = i;
                                estado = 1;
                            }
                            break;
                        case 4:
                            if (Character.isDigit(c)) {
                                estado2 = 4;
                                i++;
                                lexema += c;
                            } else if (c == 'e' || c == 'E') {
                                estado2 = 5;
                                i++;
                                lexema += c;
                            } else {
                                tokens.add(new Token(TipoToken.NUMERO, lexema, null, linea));
                                lexema = "";
                                lugarApuntador = i;
                                estado = 1;
                            }
                            break;
                        case 5:
                            if (c == '+' || c == '-') {
                                estado2 = 6;
                                lexema += c;
                                i++;
                            } else if (Character.isDigit(c)) {
                                estado2 = 7;
                                lexema += c;
                                i++;
                            } else {
                                Interprete.error(linea, "Número no valido");
                                lexema = "";
                                lugarApuntador = i;
                                estado = 1;
                            }
                            break;
                        case 6:
                            if (Character.isDigit(c)) {
                                estado2 = 7;
                                i++;
                                lexema += c;
                            } else {
                                Interprete.error(linea, "Número no valido");
                                lexema = "";
                                lugarApuntador = i;
                                estado = 1;
                            }
                            break;
                        case 7:
                            if (Character.isDigit(c)) {
                                estado2 = 7;
                                i++;
                                lexema += c;
                            } else {
                                tokens.add(new Token(TipoToken.NUMERO, lexema, null, linea));
                                lexema = "";
                                lugarApuntador = i;
                                estado = 1;
                            }
                            break;
                        default:
                            estado = 1;
                    }
                    break;
                case 3:
                    if (i == lugarApuntador) {
                        if (c != '"') {
                            estado = 4;
                        } else {
                            i++;
                        }
                    } else {
                        if (c == '"') {
                            tokens.add(new Token(TipoToken.CADENA, lexema, null, linea));
                            lexema = "";
                            lugarApuntador = i;
                            estado = 1;
                            valido = false;
                        } else {
                            i++;
                            lineaC = i;
                            lexema += c;
                        }
                    }
                    break;
                case 4:
                    if (i == lugarApuntador) {
                        if (c != '/') {
                            estado = 6;
                        } else {
                            i++;
                        }
                    } else if (i == lugarApuntador + 1) {
                        if (c != '*') {
                            estado = 5;
                        } else {
                            i++;
                        }
                    } else {
                        if (c == '*') {
                            i++;
                        } else if (c == '/') {
                            lexema = "";
                            lugarApuntador = i;
                            estado = 1;
                            valido = false;
                        } else {
                            i++;
                            lineaC = i;
                        }
                    }
                    break;
                case 5:
                    if (i == lugarApuntador) {
                        if (c != '/') {
                            estado = 6;
                        } else {
                            i++;
                        }
                    } else if (i == lugarApuntador + 1) {
                        if (c != '/') {
                            estado = 7;
                        } else {
                            i++;
                        }
                    } else {
                        if (c != '\12') {
                            i++;
                        } else {
                            lugarApuntador = i;
                        }
                    }
                    break;
                case 6:
                    if (c == '(') {
                        tokens.add(new Token(TipoToken.PARABRE, "(", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == ')') {
                        tokens.add(new Token(TipoToken.PARCIERRA, ")", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == '{') {
                        tokens.add(new Token(TipoToken.LLAVEABRE, "{", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == '}') {
                        tokens.add(new Token(TipoToken.LLAVECIERAA, "}", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == '[') {
                        tokens.add(new Token(TipoToken.CORABRE, "[", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == ']') {
                        tokens.add(new Token(TipoToken.CORCIERRA, "]", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == ',') {
                        tokens.add(new Token(TipoToken.COMA, ",", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == '.') {
                        tokens.add(new Token(TipoToken.PUNTO, ".", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == ':') {
                        tokens.add(new Token(TipoToken.DOSPUNTOS, "(", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == ';') {
                        tokens.add(new Token(TipoToken.PUNTOYCOMA, ";", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == '-' || c == '+' || c == '/' || c == '*') {
                        tokens.add(new Token(TipoToken.OPARITMETICOS, "(", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == '=') {
                        tokens.add(new Token(TipoToken.IGUAL, "=", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == '<') {
                        tokens.add(new Token(TipoToken.OPCOMPARACION, "<", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (c == '>') {
                        tokens.add(new Token(TipoToken.OPCOMPARACION, ">", null, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 1;
                    } else if (i == lugarApuntador) {
                        if (c == '!') {
                            i++;
                            lexema += c;
                        } else if (c == '=') {
                            i++;
                            lexema += c;
                        } else if (c == '<') {
                            i++;
                            lexema += c;
                        } else if (c == '>') {
                            i++;
                            lexema += c;
                        }
                    } else if (i == lugarApuntador + 1) {
                        if (c == '=') {
                            lexema+=c;
                            tokens.add(new Token(TipoToken.OPCOMPARACION, lexema, null, linea));
                            lexema = "";
                            i++;
                            lugarApuntador = i;
                            estado = 1;
                        }
                    }
                    break;
                default:
                    Interprete.error(linea, "Error caracter no valido");
            }
        }
        if (valido) {
            Interprete.error(lineaC, "Cadena no cerrada");
        }
        return tokens;
    }
}