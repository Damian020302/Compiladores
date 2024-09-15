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
}