//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Stack;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//class Token {
//    // 变量，数，逻辑字面量，数学运算符，逻辑运算符，括号，赋值符号
//    enum Type {
//        variable, number, mathOperator//, logic, logicOperator, paren, assign
//    }
//    static HashMap<Type, Pattern> patMap = new HashMap<>();
//    static {
//        patMap.put(Type.variable, Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*"));
//        patMap.put(Type.mathOperator, Pattern.compile("^[+\\-*/%]"));
//        patMap.put(Type.number, Pattern.compile("^[0-9]+"));
//    }
//
//    String name;
//    Type type;
//    public Token(String name, Type type) {
//        this.name = name;
//        this.type = type;
//    }
//    public boolean isCalable() {
//        return type==Token.Type.number
//                || type==Token.Type.variable;
//    }
//    public Object getVal() throws Exception {
//        switch (type) {
//            case variable:
//                return Var.name2val(this.name);
//            case number:
//                return Integer.parseInt(name);
//            default:
//                throw new Exception("token " + name + " 不能转化成值");
//        }
//    }
//    public String getOpt() {
//        return name;
//    }
//}
//
//class Calculate {
//    static HashMap<String, Integer> priority = new HashMap<>();
//    static {
//        priority.put("(",14); priority.put(")",14);
//        priority.put("*",11); priority.put("/",11); priority.put("%",11);
//        priority.put("+",10); priority.put("-",10);
//        priority.put(">",9); priority.put(">=",9); priority.put("<",9); priority.put("<=",9);
//        priority.put("==",8); priority.put("!=",8);
//        priority.put("&",7);
//        priority.put("^",6);
//        priority.put("|",5);
//        priority.put("&&",4);
//        priority.put("||",3);
//    }
//    static private Stack<Object> valStack = new Stack<>(); // 值栈
//    static private Stack<String> optStack = new Stack<>(); // 运算符栈
//    static Object calculate(ArrayList<Token> tokenList) throws Exception{
//        valStack.clear();
//        optStack.clear();
//        for (Token token: tokenList) {
//            if (token.isCalable()) { // 是可计算量
//                Object val;
//                val = token.getVal();
//                valStack.push(val);
//            }
//            else {  // 是运算符
//                String opt = token.getOpt();
//                // 如果新到的token优先级低于前面的
//                if (!optStack.empty() && (priority.get(opt) < priority.get(optStack.peek()) ||  opt.equals(")"))) {
//                    partCalculate();
//                }
//                optStack.push(opt);
//            }
//        }
//        return valStack.pop();
//    }
//    // 把栈顶附近优先级相同的部分计算出来
//    static void partCalculate() {
//        int p = priority.get(optStack.peek());
//        Stack<Object> s1 = new Stack<>();
//        Stack<String> s2 = new Stack<>();
//        s1.push(valStack.pop());
//        while(!optStack.empty() && priority.get(optStack.peek())==p) {
//            s1.push(valStack.pop());
//            s2.push(optStack.pop());
//        }
//        while (!s2.empty()) {
//            Object newVal = unitCalculate(s1.pop(),s2.pop(),s1.pop());
//            s1.push(newVal);
//        }
//    }
////    // 将obj的类型调整为
////    static Object adjustType(Object obj) {
////        int x = 1;
////        (boolean)x
////    }
////    static boolean adjustBoolean(Object obj) {
////        if (obj instanceof boolean)
////    }
//    static Object unitCalculate(Object o1, String opt, Object o2) {
//        int v1 = (int)o1;
//        int v2 = (int)o2;
//        switch (opt) {
//            case "+":
//                return v1+v2;
//            case "-":
//                return v1-v2;
//            case "*":
//                return v1*v2;
//            case "/":
//                return v1/v2;
//            case "%":
//                return v1%v2;
//            default:
//                return 0;
//        }
//    }
//}
//
//public class Parse {
//    private Parse(){}
//    static ArrayList<Token> generateTokenList(String expr) throws Exception{
//        ArrayList<Token> result = new ArrayList<>();
//        while(expr.length()>0) {
//            boolean found = false;
//            for (Token.Type type: Token.patMap.keySet()) {
//                Pattern pat = Token.patMap.get(type);
//                Matcher matcher = pat.matcher(expr);
//                if (matcher.find()) {
//                    String f = matcher.group();
////                    System.out.println(f);
//                    result.add(new Token(f, type));
//                    expr = expr.substring(f.length());
//                    found = true;
//                    break;
//                }
//            }
//            if (!found)
//                throw new Exception("cannot parse"+expr.substring(0,20));
//        }
//        return result;
//    }
//
//    public static void main(String[] args) {
//        ArrayList<Token> tokenList = new ArrayList<>();
//        try {
//            tokenList = generateTokenList("1+2*3-4-46/2/3");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
////        tokenList.forEach(t-> System.out.println(t.name));
//        Object result;
//        try {
//            result = Calculate.calculate(tokenList);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return;
//        }
//        System.out.println(result);
//
//    }
//}
