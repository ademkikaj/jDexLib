package web;

import java.util.ArrayList;

import DEX.DexAttribute;
import DEX.DexCategoricalScale;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexScaleView;
import DEX.DexViewSettings;

public class SetScale {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		DexProject lProject = new DexProject(null);
		DexModel lModel = new DexModel(null);
		DexViewSettings lSettings = new DexViewSettings(true);
		DexModelTreeView lModTreeView = new DexModelTreeView(null);
		DexAttribute lAtt = null;
		
		try {
			lEditor.BeginEditing();
			lEditor.LoadProject("demo.dxp");
			lEditor.ProjectViewJson(lSettings);
			lProject = lEditor.getProject();			
			lModel = lEditor.RefToModel("Mod_CAR");
			
			try {
				lModEditor.BeginEditing();
				lModEditor.EditModel(lModel);
				lModTreeView.setModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);
				lAtt = (DexAttribute) lModEditor.RefToObject("Att_CAR");
				
				DexAttribute lNewAtt = new DexAttribute(null);
				lNewAtt.setScale(new DexCategoricalScale(null));
				
				lAtt.AddInput(lNewAtt);
				
				ArrayList<DexScale> lAllScales = lModel.getRoot().AllScales(false);
				ArrayList<DexScale> lAllDiffAssignableScales = lAtt.AllDifferentAssignableScales(lAllScales);
				System.out.println(lAllDiffAssignableScales.size());
				
				for (DexScale dexScale : lAllDiffAssignableScales) {
					DexScaleView lScaleView = new DexScaleView(null);
					lScaleView.setScale(dexScale);
					System.out.println(lScaleView.ToJsonString(lModEditor.getRegistry(), lSettings));
				}
				
				
				System.out.println(lAtt.getScale().ToJsonString(lSettings));
				lModEditor.SetScaleOf(lAtt, lAllDiffAssignableScales.get(1));
				
				lAllDiffAssignableScales = lAtt.AllDifferentAssignableScales(lAllScales);
				System.out.println(lAllDiffAssignableScales.size());
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}

}
