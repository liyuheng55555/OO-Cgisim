package Parse;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Eval类，在语法树上进行运算操作.
 */
public class Eval extends CgisimBaseVisitor<Object> {
    /**
     * 构造函数.
     * @param map   包含所有变量的map，
     *              运算过程中会从中读取变量，也会修改其中的变量
     */
    public Eval(Map<String,Object> map) {
        memory = map;
    }
    Map<String, Object> memory;

//    public boolean algorithmCheck()

    /**
     * 把换行符替换成空格，用于提供更美观的报错信息.
     * @param s     待处理字符串
     * @return      美化后字符串
     */
    private String beaut(String s) {
        return s.replace('\n', ' ');
    }

    /**
     * 访问赋值语句.
     *
     * @param ctx   该语句本身
     * @return      本语法认为赋值语句本身的值==左侧变量被赋的值
     */
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

    /**
     * 访问打印语句.
     *
     * @param ctx
     * @return
     */
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

    /**
     * 访问乘除余语句.
     *
     * @param ctx
     * @return
     */
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

    /**
     * 访问加减语句.
     *
     * @param ctx
     * @return
     */
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

/**
 * 自定义的运行时异常监听器.
 * <p>
 *     如果没有它的话，发生异常会只打印异常信息，而无法被外界catch到.
 *     因为是抄的所以不知道该写什么.
 * </p>
 * @author lyh
 * @since 0.1
 */
class ThrowingErrorListener extends BaseErrorListener {
    public static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
    }
}

