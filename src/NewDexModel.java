import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexModelView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class NewDexModel {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexProject lProject;
		DexModel lModel = new DexModel(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		
		lEditor.BeginEditing();
		
		try {
			lEditor.NewProject("Project1");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[0];
			lModEditor.BeginEditing();
			try {
				lModEditor.EditModel(lModel);
				
				DexModelView[] modelView = lModEditor.getModelViews();
				System.out.println("Here");
				
				for (DexModelView dexModelView : modelView) {
					System.out.println(dexModelView.getClass());
					System.out.println(dexModelView instanceof DexModelTreeView);
				}
				
				DexModelTreeView lModTreeView = new DexModelTreeView(null);
				lModTreeView.setModel(lModel);
				lEditor.AddView(lModTreeView);
				System.out.println(lModTreeView.ToJsonString(new DexViewSettings(true)));
				modelView = lModEditor.getModelViews();
				for (DexModelView dexModelView : modelView) {
					System.out.println(dexModelView instanceof DexModelTreeView);
				}
				
				DexAttribute rootAttr = lModel.getRoot();
				System.out.println(rootAttr.getInput(0).getName() + " " +rootAttr.getInpCount());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			lModEditor.EndEditing();
			
			lEditor.AddModel("Model1");
			lModel = lProject.getModels()[1];
			System.out.println(lModel.getName());
			try {
				lModEditor.EditModel(lModel);
				DexModelView[] modelView = lModEditor.getModelViews();
				
				for (DexModelView dexModelView : modelView) {
					System.out.println(dexModelView.getClass());
					System.out.println(dexModelView instanceof DexModelTreeView);
				}
				DexAttribute rootAttr = lModel.getRoot();
				System.out.println(rootAttr.getInpCount());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
