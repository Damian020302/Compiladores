import java.util.*;

public class Lexer{
    /**
     * Estados del AFD
     */
    public enum Edo{
        q0,
        q1,
        q2,
        q3,
        q4,
        q5,
        q6,
        q7,
        q8
    }

    /**
     * Tokens
     */
    public enum Token{
        ID,
        ENT,
        REAL,
        ESP,
        OP
    }

    public Edo edoActual;
    public String entrada;
    public int indice;
    public StringBuilder valorToken;

    /**
     * Builder
     */
    public Lexer(String entrada){
        this.entrada = entrada;
        this.indice = 0;
        this.edoActual = Edo.q0;
        this.valorToken = new StringBuilder();
    }

    /**
     * Analiza si el caracter es válido
     */
    public List<Token> analizar(){
        List<Token> tokens = new ArrayList<>()
        while(indice < entrada.length()){
            char caracter = entrada.charAt(indice);
            Estado edoSiguiente = edoSiguiente(caracter);
            if(edoSiguiente == null){
                throw new RuntimeException("Caracter no valido " + indice);
            }
            edoActual = edoSiguiente;
            valorToken.append(caracter);
            indice++;
            if(esEdoAceptacion(edoActual)){
                tokens.add(new Token(getTipoToken(edoActual), valorToken.toString()));
                valorToken.setLength(0);
            }
        }
        return tokens;
    }

    /**
     * Obtiene el siguiente estado
     */
    public Edo edoSiguiente(char caracter){
        switch (edoActual){
            case q0:
                if(Character.isLetter(caracter)){
                    return Edo.q1;
                } else if (Charater.isDigit(caracter) && caracter != '0'){
                    return Edo.q2;
                } else if (caracter == '0'){
                    return Edo.q3;
                } else if (Character.isWhitespace(caracter)){
                    return Edo.q4;
                } else if (caracter == ':'){
                    return Edo.q5;
                } else if (caracter == '+'){
                    return Edo.q6;
                }
                break;
            case q1:
                if (Character.isLetter(caracter)){
                    return Edo.q1;
                }
                break;
            case q2:
                if (Character.isDigit(caracter)){
                    return Edo.q2;
                } else if (caracter == '.'){
                    return Edo.q7;
                }
                break;
            case q3:
                if (caracter == '.'){
                    return Edo.q7;
                }
                break;
            case q4:
                if (Character.isWhitespace(caracter)){
                    return Edo.q4;
                }
                break;
            case q5:
                if (caracter == ':'){
                    return Edo.q8;
                }
                break;
            case q6:
                break;
            case q7:
                if (Character.isDigit(caracter)){
                    return Edo.q7;
                }
                break;
            case q8:
                if (caracter == '='){
                    return Edo.q6;
                }
                break;
        }
        return null;
    }

    /**
     * Verifica que sea un Estado de Aceptación
     */
    public boolean esEdoAceptacion(Edo edo){
        switch (edo){
            case q1:
            case q2:
            case q3:
            case q4:
            case q6:
            case q7:
                return true;
            default:
                return false;
        }
    }

    /**
     * Obtiene el tipo de Token
     */
    public Token getTipoToken(Edo edo){
        switch(edo){
            case q1:
                return Token.ID;
            case q2:
            case q3:
                return Token.ENT;
            case q4:
                return Token.ESP;
            case q6:
                return Token.OP;
            case q7:
                return Token.REAL;
            default:
                throw new RuntimeException("Token no valido");
            
        }
    }
}