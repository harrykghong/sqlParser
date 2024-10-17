package ast;

public class WhereNode extends SqlNode{
    private final SqlNode condition;

    public WhereNode(SqlNode condition) {
        this.condition = condition;
    }

    public SqlNode getCondition() {
        return condition;
    }

    @Override
    public <T> T accept(SqlVisitor<T> visitor) {
        return visitor.visitWhere(this);
    }
}
