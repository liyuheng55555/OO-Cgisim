import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Token {
    // 变量，数，逻辑字面量，数学运算符，逻辑运算符，括号，赋值符号
    static enum Type {
        variable, number, logic, mathOperator, logicOperator, paren, assign
    }
    static HashMap<Type, Pattern> patMap = new HashMap<>();
    static {
        patMap.put(Type.variable, Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*"));
        patMap.put(Type.mathOperator, Pattern.compile("^[+\\-*/%]"));
        patMap.put(Type.number, Pattern.compile("^[0-9]+"));
    }

    String name;
    Type type;
    public Token(String name, Type type) {
        this.name = name;
        this.type = type;
    }
}

public class Parse {
    private Parse(){}
    static ArrayList<Token> generateTokenList(String expr) throws Exception{
        ArrayList<Token> result = new ArrayList<>();
        while(expr.length()>0) {
            boolean found = false;
            for (Token.Type type: Token.patMap.keySet()) {
                Pattern pat = Token.patMap.get(type);
                Matcher matcher = pat.matcher(expr);
                if (matcher.find()) {
                    String f = matcher.group();
                    System.out.println(f);
                    result.add(new Token(f, type));
                    expr = expr.substring(f.length());
                    found = true;
                    break;
                }
            }
            if (!found)
                throw new Exception("cannot parse"+expr.substring(0,20));
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            generateTokenList("1+2+namefd21+");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
