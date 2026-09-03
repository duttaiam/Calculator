import static org.junit.Assert.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

/*
 * Calculator> javac -d bin -cp "lib/*" $(find src -name "*.java")
 * Calculator> java -cp "bin:lib/*" org.junit.runner.JUnitCore CalculatorTest
 */
public class CalculatorTest {

	@Test
	public void testAdd() {
		assertEquals("3", eval("1 + 2"));
	}
	
	@Test
	public void testSub() {
		assertEquals("-1", eval("1 - 2"));
	}
	
    private String eval(String expr) {
        CharStream input = CharStreams.fromString(expr + System.lineSeparator());
        ExprLexer lexer = new ExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
        ParseTree tree = parser.prog();
        EvalVisitor visitor = new EvalVisitor();
        Integer result = visitor.visit(tree);	
        return result.toString();
    }

}
