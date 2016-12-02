/**************************************************
*  class used to hold information associated w/
*  Symbs (which are stored in SymbolTables)
*
****************************************************/
import java.util.ArrayList;

class FunctSymbol extends SymbolInfo {
    private ArrayList<SymbolInfo> params = new ArrayList<SymbolInfo>();
    private int numParams = 0;

    public FunctSymbol(String id, Kinds k, Types t, ArrayList<SymbolInfo> params){
        super(id, k, t);
        this.params = params;
        numParams = params.size();
    }

    public FunctSymbol(String id, int k, int t, ArrayList<SymbolInfo> params){
        super(id, k, t);
        this.params = params;
        numParams = params.size();
    }

    public ArrayList<SymbolInfo> getParams() {
        return params;
    }

    public void setParams(ArrayList<SymbolInfo> params) {
        this.params = params;
    }

    public int getNumOfParams() {
        return numParams;
    }
}
