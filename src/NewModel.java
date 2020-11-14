import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class NewModel {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexModel lModel = new DexModel(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		DexProject lProject = new DexProject(null);

		try {
			lEditor.NewProject("ProjectDemo");
			lProject = lEditor.getProject();
			System.out.println(lProject != null);
			
			System.out.println(lProject.getModelCount() == 1);

			lModel = lProject.getModels()[0];
			System.out.println(lModel.getName().equals("ProjectDemo"));
			
			DexModel lNewModel = new DexModel(null);
			lNewModel.setName("New Model");
			
			lEditor.AddModel(lNewModel);
			
			System.out.println(lProject.getModelCount() == 2);
			
			lModel = lProject.getModels()[1];
			
			System.out.println(lModel.getName().equals("New Model"));
			
			System.out.println(lModel.getRoot().getInpCount() == 1);
			
			lEditor.AddModel("New New Model");
			
			System.out.println(lProject.getModelCount() == 3);
			
			lModel = lProject.getModels()[2];
			
			System.out.println(lModel.getName().equals("New New Model"));
			
			System.out.println(lModel.getRoot().getInpCount() == 1);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
