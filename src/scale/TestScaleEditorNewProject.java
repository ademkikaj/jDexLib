package scale;

import java.util.ArrayList;

import DEX.DexAttribute;
import DEX.DexCategoricalScale;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexScaleEditor;
import DEX.DexScaleView;
import DEX.DexViewSettings;

public class TestScaleEditorNewProject {

	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	static DexScaleEditor lScaleEditor = new DexScaleEditor(null);
	static ArrayList<DexScale> allScales = null;
	
	public static void main(String[] args) {
		try {
			
			lEditor.NewProject("A");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[0];
			
			lModTreeView.setModel(lModel);
			
			try {
				lModEditor.EditModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);
				
				// GET ATTRIBUTE A
				DexAttribute lAttA = (DexAttribute) lModEditor.RefToObject("Att_A");
								
				// ==== Add A1 attribute with scale ====
				
				// CREATE NEW ATTRIBUTE
				DexAttribute lAttA1 = new DexAttribute(null);
				lAttA1.setName("A1");
				
				// ADD A1 to A
				lModEditor.AddInputTo(lAttA, lAttA1);
				refreshModelView();
				
				// GET A1 by Ref
				DexAttribute lAttA1ByRef = (DexAttribute)lModEditor.RefToObject("Att_A1");
				
				// ADD SCALE WITH TWO VALUE TO ATTRIBUTE A1
				DexCategoricalScale lCatScaleA1 = new DexCategoricalScale(null);
				lModEditor.SetScaleOf(lAttA1ByRef, lCatScaleA1);
				
				DexCategoricalScale lCatScaleOfA1 = (DexCategoricalScale) lAttA1ByRef.getScale();
				
				// Add Bad and Good Value
				lScaleEditor.EditScale(lCatScaleOfA1);
				lScaleEditor.AddCategory("Bad", "");
				lScaleEditor.AddCategory("Good", "");
				lScaleEditor.EndEditing();
				refreshModelView();
				
				// ==== Add A1 attribute with scale ====
				
				// ==== Add A2 attribute with scale ====
				
				// CREATE NEW ATTRIBUTE
				DexAttribute lAttA2 = new DexAttribute(null);
				lAttA2.setName("A2");
				
				// ADD A2 to A
				lModEditor.AddInputTo(lAttA, lAttA2);
				refreshModelView();
				
				// GET A2 by Ref
				DexAttribute lAttA2ByRef = (DexAttribute)lModEditor.RefToObject("Att_A2");
				
				// ADD SCALE WITH TWO VALUE TO ATTRIBUTE A2
				DexCategoricalScale lCatScaleA2 = new DexCategoricalScale(null);
				lModEditor.SetScaleOf(lAttA2ByRef, lCatScaleA2);
				
				DexCategoricalScale lCatScaleOfA2 = (DexCategoricalScale) lAttA2ByRef.getScale();
				
				// Add Bad and Good Value
				lScaleEditor.EditScale(lCatScaleOfA2);
				lScaleEditor.AddCategory("Low", "");
				lScaleEditor.AddCategory("High", "");
				lScaleEditor.EndEditing();
				refreshModelView();
				
				// ==== Add A2 attribute with scale ====
				
				// ==== Add A3 attribute and check all assiganble scales ====
				
				// CREATE NEW ATTRIBUTE
				DexAttribute lAttA3 = new DexAttribute(null);
				lAttA3.setName("A3");
				
				// ADD A3 to A
				lModEditor.AddInputTo(lAttA, lAttA3);
				refreshModelView();
				
				// GET A3 by Ref
				DexAttribute lAttA3ByRef = (DexAttribute)lModEditor.RefToObject("Att_A3");
				
				
				// Get All Assignable Scales
				
				allScales = lModel.getRoot().AllScales(false);
				ArrayList<DexScale> allDiffAssScales = lAttA3ByRef.AllDifferentAssignableScales(allScales);
				
				for (DexScale dexScale : allDiffAssScales) {
					DexScaleView lScaleView = new DexScaleView(null);
					lScaleView.setScale(dexScale);
					String s = lScaleView.ToJsonString(lModEditor.getRegistry(), lSettings);
					System.out.println(s);
				}
				
				// Get one Scale by Ref
				DexScale lScaleByRef = (DexScale) lModEditor.RefToObject("Scl_");
				
				// Set that Scale to Att A3 
				lModEditor.SetScaleOf(null, lScaleByRef);
				lModEditor.SetScaleOf(lAttA3ByRef, lScaleByRef);
				refreshModelView();
				System.out.println(lModTreeView.ToJsonString(lSettings));
				
				
				
				
			} catch (Exception e) {
				System.err.println(e);
			}
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	static void refreshModelView() {
		lModTreeView.Rearrange();
		lModEditor.ViewToJson(lModTreeView, lSettings);
	}

}
