package visitors.semantic;

import visitors.Visitor;
import visitors.lexical.EntryLexem;
import visitors.lexical.StringTable;
import visitors.semantic.exception.*;
import visitors.syntax.nodes.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author Domenico Antonio Tropeano on 22/01/2018 at 15:46
 * @project yaspl
 */
public class SemanticVisitor implements Visitor<EntrySymbol, EntrySymbol> {
    private Stack<SymbolTable> stackOfTable;
    private StringTable stringTable;
    private static final String emp = "";

    public SemanticVisitor(StringTable stringTable) {
        this.stackOfTable = new Stack<>();
        this.stringTable = stringTable;
    }


    @Override
    public EntrySymbol visit(Program programNode, EntrySymbol optParam) {
        stackOfTable.add(new SymbolTable());
        programNode.getDeclarations().forEach(d -> d.accept(this, optParam));
        programNode.getStatements().forEach(s -> s.accept(this, optParam));
        return null;
    }

    @Override
    public EntrySymbol visit(VariableDeclaration variableDeclarationNode, EntrySymbol optParam) {
        for (Variable v : variableDeclarationNode.getVariables()) {
            optParam = new EntrySymbol();
            optParam.setType(variableDeclarationNode.getType().getTypeName());
            optParam = v.accept(this, optParam);
            optParam.setLocX(stringTable.get(optParam.getName()).getLocX());
            optParam.setLocY(stringTable.get(optParam.getName()).getLocY());
            checkAlreadyDeclared(optParam);
        }
        return null;
    }

    @Override
    public EntrySymbol visit(FunctionDeclaration functionDeclarationNode, EntrySymbol optParam) {
        String id = functionDeclarationNode.getIdentifier().getName();
        EntrySymbol function = new EntrySymbol();
        if (stackOfTable.peek().containsKey(id)) {
            try {
                throw new FunctionAlreadyDeclaredException("Already Declared");
            } catch (FunctionAlreadyDeclaredException functionAlreadyDeclaredException) {
                functionAlreadyDeclaredException.printStackTrace();
            }
        } else {
            function.setName(id);
            function.setType(Constants.FUNCTION);
            function.setFunction();
            stackOfTable.push(new SymbolTable());
            for (VariableDeclaration vd : functionDeclarationNode.getVariableDeclarations()) {
                function.addVariableType(vd.getType().getTypeName());
                vd.accept(this, optParam);
            }
            for (ParameterDeclaration pd : functionDeclarationNode.getParameterDeclarations()) {
                function = pd.accept(this, function);
            }
            stackOfTable.firstElement().put(function.getName(), function);
            functionDeclarationNode.getBody().accept(this, optParam);
        }
        return null;
    }

    @Override
    public EntrySymbol visit(Variable variableNode, EntrySymbol optParam) {
        optParam = variableNode.getIdentifier().accept(this, optParam);
        return optParam;
    }

    @Override
    public EntrySymbol visit(Type typeNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(Identifier identifierNode, EntrySymbol optParam) {
        optParam.setName(identifierNode.getName());
        EntryLexem el = stringTable.get(identifierNode.getName());
        optParam.setLocX(el.getLocX());
        optParam.setLocY(el.getLocY());
        return optParam;
    }

    @Override
    public EntrySymbol visit(ParameterDeclaration parameterDeclarationNode, EntrySymbol optParam) {
        EntrySymbol variable = new EntrySymbol();
        for (VariableDeclaration vd : parameterDeclarationNode.getVariableDeclarations()) {
            optParam.addParameterType(vd.getType().getTypeName());
            variable.setType(vd.getType().getTypeName());
            variable = vd.accept(this, variable);
            variable.setLocX(stringTable.get(optParam.getName()).getLocX());
            variable.setLocY(stringTable.get(optParam.getName()).getLocY());
            checkAlreadyDeclared(variable);
        }
        return optParam;
    }


    @Override
    public EntrySymbol visit(Body bodyNode, EntrySymbol optParam) {
        bodyNode.getVariableDeclarations().forEach(vd -> vd.accept(this, optParam));
        bodyNode.getStatements().forEach(s -> s.accept(this, optParam));
        return null;
    }

    @Override
    public EntrySymbol visit(ReadStatement readStatementNode, EntrySymbol optParam) {
        List<String> types = new ArrayList<>();
        List<String> typesOfConstants = new ArrayList<>();
        for (Type t : readStatementNode.getTypes()) {
            types.add(t.getTypeName());
        }
        for (Variable v : readStatementNode.getVariables()) {
            if (stackOfTable.peek().containsKey(v.getIdentifier().getName())) {
                typesOfConstants.add(v.accept(this, optParam).getType());
            } else {
                try {
                    throw new VariableNotDeclaredException(v.getIdentifier().getName() + " not declared yet");
                } catch (VariableNotDeclaredException e) {
                    e.printStackTrace();
                }
            }
            continue;
        }
        if (types.equals(typesOfConstants)) {

        } else {
            try {
                throw new TypeMismatchException("Types Mismatch");
            } catch (TypeMismatchException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public EntrySymbol visit(WriteStatement writeStatementNode, EntrySymbol optParam) {
        writeStatementNode.getExpression().forEach(e -> e.accept(this, optParam));
        return null;
    }

    @Override
    public EntrySymbol visit(FunctionCall functionCallNode, EntrySymbol optParam) {
        ArrayList<String> exprType = new ArrayList<>();
        ArrayList<String> varsType = new ArrayList<>();
        EntrySymbol choosedFunction = null;
        if (stackOfTable.firstElement().containsKey(functionCallNode.getIdentifier().getName())) {
            choosedFunction = stackOfTable.firstElement().get(functionCallNode.getIdentifier().getName());
        } else {
            try {
                throw new FunctionNotDeclaredException(functionCallNode.getIdentifier().getName());
            } catch (FunctionNotDeclaredException e) {
                e.printStackTrace();
            }
        }

        for (Expression exp : functionCallNode.getExpressions()) {
            optParam = exp.accept(this, optParam);
            exprType.add(optParam.getType());
            if (exprType.equals(choosedFunction.getVariableArrayFirm())) {
                //Variables Match
            } else {
                try {
                    throw new TypeMismatchException("Variables mismatch with function firm");
                } catch (TypeMismatchException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Variable v : functionCallNode.getVariables()) {
            optParam = v.accept(this, optParam);
            varsType.add(optParam.getType());
            if (varsType.equals(choosedFunction.getParameterArrayFirm())) {
                //Parameter Match
            } else {
                try {
                    throw new TypeMismatchException("Parameter mismatch with function firm");
                } catch (TypeMismatchException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;//CONTROLLARE SE FINITO
    }

    @Override
    public EntrySymbol visit(CompositeStatement compositeStatementNode, EntrySymbol optParam) {
        compositeStatementNode.getStatements().forEach(s -> s.accept(this, optParam));
        return null;
    }

    @Override
    public EntrySymbol visit(WhileStatement whileStatementNode, EntrySymbol optParam) {
        whileStatementNode.getBooleanExpression().accept(this, optParam);
        whileStatementNode.getWhileStatement().accept(this, optParam);
        return null;
    }

    @Override
    public EntrySymbol visit(IfThenStatement ifThenStatementNode, EntrySymbol optParam) {
        ifThenStatementNode.getIfCondition().accept(this,optParam);
        ifThenStatementNode.getThenStatement().accept(this,optParam);
        return null;
    }

    @Override
    public EntrySymbol visit(IfThenElseStatement ifThenElseStatementNode, EntrySymbol optParam) {
        ifThenElseStatementNode.getIfCondition().accept(this,optParam);
        ifThenElseStatementNode.getThenStatement().accept(this,optParam);
        ifThenElseStatementNode.getElseStatement().accept(this,optParam);
        return null;
    }

    @Override
    public EntrySymbol visit(BinaryExpression binaryExpressionNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(UminusExpression uminusExpressionNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(DoubleConst doubleConstNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(IntegerConst integerConstNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(StringConst stringConstNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(NotExpression notExpressionNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(TrueExpression trueExpressionNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(FalseExpression falseExpressionNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(RelationalExpression relationalExpressionNode, EntrySymbol optParam) {
        return null;
    }

    @Override
    public EntrySymbol visit(AssignStatement assignStatementNode, EntrySymbol optParam) {
        return null;
    }

    private void checkAlreadyDeclared(EntrySymbol variable) {
        if (stackOfTable.peek().containsKey(variable.getName())) {
            try {
                throw new VariableAlreadyDeclared("Already declared in these scope "
                        + variable.getLocX() + " " + variable.getLocY());
            } catch (VariableAlreadyDeclared variableAlreadyDeclared) {
                variableAlreadyDeclared.printStackTrace();
            }
        } else if (stackOfTable.firstElement().containsKey(variable.getName())) {
            try {
                throw new VariableAlreadyDeclared("Already declared in global scope "
                        + variable.getLocX() + " " + variable.getLocY());
            } catch (VariableAlreadyDeclared variableAlreadyDeclared) {
                variableAlreadyDeclared.printStackTrace();
            }
        } else {
            stackOfTable.peek().put(variable.getName(), variable);
        }
    }

}