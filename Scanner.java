package interprete;

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
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("while", TipoToken.WHILE);
        palabrasReservadas.put("class", TipoToken.CLASS);
        palabrasReservadas.put("fun", TipoToken.FUN);
        palabrasReservadas.put("var", TipoToken.VAR);
        palabrasReservadas.put("id", TipoToken.ID);
        palabrasReservadas.put("true", TipoToken.VERDAD);
        palabrasReservadas.put("false", TipoToken.FALSO);
        palabrasReservadas.put("null", TipoToken.NULO);
        palabrasReservadas.put("this", TipoToken.ESTE);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("or", TipoToken.OR);
        
    }

    public Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        // Hay que leer todo el archivo
        int i = 0;
        String lexema = "";
        int estado = 0;
        int estado2 = 0;
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
            boolean bandera=false;
            char c = source.charAt(i);
<<<<<<< HEAD
            if (c == (char)10)
=======
           //System.out.println(c + " " + i);
            if (c == '\12')
>>>>>>> 60587dc5d2bfefc893156b573774e703d0612bde
                linea++;
            switch (estado) {
                
                //Palabras reservadas
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
                            tokens.add(new Token(a, lexema, linea));
                            lexema = "";
                            estado = 1;
                        }
                    }
                    break;
                
                
                //Identificadores
                case 1:
                    if (i == lugarApuntador) {
                        if (Character.isLetter(c) || c == '_') {
                            estado = 1;
                            lexema = lexema + c;
                            i++;
                            if(i==source.length()-1){
                                tokens.add(new Token(TipoToken.ID, lexema, linea));
                                lexema = "";
                                lugarApuntador = i;
                                estado = 2;
                            }
                        } else {
                            estado = 2;
                            i = lugarApuntador;
                            lexema = "";
                        }
                    } else {
                        if (Character.isLetterOrDigit(c) || c == '_') {
                            estado = 1;
                            lexema = lexema + c;
                            i++;
                        } else {
                            tokens.add(new Token(TipoToken.ID, lexema, linea));
                            lexema = "";
                            lugarApuntador = i;
                            estado = 2;
                        }
                    }
                    break;
                
                
                //Numeros
                case 2:
<<<<<<< HEAD
=======
                    //System.out.println(estado2);
                    //System.out.println(Character.isDigit(c));
>>>>>>> 60587dc5d2bfefc893156b573774e703d0612bde
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
                                tokens.add(new Token(TipoToken.NUMERO, lexema, linea));
                                lexema = "";
                                lugarApuntador = i;
<<<<<<< HEAD
                                estado = 3;
=======
                                estado = 1;
>>>>>>> 60587dc5d2bfefc893156b573774e703d0612bde
                                estado2 = 0;
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
                                estado2 = 0;
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
                                tokens.add(new Token(TipoToken.NUMERO, lexema, linea));
                                lexema = "";
                                lugarApuntador = i;
<<<<<<< HEAD
                                estado = 3;
=======
                                estado = 1;
>>>>>>> 60587dc5d2bfefc893156b573774e703d0612bde
                                estado2 = 0;
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
                                estado2 = 0;
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
                                estado2 = 0;
                            }
                            break;
                        case 7:
                            if (Character.isDigit(c)) {
                                estado2 = 7;
                                i++;
                                lexema += c;
                            } else {
                                tokens.add(new Token(TipoToken.NUMERO, lexema, linea));
                                lexema = "";
                                lugarApuntador = i;
<<<<<<< HEAD
                                estado = 3;
=======
                                estado = 1;
>>>>>>> 60587dc5d2bfefc893156b573774e703d0612bde
                                estado2 = 0;
                            }
                            break;
                        default:
                            estado = 1;
                    }
                    break;
                
                
                //Cadenas
                case 3:
                    if (i == lugarApuntador) {
                        if (c != '"') {
                            estado = 4;
                        } else {
                            valido = false;
                            i++;
                        }
                    } else {
                        if (c == '"') {
                            i++;
                            tokens.add(new Token(TipoToken.CADENA, lexema, linea));
                            lexema = "";
                            lugarApuntador = i;
<<<<<<< HEAD
                            estado = 4;
=======
                            estado = 1;
>>>>>>> 60587dc5d2bfefc893156b573774e703d0612bde
                            valido = true;
                        } else {
                            i++;
                            lineaC = i;
                            lexema += c;
                        }
                    }
                    break;
                
                
                //Comentarios largos
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
                            i++;
                            lugarApuntador = i;
                            estado = 6;
                            valido = true;
                        } else {
                            i++;
                            lineaC = i;
                        }
                    }
                    break;
                
                
                //Comentarios cortos
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
                
                
                //Caracteres
                case 6:
                    char c2 = '\0';
                    if(i+1<source.length()){
                        c2 = source.charAt(i+1);
                    }
                    if (c == '(') {
                        tokens.add(new Token(TipoToken.PARABRE, "(", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == ')') {
                        tokens.add(new Token(TipoToken.PARCIERRA, ")", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == '{') {
                        tokens.add(new Token(TipoToken.LLAVEABRE, "{", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == '}') {
                        tokens.add(new Token(TipoToken.LLAVECIERRA, "}", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == '[') {
                        tokens.add(new Token(TipoToken.CORABRE, "[", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == ']') {
                        tokens.add(new Token(TipoToken.CORCIERRA, "]", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == ',') {
                        tokens.add(new Token(TipoToken.COMA, ",", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == '.') {
                        tokens.add(new Token(TipoToken.PUNTO, ".", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == ':') {
                        tokens.add(new Token(TipoToken.DOSPUNTOS, "(", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == ';') {
                        tokens.add(new Token(TipoToken.PUNTOYCOMA, ";", linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == '-' || c == '+' || c == '/' || c == '*') {
                        tokens.add(new Token(TipoToken.OPARITMETICOS, ""+c, linea));
                        lexema = "";
                        i++;
                        lugarApuntador = i;
                        estado = 0;
                    } else if (c == '=') {
<<<<<<< HEAD
                        if(c2=='='){
                            tokens.add(new Token(TipoToken.OPCOMPARACION, "==", linea));
                            lexema = "";
                            i+=2;
                            lugarApuntador = i+1;
                            estado = 0;
                        }else{
                            tokens.add(new Token(TipoToken.IGUAL, "=", linea));
=======
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
                    } else if(c == ' '){
                        i++;
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
>>>>>>> 60587dc5d2bfefc893156b573774e703d0612bde
                            lexema = "";
                            i++;
                            lugarApuntador = i;
                            estado = 0;
                        }
                    } else if (c == '<') {
                        if(c2=='='){
                            tokens.add(new Token(TipoToken.OPCOMPARACION, "==", linea));
                            lexema = "";
                            i+=2;
                            lugarApuntador = i+1;
                            estado = 0;
                        }else{
                            tokens.add(new Token(TipoToken.OPCOMPARACION, "<", linea));
                            lexema = "";
                            i++;
                            lugarApuntador = i;
                            estado = 0;
                        }
                    } else if (c == '>') {
                        if(c2=='='){
                            tokens.add(new Token(TipoToken.OPCOMPARACION, "==", linea));
                            lexema = "";
                            i+=2;
                            lugarApuntador = i+1;
                            estado = 0;
                        }else{
                            tokens.add(new Token(TipoToken.OPCOMPARACION, ">", linea));
                            lexema = "";
                            i++;
                            lugarApuntador = i;
                            estado = 0;
                        }
                    } else if (c == '!') {
                        if(c2=='='){
                            tokens.add(new Token(TipoToken.OPCOMPARACION, "==", linea));
                            lexema = "";
                            i+=2;
                            lugarApuntador = i+1;
                            estado = 0;
                        }else{
                            tokens.add(new Token(TipoToken.OPCOMPARACION, "!", linea));
                            lexema = "";
                            i++;
                            lugarApuntador = i;
                            estado = 0;
                        }
                    } else if(c == (char)32){
                        i++;
                        estado = 0;
                        lugarApuntador = i;
                    }
                    break;
                default:
                    Interprete.error(linea, "Error caracter "+ (int)c +" no valido posición i:"+i);
                    bandera=true;
            }
<<<<<<< HEAD
            if(bandera)
                break;
=======
            //System.out.println(valido);
>>>>>>> 60587dc5d2bfefc893156b573774e703d0612bde
        }
        
        if (!valido) {
            Interprete.error(lineaC, "Cadena no cerrada");
        }
        return tokens;
    }
}