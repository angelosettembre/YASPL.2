package visitors;

import Files.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class SyntaxVisitor implements Visitor<Element, Void> {
    private Document xmlDocument;

    public SyntaxVisitor() {
        super();
    }

    @Override
    public Element visit(Program programNode, Void optionalParameter) {
        Element program = xmlDocument.createElement("program");
        program.setAttribute("author", "d.tropeano");
        programNode.ownChildren().forEach(e -> program.appendChild(e.accept(this, optionalParameter)));
        return program;
    }

    @Override
    public Element visit(VariableDeclaration variableDeclarationNode, Void optionalParameter) {
        Element variables = xmlDocument.createElement("variable");
        variableDeclarationNode.ownChildren().forEach(e -> variables.appendChild(e.accept(this, optionalParameter)));
        return variables;
    }

    @Override
    public Element visit(FunctionDeclaration functionDeclarationNode, Void optionalParameter) {
        Element function = xmlDocument.createElement("function");
        functionDeclarationNode.ownChildren()
                .forEach(e -> function.appendChild(e.accept(this, optionalParameter)));
        return function;
    }

    @Override
    public Element visit(Variable variableNode, Void optionalParameter) {
        Element var = xmlDocument.createElement("variable");
        variableNode.ownChildren()
                .forEach(e -> var.appendChild(e.accept(this, optionalParameter)));
        return var;
    }

    @Override
    public Element visit(BooleanType booleanTypeNode, Void optionalParameter) {
        Element bool = xmlDocument.createElement("type");
        bool.setNodeValue("bool");
        booleanTypeNode.ownChildren()
                .forEach(e -> bool.appendChild(e.accept(this, optionalParameter)));
        return bool;
    }

    @Override
    public Element visit(IntegerType integerTypeNode, Void optionalParameter) {
        Element integer = xmlDocument.createElement("type");
        integer.setNodeValue("int");
        integerTypeNode.ownChildren().forEach(e -> integer.appendChild(e.accept(this, optionalParameter)));
        return integer;
    }

    @Override
    public Element visit(StringType stringTypeNode, Void optionalParameter) {
        Element string = xmlDocument.createElement("type");
        string.setNodeValue("string");
        stringTypeNode.ownChildren().forEach(e -> string.appendChild(e.accept(this, optionalParameter)));
        return string;
    }

    @Override
    public Element visit(DoubleType doubleTypeNode, Void optionalParameter) {
        Element doublet = xmlDocument.createElement("type");
        doublet.setNodeValue("double");
        doubleTypeNode.ownChildren().forEach(e -> doublet.appendChild(e.accept(this, optionalParameter)));
        return doublet;
    }

    @Override
    public Element visit(Identifier identifierNode, Void optionalParameter) {
        Element id = xmlDocument.createElement("identifier");
        identifierNode.ownChildren()
                .forEach(e -> id.appendChild(e.accept(this, optionalParameter)));
        return id;
    }

    @Override
    public Element visit(ParameterDeclaration parameterDeclarationNode, Void optionalParameter) {
        Element par = xmlDocument.createElement("parameter");
        parameterDeclarationNode.ownChildren()
                .forEach(e -> par.appendChild(e.accept(this, optionalParameter)));
        return par;
    }

    @Override
    public Element visit(Body bodyNode, Void optionalParameter) {
        Element body = xmlDocument.createElement("body");
        bodyNode.ownChildren()
                .forEach(e -> body.appendChild(e.accept(this, optionalParameter)));
        return body;
    }

    @Override
    public Element visit(ReadStatement readStatementNode, Void optionalParameter) {
        Element read = xmlDocument.createElement("readStatement");
        readStatementNode.ownChildren()
                .forEach(e -> read.appendChild(e.accept(this, optionalParameter)));
        return read;
    }

    @Override
    public Element visit(WriteStatement writeStatementNode, Void optionalParameter) {
        Element write = xmlDocument.createElement("writeStatement");
        writeStatementNode.ownChildren()
                .forEach(e -> write.appendChild(e.accept(this, optionalParameter)));
        return write;
    }

    @Override
    public Element visit(FunctionCall functionCallNode, Void optionalParameter) {
        Element funcall = xmlDocument.createElement("funCall");
        functionCallNode.ownChildren()
                .forEach(e -> funcall.appendChild(e.accept(this, optionalParameter)));
        return funcall;
    }

    @Override
    public Element visit(CompositeStatement compositeStatementNode, Void optionalParameter) {
        Element comp = xmlDocument.createElement("compositeStatement");
        compositeStatementNode.ownChildren()
                .forEach(e -> comp.appendChild(e.accept(this, optionalParameter)));
        return comp;
    }

    @Override
    public Element visit(WhileStatement whileStatementNode, Void optionalParameter) {
        Element wh = xmlDocument.createElement("while");
        whileStatementNode.ownChildren()
                .forEach(e -> wh.appendChild(e.accept(this, optionalParameter)));
        return wh;
    }

    @Override
    public Element visit(IfThenStatement ifThenStatementNode, Void optionalParameter) {
        Element ift = xmlDocument.createElement("if");
        ifThenStatementNode.ownChildren()
                .forEach(e -> ift.appendChild(e.accept(this, optionalParameter)));
        return ift;
    }

    @Override
    public Element visit(IfThenElseStatement ifThenElseStatementNode, Void optionalParameter) {
        Element ifelse = xmlDocument.createElement("ifelse");
        ifThenElseStatementNode.ownChildren()
                .forEach(e -> ifelse.appendChild(e.accept(this, optionalParameter)));
        return ifelse;
    }

    @Override
    public Element visit(SumExpression sumExpressionNode, Void optionalParameter) {
        Element sum = xmlDocument.createElement("binaryExpression");
        sum.setAttribute("type", "sum");
        sumExpressionNode.ownChildren()
                .forEach(e -> sum.appendChild(e.accept(this, optionalParameter)));
        return sum;
    }

    @Override
    public Element visit(DifferenceExpression differenceExpressionNode, Void optionalParameter) {
        Element diff = xmlDocument.createElement("binaryExpression");
        diff.setAttribute("type", "difference");
        differenceExpressionNode.ownChildren()
                .forEach(e -> diff.appendChild(e.accept(this, optionalParameter)));
        return diff;
    }

    @Override
    public Element visit(ProductExpression productExpressionNode, Void optionalParameter) {
        Element prod = xmlDocument.createElement("binaryExpression");
        prod.setAttribute("type", "product");
        productExpressionNode.ownChildren()
                .forEach(e -> prod.appendChild(e.accept(this, optionalParameter)));
        return prod;
    }

    @Override
    public Element visit(DivideExpression divideExpressionNode, Void optionalParameter) {
        Element div = xmlDocument.createElement("binaryExpression");
        div.setAttribute("type", "divide");
        divideExpressionNode.ownChildren()
                .forEach(e -> div.appendChild(e.accept(this, optionalParameter)));
        return div;
    }

    @Override
    public Element visit(UminusExpression uminusExpressionNode, Void optionalParameter) {
        Element unary = xmlDocument.createElement("unaryExpression");
        uminusExpressionNode.ownChildren()
                .forEach(e -> unary.appendChild(e.accept(this, optionalParameter)));
        return unary;
    }

    @Override
    public Element visit(DoubleConst doubleConstNode, Void optionalParameter) {
        Element dc = xmlDocument.createElement("literal");
        dc.setAttribute("type", "double");
        doubleConstNode.ownChildren()
                .forEach(e -> dc.appendChild(e.accept(this, optionalParameter)));
        return dc;
    }

    @Override
    public Element visit(IntegerConst integerConstNode, Void optionalParameter) {
        Element ic = xmlDocument.createElement("literal");
        ic.setAttribute("type", "integer");
        integerConstNode.ownChildren()
                .forEach(e -> ic.appendChild(e.accept(this, optionalParameter)));
        return ic;
    }

    @Override
    public Element visit(StringConst stringConstNode, Void optionalParameter) {
        Element ic = xmlDocument.createElement("literal");
        ic.setAttribute("type", "string");
        stringConstNode.ownChildren()
                .forEach(e -> ic.appendChild(e.accept(this, optionalParameter)));
        return ic;
    }

    @Override
    public Element visit(AndExpression andExpressionNode, Void optionalParameter) {
        Element relop = xmlDocument.createElement("relationalExpression");
        relop.setAttribute("type", "and");
        andExpressionNode.ownChildren()
                .forEach(e -> relop.appendChild(e.accept(this, optionalParameter)));
        return relop;
    }

    @Override
    public Element visit(OrExpression orExpressionNode, Void optionalParameter) {
        Element relop = xmlDocument.createElement("relationalExpression");
        relop.setAttribute("type", "or");
        orExpressionNode.ownChildren()
                .forEach(e -> relop.appendChild(e.accept(this, optionalParameter)));
        return relop;
    }

    @Override
    public Element visit(NotExpression notExpressionNode, Void optionalParameter) {
        Element relop = xmlDocument.createElement("relationalExpression");
        relop.setAttribute("type", "not");
        notExpressionNode.ownChildren()
                .forEach(e -> relop.appendChild(e.accept(this, optionalParameter)));
        return relop;
    }

    @Override
    public Element visit(TrueExpression trueExpressionNode, Void optionalParameter) {
        Element trueExpr = xmlDocument.createElement("boolLiteral");
        trueExpr.setAttribute("value", "true");
        trueExpressionNode.ownChildren()
                .forEach(e -> trueExpr.appendChild(e.accept(this, optionalParameter)));
        return trueExpr;
    }

    @Override
    public Element visit(FalseExpression falseExpressionNode, Void optionalParameter) {
        Element falseExpr = xmlDocument.createElement("boolLiteral");
        falseExpr.setAttribute("value", "false");
        falseExpressionNode.ownChildren().forEach(e -> falseExpr.appendChild(e.accept(this, optionalParameter)));
        return falseExpr;
    }

    @Override
    public Element visit(GreaterThanExpression greatThanExpressionNode, Void optionalParameter) {
        Element relop = xmlDocument.createElement("relationalExpression");
        relop.setAttribute("type", "greatThan");
        greatThanExpressionNode.ownChildren()
                .forEach(e -> relop.appendChild(e.accept(this, optionalParameter)));
        return relop;
    }

    @Override
    public Element visit(GreaterThanEqualExpression greatThanEqualExpression, Void optionalParameter) {
        Element relop = xmlDocument.createElement("relationalExpression");
        relop.setAttribute("type", "greatThanEqual");
        greatThanEqualExpression.ownChildren()
                .forEach(e -> relop.appendChild(e.accept(this, optionalParameter)));
        return relop;
    }

    @Override
    public Element visit(LessThanExpression lessThanExpression, Void optionalParameter) {
        Element relop = xmlDocument.createElement("relationalExpression");
        relop.setAttribute("type", "lessThan");
        lessThanExpression.ownChildren()
                .forEach(e -> relop.appendChild(e.accept(this, optionalParameter)));
        return relop;
    }

    @Override
    public Element visit(LessThanEqualExpression lessThanEqualExpressionNode, Void optionalParameter) {
        Element relop = xmlDocument.createElement("relationalExpression");
        relop.setAttribute("type", "lessThanEqual");
        lessThanEqualExpressionNode.ownChildren()
                .forEach(e -> relop.appendChild(e.accept(this, optionalParameter)));
        return relop;
    }

    @Override
    public Element visit(EqualsExpression equalsExpressionNode, Void optionalParameter) {
        Element relop = xmlDocument.createElement("relationalExpression");
        relop.setAttribute("type", "equals");
        equalsExpressionNode.ownChildren()
                .forEach(e -> relop.appendChild(e.accept(this, optionalParameter)));
        return relop;
    }

    @Override
    public Element visit(AssignStatement assignStatementNode, Void optionalParameter) {
        Element assign = xmlDocument.createElement("assign");
        assign.setAttribute("type", "and");
        assignStatementNode.ownChildren()
                .forEach(e -> assign.appendChild(e.accept(this, optionalParameter)));
        return assign;
    }

    public Document createDocument() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            this.xmlDocument = docBuilder.newDocument();
            return this.xmlDocument;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void toXml() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(this.xmlDocument);
            StreamResult result = new StreamResult(new File(System.getProperty("user.home").concat("\\output.xml")));
            transformer.transform(source, result);
            System.out.println("File saved!");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    /* */
}