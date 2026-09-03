import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends ExprBaseVisitor<Integer> {

    private final Map<String, Integer> variables = new HashMap<>();

    @Override
    public Integer visitProg(ExprParser.ProgContext ctx) {
        int result = 0;

        for (ExprParser.StatContext stat : ctx.stat()) {
            result = visit(stat);
        }

        return result;
    }

    @Override
    public Integer visitStat(ExprParser.StatContext ctx) {
        // Assignment: x = expression
        if (ctx.ID() != null) {
            String name = ctx.ID().getText();

            int value = visit(ctx.expr());

            variables.put(name, value);

            return value;
        }

        // Just an expression
        return visit(ctx.expr());
    }

    @Override
    public Integer visitExpr(ExprParser.ExprContext ctx) {

        // Parentheses: (expr)
        if (ctx.INT() == null && ctx.ID() == null && ctx.op == null) {
            return visit(ctx.expr(0));
        }

        // Integer literal
        if (ctx.INT() != null) {
            return Integer.parseInt(ctx.INT().getText());
        }

        // Variable
        if (ctx.ID() != null) {
            String name = ctx.ID().getText();

            if (!variables.containsKey(name)) {
                throw new RuntimeException(
                    "Undefined variable: " + name
                );
            }

            return variables.get(name);
        }

        // Binary operation
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));

        return switch (ctx.op.getText()) {
            case "+" -> left + right;
            case "-" -> left - right;
            default -> throw new RuntimeException(
                "Unknown operator: " + ctx.op.getText()
            );
        };
    }
}
