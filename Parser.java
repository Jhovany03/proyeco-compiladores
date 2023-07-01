package interprete;

import interprete.Token;
import java.util.List;

public class Parser {

    private final List<Token> tokens;

    private final Token Else = new Token(TipoToken.ELSE, "else");
    private final Token Return = new Token(TipoToken.RETURN, "return");
    private final Token For = new Token(TipoToken.FOR, "for");
    private final Token If = new Token(TipoToken.IF, "if");
    private final Token While = new Token(TipoToken.WHILE, "while");
    private final Token mayorque = new Token(TipoToken.OPCOMPARACION, ">");
    private final Token menorque = new Token(TipoToken.OPCOMPARACION, "<");
    private final Token igualcomp = new Token(TipoToken.OPCOMPARACION, "==");
    private final Token mayorigual = new Token(TipoToken.OPCOMPARACION, ">=");
    private final Token menorigual = new Token(TipoToken.OPCOMPARACION, "<=");
    private final Token diferente = new Token(TipoToken.OPCOMPARACION, "!=");
    private final Token negacion = new Token(TipoToken.OPCOMPARACION, "!");
    private final Token parabre = new Token(TipoToken.PARABRE, "(");
    private final Token parcierra = new Token(TipoToken.PARCIERRA, ")");
    private final Token corabre = new Token(TipoToken.CORABRE, "[");
    private final Token corcierra = new Token(TipoToken.CORCIERRA, "]");
    private final Token llaveabre = new Token(TipoToken.LLAVEABRE, "{");
    private final Token llavecierra = new Token(TipoToken.LLAVECIERRA, "}");
    private final Token cadena = new Token(TipoToken.CADENA, "");
    private final Token identificador = new Token(TipoToken.IDENTIFICADOR, "");
    private final Token numero = new Token(TipoToken.NUMERO, "");
    private final Token coma = new Token(TipoToken.COMA, ",");
    private final Token punto = new Token(TipoToken.PUNTO, ".");
    private final Token puntoycoma = new Token(TipoToken.PUNTOYCOMA, ";");
    private final Token suma = new Token(TipoToken.OPARITMETICOS, "+");
    private final Token resta = new Token(TipoToken.OPARITMETICOS, "-");
    private final Token multiplicacion = new Token(TipoToken.OPARITMETICOS, "*");
    private final Token division = new Token(TipoToken.OPARITMETICOS, "/");
    private final Token igual = new Token(TipoToken.IGUAL, "=");
    private final Token finCadena = new Token(TipoToken.EOF, "");
    private final Token clas = new Token(TipoToken.CLASS, "class");
    private final Token fun = new Token(TipoToken.FUN, "fun");
    private final Token var = new Token(TipoToken.VAR, "var");
    private final Token id = new Token(TipoToken.ID, "id");
    private final Token verdad = new Token(TipoToken.VERDAD, "true");
    private final Token falso = new Token(TipoToken.FALSO, "false");
    private final Token nulo = new Token(TipoToken.NULO, "null");
    private final Token este = new Token(TipoToken.ESTE, "this");
    private final Token superr = new Token(TipoToken.SUPERR, "super");
    private final Token print = new Token(TipoToken.PRINT, "print");
    private final Token or = new Token(TipoToken.OR, "or");
    private final Token and = new Token(TipoToken.AND, "and");
    private final Token epsilon = new Token(TipoToken.EPSILON, "");

    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void parse(){
        i = 0;
        preanalisis = tokens.get(i);
        program();

        if(hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }
        else if(!hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Consulta válida");
        }

        /*if(!preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }else if(!hayErrores){
            System.out.println("Consulta válida");
        }*/
    }

    void program(){
        if(preanalisis.equals(clas)||preanalisis.equals(fun)||preanalisis.equals(var)
        ||preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)||preanalisis.equals(For)
        ||preanalisis.equals(If)||preanalisis.equals(print)||preanalisis.equals(While)
        ||preanalisis.equals(Return)||preanalisis.equals(llaveabre)){
            declaration();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba una palabra reservada.");
        }
    }
    
    void declaration(){
        if(hayErrores) return;

        if(preanalisis.equals(clas)){
            class_decl();   
            declaration();
        }else if(preanalisis.equals(fun)){
            fun_decl();
            declaration();
        }else if(preanalisis.equals(var)){
            System.out.println("Entre var_decl");
            var_decl();
            declaration();
        }else if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)||preanalisis.equals(For)
        ||preanalisis.equals(If)||preanalisis.equals(print)||preanalisis.equals(While)
        ||preanalisis.equals(Return)||preanalisis.equals(llaveabre)){
            statement();
            declaration();
        }else{
            
        }
    }

    void class_decl(){
        if(hayErrores) return;

        if(preanalisis.equals(clas)){
            coincidir(clas);
            coincidir(id);
            class_inher();
            coincidir(llaveabre);
            functions();
            coincidir(llavecierra);
        }else{
            System.out.println("class_decl");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void class_inher(){
        if(hayErrores) return;

        if(preanalisis.equals(menorque)){
            coincidir(menorque);
            coincidir(id);
        }else{
            
        }
    }

    void fun_decl(){
        if(hayErrores) return;

        if(preanalisis.equals(fun)){
            coincidir(fun);
            function();
        }else{
            System.out.println("fun_decl");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }
    
    void var_decl(){
        if(hayErrores) return;
        
        if(preanalisis.equals(var)){
            coincidir(var);
            coincidir(id);
            var_init();
            coincidir(puntoycoma);
        }else{
            System.out.println("var_decl");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void var_init(){
        if(hayErrores) return;
        if(preanalisis.equals(igual)){
            coincidir(igual);
            expression();
        }else{
            
        }
    }

    void statement(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            expr_stmt();
        }else if(preanalisis.equals(For)){
            for_stmt();            
        }else if(preanalisis.equals(If)){
            if_stmt();            
        }else if(preanalisis.equals(print)){
            print_stmt();            
        }else if(preanalisis.equals(Return)){
            return_stmt();            
        }else if(preanalisis.equals(While)){
            while_stmt();            
        }else if(preanalisis.equals(llaveabre)){
            block();            
        }else{
            System.out.println("statement");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void expr_stmt(){
        if(hayErrores) return;

        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            expression();
            coincidir(puntoycoma);            
        }else{
            System.out.println("expr_stmt");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void for_stmt(){
        if(hayErrores) return;
        
        if(preanalisis.equals(For)){
            coincidir(For);
            coincidir(parabre);
            for_stmt_1();
            for_stmt_2();
            for_stmt_3();
            coincidir(parcierra);
            statement();            
        }else{
            System.out.println("for_stmt");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void for_stmt_1(){
        if(hayErrores) return;

        if(preanalisis.equals(var)){
            var_decl();            
        }else if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            expr_stmt();
        }else if(preanalisis.equals(puntoycoma)){
            coincidir(puntoycoma);
        }else{
            System.out.println("for_stmt_1");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void for_stmt_2(){
        if(hayErrores) return;

        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            expression();
            coincidir(puntoycoma);
        }else if(preanalisis.equals(puntoycoma)){
            coincidir(puntoycoma);
        }else{
            System.out.println("for_stmt_2");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void for_stmt_3(){
        if(hayErrores) return;

        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            expression();
        }else{
            
        }
    }

    void if_stmt(){
        if(hayErrores) return;
        
        if(preanalisis.equals(If)){
            coincidir(If);
            coincidir(parabre);
            expression();
            coincidir(parcierra);
            statement();
            else_statement();
        }else{
            System.out.println("if_stmt");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void else_statement(){
        if(hayErrores) return;

        if(preanalisis.equals(Else)){
            coincidir(Else);
            statement();
        }else{
            
        }
    }

    void print_stmt(){
        if(hayErrores) return;

        if(preanalisis.equals(print)){
            coincidir(print);
            expression();
            coincidir(puntoycoma);
        }else{
            System.out.println("print_stmt");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void return_stmt(){
        if(hayErrores) return;

        if(preanalisis.equals(Return)){
            coincidir(Return);
            return_exp_opc();
            coincidir(puntoycoma);
        }else{
            System.out.println("return_stmt");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void return_exp_opc(){
        if(hayErrores) return;

        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            expression();
        }else{
            
        }
    }

    void while_stmt(){
        if(hayErrores) return;

        if(preanalisis.equals(While)){
            coincidir(While);
            coincidir(parabre);
            expression();
            coincidir(parcierra);
            statement();
        }else{
            System.out.println("while_stmt");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void block(){
        if(hayErrores) return;

        if(preanalisis.equals(llaveabre)){
            coincidir(llaveabre);
            block_decl();
            coincidir(llavecierra);
        }else{
            
        }
    }

    void block_decl(){
        if(hayErrores) return;

        if(preanalisis.equals(clas)||preanalisis.equals(fun)||preanalisis.equals(var)
        ||preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)||preanalisis.equals(For)
        ||preanalisis.equals(If)||preanalisis.equals(print)||preanalisis.equals(While)
        ||preanalisis.equals(Return)||preanalisis.equals(llaveabre)){
            declaration();
            block_decl();
        }else{
            
        }
    }

    void expression(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            assignment();
        }else{
            System.out.println("expression");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void assignment(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            logic_or();
            assignment_opc();
        }else{
            System.out.println("assigment");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void assignment_opc(){
        if(hayErrores) return;

        if(preanalisis.equals(igual)){
            coincidir(igual);
            expression();
        }else{
            
        }
    }

    void logic_or(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            logic_and();
            logic_or_2();
        }else{
            System.out.println("logic_or");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void logic_or_2(){
        if(hayErrores) return;

        if(preanalisis.equals(or)){
            coincidir(or);
            logic_and();
            logic_or_2();
        }else{
            
        }
    }

    void logic_and(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            equality();
            logic_and_2();
        }else{
            System.out.println("logic_and");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void logic_and_2(){
        if(hayErrores) return;

        if(preanalisis.equals(and)){
            coincidir(and);
            equality();
            logic_and_2();
        }else{
            
        }
    }

    void equality(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            comparison();
            equality_2();
        }else{
            System.out.println("equality");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void equality_2(){
        if(hayErrores) return;

        if(preanalisis.equals(diferente)){
            coincidir(diferente);
            comparison();
            equality_2();
        }else if(preanalisis.equals(igualcomp)){
            coincidir(igualcomp);
            comparison();
            equality_2();
        }else{
            
        }
    }

    void comparison(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            term();
            comparison_2();
        }else{
            System.out.println("comparison");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void comparison_2(){
        if(hayErrores) return;
        
        if(preanalisis.equals(mayorque)){
            coincidir(mayorque);
            term();
            comparison_2();
        }else if(preanalisis.equals(mayorigual)){
            coincidir(mayorigual);
            term();
            comparison_2();
        }else if(preanalisis.equals(menorque)){
            coincidir(menorque);
            term();
            comparison_2();
        }else if(preanalisis.equals(menorigual)){
            coincidir(menorigual);
            term();
            comparison_2();
        }else{
            
        }
    }

    void term(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            factor();
            term_2();
        }else{
            System.out.println("term");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void term_2(){
        if(hayErrores) return;

        if(preanalisis.equals(resta)){
            coincidir(resta);
            factor();
            term_2();
        }else if(preanalisis.equals(suma)){
            coincidir(suma);
            factor();
            term_2();
        }else{
            
        }
    }

    void factor(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            unary();
            factor_2();
        }else{
            System.out.println("factor");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void factor_2(){
        if(hayErrores) return;
        
        if(preanalisis.equals(division)){
            coincidir(division);
            unary();
            factor_2();
        }else if(preanalisis.equals(multiplicacion)){
            coincidir(multiplicacion);
            unary();
            factor_2();
        }else{
            
        }
    }

    void unary(){
        if(hayErrores) return;
        
        if(preanalisis.equals(negacion)){
            coincidir(negacion);
            unary();
        }else if(preanalisis.equals(resta)){
            coincidir(resta);
            unary();
        }else if(preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            call();
        }else{
            System.out.println("unary");
            hayErrores = true;
            System.out.println("Error en la posición " + i + ". Se esperaba otro caracter");
        }
    }

    void call(){
        if(hayErrores) return;
        
        if(preanalisis.equals(verdad)||preanalisis.equals(falso)||preanalisis.equals(nulo)
        ||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)
        ||preanalisis.equals(id)||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            primary();
            call_2();
        }else{
            System.out.println("call");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void call_2(){
        if(hayErrores) return;
        
        if(preanalisis.equals(parabre)){
            coincidir(parabre);
            arguments_opc();
            coincidir(parcierra);
            call_2();
        }else if(preanalisis.equals(punto)){
            coincidir(punto);
            coincidir(id);
            call_2();
        }else{
            
        }
    }

    void call_opc(){
        if(hayErrores) return;

        if(preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            call();
            coincidir(punto);
        }else{
            
        }
    }

    void primary(){
        if(hayErrores) return;
        
        if(preanalisis.equals(verdad)){
            coincidir(verdad);
        }else if(preanalisis.equals(falso)){
            coincidir(falso);
        }else if(preanalisis.equals(nulo)){
            coincidir(nulo);
        }else if(preanalisis.equals(este)){
            coincidir(este);
        }else if(preanalisis.equals(numero)){
            coincidir(numero);
        }else if(preanalisis.equals(cadena)){
            coincidir(cadena);
        }else if(preanalisis.equals(id)){
            coincidir(id);
        }else if(preanalisis.equals(parabre)){
            coincidir(parabre);
            expression();
            coincidir(parcierra);
        }else if(preanalisis.equals(superr)){
            coincidir(superr);
            coincidir(punto);
            coincidir(id);
        }else{
            System.out.println("primary");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void function(){
        if(hayErrores) return;

        if(preanalisis.equals(id)){
            coincidir(id);
            coincidir(parabre);
            parameters_opc();
            coincidir(parcierra);
            block();
        }else{
            System.out.println("function");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void functions(){
        if(hayErrores) return;

        if(preanalisis.equals(id)){
            function();
            functions();
        }else{
            
        }
    }

    void parameters_opc(){
        if(hayErrores) return;

        if(preanalisis.equals(id)){
            parameters();
        }else{
            
        }
    }

    void parameters(){
        if(hayErrores) return;

        if(preanalisis.equals(id)){
            coincidir(id);
            parameters_2();
        }else{
            System.out.println("parameters");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void parameters_2(){
        if(hayErrores) return;

        if(preanalisis.equals(coma)){
            coincidir(coma);
            coincidir(id);
            parameters_2();
        }else{
            
        }
    }

    void arguments_opc(){
        if(hayErrores) return;

        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            arguments();
        }else{
            
        }
    }

    void arguments(){
        if(hayErrores) return;

        if(preanalisis.equals(negacion)||preanalisis.equals(resta)||preanalisis.equals(verdad)
        ||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)
        ||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(id)
        ||preanalisis.equals(parabre)||preanalisis.equals(superr)){
            expression();
            arguments_2();
        }else{
            System.out.println("arguments");
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void arguments_2(){
        if(hayErrores) return;

        if(preanalisis.equals(coma)){
            coincidir(coma);
            expression();
            arguments_2();
        }else{
            
        }
    }

    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            if(tokens.size()-1>i)
                i++;
            preanalisis = tokens.get(i);
        }
        else{
            System.out.println("coicidir:"+ preanalisis);
            System.out.println("i:"+i);
            hayErrores = true;
            System.out.println("Error en la posición " + i + ". Se esperaba un  " + t.tipo);

        }
    }
}
