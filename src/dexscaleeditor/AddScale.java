package dexscaleeditor;

import DEX.DexAttribute;
import DEX.DexCategoricalScale;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexScaleEditor;
import DEX.DexViewSettings;

public class AddScale {
	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	static DexScaleEditor lScaleEditor = new DexScaleEditor(null);

	
	public static void main(String[] args) {
		
		lEditor.LoadProject("demo.dxp");
		lProject = lEditor.getProject();
		lModel = lProject.getModels()[1];

		System.out.println("Model Name: " + lModel.getName());
		
		lModTreeView.setModel(lModel);

		lModEditor.BeginEditing();

		try {
			lModEditor.EditModel(lModel);
			lModEditor.ViewToJson(lModTreeView, lSettings);

			lModTreeView.ToJsonString(lSettings);
			
			DexAttribute lAttrCar = (DexAttribute) lModEditor.RefToObject("Att_CAR");
			
			System.out.println(lAttrCar.getName());
			
			DexScale lCarScale = lAttrCar.getScale();
			
			lScaleEditor.EditScale(lCarScale);
			
			//Add Categorical Scale
//			DexCategoricalScale lCatScale = new DexCategoricalScale(null);
//			
//			lCatScale.setName("NewScale");
//			lCatScale.setDescription("New Description");
			System.out.println(lCarScale.ToJsonString(lSettings));
			
			lScaleEditor.AddCategory("New Scale", "New Description");
			System.out.println(lCarScale.ToJsonString(lSettings));
			
			lScaleEditor.DeleteCategory(3);
			System.out.println(lCarScale.ToJsonString(lSettings));
			
		}
		catch (Exception e) {
			System.err.println(e);
		}
		}
}
