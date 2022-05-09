package Parse;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.HashMap;
import java.util.Map;

//import ;

public class Eval extends CgisimBaseVisitor<Object> {
    public Eval(Map<String,Object> map) {
        memory = map;
    }
    Map<String, Object> memory;

//    public boolean algorithmCheck()

    private String beaut(String s) {
        return s.replace('\n', ' ');
    }

    @Override
    public Object visitAssign(CgisimParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        if(!memory.containsKey(id))
            throw new RuntimeException(beaut(ctx.getText())+": 变量 "+id+" 不存在");
        Object value = visit(ctx.expr());
        if (!memory.get(id).getClass().equals(value.getClass()))
            throw new RuntimeException(beaut(ctx.getText())+": 类型错误");
        memory.put(id, value);
        return value;
    }

    @Override
    public Object visitPrintExpr(CgisimParser.PrintExprContext ctx) {
        System.out.println(visit(ctx.expr()));
        return 0;
    }

    @Override
    public Object visitInt(CgisimParser.IntContext ctx) {
        return visit(ctx.integer());
    }

    @Override
    public Object visitId(CgisimParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id))
            return memory.get(id);
        throw new RuntimeException(beaut(ctx.getText())+": 变量 "+id+" 不存在");
    }

    @Override
    public Object visitMulDiv(CgisimParser.MulDivContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        if (!left.getClass().equals(Integer.class) || !right.getClass().equals(Integer.class))
            throw new RuntimeException(beaut(ctx.getText())+": 尝试对非数字做算术运算");
        Integer i1 = (Integer)left;
        Integer i2 = (Integer)right;
        switch (ctx.op.getType()) {
            case CgisimParser.MUL: return i1*i2;
            case CgisimParser.DIV: return i1/i2;
            case CgisimParser.MOD: return i1%i2;
        }
        throw new RuntimeException("未知的错误");
    }

    @Override
    public Object visitAddSub(CgisimParser.AddSubContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        if (!left.getClass().equals(Integer.class) || !right.getClass().equals(Integer.class))
            throw new RuntimeException(beaut(ctx.getText())+": 尝试对非数字做算术运算");
        Integer i1 = (Integer)left;
        Integer i2 = (Integer)right;
        switch (ctx.op.getType()) {
            case CgisimParser.ADD: return i1+i2;
            case CgisimParser.SUB: return i1-i2;
        }
        throw new RuntimeException("未知的错误");
    }

    @Override
    public Object visitOr(CgisimParser.OrContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        if (!left.getClass().equals(Boolean.class) || !right.getClass().equals(Boolean.class))
            throw new RuntimeException(beaut(ctx.getText())+": 尝试对非逻辑算式做或运算");
        return (Boolean)left || (Boolean)right;
    }

    @Override
    public Object visitAnd(CgisimParser.AndContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        if (!left.getClass().equals(Boolean.class) || !right.getClass().equals(Boolean.class))
            throw new RuntimeException(beaut(ctx.getText())+": 尝试对非逻辑算式做与运算");
        return (Boolean)left && (Boolean)right;
    }

    @Override
    public Object visitBool(CgisimParser.BoolContext ctx) {
        return Boolean.valueOf(ctx.getText());
    }

    @Override
    public Object visitEqual(CgisimParser.EqualContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        if (!left.getClass().equals(right.getClass()))
            throw new RuntimeException(beaut(ctx.getText())+": 尝试对不同性质的算式做判等运算");
        switch (ctx.op.getType()) {
            case CgisimParser.EQUAL: return left == right;
            case CgisimParser.NE: return left != right;
        }
        throw new RuntimeException("未知的错误");
    }

    @Override
    public Object visitCompare(CgisimParser.CompareContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        if (!left.getClass().equals(Integer.class) || !right.getClass().equals(Integer.class))
            throw new RuntimeException(beaut(ctx.getText())+": 尝试对非数字做算术运算");
        Integer i1 = (Integer)left;
        Integer i2 = (Integer)right;
        switch (ctx.op.getType()) {
            case CgisimParser.L:    return i1 < i2;
            case CgisimParser.G:    return i1 > i2;
            case CgisimParser.LE:   return i1 <= i2;
            case CgisimParser.GE:   return i1 >= i2;
        }
        throw new RuntimeException("未知的错误");
    }

    @Override
    public Object visitNegInt(CgisimParser.NegIntContext ctx) {
        return - (Integer) visit(ctx.integer());
    }

    @Override
    public Object visitPosInt(CgisimParser.PosIntContext ctx) {
        return Integer.parseInt(ctx.NATURAL().getText());
    }

    @Override
    public Object visitParens(CgisimParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
}

class ThrowingErrorListener extends BaseErrorListener {
    public static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
    }

}

