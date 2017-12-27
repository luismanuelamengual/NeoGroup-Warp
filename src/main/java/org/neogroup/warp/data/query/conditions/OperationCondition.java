package org.neogroup.warp.data.query.conditions;

public class OperationCondition extends Condition {

    private final Object operandA;
    private final Object operandB;
    private final Operator operator;

    public OperationCondition(Object operandA, Object operandB) {
        this(operandA, Operator.EQUALS, operandB);
    }

    public OperationCondition(Object operandA, Operator operator, Object operandB) {
        this.operandA = operandA;
        this.operandB = operandB;
        this.operator = operator;
    }

    public Object getOperandA() {
        return operandA;
    }

    public Object getOperandB() {
        return operandB;
    }

    public Operator getOperator() {
        return operator;
    }
}
