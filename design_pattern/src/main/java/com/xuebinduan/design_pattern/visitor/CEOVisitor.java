package com.xuebinduan.design_pattern.visitor;

public class CEOVisitor implements Visitor {
    @Override
    public void visit(Staff staff) {
        System.out.println("员工："+staff.name+"，kpi："+staff.kpi);
    }

    @Override
    public void visitEngineer(EngineerStaff engineer) {
        System.out.println("工程师："+engineer.name+"，kpi："+engineer.kpi+",代码质量："+engineer.getCodeQuality());
    }

    @Override
    public void visitManager(ManagerStaff manager) {
        System.out.println("经理："+manager.name+"，kpi："+manager.kpi+",产品数量："+manager.getProducts());
    }
}
