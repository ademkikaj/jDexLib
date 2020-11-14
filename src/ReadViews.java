import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexModelView;
import DEX.DexProject;
import DEX.DexProjectEditor;

public class ReadViews {

	public static void main(String[] args) {
		
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexProject lProject;
		DexModel lModel = new DexModel(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		
		
		lEditor.BeginEditing();
		
		try {
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			
			lModel = lProject.getModels()[0];
			
			lModEditor.BeginEditing();
			try {
				lModEditor.EditModel(lModel);
				System.out.println(lModel.getUniqueID());
				
				DexModelView[] modelView = lModEditor.getModelViews();
				
				for (DexModelView dexModelView : modelView) {
					System.out.println(dexModelView.getClass());
					System.out.println(dexModelView instanceof DexModelTreeView);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		} catch (Exception e) {
			
		}
	}
}
