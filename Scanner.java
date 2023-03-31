import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import AnalLex;

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
        palabrasReservadas.put("(", TipoToken.PARABRE);
        palabrasReservadas.put(")", TipoToken.PARCIERRA);
        palabrasReservadas.put("{", TipoToken.LLAVEABRE);
        palabrasReservadas.put("}", TipoToken.LLAVECIERAA);
        palabrasReservadas.put("[", TipoToken.CORABRE);
        palabrasReservadas.put("]", TipoToken.CORCIERRA);
        palabrasReservadas.put(",", TipoToken.COMA);
        palabrasReservadas.put(".", TipoToken.PUNTO);
        palabrasReservadas.put(";", TipoToken.PUNTOYCOMA);
        palabrasReservadas.put("-", TipoToken.OPARITMETICOS);
        palabrasReservadas.put("+", TipoToken.OPARITMETICOS);
        palabrasReservadas.put("*", TipoToken.OPARITMETICOS);
        palabrasReservadas.put("/", TipoToken.OPARITMETICOS);
        palabrasReservadas.put("!=", TipoToken.OPCOMPARACION);
        palabrasReservadas.put("==", TipoToken.OPCOMPARACION);
        palabrasReservadas.put("=", TipoToken.IGUAL);
        palabrasReservadas.put("<", TipoToken.OPCOMPARACION);
        palabrasReservadas.put("<=", TipoToken.OPCOMPARACION);
        palabrasReservadas.put(">=", TipoToken.OPCOMPARACION);
        palabrasReservadas.put(">", TipoToken.OPCOMPARACION);
    }

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens(String lexema, Object literal, int linea) {
        if(esNum(lexema)){
            tokens.add(new Token(TipoToken.NUMERO, lexema, literal, linea));
        }else if(esCadena(lexema)){
            tokens.add(new Token(TipoToken.CADENA, lexema, literal, linea));
        }else if(esID(lexema)){
            tokens.add(new Token(TipoToken.IDENTIFICADOR, lexema, literal, linea));
        }else{
            String aux;
            boolean flag = false;
            for(Map.Entry<String,TipoToken> palabra : palabrasReservadas.entrySet()){
                aux = palabra.getKey();
                if(aux.equalsIgnoreCase(lexema)){
                    tokens.add(new Token(palabra.getValue(), lexema, literal, linea));
                    flag = true;
                    break;
                }
            }
            if(!flag){
                Interprete.error(linea, "Lexema desconocido");
            }
        }

        return tokens;
    }
}