import java.util.Arrays;

import DEX.DexModel;
import DEX.DexModelInfoView;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class DexTreeViewDemo {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexViewSettings lSetting = new DexViewSettings(true);
		DexModelInfoView lModInfoView = new DexModelInfoView(null);
		DexModelTreeView lModelTreeView = new DexModelTreeView(null);
		
		try {
			lEditor.BeginEditing();
			lEditor.NewProject("NewProject");
			
			// Load two DEXi models with same name and description but different model structure
			lEditor.AddDEXiModel("Car.dxi");
			lEditor.AddDEXiModel("CarDouble.dxi");
			
			// for registering the Ref
			String s = lEditor.ProjectViewJson(lSetting);
			System.out.println(s);
			
			
			DexModel lModel = lEditor.RefToModel("Mod_CAR");
			
			lModInfoView.setModel(lModel);
			
			System.out.println(lModInfoView.ToJsonString(lSetting));
			
			DexModel lModel1 = lEditor.RefToModel("Mod_CAR_1");
			lModInfoView = new DexModelInfoView(null);
			lModInfoView.setModel(lModel1);
			
			System.out.println(lModInfoView.ToJsonString(lSetting));
			lModelTreeView.setModel(lModel1);
			
			System.out.println(lModelTreeView.ToJsonString(lSetting));
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
