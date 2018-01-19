package Files;

import visitors.Visitor;

public class SumExpression extends BinaryExpression {
    public SumExpression(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public <T, P> T accept(Visitor<T, P> visitor, P param) {
        return visitor.visit(this, param);
    }

    @Override
    public String getRealName() {
        return null;
    }
}