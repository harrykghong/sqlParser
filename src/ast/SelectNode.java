package ast;

import java.util.List;
public class SelectNode extends SqlNode{
    private final FromNode from;
    private final List<SqlNode> columns;
    private final  WhereNode where;
    public SelectNode(List<SqlNode> columns, FromNode from, WhereNode where){
        this.from = from;
        this.columns = columns;
        this.where = where;
    }

    public List<SqlNode> getColumns(){
        return columns;
    }
    public  FromNode getFrom(){
        return from;
    }
    public  WhereNode getWhere(){
        return where;
    }
    @Override
//    这个function 会调用访问者的visitSelect方法
    public <T> T accept(SqlVisitor<T> visitor){
        return visitor.visitSelect(this);
    }
}
