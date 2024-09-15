import java.util.*;

public class Lexer{
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

    public enum Token{
        ID,
        ENT,
        REAL,
        ESP,
        OP
    }
}