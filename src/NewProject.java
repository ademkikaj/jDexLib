import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeNode;
import DEX.DexModelTreeView;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class NewProject {
	
	public static void main(String[] args) {
		
		DexProjectEditor lEditor = new DexProjectEditor(null);		
		DexViewSettings lSettings = new DexViewSettings(true);		
		DexModelEditor lModEditor = new DexModelEditor(null);
		
		lEditor.BeginEditing();
		try {			
	    	lEditor.NewProject("Demo");	    	
	    	lEditor.ProjectViewJson(lSettings);
	    	lEditor.AddDEXiModel("Car.dxi");
	    	
	    	DexModel lModel = lEditor.getProject().getModels()[1];
	    	
	    	lModEditor.BeginEditing();
	    	try {
				lModEditor.EditModel(lModel);
				
				DexModelTreeView lModTreeView = new DexModelTreeView(null);
				lModTreeView.setModel(lModel);
				lModTreeView.setName("DEXi");
				lEditor.AddView(lModTreeView);
				
				lModEditor.ViewToJson(lModTreeView, lSettings);
				
				System.out.println(lModTreeView.ToJsonString(lSettings));

				System.out.println(lModTreeView.getCurrentNode());
				
				DexAttribute attr = (DexAttribute) lModEditor.RefToObject("Att_COMFORT");
				System.out.println(attr.getName());
				
				DexModelTreeNode node = lModTreeView.getRoot().AttNode(attr);
				node.setIsExpanded(false);
				
				lModTreeView.setCurrentNode(node);
				
				System.out.println(lModTreeView.getCurrentNode().getName());
				
				DexModelTreeView lModTreeViewA = new DexModelTreeView(null);
				lModTreeViewA.setModel(lModel);
				lModTreeViewA.setName("Attributes");
				
				DexAttribute attrPrice = (DexAttribute) lModEditor.RefToObject("Att_PRICE");
				DexModelTreeNode nodePrice = lModTreeViewA.getRoot().AttNode(attrPrice);
				nodePrice.setIsExpanded(false);
				
				DexAttribute attrSafety = (DexAttribute) lModEditor.RefToObject("Att_SAFETY");
				DexModelTreeNode nodeSafety = lModTreeViewA.getRoot().AttNode(attrSafety);
				lModTreeViewA.setCurrentNode(nodeSafety);
				
				lEditor.AddView(lModTreeViewA);
				System.out.println(lModTreeViewA.ToJsonString(lSettings));
				lEditor.SaveToFile("Demo.dxp");
			} catch (Exception e) {
				// TODO: handle exception
			}	    	
		}
		finally {
			lEditor.EndEditing();
		}
	}

}
