package ast;

public class IdentifierNode extends SqlNode{
    private final String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> T accept(SqlVisitor<T> visitor) {
        // 这里可以根据需要添加对标识符的处理
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
