// Generated from E:/��Ҫ����/ѧϰ/��2ѧ��/�������/trytry/src/main/java/Parse\simpleC.g4 by ANTLR 4.10.1
package CParse;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class simpleCLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, ID=14, BOOL=15, NATURAL=16, NEWLINE=17, 
		WS=18, MUL=19, DIV=20, ADD=21, SUB=22, MOD=23, L=24, G=25, EQUAL=26, NE=27, 
		LE=28, GE=29, AND=30, OR=31;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "ID", "BOOL", "NATURAL", "NEWLINE", 
			"WS", "MUL", "DIV", "ADD", "SUB", "MOD", "L", "G", "EQUAL", "NE", "LE", 
			"GE", "AND", "OR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'main'", "'('", "')'", "'{'", "'}'", "'if'", "'while'", 
			"'return'", "'else'", "'='", "'['", "']'", null, null, null, "';'", null, 
			"'*'", "'/'", "'+'", "'-'", "'%'", "'<'", "'>'", "'=='", "'!='", "'<='", 
			"'>='", "'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "ID", "BOOL", "NATURAL", "NEWLINE", "WS", "MUL", "DIV", "ADD", 
			"SUB", "MOD", "L", "G", "EQUAL", "NE", "LE", "GE", "AND", "OR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public simpleCLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "simpleC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u001f\u00ab\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d"+
		"\u0002\u001e\u0007\u001e\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0005\rn\b\r\n\r\f\rq\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003"+
		"\u000e|\b\u000e\u0001\u000f\u0004\u000f\u007f\b\u000f\u000b\u000f\f\u000f"+
		"\u0080\u0001\u0010\u0001\u0010\u0001\u0011\u0004\u0011\u0086\b\u0011\u000b"+
		"\u0011\f\u0011\u0087\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0000"+
		"\u0000\u001f\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b"+
		"\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b"+
		"\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016"+
		"-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f\u0001"+
		"\u0000\u0004\u0003\u0000AZ__az\u0004\u000009AZ__az\u0001\u000009\u0003"+
		"\u0000\t\n\r\r  \u00ae\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003"+
		"\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007"+
		"\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001"+
		"\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000"+
		"\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000"+
		"\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000"+
		"\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000"+
		"\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000"+
		"\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000"+
		"\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000"+
		")\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001"+
		"\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000"+
		"\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u0000"+
		"7\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001"+
		"\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0001?\u0001\u0000\u0000"+
		"\u0000\u0003C\u0001\u0000\u0000\u0000\u0005H\u0001\u0000\u0000\u0000\u0007"+
		"J\u0001\u0000\u0000\u0000\tL\u0001\u0000\u0000\u0000\u000bN\u0001\u0000"+
		"\u0000\u0000\rP\u0001\u0000\u0000\u0000\u000fS\u0001\u0000\u0000\u0000"+
		"\u0011Y\u0001\u0000\u0000\u0000\u0013`\u0001\u0000\u0000\u0000\u0015e"+
		"\u0001\u0000\u0000\u0000\u0017g\u0001\u0000\u0000\u0000\u0019i\u0001\u0000"+
		"\u0000\u0000\u001bk\u0001\u0000\u0000\u0000\u001d{\u0001\u0000\u0000\u0000"+
		"\u001f~\u0001\u0000\u0000\u0000!\u0082\u0001\u0000\u0000\u0000#\u0085"+
		"\u0001\u0000\u0000\u0000%\u008b\u0001\u0000\u0000\u0000\'\u008d\u0001"+
		"\u0000\u0000\u0000)\u008f\u0001\u0000\u0000\u0000+\u0091\u0001\u0000\u0000"+
		"\u0000-\u0093\u0001\u0000\u0000\u0000/\u0095\u0001\u0000\u0000\u00001"+
		"\u0097\u0001\u0000\u0000\u00003\u0099\u0001\u0000\u0000\u00005\u009c\u0001"+
		"\u0000\u0000\u00007\u009f\u0001\u0000\u0000\u00009\u00a2\u0001\u0000\u0000"+
		"\u0000;\u00a5\u0001\u0000\u0000\u0000=\u00a8\u0001\u0000\u0000\u0000?"+
		"@\u0005i\u0000\u0000@A\u0005n\u0000\u0000AB\u0005t\u0000\u0000B\u0002"+
		"\u0001\u0000\u0000\u0000CD\u0005m\u0000\u0000DE\u0005a\u0000\u0000EF\u0005"+
		"i\u0000\u0000FG\u0005n\u0000\u0000G\u0004\u0001\u0000\u0000\u0000HI\u0005"+
		"(\u0000\u0000I\u0006\u0001\u0000\u0000\u0000JK\u0005)\u0000\u0000K\b\u0001"+
		"\u0000\u0000\u0000LM\u0005{\u0000\u0000M\n\u0001\u0000\u0000\u0000NO\u0005"+
		"}\u0000\u0000O\f\u0001\u0000\u0000\u0000PQ\u0005i\u0000\u0000QR\u0005"+
		"f\u0000\u0000R\u000e\u0001\u0000\u0000\u0000ST\u0005w\u0000\u0000TU\u0005"+
		"h\u0000\u0000UV\u0005i\u0000\u0000VW\u0005l\u0000\u0000WX\u0005e\u0000"+
		"\u0000X\u0010\u0001\u0000\u0000\u0000YZ\u0005r\u0000\u0000Z[\u0005e\u0000"+
		"\u0000[\\\u0005t\u0000\u0000\\]\u0005u\u0000\u0000]^\u0005r\u0000\u0000"+
		"^_\u0005n\u0000\u0000_\u0012\u0001\u0000\u0000\u0000`a\u0005e\u0000\u0000"+
		"ab\u0005l\u0000\u0000bc\u0005s\u0000\u0000cd\u0005e\u0000\u0000d\u0014"+
		"\u0001\u0000\u0000\u0000ef\u0005=\u0000\u0000f\u0016\u0001\u0000\u0000"+
		"\u0000gh\u0005[\u0000\u0000h\u0018\u0001\u0000\u0000\u0000ij\u0005]\u0000"+
		"\u0000j\u001a\u0001\u0000\u0000\u0000ko\u0007\u0000\u0000\u0000ln\u0007"+
		"\u0001\u0000\u0000ml\u0001\u0000\u0000\u0000nq\u0001\u0000\u0000\u0000"+
		"om\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000p\u001c\u0001\u0000"+
		"\u0000\u0000qo\u0001\u0000\u0000\u0000rs\u0005t\u0000\u0000st\u0005r\u0000"+
		"\u0000tu\u0005u\u0000\u0000u|\u0005e\u0000\u0000vw\u0005f\u0000\u0000"+
		"wx\u0005a\u0000\u0000xy\u0005l\u0000\u0000yz\u0005s\u0000\u0000z|\u0005"+
		"e\u0000\u0000{r\u0001\u0000\u0000\u0000{v\u0001\u0000\u0000\u0000|\u001e"+
		"\u0001\u0000\u0000\u0000}\u007f\u0007\u0002\u0000\u0000~}\u0001\u0000"+
		"\u0000\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000"+
		"\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0081 \u0001\u0000\u0000\u0000"+
		"\u0082\u0083\u0005;\u0000\u0000\u0083\"\u0001\u0000\u0000\u0000\u0084"+
		"\u0086\u0007\u0003\u0000\u0000\u0085\u0084\u0001\u0000\u0000\u0000\u0086"+
		"\u0087\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0087"+
		"\u0088\u0001\u0000\u0000\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089"+
		"\u008a\u0006\u0011\u0000\u0000\u008a$\u0001\u0000\u0000\u0000\u008b\u008c"+
		"\u0005*\u0000\u0000\u008c&\u0001\u0000\u0000\u0000\u008d\u008e\u0005/"+
		"\u0000\u0000\u008e(\u0001\u0000\u0000\u0000\u008f\u0090\u0005+\u0000\u0000"+
		"\u0090*\u0001\u0000\u0000\u0000\u0091\u0092\u0005-\u0000\u0000\u0092,"+
		"\u0001\u0000\u0000\u0000\u0093\u0094\u0005%\u0000\u0000\u0094.\u0001\u0000"+
		"\u0000\u0000\u0095\u0096\u0005<\u0000\u0000\u00960\u0001\u0000\u0000\u0000"+
		"\u0097\u0098\u0005>\u0000\u0000\u00982\u0001\u0000\u0000\u0000\u0099\u009a"+
		"\u0005=\u0000\u0000\u009a\u009b\u0005=\u0000\u0000\u009b4\u0001\u0000"+
		"\u0000\u0000\u009c\u009d\u0005!\u0000\u0000\u009d\u009e\u0005=\u0000\u0000"+
		"\u009e6\u0001\u0000\u0000\u0000\u009f\u00a0\u0005<\u0000\u0000\u00a0\u00a1"+
		"\u0005=\u0000\u0000\u00a18\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005>"+
		"\u0000\u0000\u00a3\u00a4\u0005=\u0000\u0000\u00a4:\u0001\u0000\u0000\u0000"+
		"\u00a5\u00a6\u0005&\u0000\u0000\u00a6\u00a7\u0005&\u0000\u0000\u00a7<"+
		"\u0001\u0000\u0000\u0000\u00a8\u00a9\u0005|\u0000\u0000\u00a9\u00aa\u0005"+
		"|\u0000\u0000\u00aa>\u0001\u0000\u0000\u0000\u0005\u0000o{\u0080\u0087"+
		"\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}