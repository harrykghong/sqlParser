package ast;

public class SubQueryNode extends SqlNode {
    private final SelectNode subQuery;

    public SubQueryNode(SelectNode subQuery) {
        this.subQuery = subQuery;
    }

    public SelectNode getSubQuery() {
        return subQuery;
    }

    @Override
    public <T> T accept(SqlVisitor<T> visitor) {
        return visitor.visitSubQuery(this);
    }
}
