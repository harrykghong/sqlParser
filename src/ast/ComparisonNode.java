package ast;

public class ComparisonNode extends SqlNode{
    private final String leftOperand;
    private final String operator;
    private final SqlNode rightOperand;

    public ComparisonNode(String leftOperand, String operator, SqlNode rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    public String getLeftOperand() {
        return leftOperand;
    }

    public String getOperator() {
        return operator;
    }

    public SqlNode getRightOperand() {
        return rightOperand;
    }

    @Override
    public <T> T accept(SqlVisitor<T> visitor) {
        return visitor.visitComparison(this);
    }
}
