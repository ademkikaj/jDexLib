import com.sun.corba.se.impl.orbutil.graph.Node;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeNode;
import DEX.DexModelTreeView;
import DEX.DexModelView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class CurrentNode {

	public static void main(String[] args) {
		
		DexProjectEditor lEditor = new DexProjectEditor(null);
		
		DexViewSettings lSetting = new DexViewSettings(true);
		
		DexModelTreeView lModelTreeView = new DexModelTreeView(null);
		
		DexModelEditor lModEditor = new DexModelEditor(null);
	
		try {
			lEditor.BeginEditing();
//			lEditor.NewProject("Project");
			lEditor.LoadProject("DemoSali.dxp");
//			lEditor.SaveToFile("DemoSali.dxp");
//			lEditor.AddDEXiModel("CarDouble.dxi");
			
//			lEditor.getProject().DeleteModel(0);
			
			
			DexModel lModel = lEditor.getProject().getModels()[0];
			

			DexModelView[] modelViews = lEditor.getProject().ViewsOfModel(lModel);
			
			for (DexModelView dexModelView : modelViews) {
				System.out.println(dexModelView.getClassName());
			}
			
			lModEditor.BeginEditing();
			lModEditor.EditModel(lModel);			
			
			lModelTreeView.setModel(lModel);
			
			System.out.println(lModEditor.ViewToJson(lModelTreeView, lSetting));
			
			
			DexAttribute lAttr = (DexAttribute) lModEditor.RefToObject("Att_CAR");
			System.err.println(lAttr);
			
			lEditor.AddView(lModelTreeView);
			
//			System.out.println(lAttr.getName());
			
			lEditor.Save();
				
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
