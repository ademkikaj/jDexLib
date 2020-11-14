import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class MoveAttributes {

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
			
			lModEditor.BeginEditing();
			
			try {
				lModEditor.EditModel(lModel);
				lModTreeView.setModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);
				System.out.println(lModTreeView.ToJsonString(lSettings));
				
				DexAttribute lPrice = (DexAttribute) lModEditor.RefToObject("Att_PRICE");
				DexAttribute lTechChar = (DexAttribute) lModEditor.RefToObject("Att_TECH_CHAR_");
				
				System.out.println(lModEditor.CanMoveInputOfNext(lPrice.getParent(), lPrice));
				System.out.println(lModEditor.CanMoveInputOfPrior(lPrice.getParent(), lPrice));
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}

}
