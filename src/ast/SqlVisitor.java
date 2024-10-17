package ast;

public interface SqlVisitor<T> {
    T visitSelect(SelectNode selectNode);
    T visitFrom(FromNode fromNode);
    T visitWhere(WhereNode whereNode);
    T visitComparison(ComparisonNode comparisonNode);
    T visitSubQuery(SubQueryNode subQueryNode);
}
