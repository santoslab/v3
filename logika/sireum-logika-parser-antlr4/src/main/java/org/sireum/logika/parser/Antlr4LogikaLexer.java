// Generated from /Users/robby/Repositories/Sireum-Private/sireum-v3/logika/sireum-logika-parser-antlr4/src/main/resources/org/sireum/logika/parser/Antlr4Logika.g4 by ANTLR 4.5.1
package org.sireum.logika.parser;

// @formatter:off

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Antlr4LogikaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		T__80=81, T__81=82, T__82=83, T__83=84, T__84=85, T__85=86, T__86=87, 
		T__87=88, T__88=89, T__89=90, T__90=91, T__91=92, T__92=93, T__93=94, 
		T__94=95, T__95=96, T__96=97, T__97=98, T__98=99, T__99=100, T__100=101, 
		T__101=102, T__102=103, T__103=104, T__104=105, T__105=106, T__106=107, 
		T__107=108, T__108=109, T__109=110, T__110=111, T__111=112, T__112=113, 
		T__113=114, T__114=115, T__115=116, T__116=117, T__117=118, T__118=119, 
		T__119=120, T__120=121, T__121=122, HLINE=123, NUM=124, ID=125, SSTRING=126, 
		STRING=127, RESERVED=128, NL=129, LINE_COMMENT=130, COMMENT=131, WS=132, 
		ERROR_CHAR=133;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32", 
		"T__33", "T__34", "T__35", "T__36", "T__37", "T__38", "T__39", "T__40", 
		"T__41", "T__42", "T__43", "T__44", "T__45", "T__46", "T__47", "T__48", 
		"T__49", "T__50", "T__51", "T__52", "T__53", "T__54", "T__55", "T__56", 
		"T__57", "T__58", "T__59", "T__60", "T__61", "T__62", "T__63", "T__64", 
		"T__65", "T__66", "T__67", "T__68", "T__69", "T__70", "T__71", "T__72", 
		"T__73", "T__74", "T__75", "T__76", "T__77", "T__78", "T__79", "T__80", 
		"T__81", "T__82", "T__83", "T__84", "T__85", "T__86", "T__87", "T__88", 
		"T__89", "T__90", "T__91", "T__92", "T__93", "T__94", "T__95", "T__96", 
		"T__97", "T__98", "T__99", "T__100", "T__101", "T__102", "T__103", "T__104", 
		"T__105", "T__106", "T__107", "T__108", "T__109", "T__110", "T__111", 
		"T__112", "T__113", "T__114", "T__115", "T__116", "T__117", "T__118", 
		"T__119", "T__120", "T__121", "HLINE", "NUM", "ID", "SSTRING", "STRING", 
		"RESERVED", "NL", "LINE_COMMENT", "COMMENT", "WS", "ERROR_CHAR"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'|-'", "'⊢'", "'{'", "'}'", "'.'", "':'", "'assume'", "'true'", 
		"'T'", "'⊤'", "'false'", "'F'", "'_|_'", "'⊥'", "'('", "')'", "'result'", 
		"'Z'", "'ZS'", "'*'", "'/'", "'%'", "'+'", "'-'", "'<'", "'<='", "'≤'", 
		"'>'", "'>='", "'≥'", "'='", "'=='", "'!='", "'≠'", "'not'", "'!'", "'~'", 
		"'¬'", "'and'", "'&&'", "'^'", "'∧'", "'or'", "'||'", "'V'", "'∨'", "'implies'", 
		"'->'", "'→'", "'forall'", "'all'", "'A'", "'∀'", "'exists'", "'some'", 
		"'E'", "'∃'", "'..'", "'|'", "'B'", "'premise'", "'andi'", "'^i'", "'ande1'", 
		"'^e1'", "'ande2'", "'^e2'", "'ori1'", "'Vi1'", "'ori2'", "'Vi2'", "'ore'", 
		"'Ve'", "'impliesi'", "'impliese'", "'noti'", "'negi'", "'note'", "'nege'", 
		"'bottome'", "'falsee'", "'Pbc'", "'foralli'", "'alli'", "'Ai'", "'foralle'", 
		"'alle'", "'Ae'", "'existsi'", "'somei'", "'Ei'", "'existse'", "'somee'", 
		"'Ee'", "'algebra'", "'invariant'", "'auto'", "'import'", "'logika'", 
		"'_'", "'l\"\"\"'", "'\"\"\"'", "'fact'", "'def'", "'var'", "'val'", "'assert'", 
		"'if'", "'else'", "'while'", "'print'", "'println'", "'Unit'", "'return'", 
		"'readInt'", "'+:'", "'modifies'", "'requires'", "'pre'", "'ensures'", 
		"'post'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "HLINE", "NUM", "ID", "SSTRING", "STRING", "RESERVED", 
		"NL", "LINE_COMMENT", "COMMENT", "WS", "ERROR_CHAR"
	};
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


	public Antlr4LogikaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Antlr4Logika.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\u0087\u040a\b\1\4"+
		"\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n"+
		"\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4"+
		"I\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\t"+
		"T\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_"+
		"\4`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k"+
		"\tk\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv"+
		"\4w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t"+
		"\u0080\4\u0081\t\u0081\4\u0082\t\u0082\4\u0083\t\u0083\4\u0084\t\u0084"+
		"\4\u0085\t\u0085\4\u0086\t\u0086\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n"+
		"\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3"+
		"\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\24\3\24\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3"+
		"\32\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3 \3 "+
		"\3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3"+
		")\3)\3)\3*\3*\3*\3+\3+\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3\60\3\60\3\61\3"+
		"\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\64\3\64\3"+
		"\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\66\3\66\3\67\3\67\38\38"+
		"\38\38\38\38\38\39\39\39\39\39\3:\3:\3;\3;\3<\3<\3<\3=\3=\3>\3>\3?\3?"+
		"\3?\3?\3?\3?\3?\3?\3@\3@\3@\3@\3@\3A\3A\3A\3B\3B\3B\3B\3B\3B\3C\3C\3C"+
		"\3C\3D\3D\3D\3D\3D\3D\3E\3E\3E\3E\3F\3F\3F\3F\3F\3G\3G\3G\3G\3H\3H\3H"+
		"\3H\3H\3I\3I\3I\3I\3J\3J\3J\3J\3K\3K\3K\3L\3L\3L\3L\3L\3L\3L\3L\3L\3M"+
		"\3M\3M\3M\3M\3M\3M\3M\3M\3N\3N\3N\3N\3N\3O\3O\3O\3O\3O\3P\3P\3P\3P\3P"+
		"\3Q\3Q\3Q\3Q\3Q\3R\3R\3R\3R\3R\3R\3R\3R\3S\3S\3S\3S\3S\3S\3S\3T\3T\3T"+
		"\3T\3U\3U\3U\3U\3U\3U\3U\3U\3V\3V\3V\3V\3V\3W\3W\3W\3X\3X\3X\3X\3X\3X"+
		"\3X\3X\3Y\3Y\3Y\3Y\3Y\3Z\3Z\3Z\3[\3[\3[\3[\3[\3[\3[\3[\3\\\3\\\3\\\3\\"+
		"\3\\\3\\\3]\3]\3]\3^\3^\3^\3^\3^\3^\3^\3^\3_\3_\3_\3_\3_\3_\3`\3`\3`\3"+
		"a\3a\3a\3a\3a\3a\3a\3a\3b\3b\3b\3b\3b\3b\3b\3b\3b\3b\3c\3c\3c\3c\3c\3"+
		"d\3d\3d\3d\3d\3d\3d\3e\3e\3e\3e\3e\3e\3e\3f\3f\3g\3g\3g\3g\3g\3h\3h\3"+
		"h\3h\3i\3i\3i\3i\3i\3j\3j\3j\3j\3k\3k\3k\3k\3l\3l\3l\3l\3m\3m\3m\3m\3"+
		"m\3m\3m\3n\3n\3n\3o\3o\3o\3o\3o\3p\3p\3p\3p\3p\3p\3q\3q\3q\3q\3q\3q\3"+
		"r\3r\3r\3r\3r\3r\3r\3r\3s\3s\3s\3s\3s\3t\3t\3t\3t\3t\3t\3t\3u\3u\3u\3"+
		"u\3u\3u\3u\3u\3v\3v\3v\3w\3w\3w\3w\3w\3w\3w\3w\3w\3x\3x\3x\3x\3x\3x\3"+
		"x\3x\3x\3y\3y\3y\3y\3z\3z\3z\3z\3z\3z\3z\3z\3{\3{\3{\3{\3{\3|\3|\3|\6"+
		"|\u0318\n|\r|\16|\u0319\3}\3}\3}\7}\u031f\n}\f}\16}\u0322\13}\5}\u0324"+
		"\n}\3~\3~\7~\u0328\n~\f~\16~\u032b\13~\3\177\3\177\3\177\3\u0080\3\u0080"+
		"\7\u0080\u0332\n\u0080\f\u0080\16\u0080\u0335\13\u0080\3\u0080\3\u0080"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\5\u0081\u03e2"+
		"\n\u0081\3\u0082\5\u0082\u03e5\n\u0082\3\u0082\3\u0082\3\u0083\3\u0083"+
		"\3\u0083\3\u0083\7\u0083\u03ed\n\u0083\f\u0083\16\u0083\u03f0\13\u0083"+
		"\3\u0083\3\u0083\3\u0084\3\u0084\3\u0084\3\u0084\7\u0084\u03f8\n\u0084"+
		"\f\u0084\16\u0084\u03fb\13\u0084\3\u0084\3\u0084\3\u0084\3\u0084\3\u0084"+
		"\3\u0085\6\u0085\u0403\n\u0085\r\u0085\16\u0085\u0404\3\u0085\3\u0085"+
		"\3\u0086\3\u0086\3\u03f9\2\u0087\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60"+
		"_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085"+
		"D\u0087E\u0089F\u008bG\u008dH\u008fI\u0091J\u0093K\u0095L\u0097M\u0099"+
		"N\u009bO\u009dP\u009fQ\u00a1R\u00a3S\u00a5T\u00a7U\u00a9V\u00abW\u00ad"+
		"X\u00afY\u00b1Z\u00b3[\u00b5\\\u00b7]\u00b9^\u00bb_\u00bd`\u00bfa\u00c1"+
		"b\u00c3c\u00c5d\u00c7e\u00c9f\u00cbg\u00cdh\u00cfi\u00d1j\u00d3k\u00d5"+
		"l\u00d7m\u00d9n\u00dbo\u00ddp\u00dfq\u00e1r\u00e3s\u00e5t\u00e7u\u00e9"+
		"v\u00ebw\u00edx\u00efy\u00f1z\u00f3{\u00f5|\u00f7}\u00f9~\u00fb\177\u00fd"+
		"\u0080\u00ff\u0081\u0101\u0082\u0103\u0083\u0105\u0084\u0107\u0085\u0109"+
		"\u0086\u010b\u0087\3\2\n\3\2\63;\3\2\62;\4\2C\\c|\6\2\62;C\\aac|\4\2\""+
		"#%\u0081\4\2%%BB\4\2\f\f\17\17\5\2\13\13\16\16\"\"\u0434\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2"+
		"\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2"+
		"K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3"+
		"\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2"+
		"\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2"+
		"q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3"+
		"\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2"+
		"\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f"+
		"\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097\3\2\2"+
		"\2\2\u0099\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u009f\3\2\2\2\2\u00a1"+
		"\3\2\2\2\2\u00a3\3\2\2\2\2\u00a5\3\2\2\2\2\u00a7\3\2\2\2\2\u00a9\3\2\2"+
		"\2\2\u00ab\3\2\2\2\2\u00ad\3\2\2\2\2\u00af\3\2\2\2\2\u00b1\3\2\2\2\2\u00b3"+
		"\3\2\2\2\2\u00b5\3\2\2\2\2\u00b7\3\2\2\2\2\u00b9\3\2\2\2\2\u00bb\3\2\2"+
		"\2\2\u00bd\3\2\2\2\2\u00bf\3\2\2\2\2\u00c1\3\2\2\2\2\u00c3\3\2\2\2\2\u00c5"+
		"\3\2\2\2\2\u00c7\3\2\2\2\2\u00c9\3\2\2\2\2\u00cb\3\2\2\2\2\u00cd\3\2\2"+
		"\2\2\u00cf\3\2\2\2\2\u00d1\3\2\2\2\2\u00d3\3\2\2\2\2\u00d5\3\2\2\2\2\u00d7"+
		"\3\2\2\2\2\u00d9\3\2\2\2\2\u00db\3\2\2\2\2\u00dd\3\2\2\2\2\u00df\3\2\2"+
		"\2\2\u00e1\3\2\2\2\2\u00e3\3\2\2\2\2\u00e5\3\2\2\2\2\u00e7\3\2\2\2\2\u00e9"+
		"\3\2\2\2\2\u00eb\3\2\2\2\2\u00ed\3\2\2\2\2\u00ef\3\2\2\2\2\u00f1\3\2\2"+
		"\2\2\u00f3\3\2\2\2\2\u00f5\3\2\2\2\2\u00f7\3\2\2\2\2\u00f9\3\2\2\2\2\u00fb"+
		"\3\2\2\2\2\u00fd\3\2\2\2\2\u00ff\3\2\2\2\2\u0101\3\2\2\2\2\u0103\3\2\2"+
		"\2\2\u0105\3\2\2\2\2\u0107\3\2\2\2\2\u0109\3\2\2\2\2\u010b\3\2\2\2\3\u010d"+
		"\3\2\2\2\5\u010f\3\2\2\2\7\u0112\3\2\2\2\t\u0114\3\2\2\2\13\u0116\3\2"+
		"\2\2\r\u0118\3\2\2\2\17\u011a\3\2\2\2\21\u011c\3\2\2\2\23\u0123\3\2\2"+
		"\2\25\u0128\3\2\2\2\27\u012a\3\2\2\2\31\u012c\3\2\2\2\33\u0132\3\2\2\2"+
		"\35\u0134\3\2\2\2\37\u0138\3\2\2\2!\u013a\3\2\2\2#\u013c\3\2\2\2%\u013e"+
		"\3\2\2\2\'\u0145\3\2\2\2)\u0147\3\2\2\2+\u014a\3\2\2\2-\u014c\3\2\2\2"+
		"/\u014e\3\2\2\2\61\u0150\3\2\2\2\63\u0152\3\2\2\2\65\u0154\3\2\2\2\67"+
		"\u0156\3\2\2\29\u0159\3\2\2\2;\u015b\3\2\2\2=\u015d\3\2\2\2?\u0160\3\2"+
		"\2\2A\u0162\3\2\2\2C\u0164\3\2\2\2E\u0167\3\2\2\2G\u016a\3\2\2\2I\u016c"+
		"\3\2\2\2K\u0170\3\2\2\2M\u0172\3\2\2\2O\u0174\3\2\2\2Q\u0176\3\2\2\2S"+
		"\u017a\3\2\2\2U\u017d\3\2\2\2W\u017f\3\2\2\2Y\u0181\3\2\2\2[\u0184\3\2"+
		"\2\2]\u0187\3\2\2\2_\u0189\3\2\2\2a\u018b\3\2\2\2c\u0193\3\2\2\2e\u0196"+
		"\3\2\2\2g\u0198\3\2\2\2i\u019f\3\2\2\2k\u01a3\3\2\2\2m\u01a5\3\2\2\2o"+
		"\u01a7\3\2\2\2q\u01ae\3\2\2\2s\u01b3\3\2\2\2u\u01b5\3\2\2\2w\u01b7\3\2"+
		"\2\2y\u01ba\3\2\2\2{\u01bc\3\2\2\2}\u01be\3\2\2\2\177\u01c6\3\2\2\2\u0081"+
		"\u01cb\3\2\2\2\u0083\u01ce\3\2\2\2\u0085\u01d4\3\2\2\2\u0087\u01d8\3\2"+
		"\2\2\u0089\u01de\3\2\2\2\u008b\u01e2\3\2\2\2\u008d\u01e7\3\2\2\2\u008f"+
		"\u01eb\3\2\2\2\u0091\u01f0\3\2\2\2\u0093\u01f4\3\2\2\2\u0095\u01f8\3\2"+
		"\2\2\u0097\u01fb\3\2\2\2\u0099\u0204\3\2\2\2\u009b\u020d\3\2\2\2\u009d"+
		"\u0212\3\2\2\2\u009f\u0217\3\2\2\2\u00a1\u021c\3\2\2\2\u00a3\u0221\3\2"+
		"\2\2\u00a5\u0229\3\2\2\2\u00a7\u0230\3\2\2\2\u00a9\u0234\3\2\2\2\u00ab"+
		"\u023c\3\2\2\2\u00ad\u0241\3\2\2\2\u00af\u0244\3\2\2\2\u00b1\u024c\3\2"+
		"\2\2\u00b3\u0251\3\2\2\2\u00b5\u0254\3\2\2\2\u00b7\u025c\3\2\2\2\u00b9"+
		"\u0262\3\2\2\2\u00bb\u0265\3\2\2\2\u00bd\u026d\3\2\2\2\u00bf\u0273\3\2"+
		"\2\2\u00c1\u0276\3\2\2\2\u00c3\u027e\3\2\2\2\u00c5\u0288\3\2\2\2\u00c7"+
		"\u028d\3\2\2\2\u00c9\u0294\3\2\2\2\u00cb\u029b\3\2\2\2\u00cd\u029d\3\2"+
		"\2\2\u00cf\u02a2\3\2\2\2\u00d1\u02a6\3\2\2\2\u00d3\u02ab\3\2\2\2\u00d5"+
		"\u02af\3\2\2\2\u00d7\u02b3\3\2\2\2\u00d9\u02b7\3\2\2\2\u00db\u02be\3\2"+
		"\2\2\u00dd\u02c1\3\2\2\2\u00df\u02c6\3\2\2\2\u00e1\u02cc\3\2\2\2\u00e3"+
		"\u02d2\3\2\2\2\u00e5\u02da\3\2\2\2\u00e7\u02df\3\2\2\2\u00e9\u02e6\3\2"+
		"\2\2\u00eb\u02ee\3\2\2\2\u00ed\u02f1\3\2\2\2\u00ef\u02fa\3\2\2\2\u00f1"+
		"\u0303\3\2\2\2\u00f3\u0307\3\2\2\2\u00f5\u030f\3\2\2\2\u00f7\u0314\3\2"+
		"\2\2\u00f9\u0323\3\2\2\2\u00fb\u0325\3\2\2\2\u00fd\u032c\3\2\2\2\u00ff"+
		"\u032f\3\2\2\2\u0101\u03e1\3\2\2\2\u0103\u03e4\3\2\2\2\u0105\u03e8\3\2"+
		"\2\2\u0107\u03f3\3\2\2\2\u0109\u0402\3\2\2\2\u010b\u0408\3\2\2\2\u010d"+
		"\u010e\7.\2\2\u010e\4\3\2\2\2\u010f\u0110\7~\2\2\u0110\u0111\7/\2\2\u0111"+
		"\6\3\2\2\2\u0112\u0113\7\u22a4\2\2\u0113\b\3\2\2\2\u0114\u0115\7}\2\2"+
		"\u0115\n\3\2\2\2\u0116\u0117\7\177\2\2\u0117\f\3\2\2\2\u0118\u0119\7\60"+
		"\2\2\u0119\16\3\2\2\2\u011a\u011b\7<\2\2\u011b\20\3\2\2\2\u011c\u011d"+
		"\7c\2\2\u011d\u011e\7u\2\2\u011e\u011f\7u\2\2\u011f\u0120\7w\2\2\u0120"+
		"\u0121\7o\2\2\u0121\u0122\7g\2\2\u0122\22\3\2\2\2\u0123\u0124\7v\2\2\u0124"+
		"\u0125\7t\2\2\u0125\u0126\7w\2\2\u0126\u0127\7g\2\2\u0127\24\3\2\2\2\u0128"+
		"\u0129\7V\2\2\u0129\26\3\2\2\2\u012a\u012b\7\u22a6\2\2\u012b\30\3\2\2"+
		"\2\u012c\u012d\7h\2\2\u012d\u012e\7c\2\2\u012e\u012f\7n\2\2\u012f\u0130"+
		"\7u\2\2\u0130\u0131\7g\2\2\u0131\32\3\2\2\2\u0132\u0133\7H\2\2\u0133\34"+
		"\3\2\2\2\u0134\u0135\7a\2\2\u0135\u0136\7~\2\2\u0136\u0137\7a\2\2\u0137"+
		"\36\3\2\2\2\u0138\u0139\7\u22a7\2\2\u0139 \3\2\2\2\u013a\u013b\7*\2\2"+
		"\u013b\"\3\2\2\2\u013c\u013d\7+\2\2\u013d$\3\2\2\2\u013e\u013f\7t\2\2"+
		"\u013f\u0140\7g\2\2\u0140\u0141\7u\2\2\u0141\u0142\7w\2\2\u0142\u0143"+
		"\7n\2\2\u0143\u0144\7v\2\2\u0144&\3\2\2\2\u0145\u0146\7\\\2\2\u0146(\3"+
		"\2\2\2\u0147\u0148\7\\\2\2\u0148\u0149\7U\2\2\u0149*\3\2\2\2\u014a\u014b"+
		"\7,\2\2\u014b,\3\2\2\2\u014c\u014d\7\61\2\2\u014d.\3\2\2\2\u014e\u014f"+
		"\7\'\2\2\u014f\60\3\2\2\2\u0150\u0151\7-\2\2\u0151\62\3\2\2\2\u0152\u0153"+
		"\7/\2\2\u0153\64\3\2\2\2\u0154\u0155\7>\2\2\u0155\66\3\2\2\2\u0156\u0157"+
		"\7>\2\2\u0157\u0158\7?\2\2\u01588\3\2\2\2\u0159\u015a\7\u2266\2\2\u015a"+
		":\3\2\2\2\u015b\u015c\7@\2\2\u015c<\3\2\2\2\u015d\u015e\7@\2\2\u015e\u015f"+
		"\7?\2\2\u015f>\3\2\2\2\u0160\u0161\7\u2267\2\2\u0161@\3\2\2\2\u0162\u0163"+
		"\7?\2\2\u0163B\3\2\2\2\u0164\u0165\7?\2\2\u0165\u0166\7?\2\2\u0166D\3"+
		"\2\2\2\u0167\u0168\7#\2\2\u0168\u0169\7?\2\2\u0169F\3\2\2\2\u016a\u016b"+
		"\7\u2262\2\2\u016bH\3\2\2\2\u016c\u016d\7p\2\2\u016d\u016e\7q\2\2\u016e"+
		"\u016f\7v\2\2\u016fJ\3\2\2\2\u0170\u0171\7#\2\2\u0171L\3\2\2\2\u0172\u0173"+
		"\7\u0080\2\2\u0173N\3\2\2\2\u0174\u0175\7\u00ae\2\2\u0175P\3\2\2\2\u0176"+
		"\u0177\7c\2\2\u0177\u0178\7p\2\2\u0178\u0179\7f\2\2\u0179R\3\2\2\2\u017a"+
		"\u017b\7(\2\2\u017b\u017c\7(\2\2\u017cT\3\2\2\2\u017d\u017e\7`\2\2\u017e"+
		"V\3\2\2\2\u017f\u0180\7\u2229\2\2\u0180X\3\2\2\2\u0181\u0182\7q\2\2\u0182"+
		"\u0183\7t\2\2\u0183Z\3\2\2\2\u0184\u0185\7~\2\2\u0185\u0186\7~\2\2\u0186"+
		"\\\3\2\2\2\u0187\u0188\7X\2\2\u0188^\3\2\2\2\u0189\u018a\7\u222a\2\2\u018a"+
		"`\3\2\2\2\u018b\u018c\7k\2\2\u018c\u018d\7o\2\2\u018d\u018e\7r\2\2\u018e"+
		"\u018f\7n\2\2\u018f\u0190\7k\2\2\u0190\u0191\7g\2\2\u0191\u0192\7u\2\2"+
		"\u0192b\3\2\2\2\u0193\u0194\7/\2\2\u0194\u0195\7@\2\2\u0195d\3\2\2\2\u0196"+
		"\u0197\7\u2194\2\2\u0197f\3\2\2\2\u0198\u0199\7h\2\2\u0199\u019a\7q\2"+
		"\2\u019a\u019b\7t\2\2\u019b\u019c\7c\2\2\u019c\u019d\7n\2\2\u019d\u019e"+
		"\7n\2\2\u019eh\3\2\2\2\u019f\u01a0\7c\2\2\u01a0\u01a1\7n\2\2\u01a1\u01a2"+
		"\7n\2\2\u01a2j\3\2\2\2\u01a3\u01a4\7C\2\2\u01a4l\3\2\2\2\u01a5\u01a6\7"+
		"\u2202\2\2\u01a6n\3\2\2\2\u01a7\u01a8\7g\2\2\u01a8\u01a9\7z\2\2\u01a9"+
		"\u01aa\7k\2\2\u01aa\u01ab\7u\2\2\u01ab\u01ac\7v\2\2\u01ac\u01ad\7u\2\2"+
		"\u01adp\3\2\2\2\u01ae\u01af\7u\2\2\u01af\u01b0\7q\2\2\u01b0\u01b1\7o\2"+
		"\2\u01b1\u01b2\7g\2\2\u01b2r\3\2\2\2\u01b3\u01b4\7G\2\2\u01b4t\3\2\2\2"+
		"\u01b5\u01b6\7\u2205\2\2\u01b6v\3\2\2\2\u01b7\u01b8\7\60\2\2\u01b8\u01b9"+
		"\7\60\2\2\u01b9x\3\2\2\2\u01ba\u01bb\7~\2\2\u01bbz\3\2\2\2\u01bc\u01bd"+
		"\7D\2\2\u01bd|\3\2\2\2\u01be\u01bf\7r\2\2\u01bf\u01c0\7t\2\2\u01c0\u01c1"+
		"\7g\2\2\u01c1\u01c2\7o\2\2\u01c2\u01c3\7k\2\2\u01c3\u01c4\7u\2\2\u01c4"+
		"\u01c5\7g\2\2\u01c5~\3\2\2\2\u01c6\u01c7\7c\2\2\u01c7\u01c8\7p\2\2\u01c8"+
		"\u01c9\7f\2\2\u01c9\u01ca\7k\2\2\u01ca\u0080\3\2\2\2\u01cb\u01cc\7`\2"+
		"\2\u01cc\u01cd\7k\2\2\u01cd\u0082\3\2\2\2\u01ce\u01cf\7c\2\2\u01cf\u01d0"+
		"\7p\2\2\u01d0\u01d1\7f\2\2\u01d1\u01d2\7g\2\2\u01d2\u01d3\7\63\2\2\u01d3"+
		"\u0084\3\2\2\2\u01d4\u01d5\7`\2\2\u01d5\u01d6\7g\2\2\u01d6\u01d7\7\63"+
		"\2\2\u01d7\u0086\3\2\2\2\u01d8\u01d9\7c\2\2\u01d9\u01da\7p\2\2\u01da\u01db"+
		"\7f\2\2\u01db\u01dc\7g\2\2\u01dc\u01dd\7\64\2\2\u01dd\u0088\3\2\2\2\u01de"+
		"\u01df\7`\2\2\u01df\u01e0\7g\2\2\u01e0\u01e1\7\64\2\2\u01e1\u008a\3\2"+
		"\2\2\u01e2\u01e3\7q\2\2\u01e3\u01e4\7t\2\2\u01e4\u01e5\7k\2\2\u01e5\u01e6"+
		"\7\63\2\2\u01e6\u008c\3\2\2\2\u01e7\u01e8\7X\2\2\u01e8\u01e9\7k\2\2\u01e9"+
		"\u01ea\7\63\2\2\u01ea\u008e\3\2\2\2\u01eb\u01ec\7q\2\2\u01ec\u01ed\7t"+
		"\2\2\u01ed\u01ee\7k\2\2\u01ee\u01ef\7\64\2\2\u01ef\u0090\3\2\2\2\u01f0"+
		"\u01f1\7X\2\2\u01f1\u01f2\7k\2\2\u01f2\u01f3\7\64\2\2\u01f3\u0092\3\2"+
		"\2\2\u01f4\u01f5\7q\2\2\u01f5\u01f6\7t\2\2\u01f6\u01f7\7g\2\2\u01f7\u0094"+
		"\3\2\2\2\u01f8\u01f9\7X\2\2\u01f9\u01fa\7g\2\2\u01fa\u0096\3\2\2\2\u01fb"+
		"\u01fc\7k\2\2\u01fc\u01fd\7o\2\2\u01fd\u01fe\7r\2\2\u01fe\u01ff\7n\2\2"+
		"\u01ff\u0200\7k\2\2\u0200\u0201\7g\2\2\u0201\u0202\7u\2\2\u0202\u0203"+
		"\7k\2\2\u0203\u0098\3\2\2\2\u0204\u0205\7k\2\2\u0205\u0206\7o\2\2\u0206"+
		"\u0207\7r\2\2\u0207\u0208\7n\2\2\u0208\u0209\7k\2\2\u0209\u020a\7g\2\2"+
		"\u020a\u020b\7u\2\2\u020b\u020c\7g\2\2\u020c\u009a\3\2\2\2\u020d\u020e"+
		"\7p\2\2\u020e\u020f\7q\2\2\u020f\u0210\7v\2\2\u0210\u0211\7k\2\2\u0211"+
		"\u009c\3\2\2\2\u0212\u0213\7p\2\2\u0213\u0214\7g\2\2\u0214\u0215\7i\2"+
		"\2\u0215\u0216\7k\2\2\u0216\u009e\3\2\2\2\u0217\u0218\7p\2\2\u0218\u0219"+
		"\7q\2\2\u0219\u021a\7v\2\2\u021a\u021b\7g\2\2\u021b\u00a0\3\2\2\2\u021c"+
		"\u021d\7p\2\2\u021d\u021e\7g\2\2\u021e\u021f\7i\2\2\u021f\u0220\7g\2\2"+
		"\u0220\u00a2\3\2\2\2\u0221\u0222\7d\2\2\u0222\u0223\7q\2\2\u0223\u0224"+
		"\7v\2\2\u0224\u0225\7v\2\2\u0225\u0226\7q\2\2\u0226\u0227\7o\2\2\u0227"+
		"\u0228\7g\2\2\u0228\u00a4\3\2\2\2\u0229\u022a\7h\2\2\u022a\u022b\7c\2"+
		"\2\u022b\u022c\7n\2\2\u022c\u022d\7u\2\2\u022d\u022e\7g\2\2\u022e\u022f"+
		"\7g\2\2\u022f\u00a6\3\2\2\2\u0230\u0231\7R\2\2\u0231\u0232\7d\2\2\u0232"+
		"\u0233\7e\2\2\u0233\u00a8\3\2\2\2\u0234\u0235\7h\2\2\u0235\u0236\7q\2"+
		"\2\u0236\u0237\7t\2\2\u0237\u0238\7c\2\2\u0238\u0239\7n\2\2\u0239\u023a"+
		"\7n\2\2\u023a\u023b\7k\2\2\u023b\u00aa\3\2\2\2\u023c\u023d\7c\2\2\u023d"+
		"\u023e\7n\2\2\u023e\u023f\7n\2\2\u023f\u0240\7k\2\2\u0240\u00ac\3\2\2"+
		"\2\u0241\u0242\7C\2\2\u0242\u0243\7k\2\2\u0243\u00ae\3\2\2\2\u0244\u0245"+
		"\7h\2\2\u0245\u0246\7q\2\2\u0246\u0247\7t\2\2\u0247\u0248\7c\2\2\u0248"+
		"\u0249\7n\2\2\u0249\u024a\7n\2\2\u024a\u024b\7g\2\2\u024b\u00b0\3\2\2"+
		"\2\u024c\u024d\7c\2\2\u024d\u024e\7n\2\2\u024e\u024f\7n\2\2\u024f\u0250"+
		"\7g\2\2\u0250\u00b2\3\2\2\2\u0251\u0252\7C\2\2\u0252\u0253\7g\2\2\u0253"+
		"\u00b4\3\2\2\2\u0254\u0255\7g\2\2\u0255\u0256\7z\2\2\u0256\u0257\7k\2"+
		"\2\u0257\u0258\7u\2\2\u0258\u0259\7v\2\2\u0259\u025a\7u\2\2\u025a\u025b"+
		"\7k\2\2\u025b\u00b6\3\2\2\2\u025c\u025d\7u\2\2\u025d\u025e\7q\2\2\u025e"+
		"\u025f\7o\2\2\u025f\u0260\7g\2\2\u0260\u0261\7k\2\2\u0261\u00b8\3\2\2"+
		"\2\u0262\u0263\7G\2\2\u0263\u0264\7k\2\2\u0264\u00ba\3\2\2\2\u0265\u0266"+
		"\7g\2\2\u0266\u0267\7z\2\2\u0267\u0268\7k\2\2\u0268\u0269\7u\2\2\u0269"+
		"\u026a\7v\2\2\u026a\u026b\7u\2\2\u026b\u026c\7g\2\2\u026c\u00bc\3\2\2"+
		"\2\u026d\u026e\7u\2\2\u026e\u026f\7q\2\2\u026f\u0270\7o\2\2\u0270\u0271"+
		"\7g\2\2\u0271\u0272\7g\2\2\u0272\u00be\3\2\2\2\u0273\u0274\7G\2\2\u0274"+
		"\u0275\7g\2\2\u0275\u00c0\3\2\2\2\u0276\u0277\7c\2\2\u0277\u0278\7n\2"+
		"\2\u0278\u0279\7i\2\2\u0279\u027a\7g\2\2\u027a\u027b\7d\2\2\u027b\u027c"+
		"\7t\2\2\u027c\u027d\7c\2\2\u027d\u00c2\3\2\2\2\u027e\u027f\7k\2\2\u027f"+
		"\u0280\7p\2\2\u0280\u0281\7x\2\2\u0281\u0282\7c\2\2\u0282\u0283\7t\2\2"+
		"\u0283\u0284\7k\2\2\u0284\u0285\7c\2\2\u0285\u0286\7p\2\2\u0286\u0287"+
		"\7v\2\2\u0287\u00c4\3\2\2\2\u0288\u0289\7c\2\2\u0289\u028a\7w\2\2\u028a"+
		"\u028b\7v\2\2\u028b\u028c\7q\2\2\u028c\u00c6\3\2\2\2\u028d\u028e\7k\2"+
		"\2\u028e\u028f\7o\2\2\u028f\u0290\7r\2\2\u0290\u0291\7q\2\2\u0291\u0292"+
		"\7t\2\2\u0292\u0293\7v\2\2\u0293\u00c8\3\2\2\2\u0294\u0295\7n\2\2\u0295"+
		"\u0296\7q\2\2\u0296\u0297\7i\2\2\u0297\u0298\7k\2\2\u0298\u0299\7m\2\2"+
		"\u0299\u029a\7c\2\2\u029a\u00ca\3\2\2\2\u029b\u029c\7a\2\2\u029c\u00cc"+
		"\3\2\2\2\u029d\u029e\7n\2\2\u029e\u029f\7$\2\2\u029f\u02a0\7$\2\2\u02a0"+
		"\u02a1\7$\2\2\u02a1\u00ce\3\2\2\2\u02a2\u02a3\7$\2\2\u02a3\u02a4\7$\2"+
		"\2\u02a4\u02a5\7$\2\2\u02a5\u00d0\3\2\2\2\u02a6\u02a7\7h\2\2\u02a7\u02a8"+
		"\7c\2\2\u02a8\u02a9\7e\2\2\u02a9\u02aa\7v\2\2\u02aa\u00d2\3\2\2\2\u02ab"+
		"\u02ac\7f\2\2\u02ac\u02ad\7g\2\2\u02ad\u02ae\7h\2\2\u02ae\u00d4\3\2\2"+
		"\2\u02af\u02b0\7x\2\2\u02b0\u02b1\7c\2\2\u02b1\u02b2\7t\2\2\u02b2\u00d6"+
		"\3\2\2\2\u02b3\u02b4\7x\2\2\u02b4\u02b5\7c\2\2\u02b5\u02b6\7n\2\2\u02b6"+
		"\u00d8\3\2\2\2\u02b7\u02b8\7c\2\2\u02b8\u02b9\7u\2\2\u02b9\u02ba\7u\2"+
		"\2\u02ba\u02bb\7g\2\2\u02bb\u02bc\7t\2\2\u02bc\u02bd\7v\2\2\u02bd\u00da"+
		"\3\2\2\2\u02be\u02bf\7k\2\2\u02bf\u02c0\7h\2\2\u02c0\u00dc\3\2\2\2\u02c1"+
		"\u02c2\7g\2\2\u02c2\u02c3\7n\2\2\u02c3\u02c4\7u\2\2\u02c4\u02c5\7g\2\2"+
		"\u02c5\u00de\3\2\2\2\u02c6\u02c7\7y\2\2\u02c7\u02c8\7j\2\2\u02c8\u02c9"+
		"\7k\2\2\u02c9\u02ca\7n\2\2\u02ca\u02cb\7g\2\2\u02cb\u00e0\3\2\2\2\u02cc"+
		"\u02cd\7r\2\2\u02cd\u02ce\7t\2\2\u02ce\u02cf\7k\2\2\u02cf\u02d0\7p\2\2"+
		"\u02d0\u02d1\7v\2\2\u02d1\u00e2\3\2\2\2\u02d2\u02d3\7r\2\2\u02d3\u02d4"+
		"\7t\2\2\u02d4\u02d5\7k\2\2\u02d5\u02d6\7p\2\2\u02d6\u02d7\7v\2\2\u02d7"+
		"\u02d8\7n\2\2\u02d8\u02d9\7p\2\2\u02d9\u00e4\3\2\2\2\u02da\u02db\7W\2"+
		"\2\u02db\u02dc\7p\2\2\u02dc\u02dd\7k\2\2\u02dd\u02de\7v\2\2\u02de\u00e6"+
		"\3\2\2\2\u02df\u02e0\7t\2\2\u02e0\u02e1\7g\2\2\u02e1\u02e2\7v\2\2\u02e2"+
		"\u02e3\7w\2\2\u02e3\u02e4\7t\2\2\u02e4\u02e5\7p\2\2\u02e5\u00e8\3\2\2"+
		"\2\u02e6\u02e7\7t\2\2\u02e7\u02e8\7g\2\2\u02e8\u02e9\7c\2\2\u02e9\u02ea"+
		"\7f\2\2\u02ea\u02eb\7K\2\2\u02eb\u02ec\7p\2\2\u02ec\u02ed\7v\2\2\u02ed"+
		"\u00ea\3\2\2\2\u02ee\u02ef\7-\2\2\u02ef\u02f0\7<\2\2\u02f0\u00ec\3\2\2"+
		"\2\u02f1\u02f2\7o\2\2\u02f2\u02f3\7q\2\2\u02f3\u02f4\7f\2\2\u02f4\u02f5"+
		"\7k\2\2\u02f5\u02f6\7h\2\2\u02f6\u02f7\7k\2\2\u02f7\u02f8\7g\2\2\u02f8"+
		"\u02f9\7u\2\2\u02f9\u00ee\3\2\2\2\u02fa\u02fb\7t\2\2\u02fb\u02fc\7g\2"+
		"\2\u02fc\u02fd\7s\2\2\u02fd\u02fe\7w\2\2\u02fe\u02ff\7k\2\2\u02ff\u0300"+
		"\7t\2\2\u0300\u0301\7g\2\2\u0301\u0302\7u\2\2\u0302\u00f0\3\2\2\2\u0303"+
		"\u0304\7r\2\2\u0304\u0305\7t\2\2\u0305\u0306\7g\2\2\u0306\u00f2\3\2\2"+
		"\2\u0307\u0308\7g\2\2\u0308\u0309\7p\2\2\u0309\u030a\7u\2\2\u030a\u030b"+
		"\7w\2\2\u030b\u030c\7t\2\2\u030c\u030d\7g\2\2\u030d\u030e\7u\2\2\u030e"+
		"\u00f4\3\2\2\2\u030f\u0310\7r\2\2\u0310\u0311\7q\2\2\u0311\u0312\7u\2"+
		"\2\u0312\u0313\7v\2\2\u0313\u00f6\3\2\2\2\u0314\u0315\7/\2\2\u0315\u0317"+
		"\7/\2\2\u0316\u0318\7/\2\2\u0317\u0316\3\2\2\2\u0318\u0319\3\2\2\2\u0319"+
		"\u0317\3\2\2\2\u0319\u031a\3\2\2\2\u031a\u00f8\3\2\2\2\u031b\u0324\7\62"+
		"\2\2\u031c\u0320\t\2\2\2\u031d\u031f\t\3\2\2\u031e\u031d\3\2\2\2\u031f"+
		"\u0322\3\2\2\2\u0320\u031e\3\2\2\2\u0320\u0321\3\2\2\2\u0321\u0324\3\2"+
		"\2\2\u0322\u0320\3\2\2\2\u0323\u031b\3\2\2\2\u0323\u031c\3\2\2\2\u0324"+
		"\u00fa\3\2\2\2\u0325\u0329\t\4\2\2\u0326\u0328\t\5\2\2\u0327\u0326\3\2"+
		"\2\2\u0328\u032b\3\2\2\2\u0329\u0327\3\2\2\2\u0329\u032a\3\2\2\2\u032a"+
		"\u00fc\3\2\2\2\u032b\u0329\3\2\2\2\u032c\u032d\7u\2\2\u032d\u032e\5\u00ff"+
		"\u0080\2\u032e\u00fe\3\2\2\2\u032f\u0333\7$\2\2\u0330\u0332\t\6\2\2\u0331"+
		"\u0330\3\2\2\2\u0332\u0335\3\2\2\2\u0333\u0331\3\2\2\2\u0333\u0334\3\2"+
		"\2\2\u0334\u0336\3\2\2\2\u0335\u0333\3\2\2\2\u0336\u0337\7$\2\2\u0337"+
		"\u0100\3\2\2\2\u0338\u0339\7c\2\2\u0339\u033a\7d\2\2\u033a\u033b\7u\2"+
		"\2\u033b\u033c\7v\2\2\u033c\u033d\7t\2\2\u033d\u033e\7c\2\2\u033e\u033f"+
		"\7e\2\2\u033f\u03e2\7v\2\2\u0340\u0341\7e\2\2\u0341\u0342\7c\2\2\u0342"+
		"\u0343\7u\2\2\u0343\u03e2\7g\2\2\u0344\u0345\7e\2\2\u0345\u0346\7c\2\2"+
		"\u0346\u0347\7v\2\2\u0347\u0348\7e\2\2\u0348\u03e2\7j\2\2\u0349\u034a"+
		"\7e\2\2\u034a\u034b\7n\2\2\u034b\u034c\7c\2\2\u034c\u034d\7u\2\2\u034d"+
		"\u03e2\7u\2\2\u034e\u034f\7f\2\2\u034f\u03e2\7q\2\2\u0350\u0351\7g\2\2"+
		"\u0351\u0352\7z\2\2\u0352\u0353\7v\2\2\u0353\u0354\7g\2\2\u0354\u0355"+
		"\7p\2\2\u0355\u0356\7f\2\2\u0356\u03e2\7u\2\2\u0357\u0358\7h\2\2\u0358"+
		"\u0359\7k\2\2\u0359\u035a\7p\2\2\u035a\u035b\7c\2\2\u035b\u03e2\7n\2\2"+
		"\u035c\u035d\7h\2\2\u035d\u035e\7k\2\2\u035e\u035f\7p\2\2\u035f\u0360"+
		"\7c\2\2\u0360\u0361\7n\2\2\u0361\u0362\7n\2\2\u0362\u03e2\7{\2\2\u0363"+
		"\u0364\7h\2\2\u0364\u0365\7q\2\2\u0365\u03e2\7t\2\2\u0366\u0367\7h\2\2"+
		"\u0367\u0368\7q\2\2\u0368\u0369\7t\2\2\u0369\u036a\7U\2\2\u036a\u036b"+
		"\7q\2\2\u036b\u036c\7o\2\2\u036c\u03e2\7g\2\2\u036d\u036e\7k\2\2\u036e"+
		"\u036f\7o\2\2\u036f\u0370\7r\2\2\u0370\u0371\7n\2\2\u0371\u0372\7k\2\2"+
		"\u0372\u0373\7e\2\2\u0373\u0374\7k\2\2\u0374\u03e2\7v\2\2\u0375\u0376"+
		"\7n\2\2\u0376\u0377\7c\2\2\u0377\u0378\7|\2\2\u0378\u03e2\7{\2\2\u0379"+
		"\u037a\7o\2\2\u037a\u037b\7c\2\2\u037b\u037c\7e\2\2\u037c\u037d\7t\2\2"+
		"\u037d\u03e2\7q\2\2\u037e\u037f\7o\2\2\u037f\u0380\7c\2\2\u0380\u0381"+
		"\7v\2\2\u0381\u0382\7e\2\2\u0382\u03e2\7j\2\2\u0383\u0384\7p\2\2\u0384"+
		"\u0385\7g\2\2\u0385\u03e2\7y\2\2\u0386\u0387\7p\2\2\u0387\u0388\7w\2\2"+
		"\u0388\u0389\7n\2\2\u0389\u03e2\7n\2\2\u038a\u038b\7q\2\2\u038b\u038c"+
		"\7d\2\2\u038c\u038d\7l\2\2\u038d\u038e\7g\2\2\u038e\u038f\7e\2\2\u038f"+
		"\u03e2\7v\2\2\u0390\u0391\7q\2\2\u0391\u0392\7x\2\2\u0392\u0393\7g\2\2"+
		"\u0393\u0394\7t\2\2\u0394\u0395\7t\2\2\u0395\u0396\7k\2\2\u0396\u0397"+
		"\7f\2\2\u0397\u03e2\7g\2\2\u0398\u0399\7r\2\2\u0399\u039a\7c\2\2\u039a"+
		"\u039b\7e\2\2\u039b\u039c\7m\2\2\u039c\u039d\7c\2\2\u039d\u039e\7i\2\2"+
		"\u039e\u03e2\7g\2\2\u039f\u03a0\7r\2\2\u03a0\u03a1\7t\2\2\u03a1\u03a2"+
		"\7k\2\2\u03a2\u03a3\7x\2\2\u03a3\u03a4\7c\2\2\u03a4\u03a5\7v\2\2\u03a5"+
		"\u03e2\7g\2\2\u03a6\u03a7\7r\2\2\u03a7\u03a8\7t\2\2\u03a8\u03a9\7q\2\2"+
		"\u03a9\u03aa\7v\2\2\u03aa\u03ab\7g\2\2\u03ab\u03ac\7e\2\2\u03ac\u03ad"+
		"\7v\2\2\u03ad\u03ae\7g\2\2\u03ae\u03e2\7f\2\2\u03af\u03b0\7u\2\2\u03b0"+
		"\u03b1\7g\2\2\u03b1\u03b2\7c\2\2\u03b2\u03b3\7n\2\2\u03b3\u03b4\7g\2\2"+
		"\u03b4\u03e2\7f\2\2\u03b5\u03b6\7u\2\2\u03b6\u03b7\7w\2\2\u03b7\u03b8"+
		"\7r\2\2\u03b8\u03b9\7g\2\2\u03b9\u03e2\7t\2\2\u03ba\u03bb\7v\2\2\u03bb"+
		"\u03bc\7j\2\2\u03bc\u03bd\7k\2\2\u03bd\u03e2\7u\2\2\u03be\u03bf\7v\2\2"+
		"\u03bf\u03c0\7j\2\2\u03c0\u03c1\7t\2\2\u03c1\u03c2\7q\2\2\u03c2\u03e2"+
		"\7y\2\2\u03c3\u03c4\7v\2\2\u03c4\u03c5\7t\2\2\u03c5\u03c6\7c\2\2\u03c6"+
		"\u03c7\7k\2\2\u03c7\u03e2\7v\2\2\u03c8\u03c9\7v\2\2\u03c9\u03ca\7t\2\2"+
		"\u03ca\u03e2\7{\2\2\u03cb\u03cc\7v\2\2\u03cc\u03cd\7{\2\2\u03cd\u03ce"+
		"\7r\2\2\u03ce\u03e2\7g\2\2\u03cf\u03d0\7y\2\2\u03d0\u03d1\7k\2\2\u03d1"+
		"\u03d2\7v\2\2\u03d2\u03e2\7j\2\2\u03d3\u03d4\7{\2\2\u03d4\u03d5\7k\2\2"+
		"\u03d5\u03d6\7g\2\2\u03d6\u03d7\7n\2\2\u03d7\u03e2\7f\2\2\u03d8\u03d9"+
		"\7>\2\2\u03d9\u03e2\7/\2\2\u03da\u03db\7>\2\2\u03db\u03e2\7<\2\2\u03dc"+
		"\u03dd\7>\2\2\u03dd\u03e2\7\'\2\2\u03de\u03df\7@\2\2\u03df\u03e2\7<\2"+
		"\2\u03e0\u03e2\t\7\2\2\u03e1\u0338\3\2\2\2\u03e1\u0340\3\2\2\2\u03e1\u0344"+
		"\3\2\2\2\u03e1\u0349\3\2\2\2\u03e1\u034e\3\2\2\2\u03e1\u0350\3\2\2\2\u03e1"+
		"\u0357\3\2\2\2\u03e1\u035c\3\2\2\2\u03e1\u0363\3\2\2\2\u03e1\u0366\3\2"+
		"\2\2\u03e1\u036d\3\2\2\2\u03e1\u0375\3\2\2\2\u03e1\u0379\3\2\2\2\u03e1"+
		"\u037e\3\2\2\2\u03e1\u0383\3\2\2\2\u03e1\u0386\3\2\2\2\u03e1\u038a\3\2"+
		"\2\2\u03e1\u0390\3\2\2\2\u03e1\u0398\3\2\2\2\u03e1\u039f\3\2\2\2\u03e1"+
		"\u03a6\3\2\2\2\u03e1\u03af\3\2\2\2\u03e1\u03b5\3\2\2\2\u03e1\u03ba\3\2"+
		"\2\2\u03e1\u03be\3\2\2\2\u03e1\u03c3\3\2\2\2\u03e1\u03c8\3\2\2\2\u03e1"+
		"\u03cb\3\2\2\2\u03e1\u03cf\3\2\2\2\u03e1\u03d3\3\2\2\2\u03e1\u03d8\3\2"+
		"\2\2\u03e1\u03da\3\2\2\2\u03e1\u03dc\3\2\2\2\u03e1\u03de\3\2\2\2\u03e1"+
		"\u03e0\3\2\2\2\u03e2\u0102\3\2\2\2\u03e3\u03e5\7\17\2\2\u03e4\u03e3\3"+
		"\2\2\2\u03e4\u03e5\3\2\2\2\u03e5\u03e6\3\2\2\2\u03e6\u03e7\7\f\2\2\u03e7"+
		"\u0104\3\2\2\2\u03e8\u03e9\7\61\2\2\u03e9\u03ea\7\61\2\2\u03ea\u03ee\3"+
		"\2\2\2\u03eb\u03ed\n\b\2\2\u03ec\u03eb\3\2\2\2\u03ed\u03f0\3\2\2\2\u03ee"+
		"\u03ec\3\2\2\2\u03ee\u03ef\3\2\2\2\u03ef\u03f1\3\2\2\2\u03f0\u03ee\3\2"+
		"\2\2\u03f1\u03f2\b\u0083\2\2\u03f2\u0106\3\2\2\2\u03f3\u03f4\7\61\2\2"+
		"\u03f4\u03f5\7,\2\2\u03f5\u03f9\3\2\2\2\u03f6\u03f8\13\2\2\2\u03f7\u03f6"+
		"\3\2\2\2\u03f8\u03fb\3\2\2\2\u03f9\u03fa\3\2\2\2\u03f9\u03f7\3\2\2\2\u03fa"+
		"\u03fc\3\2\2\2\u03fb\u03f9\3\2\2\2\u03fc\u03fd\7,\2\2\u03fd\u03fe\7\61"+
		"\2\2\u03fe\u03ff\3\2\2\2\u03ff\u0400\b\u0084\2\2\u0400\u0108\3\2\2\2\u0401"+
		"\u0403\t\t\2\2\u0402\u0401\3\2\2\2\u0403\u0404\3\2\2\2\u0404\u0402\3\2"+
		"\2\2\u0404\u0405\3\2\2\2\u0405\u0406\3\2\2\2\u0406\u0407\b\u0085\2\2\u0407"+
		"\u010a\3\2\2\2\u0408\u0409\13\2\2\2\u0409\u010c\3\2\2\2\r\2\u0319\u0320"+
		"\u0323\u0329\u0333\u03e1\u03e4\u03ee\u03f9\u0404\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}