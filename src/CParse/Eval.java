package CParse;

import java.util.ArrayList;

enum Type {
    START, END,
    BRANCH, MERGE,
    LOOP_START_LEFT, LOOP_START_MID, LOOP_START_RIGHT,
    LOOP_END_LEFT, LOOP_END_MID, LOOP_END_RIGHT,
    LOOP_ARROW,
    STATEMENT,
    // 线
    VERTICAL, HORIZON,
    左转下, 下转左,
    EMPTY,
}

public class Eval extends simpleCBaseVisitor<ArrayList<ArrayList<Type>>> {

    /**
     * 把数组arr用type填充，直到宽度为len
     * @param arr 待处理数组
     * @param len 要填充到的长度
     * @param type 用什么去填充
     */
    private void horizonFill(ArrayList<Type> arr, int len, Type type) {
        while(arr.size()<len)
            arr.add(type);
    }

    /**
     * 把二维数组arr用empty和竖线填充，直到高度为len
     * @param arr 待处理的二维数组
     * @param len 要填充到的长度
     */
    private void verticalFill(ArrayList<ArrayList<Type>> arr, int len) {
        ArrayList<Type> line = new ArrayList<>();
        line.add(Type.VERTICAL);
        if (!arr.isEmpty())
            while (line.size()<arr.get(0).size())
                line.add(Type.EMPTY);
        while (arr.size()<len)
            arr.add(new ArrayList<>(line));
    }

    static boolean good = true;

    /**
     * 检查arr的每行长度是否相等，用于debug
     * @param arr 待检查arr
     * @return 都相等则为true，反之false
     */
    private boolean sameWidthCheck(ArrayList<ArrayList<Type>> arr) {
        if (!good)
            return true;
        if (arr.isEmpty())
            return true;
        int w = arr.get(0).size();
        for (ArrayList<Type> ar : arr)
            if (ar.size()!=w) {
                good = false;
                for (ArrayList<Type> a : arr)
                    System.out.println(a);
                return false;
            }
        return true;
    }

    /**
     * prog以start为开头，end为结尾
     * @param ctx
     * @return
     */
    @Override
    public ArrayList<ArrayList<Type>> visitProg(simpleCParser.ProgContext ctx) {
        System.out.println(ctx.getChildCount());
//        int cnt = ctx.getChildCount();
//        for (int i=0; i<cnt; i++) {
//            System.out.println(ctx.getChild(i).getText());
//        }
        // 获取block矩阵
        ArrayList<ArrayList<Type>> son = visit(ctx.block());
        // 添加start、end
        ArrayList<Type> head = new ArrayList<>(), tail = new ArrayList<>();
        head.add(Type.START);
        while(head.size()<son.get(0).size())
            head.add(Type.EMPTY);
        tail.add(Type.END);
        while(tail.size()<son.get(0).size())
            tail.add(Type.EMPTY);
        son.add(0,head);
        son.add(tail);
        if (!sameWidthCheck(son))
            System.out.println(ctx.getText()+": not same");
        return son;
    }

    @Override
    public ArrayList<ArrayList<Type>> visitNormalBlock(simpleCParser.NormalBlockContext ctx) {
        ArrayList<ArrayList<Type>> result = new ArrayList<>();
        if (ctx.statement().size()==0)
            return result;
//        result = visit(ctx.statement(0));
        for (simpleCParser.StatementContext statement : ctx.statement()) {
            ArrayList<ArrayList<Type>> son = visit(statement);
            if (result.isEmpty())
                result = son;
            else {
                int size = Math.max(result.get(0).size(), son.get(0).size());
                for (ArrayList<Type> arr : result)
                    horizonFill(arr, size, Type.EMPTY);
                for (ArrayList<Type> arr : son)
                    horizonFill(arr, size, Type.EMPTY);
                result.addAll(son);
            }
        }
        if (!sameWidthCheck(result))
            System.out.println(ctx.getText()+": not same");
        return result;
    }

    @Override
    public ArrayList<ArrayList<Type>> visitEmptyBlock(simpleCParser.EmptyBlockContext ctx) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<ArrayList<Type>> visitIf(simpleCParser.IfContext ctx) {
        ArrayList<ArrayList<Type>> son1 = visit(ctx.block());
        ArrayList<ArrayList<Type>> son2 = visit(ctx.else_());
        int len = Math.max(son1.size(), son2.size());
        verticalFill(son1, len);
        verticalFill(son2, len);
        // 生成头部
        ArrayList<Type> head=new ArrayList<>();
        head.add(Type.BRANCH);
        if (!son1.isEmpty())
            horizonFill(head, son1.get(0).size(), Type.HORIZON);
        head.add(Type.左转下);
        if (!son1.isEmpty())
            horizonFill(head, son1.get(0).size()+son2.get(0).size(), Type.EMPTY);
        // 生成尾部
        ArrayList<Type> tail=new ArrayList<>();
        tail.add(Type.MERGE);
        if (!son1.isEmpty())
            horizonFill(tail, son1.get(0).size(), Type.HORIZON);
        tail.add(Type.下转左);
        if (!son1.isEmpty())
            horizonFill(tail, son1.get(0).size()+son2.get(0).size(), Type.EMPTY);
        System.out.println("tail: " + tail);
        // 拼接son1 son2
        for (int i=0; i<son1.size(); i++)
            son1.get(i).addAll(son2.get(i));
        // 添加头尾
        son1.add(0, head);
        son1.add(tail);
        if (!sameWidthCheck(son1))
            System.out.println(ctx.getText()+": not same");
        return son1;
    }

    @Override
    public ArrayList<ArrayList<Type>> visitStat_only(simpleCParser.Stat_onlyContext ctx) {
        ArrayList<ArrayList<Type>> arr = new ArrayList<>();
        ArrayList<Type> tmp = new ArrayList<>();
        tmp.add(Type.STATEMENT);
        arr.add(tmp);
        if (!sameWidthCheck(arr))
            System.out.println(ctx.getText()+": not same");
        return arr;
    }

    @Override
    public ArrayList<ArrayList<Type>> visitElseBlock(simpleCParser.ElseBlockContext ctx) {
        return visit(ctx.block());
    }

    @Override
    public ArrayList<ArrayList<Type>> visitElseState(simpleCParser.ElseStateContext ctx) {
        return visit(ctx.statement());
    }

    @Override
    public ArrayList<ArrayList<Type>> visitElseEmpty(simpleCParser.ElseEmptyContext ctx) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<ArrayList<Type>> visitWhile(simpleCParser.WhileContext ctx) {
        ArrayList<ArrayList<Type>> content = visit(ctx.block());
        // 生成循环头部
        ArrayList<Type> head = new ArrayList<>();
        head.add(Type.LOOP_START_LEFT);
        if (!content.isEmpty())
            while(head.size()<content.get(0).size())
                head.add(Type.LOOP_START_MID);
        head.add(Type.LOOP_START_RIGHT);
        // 生成循环尾部
        ArrayList<Type> tail = new ArrayList<>();
        tail.add(Type.LOOP_END_LEFT);
        if (!content.isEmpty())
            while(tail.size()<content.get(0).size())
                tail.add(Type.LOOP_END_MID);
        tail.add(Type.LOOP_END_RIGHT);
        //
        for (ArrayList<Type> line : content) {
            line.add(Type.LOOP_ARROW);
        }
        // 拼接
        content.add(0, head);
        content.add(tail);
        return content;
    }
}
