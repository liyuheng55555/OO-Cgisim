// Generated from E:/重要资料/学习/第2学期/面向对象/面向对象小组作业/Cgisim/src/Parse\Cgisim.g4 by ANTLR 4.10.1
package Parse;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CgisimParser}.
 */
public interface CgisimListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CgisimParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(CgisimParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CgisimParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(CgisimParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link CgisimParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpr(CgisimParser.PrintExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link CgisimParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpr(CgisimParser.PrintExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign}
	 * labeled alternative in {@link CgisimParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssign(CgisimParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link CgisimParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssign(CgisimParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blank}
	 * labeled alternative in {@link CgisimParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBlank(CgisimParser.BlankContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link CgisimParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBlank(CgisimParser.BlankContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(CgisimParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(CgisimParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Or}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOr(CgisimParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOr(CgisimParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bool}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBool(CgisimParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBool(CgisimParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(CgisimParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(CgisimParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(CgisimParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(CgisimParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqual(CgisimParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqual(CgisimParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code And}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAnd(CgisimParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code And}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAnd(CgisimParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Compare}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCompare(CgisimParser.CompareContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Compare}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCompare(CgisimParser.CompareContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterId(CgisimParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitId(CgisimParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(CgisimParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link CgisimParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(CgisimParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negInt}
	 * labeled alternative in {@link CgisimParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterNegInt(CgisimParser.NegIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negInt}
	 * labeled alternative in {@link CgisimParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitNegInt(CgisimParser.NegIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code posInt}
	 * labeled alternative in {@link CgisimParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterPosInt(CgisimParser.PosIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code posInt}
	 * labeled alternative in {@link CgisimParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitPosInt(CgisimParser.PosIntContext ctx);
}