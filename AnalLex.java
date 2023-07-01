package interprete;

public class AnalLex {
    public boolean esNum(String num){
        int i = 0;
        int estado = 0;
        char c;
        boolean bandera = false;
        do{
            c = num.charAt(i);
            switch(estado){
                case 0:
                    if(c == '+' || c == '-'){
                        estado = 1;
                    } else if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
                        estado = 2;
                    } else{
                        bandera=false;
                        break;
                    }
                    break;
                case 1:
                    if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
                        estado = 2;
                    } else{
                        bandera=false;
                        break;
                    }
                    break;
                case 2:
                    if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
                        estado = 2;
                    } else if(c == '.'){
                        estado = 3;
                    } else if(c == 'e' || c == 'E'){
                        estado = 5;
                    } else{
                        if(c == '\0'){
                            bandera=true;
                            break;
                        } else{
                            bandera=false;
                            break;
                        }
                    }
                    break;
                case 3: 
                    if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
                        estado = 4;
                    } else{
                        bandera=false;
                        break;
                    }
                    break;
                case 4:
                    if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
                        estado = 4;
                    } else if(c == 'e' || c == 'E'){
                        estado = 5;
                    } else{
                        if(c == '\0'){
                            bandera=true;
                            break;
                        } else{
                            bandera=false;
                            break;
                        }
                    }
                    break;
                case 5:
                    if(c == '+' || c == '-'){
                        estado = 6;
                    } else if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
                        estado = 7;
                    } else{
                        bandera=false;
                        break;
                    }
                    break;
                case 6: 
                    if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
                        estado = 7;
                    } else{
                        bandera=false;
                        break;
                    }
                    break;
                case 7:
                    if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
                        estado = 7;
                    } else{
                        if(c == '\0'){
                            bandera=true;
                            break;
                        } else{
                            bandera=false;
                            break;
                        }
                    }
                    break;
                default:
                    bandera=false;
                    break;
            }
            i++;
        } while(i < num.length()); 
        return bandera;
    }
    public boolean esCadena(String cadena){
        if(cadena.charAt(0)=='"'){
            if(cadena.charAt(cadena.length())=='"')
                return true;
        }
        return false;
    }

    public boolean esComentario(String comentario){
        if(comentario.charAt(0)=='/'&&comentario.charAt(1)=='/'){
            return true;
        }else if(comentario.charAt(0)=='/'&&comentario.charAt(1)=='*'&&comentario.charAt(comentario.charAt(comentario.length()-1))=='*'&&comentario.charAt(comentario.length())=='/'){
            return true;
        }
        return false;
    }

    public boolean esId(String cadena){
        int i = 0;
        int estado = 0;
        char c;
        boolean bandera = false;
        do{
            c=cadena.charAt(i);
            switch(estado){
                case 0:
                    if(c=='_'||Character.isLetter(c)){
                        estado=1;
                        bandera=true;
                    }else{
                        bandera=false;
                        break;
                    }
                case 1:
                    if(Character.isLetterOrDigit(c)){
                        estado=2;
                        bandera=true;
                    }else{
                        bandera=false;
                        break;
                    }
                case 2:
                    if(Character.isLetterOrDigit(c)){
                        estado=2;
                        bandera=true;
                    }else{
                        if(c=='\0'){
                            bandera=true;
                            break;
                        }
                        else{
                            bandera=false;
                            break;
                        }
                    }
                default:
                    bandera=false;
                    break;
            }
        }while(i<cadena.length());
        return bandera;
    }
}