/***************************************************************************
 * CSX Project: Program 3: Parser
 * @Authors:  Long Bui and Shane Hall           11/11/2016
 * FileName:  ast.java
 ***************************************************************************/
import java.util.ArrayList;
import java.util.Stack;

abstract class ASTNode {
// abstract superclass; only subclasses are actually created
	int linenum;
	int colnum;
    static int typeErrors = 0; // Total number of type errors found
    static Stack<Types> methodTypes = new Stack<Types>();
    public static SymbolTable st = new SymbolTable();

    static void mustBe(boolean assertion) {
		if (! assertion) {
			throw new RuntimeException();
		}
	} // mustBe

	static void typeMustBe(int testType, int requiredType, String errorMsg) {
		if ((testType != Types.Error) && (testType != requiredType)) {
			System.out.println(errorMsg);
			typeErrors++;
		}
	} // typeMustBe

    String error() {
	return "Error (line " + linenum + ", " + colnum + "): ";
    } // error

	static void genIndent(int indent) {
		for (int i = 1; i <= indent; i++) {
			System.out.print("\t");
		}
	} // genIndent

	ASTNode() {
		linenum = -1;
		colnum = -1;
	} // ASTNode()

	ASTNode(int line, int col) {
		linenum = line;
		colnum = col;
	} // ASTNode(line, col)

	boolean isNull() {
		return false; // often redefined in a subclass
	} // isNull()

	void Unparse(int indent) {
		// This routine is normally redefined in a subclass
	} // Unparse()

        void checkTypes() {
            // This will normally need to be redefined in a subclass
        }
} // class ASTNode

class nullNode extends ASTNode {
// This class definition probably doesn't need to be changed
	nullNode() {
		super();
	}

	boolean isNull() {
		return true;
	}

	void Unparse(int indent) {}
} // class nullNode

class csxLiteNode extends ASTNode {
// This node is used to root CSX lite programs

	csxLiteNode(stmtsNode stmts, int line, int col) {
		super(line, col);
		progStmts = stmts;
	} // csxLiteNode()

	void Unparse(int indent) {
		System.out.println(linenum + ":" + " {");
		progStmts.Unparse(1);
		System.out.println(linenum + ":" + " } EOF");
	} // Unparse()

        void checkTypes() {
		progStmts.checkTypes();
	} // checkTypes

	boolean isTypeCorrect() {
		st.openScope();
		checkTypes();
		return (typeErrors == 0);
	} // isTypeCorrect

	private final stmtsNode progStmts;
} // class csxLiteNode

class classNode extends ASTNode {

	classNode(identNode id, memberDeclsNode memb, int line, int col) {
		super(line, col);
		className = id;
		members = memb;
	} // classNode

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		System.out.print("class ");
		className.Unparse(0);
		System.out.println(" {");
		members.Unparse(indent+1);
		genIndent(indent);
		System.out.println("} EOF");
	} // Unparse()

	boolean isTypeCorrect() {
		st.openScope();
		checkTypes();
		return (typeErrors == 0);
	} // isTypeCorrect

        void checkTypes() {
        SymbolInfo id;
        id = (SymbolInfo) st.localLookup(className.idname);

        if (id == null) {
            id = new SymbolInfo(className.idname, new Kinds(Kinds.Other),
                                new Types(Types.Unknown));
            className.type = new Types(Types.Unknown);
            try {
				st.insert(id);
			} catch (DuplicateException d) {
				/* can't happen */
			} catch (EmptySTException e) {
				/* can't happen */
			}
            className.idinfo = id;
        } else {
            System.out.println(error() + id.name() + " is already declared.");
            typeErrors++;
            className.type = new Types(Types.Error);
        }

        className.checkTypes();
        members.checkTypes();
    } // checkTypes

	private final identNode className;
	private final memberDeclsNode members;
} // class classNode

class memberDeclsNode extends ASTNode {
	memberDeclsNode(fieldDeclsNode f, methodDeclsNode m, int line, int col) {
		super(line, col);
		fields = f;
		methods = m;
	}

	void Unparse(int indent) {
		fields.Unparse(indent);
		methods.Unparse(indent);
	} // Unparse()

    void checkTypes() {
        fields.checkTypes();
        methods.checkTypes();
        if(!methods.checkLastIsMain()) {
            System.out.println(error() + " last method must be void main().");
            typeErrors++;
        }
    } // checkTypes

	fieldDeclsNode fields;
	final methodDeclsNode methods;
} // class memberDeclsNode

class fieldDeclsNode extends ASTNode {
	fieldDeclsNode() {
		super();
	}
	fieldDeclsNode(declNode d, fieldDeclsNode f, int line, int col) {
		super(line, col);
		thisField = d;
		moreFields = f;
	}
	void Unparse(int indent) {
		thisField.Unparse(indent);
		moreFields.Unparse(indent);
	} // Unparse()
    void checkTypes() {
        thisField.checkTypes();
        moreFields.checkTypes();
    } // checkTypes
	static nullFieldDeclsNode NULL = new nullFieldDeclsNode();
	private declNode thisField;
	private fieldDeclsNode moreFields;
} // class fieldDeclsNode

class nullFieldDeclsNode extends fieldDeclsNode {
	nullFieldDeclsNode() {}
	boolean isNull() {
		return true;
	}
	void Unparse(int indent) {}
        void checkTypes() {}
} // class nullFieldDeclsNode

// abstract superclass; only subclasses are actually created
abstract class declNode extends ASTNode {
	declNode() {
		super();
	}
	declNode(int l, int c) {
		super(l, c);
	}
    void checkType() {}
} // class declNode

class varDeclNode extends declNode {
	varDeclNode(identNode id, typeNode t, exprNode e, int line, int col) {
		super(line, col);
		varName = id;
		varType = t;
		initValue = e;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		varType.Unparse(0);
		System.out.print(" ");
		varName.Unparse(0);
		if (!initValue.isNull()) {
			System.out.print(" = ");
			initValue.Unparse(0);
		}
		System.out.println(";");
	} // Unparse()

    void checkTypes() {
		SymbolInfo id;
		id = (SymbolInfo) st.localLookup(varName.idname);
		if (id == null) {
			id = new SymbolInfo(varName.idname, new Kinds(Kinds.Var), varType.type);
			varName.type = varType.type;
            try {
				st.insert(id);
			} catch (DuplicateException d) {
				/* can't happen */
			} catch (EmptySTException e) {
				/* can't happen */
			}
			varName.idinfo = id;
		} else {
			System.out.println(error() + id.name() + " is already declared.");
			typeErrors++;
			varName.type = new Types(Types.Error);
		} // id != null
	} // checkTypes

	private final identNode varName;
	private final typeNode varType;
	private final exprNode initValue;
} // class varDeclNode

class constDeclNode extends declNode {
	constDeclNode(identNode id, exprNode e, int line, int col) {
		super(line, col);
		constName = id;
		constValue = e;
	}
	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		System.out.print("const ");
		constName.Unparse(0);
		System.out.print(" = ");
		constValue.Unparse(0);
		System.out.println(";");
	} // Unparse()
    void checkTypes() {
        constValue.checkTypes();

        SymbolInfo id;
        id = (SymbolInfo) st.globalLookup(constName.idname);

        if (id == null) {
            id = new SymbolInfo(constName.idname,
                new Kinds(Kinds.Value), constValue.type);
            constName.type = constValue.type;
            constName.kind = new Kinds(Kinds.Value);

            try {
		st.insert(id);
            } catch (DuplicateException d) {
			/* can't happen */
            } catch (EmptySTException e) {
			/* can't happen */
            }
            constName.idinfo = id;
        } else {  // If in scope already -> throw error
            System.out.println(error() + id.name() + " is already declared.");
            typeErrors++;
            constName.type = new Types(Types.Error);
        } // id != null
    } // checkTypes
	private final identNode constName;
	private final exprNode constValue;
} // class constDeclNode

class arrayDeclNode extends declNode {
	arrayDeclNode(identNode id, typeNode t, intLitNode lit, int line, int col) {
		super(line, col);
		arrayName = id;
		elementType = t;
		arraySize = lit;
	}
	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		elementType.Unparse(0);
		System.out.print(" ");
		arrayName.Unparse(0);
		System.out.print("[");
		arraySize.Unparse(0);
		System.out.print("];");
	} // Unparse()

    void checkTypes() {
        SymbolInfo id;
        id = (SymbolInfo) st.localLookup(arrayName.idname);

        if (id == null) {
            id = new SymbolInfo(arrayName.idname,
                new Kinds(Kinds.Array),elementType.type);
            arrayName.type = elementType.type;
            try {
				st.insert(id);
			} catch (DuplicateException d) {
				/* can't happen */
			} catch (EmptySTException e) {
				/* can't happen */
			}
            arrayName.idinfo = id;
        } else {
            System.out.println(error() + id.name() + " is already declared.");
            typeErrors++;
            arrayName.type = new Types(Types.Error);
        } // id != null

        //Array size must be > 0
        if (arraySize.getVal() <= 0){
            typeErrors++;
            System.out.println(error() + arraySize.getVal()
                                + ": array size must be greater than 0;");
        }
    } // checkTypes
	private final identNode arrayName;
	private final typeNode elementType;
	private final intLitNode arraySize;
} // class arrayDeclNode

abstract class typeNode extends ASTNode {
// abstract superclass; only subclasses are actually created
    typeNode() {super();}
    typeNode(int l,int c, Types t) {
        super(l,c);
        type = t;
    }
    static nullTypeNode NULL = new nullTypeNode();
    Types type; // Used for typechecking -- the type of this typeNode
} // class typeNode

class nullTypeNode extends typeNode {
	nullTypeNode() {}
	boolean isNull() {
		return true;
	}
	void Unparse(int indent) {}
        void checkTypes() {}
} // class nullTypeNode

class intTypeNode extends typeNode {
	intTypeNode(int line, int col) {
		super(line, col, new Types(Types.Integer));
	}
	void Unparse(int indent) {
		genIndent(indent);
		System.out.print("int");
	} // Unparse()
} // class intTypeNode

class floatTypeNode extends typeNode {
	floatTypeNode(int line, int col) {
		super(line, col, new Types(Types.Real));
	}
	void Unparse(int indent) {
		genIndent(indent);
		System.out.print("float");
	} // Unparse()
} // class floatTypeNode

class boolTypeNode extends typeNode {
	boolTypeNode(int line, int col) {
		super(line, col, new Types(Types.Boolean));
	}
	void Unparse(int indent) {
		genIndent(indent);
		System.out.print("bool");
	} // Unparse()
} // class boolTypeNode

class charTypeNode extends typeNode {
	charTypeNode(int line, int col) {
		super(line, col, new Types(Types.Character));
	}
	void Unparse(int indent) {
		genIndent(indent);
		System.out.print("char");
	} // Unparse()
} // class charTypeNode

class voidTypeNode extends typeNode {
	voidTypeNode(int line, int col) {
		super(line, col, new Types(Types.Void));
	}
	void Unparse(int indent) {
		genIndent(indent);
		System.out.print("void");
	} // Unparse()
} // class voidTypeNode

class methodDeclsNode extends ASTNode {
	methodDeclsNode() {
		super();
	}
	methodDeclsNode(methodDeclNode m, methodDeclsNode ms,
			int line, int col) {
		super(line, col);
		thisDecl = m;
        moreDecls = ms;
	}

	void Unparse(int indent) {
		thisDecl.Unparse(indent);
		moreDecls.Unparse(indent);
	} // Unparse()
    void checkTypes() {
        thisDecl.checkTypes();
        if (!moreDecls.isNull()) {
            moreDecls.checkTypes();
        }
    } // checkTypes

    boolean checkLastIsMain() {
        if (!moreDecls.isNull()) {
            return moreDecls.checkLastIsMain();
        } else {
            return thisDecl.isMain();
        }
    }

	static nullMethodDeclsNode NULL = new nullMethodDeclsNode();
	private methodDeclNode thisDecl;
	private methodDeclsNode moreDecls;
} // class methodDeclsNode

class nullMethodDeclsNode extends methodDeclsNode {
	nullMethodDeclsNode() {}
	boolean   isNull() {return true;}
	void Unparse(int indent) {}
} // class nullMethodDeclsNode

class methodDeclNode extends ASTNode {
	methodDeclNode(identNode id, argDeclsNode a, typeNode t,
			fieldDeclsNode f, stmtsNode s, int line, int col) {
		super(line, col);
		name = id;
		args = a;
		returnType = t;
		decls = f;
		stmts = s;
	}
	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		returnType.Unparse(0);
		System.out.print(" ");
		name.Unparse(0);
		System.out.print("(");
		args.Unparse(0);
		System.out.println(") {");
		decls.Unparse(indent+1);
		stmts.Unparse(indent+1);
		genIndent(indent);
		System.out.println("}");
	} // Unparse()
    void checkTypes() {
        SymbolInfo id;
        id = (SymbolInfo) st.localLookup(name.idname);
        if (id != null) { // Check if already declared
            System.out.println(error() + id.name() + " is already declared.");
            typeErrors++;
            name.type = new Types(Types.Error);
        } else {
            /*
                Use next scope to store argument types and then reset the scope
                Add the method to the top scope
                Finish typechecking like normal
            */
            id = new SymbolInfo(name.idname, new Kinds(Kinds.Method), returnType.type);
            st.openScope();
            if (!args.isNull()) {
                args.checkTypes();
            }
            // Construct arraylist of args types
            ArrayList<SymbolInfo> paramTypes = new ArrayList<SymbolInfo>();
            argDeclsNode nodesLeft = args;
            while(!nodesLeft.isNull()){
                paramTypes.add(nodesLeft.getThisDecl().getParamInfo());
                nodesLeft = nodesLeft.getNextDecls();
            }

            //System.out.println("AFTER ADDING ARGS\n" + st);

            try{    // To add function to higher scope
                st.closeScope();
            } catch(EmptySTException e){
                System.err.println("Error: Empty scope");
                System.exit(-1);
            }

            // Insert the new symbol
            id = new FunctSymbol(name.idname,
                                new Kinds(Kinds.Method),
                                returnType.type,
                                paramTypes);
            name.type = returnType.type;
            try {
                st.insert(id);
                //System.out.println("Method declared: " + id);
            } catch (DuplicateException d) {
                /* can't happen */
            } catch (EmptySTException e) {
                /* can't happen */
            }
            name.idinfo = id;
        }

        //System.out.println("AFTER ADDING METHOD\n" + st);

        st.openScope();
        name.type = returnType.type;
        methodTypes.add(returnType.type);   // For return types
        if (!args.isNull()) {
            args.checkTypes();
        }
        decls.checkTypes();
        stmts.checkTypes();
        methodTypes.pop();
        //Close scope after branches are checked
        try{
            //System.out.println("AFTER ADDING GOING THROUGH METHOD\n" + st);
            st.closeScope();
        }catch(EmptySTException e){
            System.err.println("Error: Empty scope");
            System.exit(-1);
        }
    } // checkTypes

    public boolean isMain() {
        return (name.idname.toLowerCase().equals("main") && args.isNull()
                && returnType.type.val == Types.Void);
    }

	private final identNode name;
	private final argDeclsNode args;
	private final typeNode returnType;
	private final fieldDeclsNode decls;
	private final stmtsNode stmts;
} // class methodDeclNode

// abstract superclass; only subclasses are actually created
abstract class argDeclNode extends ASTNode {
	argDeclNode() {
		super();
	}
	argDeclNode(int l, int c) {
		super(l, c);
	}
    public SymbolInfo getParamInfo() {
        return null;
    }
}

class argDeclsNode extends ASTNode {
	argDeclsNode() {}
	argDeclsNode(argDeclNode arg, argDeclsNode args,
			int line, int col) {
		super(line, col);
		thisDecl = arg;
		moreDecls = args;
	}
	static nullArgDeclsNode NULL = new nullArgDeclsNode();

	void Unparse(int indent) {
		thisDecl.Unparse(0);
        if (!moreDecls.isNull()) {
            System.out.print(", ");
            moreDecls.Unparse(0);
        }
	} // Unparse()

    public void checkTypes() {
        thisDecl.checkTypes();
        if (!moreDecls.isNull()) {
            moreDecls.checkTypes();
        }
    }

    public argDeclNode getThisDecl() {
        return thisDecl;
    }

    public argDeclsNode getNextDecls() {
        return moreDecls;
    }

	private argDeclNode thisDecl;
	private argDeclsNode moreDecls;
} // class argDeclsNode

class nullArgDeclsNode extends argDeclsNode {
	nullArgDeclsNode() {}
	boolean   isNull() {return true;}
	void Unparse(int indent) {}

} // class nullArgDeclsNode

class arrayArgDeclNode extends argDeclNode {
	arrayArgDeclNode(identNode id, typeNode t, int line, int col) {
		super(line, col);
		argName = id;
		elementType = t;
	}

	void Unparse(int indent) {
		genIndent(indent);
		elementType.Unparse(0);
		System.out.print(" ");
		argName.Unparse(0);
		System.out.print("[]");
	} // Unparse()

    public void checkTypes(){
        SymbolInfo id;
        id = (SymbolInfo) st.localLookup(argName.idname);
        if (id == null) {
            id = new SymbolInfo(argName.idname, new Kinds(Kinds.Var),
                                elementType.type);
            argName.type = elementType.type;
            try {
				st.insert(id);
			} catch (DuplicateException d) {
				/* can't happen */
			} catch (EmptySTException e) {
				/* can't happen */
			}
            argName.idinfo = id;
        } else {
            System.out.println(error() + id.name() + " is already a parameter.");
            typeErrors++;
            argName.type = new Types(Types.Error);
        } // id != null
    }

    public SymbolInfo getParamInfo() {
        return new SymbolInfo("", new Kinds(Kinds.Array_Parameter), argName.idinfo.type);
    }

	private final identNode argName;
	private final typeNode elementType;
} // class arrayArgDeclNode

class valArgDeclNode extends argDeclNode {
	valArgDeclNode(identNode id, typeNode t, int line, int col) {
		super(line, col);
		argName = id;
		argType = t;
	}

	void Unparse(int indent) {
		genIndent(indent);
		argType.Unparse(0);
		System.out.print(" ");
		argName.Unparse(0);
	} // Unparse()

    public void checkTypes(){
        SymbolInfo id;
        id = (SymbolInfo) st.localLookup(argName.idname);

        if (id == null) {
            id = new SymbolInfo(argName.idname, new Kinds(Kinds.Var),
                                argType.type);
            argName.type = argType.type;
            try {
				st.insert(id);
			} catch (DuplicateException d) {
				/* can't happen */
			} catch (EmptySTException e) {
				/* can't happen */
			}
            argName.idinfo = id;
        } else {
            System.out.println(error() + id.name() + " is already declared.");
            typeErrors++;
            argName.type = new Types(Types.Error);
        } // id != null
    }

    public SymbolInfo getParamInfo() {
        return new SymbolInfo("", new Kinds(Kinds.Scalar_Parameter) , argName.idinfo.type);
    }

	private final identNode argName;
	private final typeNode argType;
} // class valArgDeclNode

// abstract superclass; only subclasses are actually created
abstract class stmtNode extends ASTNode {
	stmtNode() {
		super();
	}
	stmtNode(int l, int c) {
		super(l, c);
	}
	static nullStmtNode NULL = new nullStmtNode();
}

class nullStmtNode extends stmtNode {
	nullStmtNode() {}
	boolean   isNull() {return true;}
	void Unparse(int indent) {}
} // class nullStmtNode

class stmtsNode extends ASTNode {
	stmtsNode(stmtNode stmt, stmtsNode stmts, int line, int col) {
		super(line, col);
		thisStmt = stmt;
		moreStmts = stmts;
	}
	stmtsNode() {}

	void Unparse(int indent) {
		thisStmt.Unparse(indent);
		moreStmts.Unparse(indent);
	}

    void checkTypes() {
        thisStmt.checkTypes();
        if(!moreStmts.isNull()){
            moreStmts.checkTypes();
        }
    }

	static nullStmtsNode NULL = new nullStmtsNode();
	private stmtNode thisStmt;
	private stmtsNode moreStmts;
} // class stmtsNode

class nullStmtsNode extends stmtsNode {
	nullStmtsNode() {}
	boolean   isNull() {return true;}
	void Unparse(int indent) {}

} // class nullStmtsNode

class asgNode extends stmtNode {
	asgNode(nameNode n, exprNode e, int line, int col) {
		super(line, col);
		target = n;
		source = e;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		target.Unparse(0);
		System.out.print(" = ");
		source.Unparse(0);
		System.out.println(";");
	}

    void checkTypes(){
        target.checkTypes();
        source.checkTypes();

        if (target.kind.val == Kinds.Value){
            typeErrors++;
            System.out.println(error() + "Illegal assignment: "+ target.varName.idname
                                + " is a constant.");
        }

        typeMustBe(source.type.val, target.type.val,
                    error() + "Illegal assignment: Type mismatch "
                    + source.type + " to " + target.type);
    }

    private final nameNode target;
    private final exprNode source;
} // class asgNode

class ifThenNode extends stmtNode {
	ifThenNode(exprNode e, stmtsNode s1, stmtsNode s2, int line, int col) {
		super(line, col);
		condition = e;
		thenPart = s1;
		elsePart = s2;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		System.out.print("if (");
		condition.Unparse(0);
		System.out.println(") {");
		thenPart.Unparse(indent+1);
		if (!elsePart.isNull()) {
			genIndent(indent);
			System.out.println("} else {");
			elsePart.Unparse(indent+1);
		}
		genIndent(indent);
		System.out.println ("}");
	}

    void checkTypes(){
        condition.checkTypes();
        typeMustBe(condition.type.val, Types.Boolean, error() + "The conditional expression must be boolean.");
        try {
            st.openScope();
            if(!thenPart.isNull()){
                thenPart.checkTypes();
            }
            st.closeScope();
            st.openScope();
            if(!elsePart.isNull()){
                elsePart.checkTypes();
            }
            st.closeScope();
        } catch(EmptySTException e) {
            System.out.println("Closing scope of empty symbol table.");
        }
    }

	private final exprNode condition;
	private final stmtsNode thenPart;
	private final stmtsNode elsePart;
} // class ifThenNode

class whileNode extends stmtNode {
	whileNode(identNode i, exprNode e, stmtNode s, int line, int col) {
		super(line, col);
	 label = i;
	 condition = e;
	 loopBody = s;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		if(!label.isNull()) {
			label.Unparse(0);
			System.out.print(": ");
		}
		System.out.print("while (");
		condition.Unparse(0);
		System.out.println(") {");
		loopBody.Unparse(indent+1);
		genIndent(indent);
		System.out.println ("}");
	}

    void checkTypes() {
        condition.checkTypes();
        typeMustBe(condition.type.val, Types.Boolean, error() +
            "The conditional expression must be boolean.");
        st.openScope();
        if(!label.isNull()){
            SymbolInfo id;
            id = (SymbolInfo) st.localLookup(label.idname);
            if (id == null) {
                id = new SymbolInfo(label.idname, new Kinds(Kinds.Label), new Types(Types.Void));
                label.type = new Types(Types.Void);
                try {
    				st.insert(id);
    			} catch (DuplicateException d) {
    				/* can't happen */
    			} catch (EmptySTException e) {
    				/* can't happen */
    			}
                label.idinfo = id;
            } else {
                System.out.println(error() + id.name() + " is already declared.");
                typeErrors++;
                label.type = new Types(Types.Error);
            } // id != null
        }

        if(!loopBody.isNull()){
            loopBody.checkTypes();
        }

        try {
            st.closeScope();
        } catch (EmptySTException e) {
            System.out.println("Closing scope of empty symbol table.");
        }
    }

	private final identNode label;
	private final exprNode condition;
	private final stmtNode loopBody;
} // class whileNode

class readNode extends stmtNode {
    readNode() {}
    readNode(nameNode n, readNode rn, int line, int col) {
    super(line, col);
    targetVar = n;
    moreReads = rn;
}

    void Unparse(int indent) {
    	System.out.print(linenum + ":");
	genIndent(indent);
	System.out.print("read(");
	targetVar.Unparse(0);

        // Print out the whole read list
        readNode child = moreReads;
        while (!child.isNull()) {
            System.out.print(", ");
            child.getTargetVar().Unparse(0);
            child = child.getReadList();
        }
	System.out.println(");");
    }

    void checkTypes() {
        targetVar.checkTypes();
        if (!moreReads.isNull()) {
            moreReads.checkTypes();
        }

        switch(targetVar.type.val){
            case Types.Integer:
            case Types.Real:
                //Do nothing!
                break;
            default:
                System.out.println(error() + "Can only read integer or float values");
                typeErrors++;
                break;
        }
    }

    public readNode getReadList() {
	return moreReads;
    }
    public nameNode getTargetVar() {
	return targetVar;
    }

    static nullReadNode NULL = new nullReadNode();
    private nameNode targetVar;
    private readNode moreReads;
} // class readNode

class nullReadNode extends readNode {
	nullReadNode() {}
	boolean   isNull() {return true;}
	void Unparse(int indent) {}
} // class nullReadNode

class printNode extends stmtNode {
	printNode() {}
	printNode(exprNode val, printNode pn, int line, int col) {
		super(line, col);
		outputValue = val;
		morePrints = pn;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		System.out.print("print(");
		outputValue.Unparse(0);

        // Print out the whole print list
		printNode child = morePrints;
		while (!child.isNull()) {
			System.out.print(", ");
			child.getValue().Unparse(0);
            child = child.getPrintList();
		}

		System.out.println(");");
	}

    void checkTypes() {
        outputValue.checkTypes();

        if (outputValue.kind.val != Kinds.Array) {
            if (outputValue.kind.val != Kinds.Value) { // Note sure if correct
                System.out.println(error() + " Can only print values");
                typeErrors++;
            }
            if (outputValue.type.val != Types.Integer
                    && outputValue.type.val != Types.Boolean
                    && outputValue.type.val != Types.Real
                    && outputValue.type.val != Types.Character
                    && outputValue.type.val != Types.String) {
                System.out.println(error() + " Current type is not printable.");
                typeErrors++;
            }
        } else {
            if(outputValue.type.val != Types.Character) {
                System.out.println(error() + " Current array type is not printable.");
                typeErrors++;
            }
        }
        if (!morePrints.isNull()){
            morePrints.checkTypes();
        }
    }

	public printNode getPrintList() {
		return morePrints;
	}
	public exprNode getValue() {
		return outputValue;
	}

	static nullPrintNode NULL = new nullPrintNode();
	private exprNode outputValue;
	private printNode morePrints;
} // class printNode

class nullPrintNode extends printNode {
	nullPrintNode() {}
	boolean   isNull() {return true;}
	void Unparse(int indent) {}
} // class nullprintNode

class callNode extends stmtNode {
	callNode(identNode id, argsNode a, int line, int col) {
		super(line, col);
		methodName = id;
		args = a;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		methodName.Unparse(0);
		System.out.print("(");
		args.Unparse(0);
		System.out.println(");");
	}

    void checkTypes() {
        methodName.checkTypes();

        // NEEDS TO CHECK IF CORRECT PARAMETERS
        if (!args.isNull()) {
            args.checkTypes();
        }

        SymbolInfo id;
        id = (SymbolInfo) st.globalLookup(methodName.idname);
        //System.out.println("Function call: " + id);
        //System.out.println(st.toString());
        if (id != null) {
            if (methodName.type.val != Types.Void){
                System.out.println(error() + methodName.idname +
                        " is not Void.");
                typeErrors++;
            }
            if (id.kind.val == Kinds.Method) {
                ArrayList<SymbolInfo> paramTypes = ((FunctSymbol)id).getParams();

                // Get list of arguement types
                ArrayList<SymbolInfo> argTypes = new ArrayList<SymbolInfo>();
                argsNode nodesLeft = args;
                while(!nodesLeft.isNull()){
                    argTypes.add(nodesLeft.getArgInfo());
                    nodesLeft = nodesLeft.getNextArgs();
                }

                //System.out.println("\nPARAMS: " + paramTypes);
                //System.out.println("ARGS: " + argTypes);

                // Compare lengths
                if (paramTypes.size() == argTypes.size()) {
                    // Compare types
                    /* For each element of paramTyes and argTypes
                    // If paramType.type != ArgType.type, error
                    // If paramType.kind == Array_Parameter,
                        if argType.kind != Kinds.Array_Parameter || argType.kind != Kinds.Array_Parameter, error
                    // else
                        if argType.kind != Var, Val, Scalar_Parameter, error
                    */
                    for (int i = 0; i < paramTypes.size(); ++i) {
                        if (argTypes.get(i).type.val == paramTypes.get(i).type.val ) {
                            Kinds argKindVal = argTypes.get(i).kind;
                            if (paramTypes.get(i).kind.val == Kinds.Scalar_Parameter) {
                                if (argKindVal.val != Kinds.Var && argKindVal.val != Kinds.Value
                                        && argKindVal.val != Kinds.Scalar_Parameter) {
                                    System.out.println(error() + "Parameter " + (i+1) + " is not a scalar. " + argKindVal + " was given.");
                                    typeErrors++;
                                }
                            } else {
                                if (argKindVal.val != Kinds.Array && argKindVal.val != Kinds.Array_Parameter) {
                                    System.out.println(error() + "Parameter " + (i+1) + " is not an array. " + argKindVal + " was given.");
                                    typeErrors++;
                                }
                            }
                        } else {
                            System.out.println(error() + "Parameter " + (i+1) + " should be "
                                + paramTypes.get(i).type + ". " + argTypes.get(i).type + " was given.");
                            typeErrors++;
                        }
                    }
                } else {
                    System.out.println(error() + methodName.idname + " requires " + paramTypes.size() +
                        " parameters. " + argTypes.size() + " supplied.");
                    typeErrors++;
                }
            }
        } else {
            System.out.println(error() + methodName.idname + " is not declared.");
            typeErrors++;
            methodName.type = new Types(Types.Error);
        }
    }

	private final identNode methodName;
	private final argsNode args;
} // class callNode

class returnNode extends stmtNode {
	returnNode(exprNode e, int line, int col) {
		super(line, col);
		returnVal = e;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		if (returnVal == exprNode.NULL)
            System.out.print("return");
        else
            System.out.print("return ");
		returnVal.Unparse(0);
		System.out.println(";");
	}

    void checkTypes(){
        returnVal.checkTypes();
        if (methodTypes.size() > 0) {
            if (methodTypes.peek().val != Types.Void) {
                if (methodTypes.peek().val != returnVal.type.val) {
                    System.out.println(error() + "Return type of " + methodTypes.peek() + " expected. "
                        + returnVal.type + " was supplied.");
                    typeErrors++;
                }
            } else {
                System.out.println(error() + "Return statement in void method.");
                typeErrors++;
            }
        } else {
            System.out.println(error() + "Return not in method.");
            typeErrors++;
        }

    }

	private final exprNode returnVal;
} // class returnNode

class blockNode extends stmtNode {
	blockNode(fieldDeclsNode f, stmtsNode s, int line, int col) {
		super(line, col);
		decls = f;
		stmts = s;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		System.out.println("{");
		decls.Unparse(indent+1);
		stmts.Unparse(indent+1);
		genIndent(indent);
		System.out.println("}");
	}

        void checkTypes(){
            st.openScope();
            decls.checkTypes();
            stmts.checkTypes();

            try{
                st.closeScope();
            }catch(EmptySTException e){
                System.err.println("Empty scope");
                System.exit(-1);
            }

        }

	private final fieldDeclsNode decls;
	private final stmtsNode stmts;
} // class blockNode

class breakNode extends stmtNode {
	breakNode(identNode i, int line, int col) {
		super(line, col);
		label = i;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		System.out.print("break ");
		label.Unparse(0);
		System.out.println(";");
	}

    void checkTypes(){
        SymbolInfo id;
        id = (SymbolInfo) st.globalLookup(label.idname);

        if (id == null) {
            System.out.println(error() + label.idname + " is not declared.");
            typeErrors++;
        } else {
            if (id.kind.val != Kinds.Label) {
                System.out.println(error() + label.idname + " is not a label.");
                typeErrors++;
            }
        }
    }


    private final identNode label;
} // class breakNode

class continueNode extends stmtNode {
	continueNode(identNode i, int line, int col) {
		super(line, col);
		label = i;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		System.out.print("continue ");
		label.Unparse(0);
		System.out.println(";");
	}

    void checkTypes(){
        SymbolInfo id;
        id = (SymbolInfo) st.globalLookup(label.idname);

        if (id == null) {
            System.out.println(error() + label.idname + " is not declared.");
            typeErrors++;
        } else {
            if (id.kind.val != Kinds.Label) {
                System.out.println(error() + label.idname + " is not a label.");
                typeErrors++;
            }
        }
    }

	private final identNode label;
} // class continueNode

class argsNode extends ASTNode {
	argsNode() {}
	argsNode(exprNode e, argsNode a, int line, int col) {
		super(line, col);
		argVal = e;
		moreArgs = a;
	}

	void Unparse(int indent) {
		genIndent(indent);
		argVal.Unparse(0);
		if(!moreArgs.isNull()) {
			System.out.print(", ");
			moreArgs.Unparse(0);
		}
	}

    void checkTypes(){
        argVal.checkTypes();
        if (!moreArgs.isNull()) {
            moreArgs.checkTypes();
        }
    }

    public SymbolInfo getArgInfo() {
        return new SymbolInfo("", argVal.kind ,argVal.type);
    }

    public argsNode getNextArgs() {
        return moreArgs;
    }

	static nullArgsNode NULL = new nullArgsNode();
	private exprNode argVal;
	private argsNode moreArgs;
} // class argsNode

class nullArgsNode extends argsNode {
	nullArgsNode() {
		// empty constructor
	}
	boolean   isNull() {return true;}
	void Unparse(int indent) {}
} // class nullArgsNode

class strLitNode extends exprNode {
	strLitNode(String stringval, int line, int col) {
		super(line, col);
		strval = stringval;
	}

	void Unparse(int indent) {
		genIndent(indent);
		System.out.print(strval);
	}

        void checkTypes(){}

	private final String strval;
} // class strLitNode

// abstract superclass; only subclasses are actually created
abstract class exprNode extends ASTNode {
	exprNode() {
		super();
	}
	exprNode(int l, int c) {
		super(l, c);
        type = new Types();
        kind = new Kinds();
	}
    exprNode(int l,int c, Types t, Kinds k) {
		super(l,c);
		type = t;
		kind = k;
	} // exprNode

    static nullExprNode NULL = new nullExprNode();
    protected Types type; // Used for typechecking: the type of this node
    protected Kinds kind; // Used for typechecking: the kind of this node
}

class nullExprNode extends exprNode {
	nullExprNode() {
		super();
	}
	boolean   isNull() {return true;}
	void Unparse(int indent) {}
        void checkTypes(){}
} // class nullExprNode

class binaryOpNode extends exprNode {
    binaryOpNode(exprNode e1, int op, exprNode e2, int line, int col, Types type) {
	super(line, col, type, new Kinds(Kinds.Value));
	operatorCode = op;
	leftOperand = e1;
	rightOperand = e2;
    }

    static void printOp(int op) {
	switch (op) {
            case sym.PLUS:
                System.out.print(" + ");
                break;
            case sym.MINUS:
		System.out.print(" - ");
		break;
            case sym.LT:
            	System.out.print(" < ");
		break;
            case sym.LEQ:
		System.out.print(" <= ");
		break;
            case sym.GT:
		System.out.print(" > ");
		break;
            case sym.GEQ:
		System.out.print(" >= ");
		break;
            case sym.EQ:
		System.out.print(" == ");
		break;
            case sym.NOTEQ:
		System.out.print(" != ");
		break;
            case sym.COR:
		System.out.print(" || ");
		break;
            case sym.CAND:
		System.out.print(" && ");
		break;
            case sym.TIMES:
		System.out.print(" * ");
		break;
            case sym.SLASH:
		System.out.print(" / ");
		break;
            default:
		throw new Error("printOp: case not found");
	}
    }

    void Unparse(int indent) {
	genIndent(indent);
	System.out.print("(");
	leftOperand.Unparse(0);
	printOp(operatorCode);
	rightOperand.Unparse(0);
	System.out.print(")");
    }

    void checkTypes(){
        //Check types of each side.
        leftOperand.checkTypes();
        rightOperand.checkTypes();
        type = leftOperand.type;

        //Get op code to begin process.
        switch(operatorCode){
            case sym.PLUS:
            case sym.MINUS:
            case sym.SLASH:
            case sym.TIMES:
                type = leftOperand.type;
                switch(leftOperand.type.val){
                    case Types.Character:
                    case Types.Integer:
                        switch(rightOperand.type.val){
                            case Types.Character:
                            case Types.Integer:
                                //Sets type to correct kind.
                                if (rightOperand.type.val == Types.Integer){
                                    type = rightOperand.type;
                                }
                                break;
                            default:
                                typeMustBe(Types.Error, 0, error() +
                                    "Both operands must be Integer or Character.");
                                type = new Types(Types.Integer);
                                break;
                        }
                        break;
                    case Types.Real:
                        typeMustBe(leftOperand.type.val, rightOperand.type.val,
                            error() + "Both left and right operands must be Floats.");
                            type = new Types(Types.Integer);
                        break;
                    default:
                        typeMustBe(Types.Error, 1 , error() +
                            "Left operand must be an int, float, or char.");
                            type = new Types(Types.Integer);
                        break;
                }
                break;
            case sym.EQ:
            case sym.NOTEQ:
            case sym.GEQ:
            case sym.GT:
            case sym.LEQ:
            case sym.LT:
                type = new Types(Types.Boolean);
                switch(leftOperand.type.val){
                    case Types.Integer:
                    case Types.Character:
                    case Types.Real:
                        switch(rightOperand.type.val){
                            case Types.Integer:
                            case Types.Character:
                            case Types.Real:
                                break;
                            default:
                                typeMustBe(leftOperand.type.val, rightOperand.type.val, error() +
                                        "Right operand must be an int, float, or char.");
                                break;
                        }
                        break;
                    case Types.Boolean:
                        typeMustBe(Types.Boolean, rightOperand.type.val, error() +
                                        "Right operand must be an Boolean.");
                        break;
                    default:
                        typeMustBe(Types.Error, 1, error() +
                                "Left operand must be an int, float, char, or boolean.");
                        break;
                }
                break;
            case sym.CAND:
            case sym.COR:
                type = new Types(Types.Boolean);

                if ((leftOperand.type.val == Types.Boolean)
                        && (rightOperand.type.val == Types.Boolean)){
                    //Do nothing
                } else {
                    System.out.println(error() + "Left and right operands must be Boolean.");
                    typeErrors++;
                }
                break;
            default:
                //Nothing wrong!
        }
    }

	private final exprNode leftOperand;
	private final exprNode rightOperand;
	private final int operatorCode; // Token code of the operator
} // class binaryOpNode

class unaryOpNode extends exprNode {
	unaryOpNode(int op, exprNode e, int line, int col, Types type) {
		super(line, col, type, new Kinds(Kinds.Value));
		operand = e;
		operatorCode = op;
	}

	static void printOp(int op) {
		switch (op) {
			case sym.NOT:
				System.out.print("!");
				break;
			case -1:
				break;
			default:
				throw new Error("printOp: case not found");
		}
	}

	void Unparse(int indent) {
		genIndent(indent);
		printOp(operatorCode);
		operand.Unparse(0);
	}

        void checkTypes(){
            operand.checkTypes();

            if (operatorCode == sym.NOT){
                typeMustBe(operand.type.val, Types.Boolean, error()
                        + "Operand must be a Boolean");
                type = new Types(Types.Boolean);
            } else {
                type = operand.type;
                kind = operand.kind;
            }
        }

	private final exprNode operand;
	private final int operatorCode; // Token code of the operator
} // class unaryOpNode

class castNode extends exprNode {
	castNode(typeNode t, exprNode e, int line, int col) {
		super(line, col);
		operand = e;
		resultType = t;
	}

	void Unparse(int indent) {
		genIndent(indent);
		System.out.print("(");
		resultType.Unparse(0);
		System.out.print(")");
		operand.Unparse(0);
	}

    void checkTypes() {
        operand.checkTypes();
        if(operand.type.val != Types.Integer
                && operand.type.val != Types.Character
                && operand.type.val != Types.Real) {
            System.out.println(error() + "Type-cast operand must be an int, char, or bool.");
            typeErrors++;
        }
        if(resultType.type.val  != Types.Integer
                && resultType.type.val  != Types.Character
                && resultType.type.val  != Types.Real
                && resultType.type.val  != Types.Boolean) {
            System.out.println(error() + "Can only Type-cast to int, char, float, or bool.");
            typeErrors++;
        }
    }

	private final exprNode operand;
	private final typeNode resultType;
} // class castNode

class fctCallNode extends exprNode {
	fctCallNode(identNode id, argsNode a, int line, int col) {
		super(line, col);
		methodName = id;
		args = a;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		methodName.Unparse(0);
		System.out.print("(");
		args.Unparse(0);
		System.out.println(");");
	}

    void checkTypes() {
        methodName.checkTypes();
        type = methodName.type;
        kind = new Kinds(Kinds.Value);

        // NEEDS TO CHECK IF CORRECT PARAMETERS
        if (!args.isNull()) {
            args.checkTypes();
        }

        SymbolInfo id;
        id = (SymbolInfo) st.globalLookup(methodName.idname);
        //System.out.println("Function call: " + id);
        //System.out.println(st.toString());
        if (id != null) {
            if (id.kind.val == Kinds.Method) {
                ArrayList<SymbolInfo> paramTypes = ((FunctSymbol)id).getParams();

                // Get list of arguement types
                ArrayList<SymbolInfo> argTypes = new ArrayList<SymbolInfo>();
                argsNode nodesLeft = args;
                while(!nodesLeft.isNull()){
                    argTypes.add(nodesLeft.getArgInfo());
                    nodesLeft = nodesLeft.getNextArgs();
                }

                //System.out.println("\nPARAMS: " + paramTypes);
                //System.out.println("ARGS: " + argTypes);

                // Compare lengths
                if (paramTypes.size() == argTypes.size()) {
                    // Compare types
                    /* For each element of paramTyes and argTypes
                    // If paramType.type != ArgType.type, error
                    // If paramType.kind == Array_Parameter,
                        if argType.kind != Kinds.Array_Parameter || argType.kind != Kinds.Array_Parameter, error
                    // else
                        if argType.kind != Var, Val, Scalar_Parameter, error
                    */
                    for (int i = 0; i < paramTypes.size(); ++i) {
                        if (argTypes.get(i).type.val == paramTypes.get(i).type.val ) {
                            Kinds argKindVal = argTypes.get(i).kind;
                            if (paramTypes.get(i).kind.val == Kinds.Scalar_Parameter) {
                                if (argKindVal.val != Kinds.Var && argKindVal.val != Kinds.Value
                                        && argKindVal.val != Kinds.Scalar_Parameter) {
                                    System.out.println(error() + "Parameter " + (i+1) + " is not a scalar. " + argKindVal + " was given.");
                                    typeErrors++;
                                }
                            } else {
                                if (argKindVal.val != Kinds.Array && argKindVal.val != Kinds.Array_Parameter) {
                                    System.out.println(error() + "Parameter " + (i+1) + " is not an array. " + argKindVal + " was given.");
                                    typeErrors++;
                                }
                            }
                        } else {
                            System.out.println(error() + "Parameter " + (i+1) + " should be "
                                + paramTypes.get(i).type + ". " + argTypes.get(i).type + " was given.");
                            typeErrors++;
                        }
                    }
                } else {
                    System.out.println(error() + methodName.idname + " requires " + paramTypes.size() +
                        " parameters. " + argTypes.size() + " supplied.");
                    typeErrors++;
                }
            }
        } else {
            System.out.println(error() + methodName.idname + " is not declared.");
            typeErrors++;
            methodName.type = new Types(Types.Error);
        }
    }

	private final identNode methodName;
	private final argsNode args;
} // class callNode


class identNode extends exprNode {
	identNode(String identname, int line, int col) {
                super(line, col, new Types(Types.Unknown), new Kinds(Kinds.Var));
            idname   = identname;
            nullFlag = false;
	}

    identNode(boolean flag) {
        super(0,0,new Types(Types.Unknown), new Kinds(Kinds.Var));
        idname = "";
        nullFlag = flag;
    } // identNode

    boolean isNull() {return nullFlag;} // Is this node null?

    static identNode NULL = new identNode(true);

	void Unparse(int indent) {
		genIndent(indent);
		System.out.print(idname);
	}

    void checkTypes() {
		SymbolInfo id;
		mustBe(kind.val != Kinds.Other);
		id = (SymbolInfo) st.globalLookup(idname);
        if (id == null) {
            System.out.println(error() + idname + " is not declared.");
			typeErrors++;
			type = new Types(Types.Error);
		} else {
        	type = id.type;
            kind = id.kind;
			idinfo = id; // Save ptr to correct symbol table entry
		} // id != null
	} // checkTypes

	public final String idname;
    public SymbolInfo idinfo; // symbol table entry for this ident
	private final boolean nullFlag;
} // class identNode

class nameNode extends exprNode {
	nameNode(identNode id, exprNode expr, int line, int col) {
		super(line, col, new Types(Types.Unknown), new Kinds(Kinds.Var));
		varName = id;
		subscriptVal = expr;
	}

	void Unparse(int indent) {
		genIndent(indent);
		varName.Unparse(0);
		if(!subscriptVal.isNull()) {
			System.out.print("[");
			subscriptVal.Unparse(0);
			System.out.print("]");
		}
	}

    void checkTypes(){
        varName.checkTypes();
        if (subscriptVal.isNull()) {
            kind = varName.kind;
            type = varName.type;
            //System.out.println("FinalType: " + type.val);
        } else {
            // Must be an array with int subscript
            subscriptVal.checkTypes();

            if(varName.kind.val != Kinds.Array) {
                System.out.println(error() + varName.idname + " is not an array.");
                typeErrors++;
            }
            if(subscriptVal.kind.val != Kinds.Value
                    && subscriptVal.kind.val != Kinds.Var
                    && subscriptVal.kind.val != Kinds.Scalar_Parameter){
                System.out.println(error() + " subscript must be a scalar.");
                typeErrors++;
            }
            if(subscriptVal.type.val != Types.Integer
                    && subscriptVal.type.val != Types.Character) {
                System.out.println(error() + " Subscript must be an integer");
                typeErrors++;
            }
            type = varName.type;
            kind = new Kinds(Kinds.Var);
        }
    }

	protected final identNode varName;
	private final exprNode subscriptVal;
} // class nameNode

class intLitNode extends exprNode {
	intLitNode(int val, int line, int col) {
		super(line, col, new Types(Types.Integer),
                        new Kinds(Kinds.Value));
		intval = val;
	}

	void Unparse(int indent) {
		genIndent(indent);
		System.out.print(intval);
	}

        int getVal(){
            return intval;
        }

        void typeCheck(){}

        private final int intval;
} // class intLitNode

class floatLitNode extends exprNode {
	floatLitNode(float val, int line, int col) {
		super(line, col, new Types(Types.Real),
                        new Kinds(Kinds.Value));
		floatval = val;
	}

	void Unparse(int indent) {
		genIndent(indent);
		System.out.print(floatval);
	}

        void typeCheck(){}

	private final float floatval;
} // class floatLitNode

class charLitNode extends exprNode {
	charLitNode(char val, int line, int col) {
		super(line, col, new Types(Types.Unknown),
                        new Kinds(Kinds.Value));
		charval = val;
	}

	void Unparse(int indent) {
		genIndent(indent);
		System.out.print(charval);
	}

        int getVal(){
            return charval;
        }

        void typeCheck(){}

	private final char charval;
} // class charLitNode

class trueNode extends exprNode {
    trueNode(int line, int col) {
	super(line, col);
        type = new Types(Types.Boolean);
        kind = new Kinds(Kinds.Value);
    }

    void Unparse(int indent) {
        genIndent(indent);
	System.out.print("true");
    }

    void checkTypes() {}

} // class trueNode

class falseNode extends exprNode {
    falseNode(int line, int col) {
	super(line, col);
        type = new Types(Types.Boolean);
        kind = new Kinds(Kinds.Value);
    }

    void Unparse(int indent) {
	genIndent(indent);
	System.out.print("false");
    }
} // class falseNode

/**************************************************************************
***********************NEW AUXILIARY CLASSES ******************************
**************************************************************************/
//Created to hold optional semicolons
class semicolonNode extends exprNode {
	semicolonNode() {}
	semicolonNode(int line, int col) {
		super(line, col);
        }

	void Unparse(int indent) {
		genIndent(indent);
		System.out.print(";");
	}

        void typeCheck() {}

	static nullSemicolonNode NULL = new nullSemicolonNode();
} // class semicolonNode

class nullSemicolonNode extends semicolonNode {
	nullSemicolonNode() {}
	boolean isNull() {
		return true;
	}
        void Unparse(int indent) {}
        void typeCheck() {}
} // class nullSemicolonNode

//Node for preincrement statement.
class preIncrementNode extends stmtNode {
    preIncrementNode(identNode i, int line, int col) {
	super(line, col);
	idName = i;
    }

    void Unparse(int indent) {
	System.out.print(linenum + ":");
	genIndent(indent);
	System.out.print("++");
	idName.Unparse(0);
	System.out.println(";");
    }

    void checkTypes(){
        SymbolInfo id;
        id = (SymbolInfo) st.globalLookup(idName.idname);

        if (id == null) {
            System.out.println(error() + idName.idname + " is not declared.");
            typeErrors++;
        } else {
            idName.checkTypes();
            switch(idName.type.val){
                case Types.Integer:
                case Types.Real:
                    break;
                default:
                    System.out.println(error() + "Variable: " + idName.idname
                            + " has to be an Integer or Float.");
                    typeErrors++;
                    break;
            }
        }
    }

    private final identNode idName;

} // class preIncrementNode

//Node for postincrement statement.
class postIncrementNode extends stmtNode {
    postIncrementNode(identNode i, int line, int col) {
	super(line, col);
	idName = i;
    }

    void Unparse(int indent) {
	System.out.print(linenum + ":");
	genIndent(indent);
	idName.Unparse(0);
	System.out.println("++;");
    }

    void checkTypes(){
        SymbolInfo id;
        id = (SymbolInfo) st.globalLookup(idName.idname);

        if (id == null) {
            System.out.println(error() + idName.idname + " is not declared.");
            typeErrors++;
        } else {
            idName.checkTypes();
            switch(idName.type.val){
                case Types.Integer:
                case Types.Real:
                    break;
                default:
                    System.out.println(error() + "Variable: " + idName.idname
                            + " has to be an Integer or Float.");
                    typeErrors++;
            }
        }
    }

    private final identNode idName;

} // class postIncrementNode

//Node for predecrement statement.
class preDecrementNode extends stmtNode {
    preDecrementNode(identNode i, int line, int col) {
	super(line, col);
	idName = i;
    }

    void Unparse(int indent) {
	System.out.print(linenum + ":");
	genIndent(indent);
        System.out.print("--");
	idName.Unparse(0);
	System.out.println(";");
    }

    void checkTypes(){
        SymbolInfo id;
        id = (SymbolInfo) st.globalLookup(idName.idname);

        if (id == null) {
            System.out.println(error() + idName.idname + " is not declared.");
            typeErrors++;
        } else {
            idName.checkTypes();
            switch(idName.type.val){
                case Types.Integer:
                case Types.Real:
                    break;
                default:
                    System.out.println(error() + "Variable: " + idName.idname
                            + " has to be an Integer or Float.");
                    typeErrors++;
            }
        }
    }

    private final identNode idName;

} // class preDecrementNode

//Node for post decrement statement.
class postDecrementNode extends stmtNode {
    postDecrementNode(identNode i, int line, int col) {
	super(line, col);
	idName = i;
    }

    void Unparse(int indent) {
	System.out.print(linenum + ":");
	genIndent(indent);
	idName.Unparse(0);
        System.out.println("--;");
    }

    void checkTypes(){
        SymbolInfo id;
        id = (SymbolInfo) st.globalLookup(idName.idname);

        if (id == null) {
            System.out.println(error() + idName.idname + " is not declared.");
            typeErrors++;
        } else {
            idName.checkTypes();
            switch(idName.type.val){
                case Types.Integer:
                case Types.Real:
                    break;
                default:
                    System.out.println(error() + "Variable: " + idName.idname
                            + " has to be an Integer or Float.");
                    typeErrors++;
            }
        }
    }

    private final identNode idName;

} // class postDecrementNode

//Node to hold conditional expressions.
class condExprNode extends exprNode {
	condExprNode(exprNode e1, exprNode  e2, exprNode  e3, exprNode  e4, int line, int col) {
		super(line, col);
		condition1 = e1;
		condition2 = e2;
		condition3 = e3;
                condition4 = e4;
	}

	void Unparse(int indent) {
		genIndent(indent);
		System.out.print("(");
		condition1.Unparse(0);
		System.out.print(" ? ");
		condition2.Unparse(0);
		System.out.print(" -: ");
		condition3.Unparse(0);
		System.out.print(" +: ");
		condition4.Unparse(0);
		System.out.print(")");
	}

        void checkTypes(){
            condition1.checkTypes();
            condition2.checkTypes();
            condition3.checkTypes();
            condition4.checkTypes();
            type = condition2.type;

            typeMustBe(Types.Integer, condition1.type.val, error() +
                    "The first expression must be an Integer.");

            if (condition2.type.val == condition3.type.val && condition3.type.val == condition4.type.val){
                //Types are correct, do nothing!
            } else {
                System.out.println(error() + "Condition 2, 3, and 4 has to be the same type.");
                typeErrors++;
            }
        }

	private final exprNode condition1;
	private final exprNode condition2;
	private final exprNode condition3;
	private final exprNode condition4;
} // class condExprNode

//This class is needed for IF statements using the Conditional Expression
class ifCondExprNode extends stmtNode {
	ifCondExprNode(exprNode e, stmtsNode s1, stmtsNode s2, int line, int col) {
		super(line, col);
		condition = e;
		thenPart = s1;
		elsePart = s2;
	}

	void Unparse(int indent) {
		System.out.print(linenum + ":");
		genIndent(indent);
		System.out.print("if ");
		condition.Unparse(0);
		System.out.println(" {");
		thenPart.Unparse(indent+1);
		if (!elsePart.isNull()) {
			genIndent(indent);
			System.out.println("} else {");
			elsePart.Unparse(indent+1);
		}
		genIndent(indent);
		System.out.println ("}");
	}

        void checkTypes(){
            condition.checkTypes();

            if (!thenPart.isNull()) {
                thenPart.checkTypes();
            }
            if (!elsePart.isNull()) {
                elsePart.checkTypes();
            }

            typeMustBe(condition.type.val, Types.Boolean, error()
                + "Return value of the Conditional Expression must be a Boolean.");
        }

	private final exprNode condition;
	private final stmtsNode thenPart;
	private final stmtsNode elsePart;
} // class ifCondExprNode

//This class is needed to use Conditional Expressions with while statements.
class whileCondExprNode extends stmtNode {
    whileCondExprNode(identNode i, exprNode e, stmtNode s, int line, int col) {
	super(line, col);
	label = i;
	condition = e;
	loopBody = s;
    }

    void Unparse(int indent) {
	System.out.print(linenum + ":");
	genIndent(indent);

	if(!label.isNull()) {
            label.Unparse(0);
            System.out.print(": ");
	}

        System.out.print("while ");
        condition.Unparse(0);
        System.out.println(" {");
        loopBody.Unparse(indent+1);
        genIndent(indent);
        System.out.println ("}");
    }

    void checkTypes(){
        condition.checkTypes();

        typeMustBe(condition.type.val, Types.Boolean, error()
            + "Return value of the Conditional Expression must be a Boolean.");
        st.openScope();
        if(!label.isNull()){
            SymbolInfo id;
            id = (SymbolInfo) st.localLookup(label.idname);
            if (id == null) {
                id = new SymbolInfo(label.idname, new Kinds(Kinds.Label), new Types(Types.Void));
                label.type = new Types(Types.Void);
                try {
    				st.insert(id);
    			} catch (DuplicateException d) {
    				/* can't happen */
    			} catch (EmptySTException e) {
    				/* can't happen */
    			}
                label.idinfo = id;
            } else {
                System.out.println(error() + id.name() + " is already declared.");
                typeErrors++;
                label.type = new Types(Types.Error);
            } // id != null
        }

        if(!loopBody.isNull()){
            loopBody.checkTypes();
        }

        try {
            st.closeScope();
        } catch (EmptySTException e) {
            System.out.println("Closing scope of empty symbol table.");
        }
    }

	private final identNode label;
	private final exprNode condition;
	private final stmtNode loopBody;
} // class whileCondExprNode
