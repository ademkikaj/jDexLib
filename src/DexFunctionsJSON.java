import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeNode;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class DexFunctionsJSON {
	
	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexModel lModel = new DexModel(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		DexProject lProject = new DexProject(null);
		DexViewSettings lSettings = new DexViewSettings(true);
		DexModelTreeView lModTreeView = new DexModelTreeView(null);
		
		try {
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[1];
			System.out.println(lModel.getName());
			
			lModEditor.EditModel(lModel);
			
			lModTreeView.setModel(lModel);
			
			lModEditor.ViewToJson(lModTreeView, lSettings);
			
			DexAttribute lCarAtt = (DexAttribute) lModEditor.RefToObject("Att_CAR");
			System.out.println(lCarAtt.getName());
			
			DexModelTreeNode lCarNode = (DexModelTreeNode) lModEditor.RefToObject("Node_CAR");
			
			System.out.println(lCarNode.ToJsonString(lSettings));
			
			System.out.println(lCarNode.getFunctionXml());
			System.out.println(lCarAtt.getFunct().getDisplayString());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

}
