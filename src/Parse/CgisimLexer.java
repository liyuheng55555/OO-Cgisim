// Generated from E:/重要资料/学习/第2学期/面向对象/面向对象小组作业/Cgisim/src/Parse\Cgisim.g4 by ANTLR 4.10.1
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
		T__0=1, T__1=2, T__2=3, ID=4, BOOL=5, NATURAL=6, NEWLINE=7, WS=8, MUL=9, 
		DIV=10, ADD=11, SUB=12, MOD=13, L=14, G=15, EQUAL=16, NE=17, LE=18, GE=19, 
		AND=20, OR=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "ID", "BOOL", "NATURAL", "NEWLINE", "WS", "MUL", 
			"DIV", "ADD", "SUB", "MOD", "L", "G", "EQUAL", "NE", "LE", "GE", "AND", 
			"OR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "'('", "')'", null, null, null, null, null, "'*'", "'/'", 
			"'+'", "'-'", "'%'", "'<'", "'>'", "'=='", "'!='", "'<='", "'>='", "'&&'", 
			"'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "ID", "BOOL", "NATURAL", "NEWLINE", "WS", "MUL", 
			"DIV", "ADD", "SUB", "MOD", "L", "G", "EQUAL", "NE", "LE", "GE", "AND", 
			"OR"
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
		"\u0004\u0000\u0015t\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0005\u00034\b\u0003\n\u0003\f\u00037\t\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0003\u0004B\b\u0004\u0001\u0005\u0004"+
		"\u0005E\b\u0005\u000b\u0005\f\u0005F\u0001\u0006\u0003\u0006J\b\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0004\u0007O\b\u0007\u000b\u0007"+
		"\f\u0007P\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0000\u0000\u0015\u0001\u0001\u0003\u0002\u0005\u0003"+
		"\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015"+
		"\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012"+
		"%\u0013\'\u0014)\u0015\u0001\u0000\u0004\u0003\u0000AZ__az\u0004\u0000"+
		"09AZ__az\u0001\u000009\u0002\u0000\t\t  x\u0000\u0001\u0001\u0000\u0000"+
		"\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000"+
		"\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000"+
		"\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000"+
		"\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000"+
		"\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000"+
		"\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000"+
		"\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000"+
		"\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001"+
		"\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000"+
		"\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0001+\u0001\u0000\u0000\u0000"+
		"\u0003-\u0001\u0000\u0000\u0000\u0005/\u0001\u0000\u0000\u0000\u00071"+
		"\u0001\u0000\u0000\u0000\tA\u0001\u0000\u0000\u0000\u000bD\u0001\u0000"+
		"\u0000\u0000\rI\u0001\u0000\u0000\u0000\u000fN\u0001\u0000\u0000\u0000"+
		"\u0011T\u0001\u0000\u0000\u0000\u0013V\u0001\u0000\u0000\u0000\u0015X"+
		"\u0001\u0000\u0000\u0000\u0017Z\u0001\u0000\u0000\u0000\u0019\\\u0001"+
		"\u0000\u0000\u0000\u001b^\u0001\u0000\u0000\u0000\u001d`\u0001\u0000\u0000"+
		"\u0000\u001fb\u0001\u0000\u0000\u0000!e\u0001\u0000\u0000\u0000#h\u0001"+
		"\u0000\u0000\u0000%k\u0001\u0000\u0000\u0000\'n\u0001\u0000\u0000\u0000"+
		")q\u0001\u0000\u0000\u0000+,\u0005=\u0000\u0000,\u0002\u0001\u0000\u0000"+
		"\u0000-.\u0005(\u0000\u0000.\u0004\u0001\u0000\u0000\u0000/0\u0005)\u0000"+
		"\u00000\u0006\u0001\u0000\u0000\u000015\u0007\u0000\u0000\u000024\u0007"+
		"\u0001\u0000\u000032\u0001\u0000\u0000\u000047\u0001\u0000\u0000\u0000"+
		"53\u0001\u0000\u0000\u000056\u0001\u0000\u0000\u00006\b\u0001\u0000\u0000"+
		"\u000075\u0001\u0000\u0000\u000089\u0005t\u0000\u00009:\u0005r\u0000\u0000"+
		":;\u0005u\u0000\u0000;B\u0005e\u0000\u0000<=\u0005f\u0000\u0000=>\u0005"+
		"a\u0000\u0000>?\u0005l\u0000\u0000?@\u0005s\u0000\u0000@B\u0005e\u0000"+
		"\u0000A8\u0001\u0000\u0000\u0000A<\u0001\u0000\u0000\u0000B\n\u0001\u0000"+
		"\u0000\u0000CE\u0007\u0002\u0000\u0000DC\u0001\u0000\u0000\u0000EF\u0001"+
		"\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000FG\u0001\u0000\u0000\u0000"+
		"G\f\u0001\u0000\u0000\u0000HJ\u0005\r\u0000\u0000IH\u0001\u0000\u0000"+
		"\u0000IJ\u0001\u0000\u0000\u0000JK\u0001\u0000\u0000\u0000KL\u0005\n\u0000"+
		"\u0000L\u000e\u0001\u0000\u0000\u0000MO\u0007\u0003\u0000\u0000NM\u0001"+
		"\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000\u0000"+
		"PQ\u0001\u0000\u0000\u0000QR\u0001\u0000\u0000\u0000RS\u0006\u0007\u0000"+
		"\u0000S\u0010\u0001\u0000\u0000\u0000TU\u0005*\u0000\u0000U\u0012\u0001"+
		"\u0000\u0000\u0000VW\u0005/\u0000\u0000W\u0014\u0001\u0000\u0000\u0000"+
		"XY\u0005+\u0000\u0000Y\u0016\u0001\u0000\u0000\u0000Z[\u0005-\u0000\u0000"+
		"[\u0018\u0001\u0000\u0000\u0000\\]\u0005%\u0000\u0000]\u001a\u0001\u0000"+
		"\u0000\u0000^_\u0005<\u0000\u0000_\u001c\u0001\u0000\u0000\u0000`a\u0005"+
		">\u0000\u0000a\u001e\u0001\u0000\u0000\u0000bc\u0005=\u0000\u0000cd\u0005"+
		"=\u0000\u0000d \u0001\u0000\u0000\u0000ef\u0005!\u0000\u0000fg\u0005="+
		"\u0000\u0000g\"\u0001\u0000\u0000\u0000hi\u0005<\u0000\u0000ij\u0005="+
		"\u0000\u0000j$\u0001\u0000\u0000\u0000kl\u0005>\u0000\u0000lm\u0005=\u0000"+
		"\u0000m&\u0001\u0000\u0000\u0000no\u0005&\u0000\u0000op\u0005&\u0000\u0000"+
		"p(\u0001\u0000\u0000\u0000qr\u0005|\u0000\u0000rs\u0005|\u0000\u0000s"+
		"*\u0001\u0000\u0000\u0000\u0006\u00005AFIP\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}