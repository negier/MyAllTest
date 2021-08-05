package com.xuebinduan.design_pattern.visitor;

public interface Visitor {
    void visit(Staff staff);

    void visitEngineer(EngineerStaff engineer);

    void visitManager(ManagerStaff manager);
}
