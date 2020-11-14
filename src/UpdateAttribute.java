import DEX.DexAttribute;
import DEX.DexCompositeString;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeNode;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScaleStrings;
import DEX.DexView;
import DEX.DexViewSettings;
import Utils.Formatting;

public class UpdateAttribute {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexProject lProject;
		DexModel lModel = new DexModel(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		DexViewSettings lSettings = new DexViewSettings(true);
		DexModelTreeView lModTreeView = new DexModelTreeView(null);
		
		try {
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[1];
			
			lModEditor.BeginEditing();
			try {
				lModEditor.EditModel(lModel);
				
				DexView[] views = lProject.getViews();
				
				for (DexView dexView : views) {
					lModTreeView = (DexModelTreeView) dexView;
					break;
				}
							
				// This is needed to register the Ref
				lModEditor.ViewToJson(lModTreeView, lSettings);
				

				System.out.println(lModTreeView.getCurrentNode().getName());
								
				// This goes to the user
				// Here the attribute CAR has id Att_CAR
				String jsonRes = lModTreeView.ToJsonString(lSettings);
				
				System.out.println(jsonRes);
				// It can find in the DexModelEditor
//				DexAttribute lAttrCar = (DexAttribute) lModEditor.RefToObject("Att_CAR");
				DexAttribute lAttrCar = lModTreeView.getCurrentNode().getAttribute();
				// Rename it
				lModEditor.Rename(lAttrCar, "Renamed", lAttrCar.getDescription());
				
				// After rename give back to the user the treeview
				String newJSONRes = lModTreeView.ToJsonString(lSettings);
				System.out.println(newJSONRes);
				
				lModEditor.ViewToJson(lModTreeView, lSettings);
//				DexAttribute lAttrNewCar = (DexAttribute) lModEditor.RefToObject("Att_New_CAR");
				DexAttribute lAttrNewCar = lModTreeView.getCurrentNode().getAttribute();
				System.out.println(lAttrNewCar);
				System.out.println(lAttrNewCar.getName());
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
