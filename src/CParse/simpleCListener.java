// Generated from E:/��Ҫ����/ѧϰ/��2ѧ��/�������/trytry/src/main/java/Parse\simpleC.g4 by ANTLR 4.10.1
package CParse;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link simpleCParser}.
 */
public interface simpleCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link simpleCParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(simpleCParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleCParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(simpleCParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NormalBlock}
	 * labeled alternative in {@link simpleCParser#block}.
	 * @param ctx the parse tree
	 */
	void enterNormalBlock(simpleCParser.NormalBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NormalBlock}
	 * labeled alternative in {@link simpleCParser#block}.
	 * @param ctx the parse tree
	 */
	void exitNormalBlock(simpleCParser.NormalBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyBlock}
	 * labeled alternative in {@link simpleCParser#block}.
	 * @param ctx the parse tree
	 */
	void enterEmptyBlock(simpleCParser.EmptyBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyBlock}
	 * labeled alternative in {@link simpleCParser#block}.
	 * @param ctx the parse tree
	 */
	void exitEmptyBlock(simpleCParser.EmptyBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code If}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIf(simpleCParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code If}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIf(simpleCParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code While}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile(simpleCParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code While}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile(simpleCParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Return}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn(simpleCParser.ReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Return}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn(simpleCParser.ReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Stat_only}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStat_only(simpleCParser.Stat_onlyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Stat_only}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStat_only(simpleCParser.Stat_onlyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ElseBlock}
	 * labeled alternative in {@link simpleCParser#else}.
	 * @param ctx the parse tree
	 */
	void enterElseBlock(simpleCParser.ElseBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ElseBlock}
	 * labeled alternative in {@link simpleCParser#else}.
	 * @param ctx the parse tree
	 */
	void exitElseBlock(simpleCParser.ElseBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ElseState}
	 * labeled alternative in {@link simpleCParser#else}.
	 * @param ctx the parse tree
	 */
	void enterElseState(simpleCParser.ElseStateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ElseState}
	 * labeled alternative in {@link simpleCParser#else}.
	 * @param ctx the parse tree
	 */
	void exitElseState(simpleCParser.ElseStateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ElseEmpty}
	 * labeled alternative in {@link simpleCParser#else}.
	 * @param ctx the parse tree
	 */
	void enterElseEmpty(simpleCParser.ElseEmptyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ElseEmpty}
	 * labeled alternative in {@link simpleCParser#else}.
	 * @param ctx the parse tree
	 */
	void exitElseEmpty(simpleCParser.ElseEmptyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link simpleCParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpr(simpleCParser.PrintExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link simpleCParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpr(simpleCParser.PrintExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign}
	 * labeled alternative in {@link simpleCParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssign(simpleCParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link simpleCParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssign(simpleCParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blank}
	 * labeled alternative in {@link simpleCParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBlank(simpleCParser.BlankContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link simpleCParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBlank(simpleCParser.BlankContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Array}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArray(simpleCParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Array}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArray(simpleCParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(simpleCParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(simpleCParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Or}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOr(simpleCParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOr(simpleCParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bool}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBool(simpleCParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBool(simpleCParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(simpleCParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(simpleCParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(simpleCParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(simpleCParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqual(simpleCParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqual(simpleCParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code And}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAnd(simpleCParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code And}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAnd(simpleCParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Compare}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCompare(simpleCParser.CompareContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Compare}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCompare(simpleCParser.CompareContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterId(simpleCParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitId(simpleCParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(simpleCParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(simpleCParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpleCParser#arr}.
	 * @param ctx the parse tree
	 */
	void enterArr(simpleCParser.ArrContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleCParser#arr}.
	 * @param ctx the parse tree
	 */
	void exitArr(simpleCParser.ArrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negInt}
	 * labeled alternative in {@link simpleCParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterNegInt(simpleCParser.NegIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negInt}
	 * labeled alternative in {@link simpleCParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitNegInt(simpleCParser.NegIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code posInt}
	 * labeled alternative in {@link simpleCParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterPosInt(simpleCParser.PosIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code posInt}
	 * labeled alternative in {@link simpleCParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitPosInt(simpleCParser.PosIntContext ctx);
}