import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexModelView;
import DEX.DexProject;
import DEX.DexProjectEditor;

public class LoadNewDexModel {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexProject lProject;
		DexModel lModel = new DexModel(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		
		lEditor.BeginEditing();
		try {
			lEditor.LoadProject("demo.dxp");
			lEditor.AddDEXiModel("IncreaseDAdosage.dxi");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[1];
			lModEditor.BeginEditing();
			try {
				lModEditor.EditModel(lModel);
				System.out.println(lModel.getName());
				
				DexModelView[] modelView = lModEditor.getModelViews();
				
				for (DexModelView dexModelView : modelView) 
					System.out.println(dexModelView instanceof DexModelTreeView);
				
				DexModel newModel = new DexModel(null);
				newModel.setName("New Model");
				
				lEditor.AddModel(newModel);
				
				lModel = lEditor.getProject().getModels()[2];
				
				System.out.println(lModel.getName());
				lModEditor.EndEditing();
				lModEditor.BeginEditing();
				lModEditor.EditModel(newModel);
				
				modelView = lModEditor.getModelViews();
				
				for (DexModelView dexModelView : modelView) 
					System.out.println(dexModelView instanceof DexModelTreeView);
				
				System.out.println(lModel.getRoot().getInpCount());
				
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
