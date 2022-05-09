package Parse;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void run(String expr, Map<String,Object> map) throws Exception {
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
        eval.visit(pContext);
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
//        try {
//            run("a=5\n" +
//                    "b =3\n" +
//                    "c=a*b+3\n" +
//                    " (cc)\n" +
//                    "c*c\n",null);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("a",2);
        map.put("b",3);
        map.put("g",true);
        help("a+2+3",map);
        help("a+2+3\n",map);
        help("g && g || g\n",map);
        help("g && a != b\n",map);
        help("g && a == b\n",map);
        help("g && a || b\n",map);
        help("a+a-a*a*(a*a/a+b)*b%a\n",map);
        help("5e\n",map);
        help("e5\n",map);
        help("+a+b+e\n",map);

//        String expr = ""

    }
}

