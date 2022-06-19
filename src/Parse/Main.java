package Parse;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.HashMap;
import java.util.Map;

public class Main {

    /**
     * 获得表达式的结果类型，目前可能为int、bool、float；
     * @param expr  表达式字符串
     * @param map   变量map
     * @return      类型名
     * @throws Exception 如果表达式不合法，比如无法解析或变量有问题，就会抛出异常，详见e.getMessage()
     */
    public static String getType(String expr, Map<String,Object> map) throws Exception {
        CodePointCharStream cpcs = CharStreams.fromString(expr);
        CgisimLexer lexer = new CgisimLexer(cpcs);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CgisimParser parser = new CgisimParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);
        CgisimParser.ProgContext pContext = parser.prog();
        TypeEval typeEval = new TypeEval(map);
        Object result = typeEval.visit(pContext);
        switch (result.getClass().getName()) {
            case "Integer": return "int";
            case "Boolean": return "bool";
            case "Float": return "float";
        }
        return "未知的类型："+result.getClass().getName();
    }
    /**
     * 对给定的表达式和变量表执行运算.
     *
     * @param expr          表达式
     * @param map           变量表
     * @throws Exception    可能抛出无法解析的异常，或者运行时异常
     */
    public static Object run(String expr, Map<String,Object> map) throws Exception {
        CodePointCharStream cpcs = CharStreams.fromString(expr);
        CgisimLexer lexer = new CgisimLexer(cpcs);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CgisimParser parser = new CgisimParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);
        CgisimParser.ProgContext pContext = parser.prog();
        Eval eval = new Eval(map);
        return eval.visit(pContext);
    }

    static private void help(String expr, HashMap<String,Object> map) {
        try {
            run(expr,map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(expr.replace('\n',' ')+" 计算成功");
    }

    public static void main(String[] args) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("a",2);
        map.put("b",3);
        map.put("g",true);
        help("a+2+3",map);
        help("a+2+3\n",map);
        help("g && g || g\n",map);
        help("g&&a!=b\n",map);
        help("g && a == b\n",map);
        help("g && a || b\n",map);
        help("a+a-a*a*(a*a/a+b)*b%a\n",map);
        help("5e\n",map);
        help("e5\n",map);
        help("+a+b+e\n",map);

//        String expr = ""

    }
}

