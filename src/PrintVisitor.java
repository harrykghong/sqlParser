import ast.*;

public class PrintVisitor implements SqlVisitor<Void> {
    @Override
    public Void visitSelect(SelectNode selectNode) {
        System.out.println("Visiting SELECT node:");
        System.out.print("  Columns: ");
        for (int i = 0; i < selectNode.getColumns().size(); i++) {
            System.out.print(selectNode.getColumns().get(i));
            if (i < selectNode.getColumns().size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        selectNode.getFrom().accept(this);
        if (selectNode.getWhere() != null) {
            selectNode.getWhere().accept(this);
        }
        return null;
    }

    @Override
    public Void visitFrom(FromNode fromNode) {
        System.out.println("  From: " + fromNode.getTable());
        return null;
    }

    @Override
    public Void visitWhere(WhereNode whereNode) {
        System.out.println("  Where:");
        whereNode.getCondition().accept(this);
        return null;
    }

    @Override
    public Void visitComparison(ComparisonNode comparisonNode) {
        System.out.println("    Comparison: " + comparisonNode.getLeftOperand() + " " + comparisonNode.getOperator() + " " + comparisonNode.getRightOperand());
//        comparisonNode.getRightOperand().accept(this);
        return null;
    }

    @Override
    public Void visitSubQuery(SubQueryNode subQueryNode) {
        System.out.println("      SubQuery:");
        subQueryNode.getSubQuery().accept(this);
        return null;
    }
}
