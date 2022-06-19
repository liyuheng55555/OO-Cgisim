package Parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeEval extends CgisimBaseVisitor<Object>{
    /**
     * 构造函数.
     * @param map   包含所有变量的map，
     *              运算过程中会从中读取变量，也会修改其中的变量
     */
    public TypeEval(Map<String,Object> map) {
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
     * 如果o1、o2中有一个float 且另一个是int，就把两个都升格成为float；
     * 两个float、两个int就原样返回；
     * 有bool存在就抛出异常；
     * @param o1
     * @param o2
     * @return
     */
    private List<Object> intToFloat(Object o1, Object o2) throws RuntimeException {
        List<Object> result = new ArrayList<>();
        if (o1 instanceof Boolean || o2 instanceof Boolean)
            throw new RuntimeException("完蛋");
        if (o1 instanceof Float && o2 instanceof Integer)
            o2 = Float.valueOf(o2.toString());
        if (o2 instanceof Float && o1 instanceof Integer)
            o1 = Float.valueOf(o1.toString());
        result.add(o1);
        result.add(o2);
        return result;
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
        Object result = visit(ctx.expr());
        System.out.println(result);
        return result;
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
    public Object visitMulDiv(CgisimParser.MulDivContext ctx) throws RuntimeException {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        List<Object> list = null;
        try {
            list = intToFloat(left, right);
        } catch (RuntimeException re) {
            throw new RuntimeException(ctx.getText()+"：类型错误，不能运算");
        }
        left = list.get(0);
        right = list.get(1);
        if (left.getClass().equals(Integer.class)) {
            Integer i1 = (Integer)left;
            Integer i2 = (Integer)right;
            switch (ctx.op.getType()) {
                case CgisimParser.MUL:
                case CgisimParser.MOD:
                case CgisimParser.DIV:
                    return i1;
            }
            throw new RuntimeException("未知的错误");
        }
        else if (left.getClass().equals(Float.class)) {
            Float i1 = (Float)left;
            Float i2 = (Float)right;
            switch (ctx.op.getType()) {
                case CgisimParser.MUL:
                case CgisimParser.DIV:
                    return i1;
                case CgisimParser.MOD: throw new RuntimeException(beaut(ctx.getText())+": 尝试对浮点数做取余运算");
            }
            throw new RuntimeException("未知的错误");
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
        List<Object> list = null;
        try {
            list = intToFloat(left, right);
        } catch (RuntimeException re) {
            throw new RuntimeException(ctx.getText()+"：类型错误，不能运算");
        }
        left = list.get(0);
        right = list.get(1);
        return left;
//        if (left.getClass().equals(Integer.class)) {
//            Integer i1 = (Integer)left;
//            Integer i2 = (Integer)right;
//            switch (ctx.op.getType()) {
//                case CgisimParser.ADD:
//                case CgisimParser.SUB:
//                    return i1;
//            }
//            throw new RuntimeException("未知的错误");
//        }
//        else if (left.getClass().equals(Float.class)) {
//            Float i1 = (Float)left;
//            Float i2 = (Float)right;
//            switch (ctx.op.getType()) {
//                case CgisimParser.ADD: return i1+i2;
//                case CgisimParser.SUB: return i1-i2;
//            }
//            throw new RuntimeException("未知的错误");
//        }
//        throw new RuntimeException("未知的错误");
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
            case CgisimParser.EQUAL: return left.equals(right);
            case CgisimParser.NE: return !left.equals(right);
        }
        throw new RuntimeException("未知的错误");
    }

    @Override
    public Object visitCompare(CgisimParser.CompareContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        List<Object> list = null;
        try {
            list = intToFloat(left, right);
        } catch (RuntimeException re) {
            throw new RuntimeException(ctx.getText()+"：类型错误，不能运算");
        }
        left = list.get(0);
        right = list.get(1);
        return Boolean.TRUE;
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

    @Override
    public Object visitFlt(CgisimParser.FltContext ctx) {
        return visit(ctx.float_());
    }

    @Override
    public Object visitNegFloat(CgisimParser.NegFloatContext ctx) {
        return -(Float) visit(ctx.float_());
    }

    @Override
    public Object visitPosFloat(CgisimParser.PosFloatContext ctx) {
        String str = ctx.getText();
        Float f = null;
        try {
            f = Float.valueOf(str);
        } catch (Exception e) {
            throw new RuntimeException(str+"无法作为浮点数被解析");
        }
        return f;
    }


}
