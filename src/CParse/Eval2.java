package CParse;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

class Type2{
    static ImageView START, END;
    static ImageView BRANCH, MERGE;
    static ImageView LOOP_START_LEFT, LOOP_START_MID, LOOP_START_RIGHT;
    static ImageView LOOP_END_LEFT, LOOP_END_MID, LOOP_END_RIGHT;
    static ImageView LOOP_ARROW;
    static ImageView STATEMENT;

    static ImageView VERTICAL, HORIZON;
    static ImageView 左转下, 下转左;
    static ImageView EMPTY;
    static {
        END = new ImageView();
        START = new ImageView();
        MERGE = new ImageView();
        BRANCH = new ImageView();
        VERTICAL = new ImageView();
        HORIZON = new ImageView();
        下转左 = new ImageView();
        左转下 = new ImageView();
        STATEMENT = new ImageView();
        LOOP_START_LEFT = new ImageView();
        LOOP_START_RIGHT = new ImageView();
        LOOP_START_MID = new ImageView();
        LOOP_END_LEFT = new ImageView();
        LOOP_END_RIGHT = new ImageView();
        LOOP_END_MID = new ImageView();
        LOOP_ARROW = new ImageView();
        EMPTY = new ImageView();
    }
}


public class Eval2 extends simpleCBaseVisitor<ArrayList<ArrayList<ImageView>>> {

    ImageView copy(ImageView oldImage) {
        ImageView newImage = new ImageView();
        newImage.setImage(oldImage.getImage());
        return newImage;
    }

    /**
     * 把数组arr用type填充，直到宽度为len
     * @param arr 待处理数组
     * @param len 要填充到的长度
     * @param type 用什么去填充
     */
    private void horizonFill(ArrayList<ImageView> arr, int len, ImageView type) {
        while(arr.size()<len)
            arr.add(copy(type));
    }

    /**
     * 把二维数组arr用empty和竖线填充，直到高度为len
     * @param arr 待处理的二维数组
     * @param len 要填充到的长度
     */
    private void verticalFill(ArrayList<ArrayList<ImageView>> arr, int len) {
        while (arr.size()<len) {
            ArrayList<ImageView> line = new ArrayList<>();
            line.add(copy(Type2.VERTICAL));
            if (!arr.isEmpty())
                while (line.size()<arr.get(0).size())
                    line.add(copy(Type2.EMPTY));
            arr.add(new ArrayList<>(line));
        }



    }

    static boolean good = true;

    /**
     * 检查arr的每行长度是否相等，用于debug
     * @param arr 待检查arr
     * @return 都相等则为true，反之false
     */
    private boolean sameWidthCheck(ArrayList<ArrayList<ImageView>> arr) {
        if (!good)
            return true;
        if (arr.isEmpty())
            return true;
        int w = arr.get(0).size();
        for (ArrayList<ImageView> ar : arr)
            if (ar.size()!=w) {
                good = false;
                for (ArrayList<ImageView> a : arr)
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
    public ArrayList<ArrayList<ImageView>> visitProg(simpleCParser.ProgContext ctx) {
        System.out.println(ctx.getChildCount());
//        int cnt = ctx.getChildCount();
//        for (int i=0; i<cnt; i++) {
//            System.out.println(ctx.getChild(i).getText());
//        }
        // 获取block矩阵
        ArrayList<ArrayList<ImageView>> son = visit(ctx.block());
        // 添加start、end
        ArrayList<ImageView> head = new ArrayList<>(), tail = new ArrayList<>();
        head.add(copy(Type2.START));
        while(head.size()<son.get(0).size())
            head.add(copy(Type2.EMPTY));
        tail.add(copy(Type2.END));
        while(tail.size()<son.get(0).size())
            tail.add(copy(Type2.EMPTY));
        son.add(0,head);
        son.add(tail);
        if (!sameWidthCheck(son))
            System.out.println(ctx.getText()+": not same");
        return son;
    }

    @Override
    public ArrayList<ArrayList<ImageView>> visitNormalBlock(simpleCParser.NormalBlockContext ctx) {
        ArrayList<ArrayList<ImageView>> result = new ArrayList<>();
        if (ctx.statement().size()==0)
            return result;
//        result = visit(ctx.statement(0));
        for (simpleCParser.StatementContext statement : ctx.statement()) {
            ArrayList<ArrayList<ImageView>> son = visit(statement);
            if (result.isEmpty())
                result = son;
            else {
                int size = Math.max(result.get(0).size(), son.get(0).size());
                for (ArrayList<ImageView> arr : result)
                    horizonFill(arr, size, copy(Type2.EMPTY));
                for (ArrayList<ImageView> arr : son)
                    horizonFill(arr, size, copy(Type2.EMPTY));
                result.addAll(son);
            }
        }
        if (!sameWidthCheck(result))
            System.out.println(ctx.getText()+": not same");
        return result;
    }

    @Override
    public ArrayList<ArrayList<ImageView>> visitEmptyBlock(simpleCParser.EmptyBlockContext ctx) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<ArrayList<ImageView>> visitIf(simpleCParser.IfContext ctx) {
        ArrayList<ArrayList<ImageView>> son1 = visit(ctx.block());
        ArrayList<ArrayList<ImageView>> son2 = visit(ctx.else_());
        int len = Math.max(son1.size(), son2.size());
        verticalFill(son1, len);
        verticalFill(son2, len);
        // 生成头部
        ArrayList<ImageView> head=new ArrayList<>();
        head.add(copy(Type2.BRANCH));
        head.get(0).setId(ctx.expr().getText());
        if (!son1.isEmpty())
            horizonFill(head, son1.get(0).size(), copy(Type2.HORIZON));
        head.add(copy(Type2.左转下));
        if (!son1.isEmpty())
            horizonFill(head, son1.get(0).size()+son2.get(0).size(), copy(Type2.EMPTY));
        // 生成尾部
        ArrayList<ImageView> tail=new ArrayList<>();
        tail.add(copy(Type2.MERGE));
        if (!son1.isEmpty())
            horizonFill(tail, son1.get(0).size(), copy(Type2.HORIZON));
        tail.add(copy(Type2.下转左));
        if (!son1.isEmpty())
            horizonFill(tail, son1.get(0).size()+son2.get(0).size(), copy(Type2.EMPTY));
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
    public ArrayList<ArrayList<ImageView>> visitStat_only(simpleCParser.Stat_onlyContext ctx) {
        ArrayList<ArrayList<ImageView>> arr = new ArrayList<>();
        ArrayList<ImageView> tmp = new ArrayList<>();
        tmp.add(copy(Type2.STATEMENT));
        tmp.get(0).setId(ctx.getText());
        arr.add(tmp);
        if (!sameWidthCheck(arr))
            System.out.println(ctx.getText()+": not same");
        return arr;
    }

    @Override
    public ArrayList<ArrayList<ImageView>> visitElseBlock(simpleCParser.ElseBlockContext ctx) {
        return visit(ctx.block());
    }

    @Override
    public ArrayList<ArrayList<ImageView>> visitElseState(simpleCParser.ElseStateContext ctx) {
        return visit(ctx.statement());
    }

    @Override
    public ArrayList<ArrayList<ImageView>> visitElseEmpty(simpleCParser.ElseEmptyContext ctx) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<ArrayList<ImageView>> visitWhile(simpleCParser.WhileContext ctx) {
        ArrayList<ArrayList<ImageView>> content = visit(ctx.block());
        // 生成循环头部
        ArrayList<ImageView> head = new ArrayList<>();
        head.add(copy(Type2.LOOP_START_LEFT));
        head.get(0).setId(ctx.expr().getText());
        if (!content.isEmpty())
            while(head.size()<content.get(0).size())
                head.add(copy(Type2.LOOP_START_MID));
        head.add(copy(Type2.LOOP_START_RIGHT));
        // 生成循环尾部
        ArrayList<ImageView> tail = new ArrayList<>();
        tail.add(copy(Type2.LOOP_END_LEFT));
        if (!content.isEmpty())
            while(tail.size()<content.get(0).size())
                tail.add(copy(Type2.LOOP_END_MID));
        tail.add(copy(Type2.LOOP_END_RIGHT));
        //
        for (ArrayList<ImageView> line : content) {
            line.add(copy(Type2.LOOP_ARROW));
        }
        // 拼接
        content.add(0, head);
        content.add(tail);
        return content;
    }
}
