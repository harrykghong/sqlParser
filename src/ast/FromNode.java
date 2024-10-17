package ast;

public class FromNode extends SqlNode{
    private final String table;

    public FromNode(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }
    @Override
    public <T> T accept(SqlVisitor<T> visitor){
        return visitor.visitFrom(this);
    }
}
