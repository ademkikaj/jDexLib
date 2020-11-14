package scale;

import java.util.ArrayList;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexViewSettings;

public class TestScaleEditorCar {

	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	static ArrayList<DexScale> allScales = null;
	
	public static void main(String[] args) {
		
		try {
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[1];			
			System.out.println("Model Name: " + lModel.getName());			
			lModTreeView.setModel(lModel);					
			lModEditor.BeginEditing();
			
			try {
				lModEditor.EditModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);				
				String lModTreeViewString = lModTreeView.ToJsonString(lSettings);
				
				DexAttribute lPriceAtt = (DexAttribute) lModEditor.RefToObject("Att_PRICE");
				
				DexAttribute lNewAtt = new DexAttribute(null);
				lNewAtt.setName("New");
				lModEditor.AddInputTo(lPriceAtt, lNewAtt);
				lModTreeView.Rearrange();
				lModTreeViewString = lModTreeView.ToJsonString(lSettings);
				lModEditor.ViewToJson(lModTreeView, lSettings);
				System.out.println(lModTreeViewString);
				
				DexAttribute lNewAttByRef = (DexAttribute) lModEditor.RefToObject("Att_New");
				
				allScales = lModel.getRoot().AllScales(false);
				System.out.println("AllScales size: " + allScales.size());
				ArrayList<DexScale> allDiffAssScales = lNewAttByRef.AllAssignableScales(allScales);
				System.out.println(allDiffAssScales.size()); // this return 0			
				
			}catch (Exception e) {
				System.err.println(e);
			}
		}catch (Exception e) {
			System.err.println(e);
		}
	}

}
