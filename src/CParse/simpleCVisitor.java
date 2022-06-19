// Generated from E:/��Ҫ����/ѧϰ/��2ѧ��/�������/trytry/src/main/java/Parse\simpleC.g4 by ANTLR 4.10.1
package CParse;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link simpleCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface simpleCVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link simpleCParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(simpleCParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NormalBlock}
	 * labeled alternative in {@link simpleCParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalBlock(simpleCParser.NormalBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EmptyBlock}
	 * labeled alternative in {@link simpleCParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyBlock(simpleCParser.EmptyBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code If}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(simpleCParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code While}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(simpleCParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Return}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn(simpleCParser.ReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Stat_only}
	 * labeled alternative in {@link simpleCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_only(simpleCParser.Stat_onlyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ElseBlock}
	 * labeled alternative in {@link simpleCParser#else}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseBlock(simpleCParser.ElseBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ElseState}
	 * labeled alternative in {@link simpleCParser#else}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseState(simpleCParser.ElseStateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ElseEmpty}
	 * labeled alternative in {@link simpleCParser#else}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseEmpty(simpleCParser.ElseEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link simpleCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExpr(simpleCParser.PrintExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link simpleCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(simpleCParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link simpleCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlank(simpleCParser.BlankContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Array}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(simpleCParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(simpleCParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(simpleCParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(simpleCParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(simpleCParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(simpleCParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqual(simpleCParser.EqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code And}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(simpleCParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Compare}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare(simpleCParser.CompareContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(simpleCParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link simpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(simpleCParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpleCParser#arr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArr(simpleCParser.ArrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negInt}
	 * labeled alternative in {@link simpleCParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegInt(simpleCParser.NegIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code posInt}
	 * labeled alternative in {@link simpleCParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPosInt(simpleCParser.PosIntContext ctx);
}