// Generated from E:/重要资料/学习/第2学期/面向对象/OO-Cgisim/src/Parse\Cgisim.g4 by ANTLR 4.10.1
package Parse;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CgisimLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, PFloat=4, ID=5, BOOL=6, NATURAL=7, NEWLINE=8, 
		WS=9, MUL=10, DIV=11, ADD=12, SUB=13, MOD=14, L=15, G=16, EQUAL=17, NE=18, 
		LE=19, GE=20, AND=21, OR=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "PFloat", "ID", "BOOL", "NATURAL", "NEWLINE", 
			"WS", "MUL", "DIV", "ADD", "SUB", "MOD", "L", "G", "EQUAL", "NE", "LE", 
			"GE", "AND", "OR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "'('", "')'", null, null, null, null, null, null, "'*'", 
			"'/'", "'+'", "'-'", "'%'", "'<'", "'>'", "'=='", "'!='", "'<='", "'>='", 
			"'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "PFloat", "ID", "BOOL", "NATURAL", "NEWLINE", 
			"WS", "MUL", "DIV", "ADD", "SUB", "MOD", "L", "G", "EQUAL", "NE", "LE", 
			"GE", "AND", "OR"
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


	public CgisimLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Cgisim.g4"; }

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
		"\u0004\u0000\u0016\u0082\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0004\u00035\b\u0003\u000b\u0003"+
		"\f\u00036\u0001\u0003\u0001\u0003\u0005\u0003;\b\u0003\n\u0003\f\u0003"+
		">\t\u0003\u0001\u0004\u0001\u0004\u0005\u0004B\b\u0004\n\u0004\f\u0004"+
		"E\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005P\b\u0005"+
		"\u0001\u0006\u0004\u0006S\b\u0006\u000b\u0006\f\u0006T\u0001\u0007\u0003"+
		"\u0007X\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0004\b]\b\b\u000b\b\f"+
		"\b^\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0000\u0000\u0016\u0001\u0001\u0003\u0002\u0005\u0003\u0007"+
		"\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b"+
		"\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013"+
		"\'\u0014)\u0015+\u0016\u0001\u0000\u0004\u0001\u000009\u0003\u0000AZ_"+
		"_az\u0004\u000009AZ__az\u0002\u0000\t\t  \u0088\u0000\u0001\u0001\u0000"+
		"\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000"+
		"\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000"+
		"\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000"+
		"\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000"+
		"\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
		"\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000"+
		"\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000"+
		"#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001"+
		"\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000"+
		"\u0000\u0001-\u0001\u0000\u0000\u0000\u0003/\u0001\u0000\u0000\u0000\u0005"+
		"1\u0001\u0000\u0000\u0000\u00074\u0001\u0000\u0000\u0000\t?\u0001\u0000"+
		"\u0000\u0000\u000bO\u0001\u0000\u0000\u0000\rR\u0001\u0000\u0000\u0000"+
		"\u000fW\u0001\u0000\u0000\u0000\u0011\\\u0001\u0000\u0000\u0000\u0013"+
		"b\u0001\u0000\u0000\u0000\u0015d\u0001\u0000\u0000\u0000\u0017f\u0001"+
		"\u0000\u0000\u0000\u0019h\u0001\u0000\u0000\u0000\u001bj\u0001\u0000\u0000"+
		"\u0000\u001dl\u0001\u0000\u0000\u0000\u001fn\u0001\u0000\u0000\u0000!"+
		"p\u0001\u0000\u0000\u0000#s\u0001\u0000\u0000\u0000%v\u0001\u0000\u0000"+
		"\u0000\'y\u0001\u0000\u0000\u0000)|\u0001\u0000\u0000\u0000+\u007f\u0001"+
		"\u0000\u0000\u0000-.\u0005=\u0000\u0000.\u0002\u0001\u0000\u0000\u0000"+
		"/0\u0005(\u0000\u00000\u0004\u0001\u0000\u0000\u000012\u0005)\u0000\u0000"+
		"2\u0006\u0001\u0000\u0000\u000035\u0007\u0000\u0000\u000043\u0001\u0000"+
		"\u0000\u000056\u0001\u0000\u0000\u000064\u0001\u0000\u0000\u000067\u0001"+
		"\u0000\u0000\u000078\u0001\u0000\u0000\u00008<\u0005.\u0000\u00009;\u0007"+
		"\u0000\u0000\u0000:9\u0001\u0000\u0000\u0000;>\u0001\u0000\u0000\u0000"+
		"<:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=\b\u0001\u0000\u0000"+
		"\u0000><\u0001\u0000\u0000\u0000?C\u0007\u0001\u0000\u0000@B\u0007\u0002"+
		"\u0000\u0000A@\u0001\u0000\u0000\u0000BE\u0001\u0000\u0000\u0000CA\u0001"+
		"\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000D\n\u0001\u0000\u0000\u0000"+
		"EC\u0001\u0000\u0000\u0000FG\u0005t\u0000\u0000GH\u0005r\u0000\u0000H"+
		"I\u0005u\u0000\u0000IP\u0005e\u0000\u0000JK\u0005f\u0000\u0000KL\u0005"+
		"a\u0000\u0000LM\u0005l\u0000\u0000MN\u0005s\u0000\u0000NP\u0005e\u0000"+
		"\u0000OF\u0001\u0000\u0000\u0000OJ\u0001\u0000\u0000\u0000P\f\u0001\u0000"+
		"\u0000\u0000QS\u0007\u0000\u0000\u0000RQ\u0001\u0000\u0000\u0000ST\u0001"+
		"\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000"+
		"U\u000e\u0001\u0000\u0000\u0000VX\u0005\r\u0000\u0000WV\u0001\u0000\u0000"+
		"\u0000WX\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000YZ\u0005\n\u0000"+
		"\u0000Z\u0010\u0001\u0000\u0000\u0000[]\u0007\u0003\u0000\u0000\\[\u0001"+
		"\u0000\u0000\u0000]^\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000\u0000"+
		"^_\u0001\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000`a\u0006\b\u0000\u0000"+
		"a\u0012\u0001\u0000\u0000\u0000bc\u0005*\u0000\u0000c\u0014\u0001\u0000"+
		"\u0000\u0000de\u0005/\u0000\u0000e\u0016\u0001\u0000\u0000\u0000fg\u0005"+
		"+\u0000\u0000g\u0018\u0001\u0000\u0000\u0000hi\u0005-\u0000\u0000i\u001a"+
		"\u0001\u0000\u0000\u0000jk\u0005%\u0000\u0000k\u001c\u0001\u0000\u0000"+
		"\u0000lm\u0005<\u0000\u0000m\u001e\u0001\u0000\u0000\u0000no\u0005>\u0000"+
		"\u0000o \u0001\u0000\u0000\u0000pq\u0005=\u0000\u0000qr\u0005=\u0000\u0000"+
		"r\"\u0001\u0000\u0000\u0000st\u0005!\u0000\u0000tu\u0005=\u0000\u0000"+
		"u$\u0001\u0000\u0000\u0000vw\u0005<\u0000\u0000wx\u0005=\u0000\u0000x"+
		"&\u0001\u0000\u0000\u0000yz\u0005>\u0000\u0000z{\u0005=\u0000\u0000{("+
		"\u0001\u0000\u0000\u0000|}\u0005&\u0000\u0000}~\u0005&\u0000\u0000~*\u0001"+
		"\u0000\u0000\u0000\u007f\u0080\u0005|\u0000\u0000\u0080\u0081\u0005|\u0000"+
		"\u0000\u0081,\u0001\u0000\u0000\u0000\b\u00006<COTW^\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}